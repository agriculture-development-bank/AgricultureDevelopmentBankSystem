package com.casic.generator.mapper;

import com.casic.generator.domain.GenCodeDomain;

import java.util.List;

/**
 * 可支持生成的领域配置 数据层
 * 
 * @author yuzengwen
 * @date 2019-04-22
 */
public interface GenCodeDomainMapper 
{
	/**
     * 查询可支持生成的领域配置信息
     * 
     * @param id 可支持生成的领域配置ID
     * @return 可支持生成的领域配置信息
     */
	public GenCodeDomain selectGenCodeDomainById(String id);
	
	/**
     * 查询可支持生成的领域配置列表
     * 
     * @param genCodeDomain 可支持生成的领域配置信息
     * @return 可支持生成的领域配置集合
     */
	public List<GenCodeDomain> selectGenCodeDomainList(GenCodeDomain genCodeDomain);
	
	/**
     * 新增可支持生成的领域配置
     * 
     * @param genCodeDomain 可支持生成的领域配置信息
     * @return 结果
     */
	public int insertGenCodeDomain(GenCodeDomain genCodeDomain);
	
	/**
     * 修改可支持生成的领域配置
     * 
     * @param genCodeDomain 可支持生成的领域配置信息
     * @return 结果
     */
	public int updateGenCodeDomain(GenCodeDomain genCodeDomain);
	
	/**
     * 删除可支持生成的领域配置
     * 
     * @param id 可支持生成的领域配置ID
     * @return 结果
     */
	public int deleteGenCodeDomainById(String id);
	
	/**
     * 批量删除可支持生成的领域配置
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteGenCodeDomainByIds(String[] ids);

}