package com.casic.system.service.impl;

import com.casic.common.annotation.DataScope;
import com.casic.common.constant.UserConstants;
import com.casic.common.utils.StringUtils;
import com.casic.system.domain.SysDept;
import com.casic.system.domain.SysDeptVO;
import com.casic.system.domain.SysRole;
import com.casic.system.mapper.SysDeptMapper;
import com.casic.system.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门管理 服务实现
 *
 * @author yuzengwen
 */
@Service
public class SysDeptServiceImpl implements ISysDeptService {
    @Autowired
    private SysDeptMapper deptMapper;

    /**
     * 查询部门管理数据
     *
     * @return 部门信息集合
     */
    @Override
    @DataScope(tableAlias = "d")
    public List<SysDept> selectDeptList(SysDept dept) {
        return deptMapper.selectDeptList(dept);
    }

    /**
     * 查询部门管理树
     *
     * @return 所有部门信息
     */
    @Override
    public List<Map<String, Object>> selectDeptTree() {
        List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
        List<SysDept> deptList = selectDeptList(new SysDept());
        trees = getTrees(deptList, false, null);
        return trees;
    }

    /**
     * 根据角色ID查询部门（数据权限）
     *
     * @param role 角色对象
     * @return 部门列表（数据权限）
     */
    @Override
    public List<Map<String, Object>> roleDeptTreeData(SysRole role) {
        String roleId = role.getRoleId();
        List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
        List<SysDept> deptList = selectDeptList(new SysDept());
        if (StringUtils.isNotNull(roleId)) {
            List<String> roleDeptList = deptMapper.selectRoleDeptTree(roleId);
            trees = getTrees(deptList, true, roleDeptList);
        } else {
            trees = getTrees(deptList, false, null);
        }
        return trees;
    }

    /**
     * 对象转部门树
     *
     * @param deptList     部门列表
     * @param isCheck      是否需要选中
     * @param roleDeptList 角色已存在菜单列表
     * @return
     */
    public List<Map<String, Object>> getTrees(List<SysDept> deptList, boolean isCheck, List<String> roleDeptList) {

        List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
        for (SysDept dept : deptList) {
            if (UserConstants.DEPT_NORMAL.equals(dept.getStatus())) {
                Map<String, Object> deptMap = new HashMap<String, Object>();
                deptMap.put("id", dept.getDeptId());
                deptMap.put("pId", dept.getParentId());
                deptMap.put("name", dept.getDeptName());
                deptMap.put("title", dept.getDeptName());
                if (isCheck) {
                    deptMap.put("checked", roleDeptList.contains(dept.getDeptId() + dept.getDeptName()));
                } else {
                    deptMap.put("checked", false);
                }
                trees.add(deptMap);
            }
        }
        return trees;
    }

    /**
     * 查询部门人数
     *
     * @param parentId 部门ID
     * @return 结果
     */
    @Override
    public int selectDeptCount(String parentId) {
        SysDept dept = new SysDept();
        dept.setParentId(parentId);
        return deptMapper.selectDeptCount(dept);
    }

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    @Override
    public boolean checkDeptExistUser(String deptId) {
        int result = deptMapper.checkDeptExistUser(deptId);
        return result > 0 ? true : false;
    }

    /**
     * 删除部门管理信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    @Override
    public int deleteDeptById(String deptId) {
        return deptMapper.deleteDeptById(deptId);
    }

    /**
     * 新增保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public int insertDept(SysDept dept) {

        SysDept info = deptMapper.selectDeptById(dept.getParentId());
        Integer maxOrderNum = deptMapper.selectMaxOrderNumByParentId(dept.getParentId());
        if (maxOrderNum == null) {
            dept.setOrderNum(1);
        } else {
            dept.setOrderNum(maxOrderNum + 1);
        }
        if (StringUtils.isNull(info)) {
            dept.setAncestors(dept.getParentId());
        } else {
            dept.setAncestors(info.getAncestors() + dept.getParentId() + ",");
        }
        return deptMapper.insertDept(dept);
    }

    /**
     * 修改保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public int updateDept(SysDept dept) {
        SysDept info = deptMapper.selectDeptById(dept.getParentId());
        if (StringUtils.isNotNull(info)) {
            String ancestors = info.getAncestors() + dept.getParentId() + ",";
            dept.setAncestors(ancestors);
            updateDeptChildren(dept.getDeptId(), ancestors);
        }
        return deptMapper.updateDept(dept);
    }

    /**
     * 修改子元素关系
     *
     * @param deptId    部门ID
     * @param ancestors 元素列表
     */
    public void updateDeptChildren(String deptId, String ancestors) {
        SysDept dept = new SysDept();
        dept.setParentId(deptId);
        List<SysDept> childrens = deptMapper.selectDeptList(dept);
        for (SysDept children : childrens) {
            children.setAncestors(ancestors + "," + dept.getParentId());
        }
        if (childrens.size() > 0) {
            deptMapper.updateDeptChildren(childrens);
        }
    }

    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    @Override
    public SysDept selectDeptById(String deptId) {
        return deptMapper.selectDeptById(deptId);
    }

    /**
     * 校验部门名称是否唯一
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public String checkDeptNameUnique(SysDept dept) {
        String deptId = StringUtils.isNull(dept.getDeptId()) ? "-1" : dept.getDeptId();
        SysDept info = deptMapper.checkDeptNameUnique(dept.getDeptName(), dept.getParentId());
        if (StringUtils.isNotNull(info) && !deptId.equals(info.getDeptId())) {
            return UserConstants.DEPT_NAME_NOT_UNIQUE;
        }
        return UserConstants.DEPT_NAME_UNIQUE;
    }

    /**
     * 校验部门编号是否唯一
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public String checkDeptCodeUnique(SysDept dept) {
        String deptId = StringUtils.isNull(dept.getDeptId()) ? "-1" : dept.getDeptId();
        SysDept info = deptMapper.checkDeptCodeUnique(dept.getDeptCode());
        if (StringUtils.isNotNull(info) && !deptId.equals(info.getDeptId())) {
            return UserConstants.DEPT_NAME_NOT_UNIQUE;
        }

        return UserConstants.DEPT_NAME_UNIQUE;
    }

    /**
     * 根据部门编号查询信息
     *
     * @param deptCode 部门编号
     * @return 部门信息
     */
    @Override
    public SysDept selectDeptByCode(String deptCode) {
        return deptMapper.selectDeptByCode(deptCode);
    }

    /**
     * 获取部门所属责任主体下所有部门编码
     */
    @Override
    public String getDutyBelongChildren(String deptId) {
        String dutyBelongChildren = "";
        SysDept sysDept = deptMapper.selectDeptById(deptId);
        String ancestors = sysDept.getAncestors();
        String[] ancestorArray = ancestors.split(",");
        String dutyBelong = deptId + "";
        for (int i = ancestorArray.length - 1; i >= 0; i--) {
            if (StringUtils.isNotEmpty(ancestorArray[i])) {
                SysDept dept = deptMapper.selectDeptById(ancestorArray[i]);
                if (dept != null && "1".equals(dept.getHeadDeptFalg())) {
                    dutyBelong = ancestorArray[i];
                    break;
                }
            }
        }
        SysDept dutyBelongDept = deptMapper.selectDeptById(dutyBelong);

        dutyBelongChildren += dutyBelongDept.getDeptCode();
        if ("1".equals(dutyBelongDept.getHeadDeptFalg())) {
            List<SysDept> deptChildren = deptMapper.selectDeptChildren(dutyBelong);
            for (int i = 0; i < deptChildren.size(); i++) {
                dutyBelongChildren += "," + deptChildren.get(i).getDeptCode();
            }
        }

        return dutyBelongChildren;
    }

    /**
     * 获取部门所属责任主体下所有部门id
     */
    @Override
    public String getDutyBelongChildrenId(String deptId) {
        String dutyBelongChildren = "";
        SysDept sysDept = deptMapper.selectDeptById(deptId);
        String ancestors = sysDept.getAncestors();
        String[] ancestorArray = ancestors.split(",");
        String dutyBelong = deptId + "";
        for (int i = ancestorArray.length - 1; i >= 0; i--) {
            if (StringUtils.isNotEmpty(ancestorArray[i])) {
                SysDept dept = deptMapper.selectDeptById(ancestorArray[i]);
                if (dept != null && "1".equals(dept.getHeadDeptFalg())) {
                    dutyBelong = ancestorArray[i];
                    break;
                }
            }
        }
        SysDept dutyBelongDept = deptMapper.selectDeptById(dutyBelong);

        dutyBelongChildren += dutyBelongDept.getDeptId();
        if ("1".equals(dutyBelongDept.getHeadDeptFalg())) {
            List<SysDept> deptChildren = deptMapper.selectDeptChildren(dutyBelong);
            for (int i = 0; i < deptChildren.size(); i++) {
                dutyBelongChildren += "," + deptChildren.get(i).getDeptId();
            }
        }

        return dutyBelongChildren;
    }

    @Override
    public String getChildrenIds(String deptId) {
        List<SysDept> sysDepts = deptMapper.selectDeptChildren(deptId);
        String deptIds = "";
        for (SysDept sysDept : sysDepts) {
            deptIds += sysDept.getDeptId() + ",";
        }
        return deptIds;
    }

    @Override
    public List<SysDept> selectChildDept(String deptId) {
        return deptMapper.selectDeptChildren(deptId);
    }

    /**
     * 获取部门所属责任主体
     *
     * @return
     */
    @Override
    public SysDept getDutyBelongDept(String deptId) {
        SysDept sysDept = deptMapper.selectDeptById(deptId);
        String ancestors = sysDept.getAncestors();
        String[] ancestorArray = ancestors.split(",");
        String dutyBelong = deptId + "";
        for (int i = ancestorArray.length - 1; i >= 0; i--) {
            if (StringUtils.isNotEmpty(ancestorArray[i])) {
                SysDept dept = deptMapper.selectDeptById(ancestorArray[i]);
                if (dept != null && "1".equals(dept.getHeadDeptFalg())) {
                    dutyBelong = ancestorArray[i];
                    break;
                }
            }
        }
        SysDept dutyBelongDept = deptMapper.selectDeptById(dutyBelong);
        return dutyBelongDept;
    }


    /**
     * 递归查询部门
     *
     * @param sysDept
     * @return
     */
    @Override
    public List<SysDeptVO> getSysDeptList(SysDeptVO sysDept) {
        List<SysDeptVO> list = deptMapper.selectSysDeptTrees(sysDept);
        if (list.size() > 0) {
            SysDeptVO dept = new SysDeptVO();
            list.stream().forEach(p -> {
                dept.setParentId(p.getDeptId());
                List<SysDeptVO> depts = getSysDeptList(dept);
                p.setChildDept(depts);
            });
        }
        return list;
    }
}