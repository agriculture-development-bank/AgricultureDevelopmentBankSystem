package com.casic.generator.service;

import com.casic.generator.domain.CodeDatasource;
import java.util.List;

/**
 * 数据源管理 服务层
 * 
 * @author fangzhi
 * @date 2019-04-03
 */
public interface ICodeDatasourceService 
{
	/**
     * 查询数据源管理信息
     * 
     * @param id 数据源管理ID
     * @return 数据源管理信息
     */
	public CodeDatasource selectCodeDatasourceById(String id);
	
	/**
     * 查询数据源管理列表
     * 
     * @param codeDatasource 数据源管理信息
     * @return 数据源管理集合
     */
	public List<CodeDatasource> selectCodeDatasourceList(CodeDatasource codeDatasource);
	
	/**
     * 新增数据源管理
     * 
     * @param codeDatasource 数据源管理信息
     * @return 结果
     */
	public int insertCodeDatasource(CodeDatasource codeDatasource);
	
	/**
     * 修改数据源管理
     * 
     * @param codeDatasource 数据源管理信息
     * @return 结果
     */
	public int updateCodeDatasource(CodeDatasource codeDatasource);
		
	/**
     * 删除数据源管理信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteCodeDatasourceByIds(String ids);
	
}
