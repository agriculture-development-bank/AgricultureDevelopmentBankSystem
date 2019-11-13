package com.casic.auth.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.casic.common.web.domain.bo.AuthUser;
import com.casic.common.web.domain.vo.AuthUserVo;

/**
 * 用户 服务层
 * 
 * @author yuzengwen
 * @date 2018-10-25
 */
public interface IAuthUserService 
{
	/**
     * 查询用户信息
     * 
     * @param uid 用户ID
     * @return 用户信息
     */
	public AuthUserVo selectUserById(String uid);

	/**
	 * 查询用户信息
	 *
	 * @param username 用户name
	 * @return 用户信息
	 */
	public AuthUserVo selectUserByLoginName(String username);

	
	/**
     * 查询用户列表
     * 
     * @param user 用户信息
     * @return 用户集合
     */
	public List<AuthUser> selectUserList(AuthUser user,String clientId);
	
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
	public int updateUser(AuthUser user,String clientId);
		
	/**
     * 删除用户信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteUserByIds(String ids,String clientId);
	
	
	
	/**
	 * 用户名唯一性校验
	 * userName
	 */
	public String checkUserNameUnique(String userName);
	
	
	/**
     * 校验手机号码
     */
    public String checkPhoneUnique(AuthUser user);

    /**
     * 校验email邮箱
     */
    public String checkEmailUnique(AuthUser user);


	/**
	 * 根据用户id查询菜单列表
	 */
	public JSONObject selectResourcesByUserId(String uid,String sysUserId);


	/**
	 * 根据sysUserId查询AuthUser
	 * @param sysUserId
	 * @return
	 */
	public AuthUser selectUserBySysUserId(String sysUserId);

	/**
	 * 根据条件查询用户对象
	 */
	public List<AuthUserVo> selectCandidateList(String deptCode, String careerCode);


	/**
	 * 插入对应关系
	 */
	public int insertIntoUserClientRel(String sysUserId, String clientId);


	/**
	 * 查询对应关系
	 */
	public int isExistsUserClientRel(String sysUserId, String clientId);


	/**
	 * 返回各client的Id，用逗号隔开
	 * @param sysUserId
	 * @return
	 */
	public String selectClientBySysUserId(String sysUserId);


}
