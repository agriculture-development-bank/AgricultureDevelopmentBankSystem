package com.casic.system.mapper;

import com.casic.system.domain.SastindSysUserVo;
import com.casic.system.domain.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 用户表 数据层
 *
 * @author yuzengwen
 */
@Repository
public interface SysUserMapper {
    /**
     * 根据条件分页查询用户对象
     *
     * @param sysUser 用户信息
     * @return 用户信息集合信息
     */
    public List<SastindSysUserVo> selectUserList(SysUser sysUser);

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    public SysUser selectUserByLoginName(String userName);

    /**
     * 通过用户名查询用户
     */
    SysUser selectUserByUserName(String userName);

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
     */
    public int deleteUserByIds(String[] ids);

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public int updateUser(SysUser user);

    /**
     * 新增用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public int insertUser(SysUser user);

    /**
     * 校验用户名称是否唯一
     *
     * @param loginName 登录名称
     * @return 结果
     */
    public int checkLoginNameUnique(String loginName);

    /**
     * 校验手机号码是否唯一
     *
     * @param phonenumber 手机号码
     * @return 结果
     */
    public SysUser checkPhoneUnique(String phonenumber);

    /**
     * 校验身份真号码唯一
     *
     * @param idCard
     * @return int
     */
    public SysUser checkIdCardUnique(String idCard);

    /**
     * 校验email是否唯一
     *
     * @param email 用户邮箱
     * @return 结果
     */
    public SysUser checkEmailUnique(String email);

    public List<SysUser> selectCandidateList(@Param("deptCode") String[] deptCode, @Param("careerCode") String careerCode);

    List<SastindSysUserVo> selectUserByDept(@Param("deptIds") String[] deptIds, @Param("sysUser")SysUser sysUser);

    List<SysUser> selectCandidateListByDeptAndRole(@Param("deptCode") String[] deptCodeArray, @Param("roleCode") String roleCode);

    /**
     * 同步基础数据
     * @return
     */
    List<Map> updateBasicData();

    List<SysUser> selectSysUserList(SysUser sysUser);

    /**
     * 根据身份卡号查询用户基本信息
     *
     * @param cardNum 身份卡号
     * @return
     */
    SysUser checkCardNumUnique(String cardNum);
}
