package com.casic.generator.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.casic.generator.mapper.CodeDatasourceMapper;
import com.casic.generator.domain.CodeDatasource;
import com.casic.generator.service.ICodeDatasourceService;
import com.casic.common.support.Convert;

/**
 * 数据源管理 服务层实现
 * 
 * @author fangzhi
 * @date 2019-04-03
 */
@Service
public class CodeDatasourceServiceImpl implements ICodeDatasourceService 
{
	@Autowired
	private CodeDatasourceMapper codeDatasourceMapper;

	/**
     * 查询数据源管理信息
     * 
     * @param id 数据源管理ID
     * @return 数据源管理信息
     */
    @Override
	public CodeDatasource selectCodeDatasourceById(String id)
	{
	    return codeDatasourceMapper.selectCodeDatasourceById(id);
	}
	
	/**
     * 查询数据源管理列表
     * 
     * @param codeDatasource 数据源管理信息
     * @return 数据源管理集合
     */
	@Override
	public List<CodeDatasource> selectCodeDatasourceList(CodeDatasource codeDatasource)
	{
	    return codeDatasourceMapper.selectCodeDatasourceList(codeDatasource);
	}
	
    /**
     * 新增数据源管理
     * 
     * @param codeDatasource 数据源管理信息
     * @return 结果
     */
	@Override
	public int insertCodeDatasource(CodeDatasource codeDatasource)
	{
	    return codeDatasourceMapper.insertCodeDatasource(codeDatasource);
	}
	
	/**
     * 修改数据源管理
     * 
     * @param codeDatasource 数据源管理信息
     * @return 结果
     */
	@Override
	public int updateCodeDatasource(CodeDatasource codeDatasource)
	{
	    return codeDatasourceMapper.updateCodeDatasource(codeDatasource);
	}

	/**
     * 删除数据源管理对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteCodeDatasourceByIds(String ids)
	{
		return codeDatasourceMapper.deleteCodeDatasourceByIds(Convert.toStrArray(ids));
	}
	
}
