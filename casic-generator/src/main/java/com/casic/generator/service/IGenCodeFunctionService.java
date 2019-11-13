package com.casic.generator.service;

import com.casic.common.web.domain.bo.AuthResource;
import com.casic.common.web.domain.bo.OAuthClientDetails;
import com.casic.generator.domain.GenCodeComponent;
import com.casic.generator.domain.GenCodeFunction;
import com.casic.generator.domain.GenCodeFunctionTable;

import java.util.List;
import java.util.Map;

/**
 * 描述代码生成功能的配置 服务层
 * 
 * @author yuzengwen
 * @date 2019-04-22
 */
public interface IGenCodeFunctionService 
{
	/**
     * 查询描述代码生成功能的配置信息
     * 
     * @param id 描述代码生成功能的配置ID
     * @return 描述代码生成功能的配置信息
     */
	public GenCodeFunction selectGenCodeFunctionById(String id);
	
	/**
     * 查询描述代码生成功能的配置列表
     * 
     * @param genCodeFunction 描述代码生成功能的配置信息
     * @return 描述代码生成功能的配置集合
     */
	public List<GenCodeFunction> selectGenCodeFunctionList(GenCodeFunction genCodeFunction);
	
	/**
     * 新增描述代码生成功能的配置
     * 
     * @param genCodeFunction 描述代码生成功能的配置信息
     * @return 结果
     */
	public int insertGenCodeFunction(GenCodeFunction genCodeFunction);
	
	/**
     * 修改描述代码生成功能的配置
     * 
     * @param genCodeFunction 描述代码生成功能的配置信息
     * @return 结果
     */
	public int updateGenCodeFunction(GenCodeFunction genCodeFunction);
		
	/**
     * 删除描述代码生成功能的配置信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteGenCodeFunctionByIds(String ids);

	/**
	 * 插入组件和功能关系
	 *
	 * @param params
	 * @return
	 */
    int insertGenCodeComponentFunction(Map<String, Object> params);

	/**
	 * 保存功能与表的关系
	 *
	 * @param list
	 * @return
	 */
	int insertGenCodeFunctionTable(List<GenCodeFunctionTable> list);

	/**
	 * 通过功能id查找组件信息
	 *
	 * @param id 功能id
	 * @return
	 */
    GenCodeComponent selectGenCodeComponentByFunctionId(String id);

	/**
	 * 查询所有菜单信息
	 *
	 * @return
     * @param clientId
	 */
	List<Map<String, Object>> menuTreeData(String clientId);

	/**
	 * 根据功能id查询所包含的表
	 *
	 * @param id 功能id
	 * @return
	 */
    List<Map> selectTableByFunctionId(String id);

	/**
	 * 根据功能id删除功能与表之间的关系
	 *
	 * @param functionId 功能id
	 * @return
	 */
	int deleteGenCodeFunctionTableByFunctionId(String functionId);

	/**
	 * 根据功能id批量删除功能与表之间的关系
	 *
	 * @param funtionIds
	 * @return
	 */
    int deleteGenCodeFunctionTableByFunctionIds(String[] funtionIds);

	/**
	 * 根据组件id查询该组件下的所有功能
	 *
	 * @param params 查询参数集合
	 * @return 功能集合
	 */
	List<GenCodeFunction> selectGenCodeFunctionListByComponentId(Map<String, Object> params);

	/**
	 * 根据组件查询所属应用信息
	 *
	 * @param componentId 组件id
	 * @return
	 */
    OAuthClientDetails selectOauthClientDetailsByComponentId(String componentId);

	/**
	 * 根据应用ID查询信息
	 *
	 * @param clientId 用户id
	 * @return
	 */
	AuthResource selectResourceByClientId(String clientId);
}
