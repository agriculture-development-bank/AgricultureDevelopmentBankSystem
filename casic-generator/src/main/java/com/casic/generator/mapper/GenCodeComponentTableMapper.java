package com.casic.generator.mapper;

import com.casic.generator.domain.GenCodeComponentTable;
import com.casic.generator.domain.GenCodeTableColumns;
import com.casic.generator.domain.gen.TableInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 组件对应的数据配置 数据层
 * 
 * @author yuzengwen
 * @date 2019-04-22
 */
public interface GenCodeComponentTableMapper 
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
     * 删除组件对应的数据配置
     * 
     * @param id 组件对应的数据配置ID
     * @return 结果
     */
	public int deleteGenCodeComponentTableById(String id);
	
	/**
     * 批量删除组件对应的数据配置
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteGenCodeComponentTableByIds(String[] ids);

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
    List<TableInfo> selectTableList(@Param("tableName") String tableName, @Param("databaseName") String databaseName);

	/**
	 * 根据scheme和表名查询表信息
	 *
	 * @param tableSchema
	 * @param tableName 表名
	 * @return
	 */
    Map<String, Object> selectTableInfoByTableNameAndTableSchema(@Param("tableSchema") String tableSchema, @Param("tableName") String tableName);

	/**
	 * 根据scheme和表名查询列信息
	 * @param tableSchema
	 * @param tableName
	 * @return
	 */
    List<Map<String, Object>> selectColumnsByTableSchemaAndTableName(@Param("tableSchema") String tableSchema, @Param("tableName") String tableName);

	/**
	 * 校验唯一性
	 *
	 * @param filedName
	 * @param fieldValue
	 * @return
	 */
    GenCodeComponentTable checkFieldUnique(@Param("filedName") String filedName, @Param("fieldValue") String fieldValue);

	/**
	 * 通过schema和表名查询表信息
	 *
	 * @param table_schema
	 * @param table_name
	 * @param column_name
	 * @return
	 */
    Map<String, Object> selectTableByTableSchemaAndTableName(@Param("table_schema") String table_schema, @Param("table_name") String table_name, @Param("column_name") String column_name);



	/**
	 * 删除表关联关系
	 * @param tableId
	 * @return
	 */
	public Integer deleteGenCodeFunctionTableRel(@Param("tableId") String tableId);

}