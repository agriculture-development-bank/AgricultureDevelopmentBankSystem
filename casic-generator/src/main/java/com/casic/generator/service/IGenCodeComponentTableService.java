package com.casic.generator.service;

import com.casic.generator.domain.GenCodeComponentTable;
import com.casic.generator.domain.GenCodeTableColumns;
import com.casic.generator.domain.gen.TableInfo;

import java.util.List;
import java.util.Map;

/**
 * 组件对应的数据配置 服务层
 * 
 * @author yuzengwen
 * @date 2019-04-22
 */
public interface IGenCodeComponentTableService 
{
	/**
     * 查询组件对应的数据配置信息
     * 
     * @param id 组件对应的数据配置ID
     * @return 组件对应的数据配置信息
     */
	public GenCodeComponentTable selectGenCodeComponentTableById(String id);
	
	/**
     * 查询组件对应的数据配置列表
     * 
     * @param genCodeComponentTable 组件对应的数据配置信息
     * @return 组件对应的数据配置集合
     */
	public List<GenCodeComponentTable> selectGenCodeComponentTableList(GenCodeComponentTable genCodeComponentTable);
	
	/**
     * 新增组件对应的数据配置
     * 
     * @param genCodeComponentTable 组件对应的数据配置信息
     * @return 结果
     */
	public int insertGenCodeComponentTable(GenCodeComponentTable genCodeComponentTable);
	
	/**
     * 修改组件对应的数据配置
     * 
     * @param genCodeComponentTable 组件对应的数据配置信息
     * @return 结果
     */
	public int updateGenCodeComponentTable(GenCodeComponentTable genCodeComponentTable);
		
	/**
     * 删除组件对应的数据配置信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteGenCodeComponentTableByIds(String ids);

	/**
	 * 根据表名查询表中的列字段
	 *
	 * @param tableId 表名
	 * @return
	 */
    public List<GenCodeTableColumns> selectTableFieldByTableId(String tableId);

	/**
	 * 根据数据源和表名查询
	 *
	 * @param tableName
     * @param databaseName
     * @return
	 */
    List<TableInfo> selectTableList(String tableName, String databaseName);

	/**
	 * 根据scheme和表名查询表信息
	 *
	 * @param tableSchema
	 * @param tableName 表名
	 * @return
	 */
    Map<String, Object> selectTableInfoByTableNameAndTableSchema(String tableSchema, String tableName);

	/**
	 * 根据scheme和表名查询列信息
	 * @param tableSchema
	 * @param p
	 * @return
	 */
    List<Map<String, Object>> selectColumnsByTableSchemaAndTableName(String tableSchema, String p);

	/**
	 * 校验唯一性
	 *
	 *
	 * @param genCodeComponentTable
	 * @param filedName
	 * @param fieldValue
	 * @return
	 */
    String checkFieldUnique(GenCodeComponentTable genCodeComponentTable, String filedName, String fieldValue);


	/**
	 * 删除表关联关系
	 * @param tableId
	 * @return
	 */
	public Integer deleteGenCodeFunctionTableRel(String tableId);


	/**
	 * 通过schema和表名查询表信息
	 *
	 * @param table_schema
     * @param table_name
     * @param column_name
     * @return
	 */
    Map<String, Object> selectTableByTableSchemaAndTableName(String table_schema, String table_name, String column_name);
}
