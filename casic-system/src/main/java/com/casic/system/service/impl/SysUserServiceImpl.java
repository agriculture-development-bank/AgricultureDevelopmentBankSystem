package com.casic.system.service.impl;

import com.casic.common.annotation.DataScope;
import com.casic.common.base.AjaxResult;
import com.casic.common.config.Global;
import com.casic.common.constant.UserConstants;
import com.casic.common.utils.DateUtils;
import com.casic.common.utils.StringUtils;
import com.casic.common.utils.UuidUtils;
import com.casic.common.utils.poi.ExcelUtil;
import com.casic.system.domain.*;
import com.casic.system.mapper.*;
import com.casic.system.service.ISysDictDataService;
import com.casic.system.service.ISysUserService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 用户 业务层处理
 *
 * @author yuzengwen
 */
@Service
public class SysUserServiceImpl implements ISysUserService {

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysPostMapper postMapper;

    @Autowired
    private SysUserPostMapper userPostMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Autowired
    private ISysDictDataService dictService;

    @Autowired
    private SysRoleMapper sysRoleMapper;


    /**
     * 根据条件分页查询用户对象
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(tableAlias = "u")
    public List<SastindSysUserVo> selectUserList(SysUser user) {
        List<SastindSysUserVo> list = userMapper.selectUserList(user);
        for (SastindSysUserVo sastindSysUserVo : list) {
            String roleName = "";
            List<SysRole> roles = sastindSysUserVo.getRoles();
            for (SysRole sysRole : roles) {
                roleName += sysRole.getRoleName() + ",";
            }
            sastindSysUserVo.setRoleName(roleName.endsWith(",") ? roleName.substring(0, roleName.length() - 1) : roleName);
            SysDept sysDept = sastindSysUserVo.getDept();
            String deptName = sysDept.getDeptName();
            sastindSysUserVo.setDeptName(deptName);
        }
        return list;
    }

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserByLoginName(String userName) {
        return userMapper.selectUserByLoginName(userName);
    }

    /**
     * 通过手机号码查询用户
     *
     * @param phoneNumber 用户名
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserByPhoneNumber(String phoneNumber) {
        return userMapper.selectUserByPhoneNumber(phoneNumber);
    }

    /**
     * 通过邮箱查询用户
     *
     * @param email 邮箱
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserByEmail(String email) {
        return userMapper.selectUserByEmail(email);
    }

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserById(String userId) {
        return userMapper.selectUserById(userId);
    }

    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteUserById(String userId) {
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 删除用户与岗位表
        userPostMapper.deleteUserPostByUserId(userId);
        return userMapper.deleteUserById(userId);
    }

    /**
     * 批量删除用户信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    @Transactional
    public AjaxResult deleteUserByIds(String ids) {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.put("code", 0);
        ajaxResult.put("msg", "删除成功");
        String[] split = ids.split(",");
        if (split.length > 0) {
            for (String userId : split) {
                SysUser sysUser = selectUserById(userId);
                if (sysUser != null ) {
                    List<SysRole> roles = sysUser.getRoles();
                    if (StringUtils.isNotEmpty(roles)) {
                        List<String> collect = roles.stream().map(SysRole::getRoleKey).collect(Collectors.toList());
                        if (collect.size() > 0) {
                            boolean isAdmin = collect.contains("admin");
                            if (isAdmin) {
                                return AjaxResult.error(1, "管理员用户不允许删除");
                            } else {
                                deleteUserById(userId);
                            }
                        } else {
                            deleteUserById(userId);
                        }
                    } else {
                        deleteUserById(userId);
                    }
                }
            }
        }

        return ajaxResult;
    }

    /**
     * 新增保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertUser(SysUser user) {
        // 新增用户信息
        user.setUserId(UuidUtils.getUUIDString());
        int rows = userMapper.insertUser(user);
        // 新增用户岗位关联
        if (user.getPostIds() != null) {
            insertUserPost(user);
        }
        // 新增用户与角色管理
        if (user.getRoleIds() != null) {
            insertUserRole(user);
        }

        return rows;
    }

    /**
     * 修改保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUser(SysUser user) {

        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(user.getUserId());
        // 新增用户与角色管理
        insertUserRole(user);
        // 删除用户与岗位关联
        userPostMapper.deleteUserPostByUserId(user.getUserId());
        // 新增用户与岗位管理
        insertUserPost(user);
        return userMapper.updateUser(user);
    }

    /**
     * 修改用户个人详细信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserInfo(SysUser user) {
        return userMapper.updateUser(user);
    }

    /**
     * 修改用户密码
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int resetUserPwd(SysUser user) {
        return updateUserInfo(user);
    }

    /**
     * 新增用户角色信息
     *
     * @param user 用户对象
     */
    public void insertUserRole(SysUser user) {
        // 新增用户与角色管理
        List<SysUserRole> list = new ArrayList<SysUserRole>();
        for (String roleId : user.getRoleIds()) {
            SysUserRole ur = new SysUserRole();
            ur.setUserId(user.getUserId());
            ur.setRoleId(roleId);
            list.add(ur);
        }
        if (list.size() > 0) {
            userRoleMapper.batchUserRole(list);
        }
    }

    /**
     * 新增用户岗位信息
     *
     * @param user 用户对象
     */
    public void insertUserPost(SysUser user) {
        // 新增用户与岗位管理
        List<SysUserPost> list = new ArrayList<SysUserPost>();
        for (String postId : user.getPostIds()) {
            SysUserPost up = new SysUserPost();
            up.setUserId(user.getUserId());
            up.setPostId(postId);
            list.add(up);
        }
        if (list.size() > 0) {
            userPostMapper.batchUserPost(list);
        }
    }

    /**
     * 校验登录名是否唯一
     *
     * @param loginName 用户名
     * @return
     */
    @Override
    public String checkLoginNameUnique(String loginName) {
        int count = userMapper.checkLoginNameUnique(loginName);
        if (count > 0) {
            return UserConstants.USER_NAME_NOT_UNIQUE;
        }
        return UserConstants.USER_NAME_UNIQUE;
    }

    /**
     * 编辑时校验用户名称是否唯一
     *
     * @return 结果
     */
    @Override
    public String checkEditLoginNameUnique(SysUser user) {
        String userId = StringUtils.isEmpty(user.getUserId()) ? "-1" : user.getUserId();
        SysUser info = userMapper.selectUserByLoginName(user.getLoginName());
        if (StringUtils.isNotNull(info) && !info.getUserId().equals(userId)) {
            return UserConstants.USER_NAME_NOT_UNIQUE;
        }
        return UserConstants.USER_NAME_UNIQUE;
    }

    /**
     * 校验手机号是否唯一
     *
     * @param user 用户名
     * @return
     */
    @Override
    public String checkPhoneUnique(SysUser user) {
        String userId = StringUtils.isEmpty(user.getUserId()) ? "-1" : user.getUserId();
        SysUser info = userMapper.checkPhoneUnique(user.getPhonenumber());
        if (StringUtils.isNotNull(info) && !info.getUserId().equals(userId)) {
            return UserConstants.USER_PHONE_NOT_UNIQUE;
        }
        return UserConstants.USER_PHONE_UNIQUE;
    }

    /**
     * 校验身份真号码唯一
     *
     * @param user
     * @return String
     */
    @Override
    public String checkIdCardUnique(SysUser user) {
        String userId = StringUtils.isNull(user.getUserId()) ? "-1" : user.getUserId();
        SysUser userUnique = userMapper.checkIdCardUnique(user.getIdentityCard());
        if (userUnique != null && !userUnique.getUserId().equals(userId)) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验email是否唯一
     *
     * @param user 用户名
     * @return
     */
    @Override
    public String checkEmailUnique(SysUser user) {
        String userId = StringUtils.isNull(user.getUserId()) ? "-1" : user.getUserId();
        SysUser info = userMapper.checkEmailUnique(user.getEmail());
        if (StringUtils.isNotNull(info) && !info.getUserId().equals(userId)) {
            return UserConstants.USER_EMAIL_NOT_UNIQUE;
        }
        return UserConstants.USER_EMAIL_UNIQUE;
    }

    /**
     * 查询用户所属角色组
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public String selectUserRoleGroup(String userId) {
        List<SysRole> list = roleMapper.selectRolesByUserId(userId);
        StringBuffer idsStr = new StringBuffer();
        for (SysRole role : list) {
            idsStr.append(role.getRoleName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString())) {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    /**
     * 查询用户所属岗位组
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public String selectUserPostGroup(String userId) {
        List<SysPost> list = postMapper.selectPostsByUserId(userId);
        StringBuffer idsStr = new StringBuffer();
        for (SysPost post : list) {
            idsStr.append(post.getPostName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString())) {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    /**
     * 根据条件查询用户对象
     *
     * @return 用户信息集合信息
     */
    @Override
    public List<SysUser> selectCandidateList(String deptCode, String careerCode) {
        String[] deptCodeArray = {};
        if (StringUtils.isNotEmpty(deptCode)) {
            deptCodeArray = deptCode.split(",");
        }
        return userMapper.selectCandidateList(deptCodeArray, careerCode);
    }

    /**
     * 根据角色和部门条件查询用户
     *
     * @return 用户信息集合信息
     */
    @Override
    public List<SysUser> selectCandidateListByDeptAndRole(String deptCode, String roleCode) {
        String[] deptCodeArray = {};
        if (StringUtils.isNotEmpty(deptCode)) {
            deptCodeArray = deptCode.split(",");
        }
        return userMapper.selectCandidateListByDeptAndRole(deptCodeArray, roleCode);
    }

    @Override
    public List<SastindSysUserVo> selectUserListByDept(SysUser sysUser) {
        List<SysDept> sysDepts = sysDeptMapper.selectDeptChildren(sysUser.getDeptId());
        String[] deptIds = new String[sysDepts.size() + 1];
        int i = 0;
        for (SysDept sysDept : sysDepts) {
            deptIds[i] = sysDept.getDeptId();
            i++;
        }
        deptIds[i] = sysUser.getDeptId();
        List<SastindSysUserVo> sysUsers = userMapper.selectUserByDept(deptIds, sysUser);
        return sysUsers;
    }

    @Override
    public List<SastindSysUserVo> selectUserByDeptIds(SysUser sysUser, String deptIds) {
        String[] deptArray = deptIds.split(",");
        String[] deptArrayLong = new String[deptArray.length];
        int i = 0;
        for (String str : deptArray) {
            if (!StringUtils.isEmpty(str)) {
                deptArrayLong[i] = str;
            }
            i++;
        }
        List<SastindSysUserVo> sysUsers = userMapper.selectUserByDept(deptArrayLong, sysUser);
        return sysUsers;
    }

    /**
     * 校验导入文件是否为空
     *
     * @param object
     * @return
     */
    private boolean checkObjectFileIsNull(Object object) {
        if (null == object) {
            return true;
        }
        try {
            for (Field field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.get(object) != null && StringUtils.isNotBlank(field.get(object).toString())) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 读取并校验导入文件
     *
     * @param file
     */
    @Override
    public Map<String, Object> importExcel(MultipartFile file) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String originName = file.getOriginalFilename();
        //用户默认为普通员工角色
        SysRole role = sysRoleMapper.selectRoleByKey(UserConstants.GENERAL_STAFF);
        String[] roleIds = new String[]{role.getRoleId()};
        try {
            String suffix = originName.substring(originName.lastIndexOf(".") + 1);
            if ("xlsx".equalsIgnoreCase(suffix) || "xls".equalsIgnoreCase(suffix)) {
                InputStream is = new ByteArrayInputStream(file.getBytes());
                ExcelUtil<SysUserTemplate> util = new ExcelUtil<SysUserTemplate>(SysUserTemplate.class);
                List<SysUserTemplate> sysUserTemplateTemplates = util.importExcel("用户信息", is);
                List<SysUser> sysUserList = new ArrayList<SysUser>();
                List<Map<Integer, List<Integer>>> rowNums = new ArrayList<>();
                if (!CollectionUtils.isEmpty(sysUserTemplateTemplates)) {
                    Set<String> loginNameSet = new HashSet<String>();
                    int countName = 0;
                    for (SysUserTemplate sysUserTemplate : sysUserTemplateTemplates) {
                        if (checkObjectFileIsNull(sysUserTemplate) || StringUtils.isEmpty(sysUserTemplate.getLoginName())) {
                            countName += 1;

                        } else {
                            loginNameSet.add(sysUserTemplate.getLoginName());
                        }
                    }
                    if (loginNameSet.size() != sysUserTemplateTemplates.size() - countName) {
                        map.put("data", null);
                        map.put("msg", "登录名存在重复的，请检查");
                        map.put("fileName", "");
                        return map;
                    }


                    String msg = "";
                    int rowNum = 1;
                    SysUser sysUser = null;

                    for (SysUserTemplate sysUserTemplate : sysUserTemplateTemplates) {
                        if (checkObjectFileIsNull(sysUserTemplate)) {
                            rowNum += 1;
                            continue;
                        }
                        List<Integer> cellNums = new ArrayList<>();
                        String msgTemp = "第" + rowNum + "行" + sysUserTemplate.getLoginName() + "：";
                        int count = 0;

                        sysUser = new SysUser();

                        //BeanUtils.copyBeanProp(sysUser, sysUserTemplate);
                        //1.用户名称校验 （非空、长度不能超过20）
                        if (StringUtils.isEmpty(sysUserTemplate.getUserName())) {
                            msgTemp += "用户名称不能为空、";
                            cellNums.add(3);
                            count += 1;
                        } else {

                            Pattern pattern = Pattern.compile("^[\\u4e00-\\u9fa5]{2,20}$");
                            boolean flag = pattern.matcher(sysUserTemplate.getUserName()).matches();
                            if (!flag) {
                                msgTemp += "用户名称姓名只能用汉字,字符长度在2-20个、";
                                cellNums.add(3);
                                count += 1;
                            } else {
                                sysUser.setUserName(sysUserTemplate.getUserName());
                            }
                        }
                        //2、登陆名校验（非空，长度不能超过20，唯一）
                        if (StringUtils.isEmpty(sysUserTemplate.getLoginName())) {
                            msgTemp += "登录名称不能为空、";
                            cellNums.add(0);
                            count += 1;
                        } else {
                            Pattern pattern = Pattern.compile("^[a-zA-Z0-9]{2,20}$");
                            boolean flagPattern = pattern.matcher(sysUserTemplate.getLoginName()).matches();
                            if (!flagPattern) {
                                msgTemp += "只能数字或者字母,不包含特殊字符,字符长度在2-20个、";
                                cellNums.add(0);
                                count += 1;
                            } else {
                                String flag = this.checkLoginNameUnique(sysUserTemplate.getLoginName());
                                if (flag.equals(UserConstants.USER_NAME_NOT_UNIQUE)) {
                                    msgTemp += "登录名称已存在、";
                                    cellNums.add(0);
                                    count += 1;
                                } else {
                                    sysUser.setLoginName(sysUserTemplate.getLoginName());
                                }
                            }

                        }
                        //3、部门名称（非空，是否存在该部门，长度不能超过30）
                        if (StringUtils.isEmpty(sysUserTemplate.getDeptName())) {
                            msgTemp += "部门名称不能为空、";
                            cellNums.add(2);
                            count += 1;
                        } else {
                            SysDept dept = new SysDept();
                            dept.setDeptName(sysUserTemplate.getDeptName().trim());
                            dept.setParentName(sysUserTemplate.getParentDeptName().trim());
                            List<SysDept> deptList = sysDeptMapper.queryDeptByName(dept);
                            if (deptList.size() == 0) {
                                msgTemp += "不存在该部门、";
                                cellNums.add(2);
                                count += 1;
                            } else if (deptList.size() == 1) {
                                sysUser.setDeptId(deptList.get(0).getDeptId());
                            }
                        }
                        //4 密码（非空，长度不能超过20）
                        if (StringUtils.isEmpty(sysUserTemplate.getPassword())) {
                            msgTemp += "密码不能为空、";
                            cellNums.add(4);
                            count += 1;
                        } else {
                            if (sysUserTemplate.getPassword().length() > 20) {
                                msgTemp += "密码不能超过20个字符、";
                                cellNums.add(4);
                                count += 1;
                            } else if (sysUserTemplate.getPassword().length() < 6) {
                                msgTemp += "密码不能少于6个字符、";
                                cellNums.add(4);
                                count += 1;
                            } else {
                                sysUser.setPassword(sysUserTemplate.getPassword());
                            }
                        }
                        //5、邮箱（正则校验）
                        if (!StringUtils.isEmpty(sysUserTemplate.getEmail())) {
                            Pattern pattern = Pattern.compile("^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
                            boolean flagPattern = pattern.matcher(sysUserTemplate.getEmail()).matches();
                            if (!flagPattern) {
                                msgTemp += "邮箱格式不正确、";
                                cellNums.add(5);
                                count += 1;
                            } else {
                                SysUser user = new SysUser();
                                user.setEmail(sysUserTemplate.getEmail());
                                String flag = this.checkEmailUnique(user);
                                if (flag.equals(UserConstants.USER_EMAIL_NOT_UNIQUE)) {
                                    msgTemp += "邮箱已存在";
                                    cellNums.add(5);
                                    count += 1;
                                } else {
                                    sysUser.setEmail(sysUserTemplate.getEmail());
                                }
                            }
                        }
                        //6、手机号（正则校验）
                        if (!StringUtils.isEmpty(sysUserTemplate.getPhonenumber())) {
                            Pattern pattern = Pattern.compile("^1[3|4|5|6|7|8][0-9]\\d{8}$");
                            boolean flagPattern = pattern.matcher(sysUserTemplate.getPhonenumber()).matches();
                            if (!flagPattern) {
                                msgTemp += "手机格式不正确、";
                                cellNums.add(6);
                                count += 1;
                            } else {
                                SysUser user = new SysUser();
                                user.setPhonenumber(sysUserTemplate.getPhonenumber());
                                String flag = this.checkPhoneUnique(user);
                                if (flag.equals(UserConstants.USER_PHONE_NOT_UNIQUE)) {
                                    msgTemp += "手机号已存在、";
                                    cellNums.add(6);
                                    count += 1;
                                } else {
                                    sysUser.setPhonenumber(sysUserTemplate.getPhonenumber());
                                }
                            }
                        }
                        //7.性别（非空，在字典项中）
                        if (StringUtils.isEmpty(sysUserTemplate.getSex())) {
                            msgTemp += "性别不能为空、";
                            cellNums.add(7);
                            count += 1;
                        } else {
                            String value = dictService.selectDictValue("sys_user_sex", sysUserTemplate.getSex().trim());
                            if (StringUtils.isEmpty(value)) {
                                msgTemp += "性别填写不正确、";
                                cellNums.add(7);
                                count += 1;
                            } else {
                                sysUser.setSex(value);
                            }
                        }
                        //8、用户状态
                        if (StringUtils.isEmpty(sysUserTemplate.getUserStatus())) {
                            msgTemp += "用户状态不能为空、";
                            cellNums.add(9);
                            count += 1;
                        } else {
                            String value = dictService.selectDictValue("sys_user_status", sysUserTemplate.getUserStatus().trim());
                            if (StringUtils.isEmpty(value)) {
                                msgTemp += "用户状态填写不正确、";
                                cellNums.add(9);
                                count += 1;
                            } else {
                                sysUser.setStatus(value);
                            }
                        }

                        //11、身份证（正则校验）
                        if (!StringUtils.isEmpty(sysUserTemplate.getIdentityCard())) {
                            Pattern pattern = Pattern.compile("(^\\d{15}$)|(^\\d{17}([0-9]|X)$)");
                            boolean flagPattern = pattern.matcher(sysUserTemplate.getIdentityCard()).matches();
                            if (!flagPattern) {
                                msgTemp += "身份证号格式不正确、";
                                cellNums.add(10);
                                count += 1;
                            } else {
                                SysUser user = new SysUser();
                                user.setIdentityCard(sysUserTemplate.getIdentityCard());
                                String flag = this.checkIdCardUnique(user);
                                if (flag.equals(UserConstants.NOT_UNIQUE)) {
                                    msgTemp += "身份证号已存在、";
                                    cellNums.add(10);
                                    count += 1;
                                } else {
                                    sysUser.setIdentityCard(sysUserTemplate.getIdentityCard());
                                }
                            }
                        }
                        //10、入职日期
                        if (!StringUtils.isEmpty(sysUserTemplate.getEmployDate())) {
                            Pattern pattern = Pattern.compile("^\\d{4}-(0?[1-9]|1[0-2])-(0?[1-9]|[1-2]\\d|3[0-1])$");
                            boolean flagPattern = pattern.matcher(sysUserTemplate.getEmployDate()).matches();
                            if (!flagPattern) {
                                msgTemp += "入职日期格式不正确、";
                                cellNums.add(11);
                                count += 1;
                            } else {
                                sysUser.setEmployDate(DateUtils.parseDate(sysUserTemplate.getEmployDate()));
                            }
                        }

                        sysUser.setRoleIds(roleIds);
                        sysUser.setStatus(UserConstants.NORMAL);

                        sysUserList.add(sysUser);

                        if (count > 0) {
                            msg += msgTemp.substring(0, msgTemp.length());
                            Map<Integer, List<Integer>> map1 = new HashMap<Integer, List<Integer>>();
                            map1.put(rowNum, cellNums);
                            rowNums.add(map1);
                        }
                        rowNum += 1;
                    }
                    map.put("data", sysUserList);
                    map.put("msg", msg);
                    String filePath = Global.getDownloadPath();

                    File newFilePath = new File(filePath);
                    if (!newFilePath.exists()) {
                        newFilePath.mkdirs();
                    }
                    File newFile = new File(filePath + file.getOriginalFilename());
                    Workbook workbook = WorkbookFactory.create(file.getInputStream());
                    Sheet sheet = workbook.getSheet("用户信息");
                    CellStyle cellStyle = workbook.createCellStyle();
                    cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
                    cellStyle.setFillBackgroundColor(IndexedColors.RED.getIndex());
                    cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                    for (Map<Integer, List<Integer>> map1 : rowNums) {
                        for (Integer rowErrorNum : map1.keySet()) {
                            Row row = sheet.getRow(rowErrorNum);
                            if (row != null) {
                                for (Integer cellErrorNum : map1.get(rowErrorNum)) {

                                    Cell cell = row.getCell(cellErrorNum);
                                    if (cell == null) {
                                        cell = row.createCell(cellErrorNum);
                                    }
//										CellStyle cellStyle = workbook.createCellStyle();
//										cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
//										cellStyle.setFillBackgroundColor(IndexedColors.RED.getIndex());
//										cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                    cell.setCellStyle(cellStyle);
                                }
                            }
                        }

                    }


                    workbook.write(new FileOutputStream(newFile));
                    workbook.close();
                    String fileName = newFile.getName();

                    is.close();
                    map.put("fileName", fileName);
                }
            } else {
                map.put("data", null);
                map.put("msg", "文件数据为空");
            }
            return map;
        } catch (Exception e) {
            map.put("data", null);
            map.put("msg", "填写数据有异常，请检查");
            return map;
        }
    }

    @Override
    public List<SysUser> selectSysUserList(SysUser sysUser) {
        return userMapper.selectSysUserList(sysUser);
    }

    /**
     * 同步基础数据
     *
     * @return
     */
    @Override
    public List<Map> updateBasicData() {
        return userMapper.updateBasicData();
    }

    /**
     * 校验身份卡号的唯一性
     *
     * @param user
     * @return
     */
    @Override
    public String checkCardNumUnique(SysUser user) {
        String userId = StringUtils.isNull(user.getUserId()) ? "-1" : user.getUserId();
        SysUser userUnique = userMapper.checkCardNumUnique(user.getCardNum());
        if (userUnique != null && !userUnique.getUserId().equals(userId)) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }
}
