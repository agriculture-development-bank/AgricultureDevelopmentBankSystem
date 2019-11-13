package com.casic.system.service;

import com.casic.system.domain.SysDept;
import com.casic.system.domain.SysDeptVO;
import com.casic.system.domain.SysRole;

import java.util.List;
import java.util.Map;

/**
 * 部门管理 服务层
 *
 * @author yuzengwen
 */
public interface ISysDeptService {
    /**
     * 查询部门管理数据
     *
     * @param dept 部门信息
     * @return 部门信息集合
     */
    public List<SysDept> selectDeptList(SysDept dept);

    /**
     * 查询部门管理树
     *
     * @return 所有部门信息
     */
    public List<Map<String, Object>> selectDeptTree();

    /**
     * 根据角色ID查询菜单
     *
     * @param role 角色对象
     * @return 菜单列表
     */
    public List<Map<String, Object>> roleDeptTreeData(SysRole role);

    /**
     * 查询部门人数
     *
     * @param parentId 父部门ID
     * @return 结果
     */
    public int selectDeptCount(String parentId);

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    public boolean checkDeptExistUser(String deptId);

    /**
     * 删除部门管理信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    public int deleteDeptById(String deptId);

    /**
     * 新增保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    public int insertDept(SysDept dept);

    /**
     * 修改保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    public int updateDept(SysDept dept);

    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    public SysDept selectDeptById(String deptId);

    /**
     * 校验部门名称是否唯一
     *
     * @param dept 部门信息
     * @return 结果
     */
    public String checkDeptNameUnique(SysDept dept);

    /**
     * 校验部门编号是否唯一
     *
     * @param dept 部门信息
     * @return 结果
     */
    public String checkDeptCodeUnique(SysDept dept);

    /**
     * 根据部门编号查询信息
     *
     * @param deptCode 部门编号
     * @return 部门信息
     */
    public SysDept selectDeptByCode(String deptCode);

    /**
     * 获取部门所属责任主体下所有部门编码
     */
    public String getDutyBelongChildren(String deptId);

    /**
     * 获取部门所属责任主体下所有部门id
     */
    public String getDutyBelongChildrenId(String deptId);

    String getChildrenIds(String deptId);

    List<SysDept> selectChildDept(String deptId);

    /**
     * 获取部门所属责任主体
     *
     * @return
     */
    public SysDept getDutyBelongDept(String deptId);

    /**
     * 查询部门树列表
     *
     * @param sysDept 部门
     * @return
     */
    List<SysDeptVO> getSysDeptList(SysDeptVO sysDept);
}
