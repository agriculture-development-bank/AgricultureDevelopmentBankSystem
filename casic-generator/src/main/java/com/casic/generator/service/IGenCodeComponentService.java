package com.casic.generator.service;

import com.casic.generator.domain.GenCodeComponent;
import com.casic.generator.domain.vo.GenCodeComponentVo;

import java.util.List;

/**
 * 组件配置 服务层
 * 
 * @author yuzengwen
 * @date 2019-04-22
 */
public interface IGenCodeComponentService 
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
	public int insertGenCodeComponent(GenCodeComponentVo genCodeComponent);
	
	/**
     * 修改组件配置
     * 
     * @param genCodeComponent 组件配置信息
     * @return 结果
     */
	public int updateGenCodeComponent(GenCodeComponentVo genCodeComponent);
		
	/**
     * 删除组件配置信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteGenCodeComponentByIds(String ids);

	/**
	 * 校验ComponentName是否唯一
	 *
	 * @param genCodeComponent
	 * @return 结果
	 */
	public String checkComponentNameUnique(GenCodeComponent genCodeComponent);

	/**
	 * 校验ComponentCode是否唯一
	 *
	 * @param genCodeComponent
	 * @return 结果
	 */
	public String checkComponentCodeUnique(GenCodeComponent genCodeComponent);

	/**
	 * 校验clientId是否唯一
	 *
	 * @param genCodeComponent
	 * @return 结果
	 */
	public String checkClientIdUnique(GenCodeComponent genCodeComponent);
	
}
