package com.casic.generator.service.impl;

import com.casic.common.support.Convert;
import com.casic.generator.domain.GenCodeComponentTable;
import com.casic.generator.domain.GenCodeTableColumns;
import com.casic.generator.domain.gen.TableInfo;
import com.casic.generator.mapper.GenCodeComponentTableMapper;
import com.casic.generator.service.IGenCodeComponentTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 组件对应的数据配置 服务层实现
 * 
 * @author yuzengwen
 * @date 2019-04-22
 */
@Service
public class GenCodeComponentTableServiceImpl implements IGenCodeComponentTableService
{
	@Autowired
	private GenCodeComponentTableMapper genCodeComponentTableMapper;

	/**
     * 查询组件对应的数据配置信息
     * 
     * @param id 组件对应的数据配置ID
     * @return 组件对应的数据配置信息
     */
    @Override
	public GenCodeComponentTable selectGenCodeComponentTableById(String id)
	{
	    return genCodeComponentTableMapper.selectGenCodeComponentTableById(id);
	}
	
	/**
     * 查询组件对应的数据配置列表
     * 
     * @param genCodeComponentTable 组件对应的数据配置信息
     * @return 组件对应的数据配置集合
     */
	@Override
	public List<GenCodeComponentTable> selectGenCodeComponentTableList(GenCodeComponentTable genCodeComponentTable)
	{
	    return genCodeComponentTableMapper.selectGenCodeComponentTableList(genCodeComponentTable);
	}
	
    /**
     * 新增组件对应的数据配置
     * 
     * @param genCodeComponentTable 组件对应的数据配置信息
     * @return 结果
     */
	@Override
	public int insertGenCodeComponentTable(GenCodeComponentTable genCodeComponentTable)
	{
	    return genCodeComponentTableMapper.insertGenCodeComponentTable(genCodeComponentTable);
	}
	
	/**
     * 修改组件对应的数据配置
     * 
     * @param genCodeComponentTable 组件对应的数据配置信息
     * @return 结果
     */
	@Override
	public int updateGenCodeComponentTable(GenCodeComponentTable genCodeComponentTable)
	{
	    return genCodeComponentTableMapper.updateGenCodeComponentTable(genCodeComponentTable);
	}

	/**
     * 删除组件对应的数据配置对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteGenCodeComponentTableByIds(String ids)
	{
		return genCodeComponentTableMapper.deleteGenCodeComponentTableByIds(Convert.toStrArray(ids));
	}

	/**
	 * 根据表名查询表中的列字段
	 *
	 * @param tableId 表主键
	 * @return
	 */
    @Override
    public List<GenCodeTableColumns> selectTableFieldByTableId(String tableId) {
        return genCodeComponentTableMapper.selectTableFieldByTableId(tableId);
    }

	/**
	 * 根据数据源和表名查询
	 *
	 * @param tableName
	 * @param databaseName
	 * @return
	 */
    @Override
    public List<TableInfo> selectTableList(String tableName, String databaseName) {
        return genCodeComponentTableMapper.selectTableList(tableName, databaseName);
    }

	/**
	 * 根据scheme和表名查询表信息
	 *
	 * @param tableSchema
	 * @param tableName 表名
	 * @return
	 */
    @Override
    public Map<String, Object> selectTableInfoByTableNameAndTableSchema(String tableSchema, String tableName) {
        return genCodeComponentTableMapper.selectTableInfoByTableNameAndTableSchema(tableSchema, tableName);
    }

	/**
	 * 根据scheme和表名查询列信息
	 * @param tableSchema
	 * @param tableName
	 * @return
	 */
    @Override
    public List<Map<String, Object>> selectColumnsByTableSchemaAndTableName(String tableSchema, String tableName) {
        return genCodeComponentTableMapper.selectColumnsByTableSchemaAndTableName(tableSchema, tableName);
    }

	/**
	 * 校验唯一性
	 *
	 *
	 * @param codeComponentTable
	 * @param filedName
	 * @param fieldValue
	 * @return
	 */
    @Override
    public String checkFieldUnique(GenCodeComponentTable codeComponentTable, String filedName, String fieldValue) {
		String id = codeComponentTable.getId();
		GenCodeComponentTable genCodeComponentTable = genCodeComponentTableMapper.checkFieldUnique(filedName, fieldValue);
    	if (genCodeComponentTable != null && !genCodeComponentTable.getId().equals(id)) {
    		return "1";
		} else {
    		return "0";
		}
    }

	/**
	 * 通过schema和表名查询表信息
	 *
	 * @param table_schema
	 * @param table_name
	 * @param column_name
	 * @return
	 */
    @Override
    public Map<String, Object> selectTableByTableSchemaAndTableName(String table_schema, String table_name, String column_name) {
        return genCodeComponentTableMapper.selectTableByTableSchemaAndTableName(table_schema, table_name, column_name);
    }

	@Override
	public Integer deleteGenCodeFunctionTableRel(String tableId) {
		return genCodeComponentTableMapper.deleteGenCodeFunctionTableRel(tableId);
	}


}
