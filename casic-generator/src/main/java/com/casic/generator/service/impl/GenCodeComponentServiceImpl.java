package com.casic.generator.service.impl;

import java.util.List;

import com.casic.common.constant.GenCodeComponentConstants;
import com.casic.generator.domain.GenCodeComponent;
import com.casic.generator.domain.vo.GenCodeComponentVo;
import com.casic.generator.mapper.GenCodeComponentMapper;
import com.casic.generator.service.IGenCodeComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.casic.common.support.Convert;

/**
 * 组件配置 服务层实现
 * 
 * @author yuzengwen
 * @date 2019-04-22
 */
@Service
public class GenCodeComponentServiceImpl implements IGenCodeComponentService
{
	@Autowired
	private GenCodeComponentMapper genCodeComponentMapper;

	/**
     * 查询组件配置信息
     * 
     * @param id 组件配置ID
     * @return 组件配置信息
     */
    @Override
	public GenCodeComponentVo selectGenCodeComponentById(String id)
	{
	    return genCodeComponentMapper.selectGenCodeComponentById(id);
	}
	
	/**
     * 查询组件配置列表
     * 
     * @param genCodeComponent 组件配置信息
     * @return 组件配置集合
     */
	@Override
	public List<GenCodeComponentVo> selectGenCodeComponentList(GenCodeComponentVo genCodeComponent)
	{
	    return genCodeComponentMapper.selectGenCodeComponentList(genCodeComponent);
	}
	
    /**
     * 新增组件配置
     * 
     * @param genCodeComponent 组件配置信息
     * @return 结果
     */
	@Override
	public int insertGenCodeComponent(GenCodeComponentVo genCodeComponent)
	{
		int row = genCodeComponentMapper.insertGenCodeComponent(genCodeComponent);
	    genCodeComponentMapper.insertGenCodeDomainComponent(genCodeComponent);
	    return row;
	}
	
	/**
     * 修改组件配置
     * 
     * @param genCodeComponent 组件配置信息
     * @return 结果
     */
	@Override
	public int updateGenCodeComponent(GenCodeComponentVo genCodeComponent)
	{
	    int row =  genCodeComponentMapper.updateGenCodeComponent(genCodeComponent);
	    genCodeComponentMapper.updateGenCodeDomainComponent(genCodeComponent);
	    return row;
	}

	/**
     * 删除组件配置对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteGenCodeComponentByIds(String ids)
	{
		int row =  genCodeComponentMapper.deleteGenCodeComponentByIds(Convert.toStrArray(ids));
		genCodeComponentMapper.deleteGenCodeDomainComponentByIds(Convert.toStrArray(ids));
		return row;
	}

	/**
	 * 校验ComponentName是否唯一
	 *
	 * @param genCodeComponent
	 * @return 结果
	 */
	@Override
	public String checkComponentNameUnique(GenCodeComponent genCodeComponent){
		String id = genCodeComponent.getId();
		GenCodeComponent info = genCodeComponentMapper.checkComponentNameUnique(genCodeComponent.getConmponentName());
		if(info != null && !info.getId().equals(id)){
			return GenCodeComponentConstants.COMPONENT_NAME_NOT_UNIQUE;
		}else{
			return GenCodeComponentConstants.COMPONENT_NAME_UNIQUE;
		}
	}

	/**
	 * 校验ComponentCode是否唯一
	 *
	 * @param genCodeComponent
	 * @return 结果
	 */
	@Override
	public String checkComponentCodeUnique(GenCodeComponent genCodeComponent){
		String id = genCodeComponent.getId();
		GenCodeComponent info = genCodeComponentMapper.checkComponentCodeUnique(genCodeComponent.getConmponentCode());
		if(info != null && !info.getId().equals(id)){
			return GenCodeComponentConstants.COMPONENT_CODE_NOT_UNIQUE;
		}else{
			return GenCodeComponentConstants.COMPONENT_CODE_UNIQUE;
		}
	}

	/**
	 * 校验clientId是否唯一
	 *
	 * @param genCodeComponent
	 * @return 结果
	 */
	public String checkClientIdUnique(GenCodeComponent genCodeComponent){
		String id = genCodeComponent.getId();
		GenCodeComponent info = genCodeComponentMapper.checkClientIdUnique(genCodeComponent.getClientId());
		if(info != null && !info.getId().equals(id)){
			return GenCodeComponentConstants.CLIENT_ID_NOT_UNIQUE;
		}else{
			return GenCodeComponentConstants.CLIENT_ID_UNIQUE;
		}
	}
}
