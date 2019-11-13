package com.casic.generator.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.casic.generator.domain.GenCodeComponent;
import com.casic.generator.domain.GenCodeDomain;
import com.casic.generator.mapper.GenCodeDomainMapper;
import com.casic.generator.service.IGenCodeDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.casic.common.support.Convert;

/**
 * 可支持生成的领域配置 服务层实现
 * 
 * @author yuzengwen
 * @date 2019-04-22
 */
@Service
public class GenCodeDomainServiceImpl implements IGenCodeDomainService
{
	@Autowired
	private GenCodeDomainMapper genCodeDomainMapper;

	/**
     * 查询可支持生成的领域配置信息
     * 
     * @param id 可支持生成的领域配置ID
     * @return 可支持生成的领域配置信息
     */
    @Override
	public GenCodeDomain selectGenCodeDomainById(String id)
	{
	    return genCodeDomainMapper.selectGenCodeDomainById(id);
	}
	
	/**
     * 查询可支持生成的领域配置列表
     * 
     * @param genCodeDomain 可支持生成的领域配置信息
     * @return 可支持生成的领域配置集合
     */
	@Override
	public List<GenCodeDomain> selectGenCodeDomainList(GenCodeDomain genCodeDomain)
	{
	    return genCodeDomainMapper.selectGenCodeDomainList(genCodeDomain);
	}
	
    /**
     * 新增可支持生成的领域配置
     * 
     * @param genCodeDomain 可支持生成的领域配置信息
     * @return 结果
     */
	@Override
	public int insertGenCodeDomain(GenCodeDomain genCodeDomain)
	{
	    return genCodeDomainMapper.insertGenCodeDomain(genCodeDomain);
	}
	
	/**
     * 修改可支持生成的领域配置
     * 
     * @param genCodeDomain 可支持生成的领域配置信息
     * @return 结果
     */
	@Override
	public int updateGenCodeDomain(GenCodeDomain genCodeDomain)
	{
	    return genCodeDomainMapper.updateGenCodeDomain(genCodeDomain);
	}

	/**
     * 删除可支持生成的领域配置对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteGenCodeDomainByIds(String ids)
	{
		return genCodeDomainMapper.deleteGenCodeDomainByIds(Convert.toStrArray(ids));
	}

	/**
	 * 获取领域组件树
	 *
	 * @param domainList
	 * @return
	 */
	public List<Map<String, Object>> getDomainComponentTrees(List<GenCodeDomain> domainList){
		List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
		for(GenCodeDomain domain : domainList){
			Map<String, Object> domainMap = new HashMap<String, Object>();
			domainMap.put("id", domain.getId());
			domainMap.put("pId", 0);
			domainMap.put("name", domain.getDomainName());
			domainMap.put("title", domain.getDomainName());
			domainMap.put("checked", false);
			trees.add(domainMap);
			List<GenCodeComponent> componentList = domain.getComponentList();
			if(componentList != null && componentList.size() > 0){
				for(GenCodeComponent component : componentList){
					Map<String, Object> componentMap = new HashMap<String, Object>();
					componentMap.put("id", component.getId());
					componentMap.put("pId", domain.getId());
					componentMap.put("name", component.getConmponentName());
					componentMap.put("title", component.getConmponentName());
					componentMap.put("checked", false);
					trees.add(componentMap);
				}
			}
		}

		return trees;
	}

}
