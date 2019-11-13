package com.casic.auth.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.casic.common.web.domain.bo.AuthResource;
import com.casic.common.web.domain.bo.AuthUser;
import com.casic.common.web.domain.vo.AuthUserVo;

/**
 * 用户 数据层
 * 
 * @author yuzengwen
 * @date 2018-10-25
 */
public interface AuthUserMapper 
{
	/**
     * 查询用户信息
     * 
     * @param uid 用户ID
     * @return 用户信息
     */
	public AuthUserVo selectUserById(String uid);
	
	/**
     * 查询用户列表
     * 
     * @param user 用户信息
     * @return 用户集合
     */
	public List<AuthUser> selectUserList(@Param("u")AuthUser user, @Param("clientId")String clientId);
	
	/**
     * 新增用户
     * 
     * @param user 用户信息
     * @return 结果
     */
	public int insertUser(AuthUser user);
	
	/**
     * 修改用户
     * 
     * @param user 用户信息
     * @return 结果
     */
	public int updateUser(AuthUser user);
	
	/**
     * 删除用户
     * 
     * @param uid 用户ID
     * @return 结果
     */
	public int deleteUserById(String uid);
	
	/**
     * 批量删除用户
     * 
     * @param uids 需要删除的数据ID
     * @return 结果
     */
	public int deleteUserByIds(String[] uids);
	
	
	/**
	 * 用户名唯一性校验
     * @param username 用户登陆名
     * @return 结果
     */
	public AuthUser checkUserNameUnique(String username);
	
	public AuthUser checkPhoneUnique(AuthUser user);
	
	public AuthUser checkEmailUnique(AuthUser user);

	public List<AuthResource> selectResourcesByUserId(
            @Param("appId") String appId,
            @Param("sysCode") String sysCode) throws DataAccessException;

	public AuthUserVo selectUserByLoginName(@Param("username") String username ) throws DataAccessException;

	public AuthUser selectUserBySysUserId(String sysUserId);

	public List<AuthUserVo> selectCandidateList(@Param("deptCode")String deptCode, @Param("careerCode")String careerCode);


	/**
	 * 新增用户
	 *
	 * @param sysUserId 用户id
	 * @param sysCode 客户端标识
	 * @return 结果
	 */
	public int insertIntoUserClientRel(@Param("sysUserId")String sysUserId,
									   @Param("sysCode")String sysCode);


	/**
	 * 新增用户
	 *
	 * @param sysUserId 用户id
	 * @param sysCode 客户端标识
	 * @return 结果
	 */
	public int isExistsUserClientRel(@Param("sysUserId")String sysUserId,
									   @Param("sysCode")String sysCode);


	/**
	 * 返回各client的Id，用逗号隔开
	 * @param sysUserId
	 * @return
	 */
	public String selectClientBySysUserId(@Param("sysUserId") String sysUserId);


	/**
	 * 删除用户应用关联
	 */
	public int deleteIntoUserClientRel(@Param("sysUserId")String sysUserId,@Param("clientId")String clientId);
}