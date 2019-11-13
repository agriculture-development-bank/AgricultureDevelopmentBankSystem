package com.casic.auth.service;

import java.util.List;
import com.casic.common.web.domain.bo.OAuthClientDetails;
import com.casic.common.web.domain.vo.GenCodeDomainVo;

/**
 * 终端定义 服务层
 * 
 * @author yuzengwen
 * @date 2019-04-15
 */
public interface IOAuthClientDetailsService
{
	/**
     * 查询终端定义信息
     * 
     * @param clientId 终端定义ID
     * @return 终端定义信息
     */
	public OAuthClientDetails selectOAuthClientDetailsById(String clientId);
	
	/**
     * 查询终端定义列表
     * 
     * @param oauthClientDetails 终端定义信息
     * @return 终端定义集合
     */
	public List<OAuthClientDetails> selectOAuthClientDetailsList(OAuthClientDetails oauthClientDetails);
	
	/**
     * 新增终端定义
     * 
     * @param oauthClientDetails 终端定义信息
     * @return 结果
     */
	public int insertOAuthClientDetails(OAuthClientDetails oauthClientDetails);
	
	/**
     * 修改终端定义
     * 
     * @param oauthClientDetails 终端定义信息
     * @return 结果
     */
	public int updateOAuthClientDetails(OAuthClientDetails oauthClientDetails);
		
	/**
     * 删除终端定义信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteOAuthClientDetailsByIds(String ids);

	/**
	 * 查询GenCodeDomainList
	 */
	public List<GenCodeDomainVo> selectGenCodeDomainList();

	/**
	 * 根据domainId 查询应用
	 */
	public List<OAuthClientDetails> selectOAuthClientDetailsByDomainId(String domainId);
}
