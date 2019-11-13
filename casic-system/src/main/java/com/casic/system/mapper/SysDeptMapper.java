package com.casic.system.mapper;

import java.util.List;
import java.util.Map;

import com.casic.system.domain.SysDeptVO;
import org.apache.ibatis.annotations.Param;

import com.casic.system.domain.SysDept;
import org.springframework.stereotype.Repository;

/**
 * 部门管理 数据层
 *
 * @author yuzengwen
 */
@Repository
public interface SysDeptMapper
{
    /**
     * 查询部门人数
     *
     * @param dept 部门信息
     * @return 结果
     */
    public int selectDeptCount(SysDept dept);
    
    /**
     * 根据部门名称查询部门信息
     * @param dept
     * @return SysDept
     */
    public List<SysDept> queryDeptByName(SysDept dept);

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return 结果
     */
    public int checkDeptExistUser(String deptId);
    
    /**
     * 根据部门父id查询 最大排序值
     * @param parentId
     * @return
     */
    public Integer selectMaxOrderNumByParentId(String parentId);

    /**
     * 查询部门管理数据
     *
     * @param dept 部门信息
     * @return 部门信息集合
     */
    public List<SysDept> selectDeptList(SysDept dept);

    /**
     * 删除部门管理信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    public int deleteDeptById(String deptId);

    /**
     * 新增部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    public int insertDept(SysDept dept);

    /**
     * 修改部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    public int updateDept(SysDept dept);

    /**
     * 修改子元素关系
     *
     * @param depts 子元素
     * @return 结果
     */
    public int updateDeptChildren(@Param("depts") List<SysDept> depts);

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
     * @param deptName 部门名称
     * @param parentId 父部门ID
     * @return 结果
     */
    public SysDept checkDeptNameUnique(@Param("deptName") String deptName, @Param("parentId") String parentId);

    /**
     * 校验部门编号是否唯一
     *
     * @param deptCode 部门编号
     * @return 结果
     */
    public SysDept checkDeptCodeUnique(@Param("deptCode") String deptCode);

    /**
     * 根据角色ID查询部门
     *
     * @param roleId 角色ID
     * @return 部门列表
     */
    public List<String> selectRoleDeptTree(String roleId);

    /**
     * 根据部门编号查询信息
     *
     * @param deptCode 部门编号
     * @return 部门信息
     */
    public SysDept selectDeptByCode(String deptCode);

    /**
     * 查询子部门
     */
    public List<SysDept> selectDeptChildren(String deptId);

    List<Map> selectAllToPhone();



    /**
     * 查询部门树
     *
     * @param sysDept
     * @return
     */
    List<SysDeptVO> selectSysDeptTrees(SysDeptVO sysDept);
}
