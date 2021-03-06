package com.casic.auth.service;

import java.util.List;
import java.util.Map;

import com.casic.common.web.domain.bo.AuthResource;

/**
 * 资源(菜单,资源) 服务层
 * 
 * @author yuzengwen
 * @date 2018-10-25
 */
public interface IAuthResourceService 
{
	/**
     * 查询资源(菜单,资源)信息
     * 
     * @param id 资源(菜单,资源)ID
     * @return 资源(菜单,资源)信息
     */
	public AuthResource selectResourceById(Integer id);
	
	/**
     * 查询资源(菜单,资源)列表
     * 
     * @param resource 资源(菜单,资源)信息
     * @return 资源(菜单,资源)集合
     */
	public List<AuthResource> selectResourceList(AuthResource resource);
	
	/**
     * 新增资源(菜单,资源)
     * 
     * @param resource 资源(菜单,资源)信息
     * @return 结果
     */
	public int insertResource(AuthResource resource);
	
	/**
     * 修改资源(菜单,资源)
     * 
     * @param resource 资源(菜单,资源)信息
     * @return 结果
     */
	public int updateResource(AuthResource resource);
		
	/**
     * 删除资源(菜单,资源)信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteResourceByIds(String ids);
	
	
	public List<Map<String, Object>> menuTreeData(String sysCode);

	/**
	 * 查询菜单数量
	 *
	 * @param parentId 菜单父ID
	 * @return 结果
	 */
	public int selectCountResourceByParentId(Integer parentId);
	
}
