package com.casic.generator.mapper;

import com.casic.generator.domain.GenCodeTableColumns;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 模块对应的字段 数据层
 * 
 * @author yuzengwen
 * @date 2019-04-22
 */
public interface GenCodeTableColumnsMapper 
{
	/**
     * 查询模块对应的字段信息
     * 
     * @param id 模块对应的字段ID
     * @return 模块对应的字段信息
     */
	public GenCodeTableColumns selectGenCodeTableColumnsById(String id);
	
	/**
     * 查询模块对应的字段列表
     * 
     * @param genCodeTableColumns 模块对应的字段信息
     * @return 模块对应的字段集合
     */
	public List<GenCodeTableColumns> selectGenCodeTableColumnsList(GenCodeTableColumns genCodeTableColumns);
	
	/**
     * 新增模块对应的字段
     * 
     * @param genCodeTableColumns 模块对应的字段信息
     * @return 结果
     */
	public int insertGenCodeTableColumns(GenCodeTableColumns genCodeTableColumns);
	
	/**
     * 修改模块对应的字段
     * 
     * @param genCodeTableColumns 模块对应的字段信息
     * @return 结果
     */
	public int updateGenCodeTableColumns(GenCodeTableColumns genCodeTableColumns);
	
	/**
     * 删除模块对应的字段
     * 
     * @param id 模块对应的字段ID
     * @return 结果
     */
	public int deleteGenCodeTableColumnsById(String id);
	
	/**
     * 批量删除模块对应的字段
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteGenCodeTableColumnsByIds(String[] ids);

	/**
	 * 根据表id查询表中的字段
	 *
	 * @param tableId  表id
	 * @return
	 */
    List<GenCodeTableColumns> selectGenCodeTableColumnsByTableId(String tableId);

	/**
	 * 校验字段的唯一性
	 *
	 *
     * @param tableId
     * @param fieldName
     * @param fieldValue
     * @return
	 */
    GenCodeTableColumns checkFieldUnique(@Param("tableId") String tableId, @Param("fieldName") String fieldName, @Param("fieldValue") String fieldValue);
}