package com.casic.generator.mapper;

import com.casic.generator.domain.GenCodeComponent;
import com.casic.generator.domain.vo.GenCodeComponentVo;

import java.util.List;

/**
 * 组件配置 数据层
 * 
 * @author yuzengwen
 * @date 2019-04-22
 */
public interface GenCodeComponentMapper 
{
	/**
     * 查询组件配置信息
     * 
     * @param id 组件配置ID
     * @return 组件配置信息
     */
	public GenCodeComponentVo selectGenCodeComponentById(String id);
	
	/**
     * 查询组件配置列表
     * 
     * @param genCodeComponent 组件配置信息
     * @return 组件配置集合
     */
	public List<GenCodeComponentVo> selectGenCodeComponentList(GenCodeComponentVo genCodeComponent);
	
	/**
     * 新增组件配置
     * 
     * @param genCodeComponent 组件配置信息
     * @return 结果
     */
	public int insertGenCodeComponent(GenCodeComponent genCodeComponent);

	/**
	 * 增加关联表信息
	 */
	public int insertGenCodeDomainComponent(GenCodeComponentVo genCodeComponent);
	
	/**
     * 修改组件配置
     * 
     * @param genCodeComponent 组件配置信息
     * @return 结果
     */
	public int updateGenCodeComponent(GenCodeComponent genCodeComponent);

	/**
	 * 增加关联表信息
	 */
	public int updateGenCodeDomainComponent(GenCodeComponentVo genCodeComponent);
	
	/**
     * 删除组件配置
     * 
     * @param id 组件配置ID
     * @return 结果
     */
	public int deleteGenCodeComponentById(String id);
	
	/**
     * 批量删除组件配置
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteGenCodeComponentByIds(String[] ids);

	/**
	 * 批量删除关联表信息
	 *
	 */
	public int deleteGenCodeDomainComponentByIds(String[] ids);

	/**
	 * 根据componentName 获取 GenCodeComponent对象的id 和 componentName
	 * @param componentName
	 * @return
	 */
	public GenCodeComponent checkComponentNameUnique(String componentName);

	/**
	 * 根据componentCode 获取 GenCodeComponent对象的id 和 componentCode
	 * @param componentCode
	 * @return
	 */
	public GenCodeComponent checkComponentCodeUnique(String componentCode);

	/**
	 * 根据clientId 获取 GenCodeComponent对象的id 和 clientId
	 * @param clientId
	 * @return
	 */
	public GenCodeComponent checkClientIdUnique(String clientId);
	
}