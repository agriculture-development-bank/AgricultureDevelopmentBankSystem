package com.casic.auth.service.impl;

import java.util.List;

import com.casic.auth.mapper.OAuthClientDetailsMapper;
import com.casic.auth.service.IOAuthClientDetailsService;
import com.casic.common.web.domain.bo.OAuthClientDetails;
import com.casic.common.web.domain.vo.GenCodeDomainVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.casic.common.support.Convert;

/**
 * 终端定义 服务层实现
 * 
 * @author yuzengwen
 * @date 2019-04-15
 */
@Service
public class OAuthClientDetailsServiceImpl implements IOAuthClientDetailsService
{
	@Autowired
	private OAuthClientDetailsMapper oauthClientDetailsMapper;

	/**
     * 查询终端定义信息
     * 
     * @param clientId 终端定义ID
     * @return 终端定义信息
     */
    @Override
	public OAuthClientDetails selectOAuthClientDetailsById(String clientId)
	{
	    return oauthClientDetailsMapper.selectOAuthClientDetailsById(clientId);
	}
	
	/**
     * 查询终端定义列表
     * 
     * @param oauthClientDetails 终端定义信息
     * @return 终端定义集合
     */
	@Override
	public List<OAuthClientDetails> selectOAuthClientDetailsList(OAuthClientDetails oauthClientDetails)
	{
	    return oauthClientDetailsMapper.selectOAuthClientDetailsList(oauthClientDetails);
	}
	
    /**
     * 新增终端定义
     * 
     * @param oauthClientDetails 终端定义信息
     * @return 结果
     */
	@Override
	public int insertOAuthClientDetails(OAuthClientDetails oauthClientDetails)
	{
	    return oauthClientDetailsMapper.insertOAuthClientDetails(oauthClientDetails);
	}
	
	/**
     * 修改终端定义
     * 
     * @param oauthClientDetails 终端定义信息
     * @return 结果
     */
	@Override
	public int updateOAuthClientDetails(OAuthClientDetails oauthClientDetails)
	{
	    return oauthClientDetailsMapper.updateOAuthClientDetails(oauthClientDetails);
	}

	/**
     * 删除终端定义对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteOAuthClientDetailsByIds(String ids)
	{
		return oauthClientDetailsMapper.deleteOAuthClientDetailsByIds(Convert.toStrArray(ids));
	}

	/**
	 * 查询GenCodeDomainList
	 */
	@Override
	public List<GenCodeDomainVo> selectGenCodeDomainList(){
		return oauthClientDetailsMapper.selectGenCodeDomainList();
	}

	/**
	 * 根据domainId 查询应用
	 */
	@Override
	public List<OAuthClientDetails> selectOAuthClientDetailsByDomainId(String domainId){
		return oauthClientDetailsMapper.selectOAuthClientDetailsByDomainId(domainId);
	}
	
}
