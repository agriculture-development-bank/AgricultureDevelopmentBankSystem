package com.casic.generator.service.impl;

import java.util.List;

import com.casic.generator.domain.GenCodeTemplateCfgColumns;
import com.casic.generator.mapper.GenCodeTemplateCfgColumnsMapper;
import com.casic.generator.service.IGenCodeTemplateCfgColumnsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.casic.common.support.Convert;

/**
 * 存贮当前方案所需要的字段，是table_columns的子集 服务层实现
 * 
 * @author yuzengwen
 * @date 2019-04-22
 */
@Service
public class GenCodeTemplateCfgColumnsServiceImpl implements IGenCodeTemplateCfgColumnsService
{
	@Autowired
	private GenCodeTemplateCfgColumnsMapper genCodeTemplateCfgColumnsMapper;

	/**
     * 查询存贮当前方案所需要的字段，是table_columns的子集信息
     * 
     * @param id 存贮当前方案所需要的字段，是table_columns的子集ID
     * @return 存贮当前方案所需要的字段，是table_columns的子集信息
     */
    @Override
	public GenCodeTemplateCfgColumns selectGenCodeTemplateCfgColumnsById(String id)
	{
	    return genCodeTemplateCfgColumnsMapper.selectGenCodeTemplateCfgColumnsById(id);
	}
	
	/**
     * 查询存贮当前方案所需要的字段，是table_columns的子集列表
     * 
     * @param genCodeTemplateCfgColumns 存贮当前方案所需要的字段，是table_columns的子集信息
     * @return 存贮当前方案所需要的字段，是table_columns的子集集合
     */
	@Override
	public List<GenCodeTemplateCfgColumns> selectGenCodeTemplateCfgColumnsList(GenCodeTemplateCfgColumns genCodeTemplateCfgColumns)
	{
	    return genCodeTemplateCfgColumnsMapper.selectGenCodeTemplateCfgColumnsList(genCodeTemplateCfgColumns);
	}
	
    /**
     * 新增存贮当前方案所需要的字段，是table_columns的子集
     * 
     * @param genCodeTemplateCfgColumns 存贮当前方案所需要的字段，是table_columns的子集信息
     * @return 结果
     */
	@Override
	public int insertGenCodeTemplateCfgColumns(GenCodeTemplateCfgColumns genCodeTemplateCfgColumns)
	{
	    return genCodeTemplateCfgColumnsMapper.insertGenCodeTemplateCfgColumns(genCodeTemplateCfgColumns);
	}
	
	/**
     * 修改存贮当前方案所需要的字段，是table_columns的子集
     * 
     * @param genCodeTemplateCfgColumns 存贮当前方案所需要的字段，是table_columns的子集信息
     * @return 结果
     */
	@Override
	public int updateGenCodeTemplateCfgColumns(GenCodeTemplateCfgColumns genCodeTemplateCfgColumns)
	{
	    return genCodeTemplateCfgColumnsMapper.updateGenCodeTemplateCfgColumns(genCodeTemplateCfgColumns);
	}

	/**
     * 删除存贮当前方案所需要的字段，是table_columns的子集对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteGenCodeTemplateCfgColumnsByIds(String ids)
	{
		return genCodeTemplateCfgColumnsMapper.deleteGenCodeTemplateCfgColumnsByIds(Convert.toStrArray(ids));
	}
	
}
