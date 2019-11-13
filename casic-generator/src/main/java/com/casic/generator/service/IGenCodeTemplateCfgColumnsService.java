package com.casic.generator.service;

import com.casic.generator.domain.GenCodeTemplateCfgColumns;

import java.util.List;

/**
 * 存贮当前方案所需要的字段，是table_columns的子集 服务层
 * 
 * @author yuzengwen
 * @date 2019-04-22
 */
public interface IGenCodeTemplateCfgColumnsService 
{
	/**
     * 查询存贮当前方案所需要的字段，是table_columns的子集信息
     * 
     * @param id 存贮当前方案所需要的字段，是table_columns的子集ID
     * @return 存贮当前方案所需要的字段，是table_columns的子集信息
     */
	public GenCodeTemplateCfgColumns selectGenCodeTemplateCfgColumnsById(String id);
	
	/**
     * 查询存贮当前方案所需要的字段，是table_columns的子集列表
     * 
     * @param genCodeTemplateCfgColumns 存贮当前方案所需要的字段，是table_columns的子集信息
     * @return 存贮当前方案所需要的字段，是table_columns的子集集合
     */
	public List<GenCodeTemplateCfgColumns> selectGenCodeTemplateCfgColumnsList(GenCodeTemplateCfgColumns genCodeTemplateCfgColumns);
	
	/**
     * 新增存贮当前方案所需要的字段，是table_columns的子集
     * 
     * @param genCodeTemplateCfgColumns 存贮当前方案所需要的字段，是table_columns的子集信息
     * @return 结果
     */
	public int insertGenCodeTemplateCfgColumns(GenCodeTemplateCfgColumns genCodeTemplateCfgColumns);
	
	/**
     * 修改存贮当前方案所需要的字段，是table_columns的子集
     * 
     * @param genCodeTemplateCfgColumns 存贮当前方案所需要的字段，是table_columns的子集信息
     * @return 结果
     */
	public int updateGenCodeTemplateCfgColumns(GenCodeTemplateCfgColumns genCodeTemplateCfgColumns);
		
	/**
     * 删除存贮当前方案所需要的字段，是table_columns的子集信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteGenCodeTemplateCfgColumnsByIds(String ids);
	
}
