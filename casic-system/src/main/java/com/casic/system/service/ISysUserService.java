package com.casic.system.service;

import com.casic.common.base.AjaxResult;
import com.casic.system.domain.SastindSysUserVo;
import com.casic.system.domain.SysUser;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

/**
 * 用户 业务层
 *
 * @author yuzengwen
 */
public interface ISysUserService
{
    /**
     * 根据条件分页查询用户对象
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    public List<SastindSysUserVo> selectUserList(SysUser user);

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    public SysUser selectUserByLoginName(String userName);

    /**
     * 通过手机号码查询用户
     *
     * @param phoneNumber 手机号码
     * @return 用户对象信息
     */
    public SysUser selectUserByPhoneNumber(String phoneNumber);

    /**
     * 通过邮箱查询用户
     *
     * @param email 邮箱
     * @return 用户对象信息
     */
    public SysUser selectUserByEmail(String email);

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    public SysUser selectUserById(String userId);

    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    public int deleteUserById(String userId);

    /**
     * 批量删除用户信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     * @throws Exception 异常
     */
    public AjaxResult deleteUserByIds(String ids);

    /**
     * 保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public int insertUser(SysUser user);

    /**
     * 保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public int updateUser(SysUser user);

    /**
     * 修改用户详细信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public int updateUserInfo(SysUser user);

    /**
     * 修改用户密码信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public int resetUserPwd(SysUser user);

    /**
     * 校验用户名称是否唯一
     *
     * @param loginName 登录名称
     * @return 结果
     */
    public String checkLoginNameUnique(String loginName);

    /**
     * 编辑时校验用户名称是否唯一
     *
     * @param loginName 登录名称
     * @return 结果
     */
    public String checkEditLoginNameUnique(SysUser user);

    /**
     * 校验手机号码是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    public String checkPhoneUnique(SysUser user);
    
    /**
	 * 校验身份真号码唯一
	 * @param idCard
	 * @return int
	 */
	public String checkIdCardUnique(SysUser user);

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    public String checkEmailUnique(SysUser user);

    /**
     * 根据用户ID查询用户所属角色组
     *
     * @param userId 用户ID
     * @return 结果
     */
    public String selectUserRoleGroup(String userId);

    /**
     * 根据用户ID查询用户所属岗位组
     *
     * @param userId 用户ID
     * @return 结果
     */
    public String selectUserPostGroup(String userId);

    /**
     * 根据条件查询用户对象
     */
    public List<SysUser> selectCandidateList(String deptCode, String careerCode);

    public List<SysUser> selectCandidateListByDeptAndRole(String deptCode, String roleCode);

    List<SastindSysUserVo> selectUserListByDept(SysUser sysUser);

    List<SastindSysUserVo> selectUserByDeptIds(SysUser sysUser,String deptIds);
    
    /**
     * 用户导入
     * @param multipartFile
     * @return
     * @throws Exception
     */
    Map<String, Object> importExcel(MultipartFile multipartFile) throws Exception;

    /**
     * 查询用户列表
     * @param sysUser
     * @return
     */
    List<SysUser> selectSysUserList(SysUser sysUser);

    /**
     * 给手持机发送用户信息
     * @return
     */
    List<Map> updateBasicData();

    /**
     * 校验身份卡号的唯一性
     * @param user
     * @return
     */
    String checkCardNumUnique(SysUser user);
}
