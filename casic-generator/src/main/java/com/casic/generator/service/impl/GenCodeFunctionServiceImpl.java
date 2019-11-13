package com.casic.generator.service.impl;

import com.casic.common.support.Convert;
import com.casic.common.web.domain.bo.AuthResource;
import com.casic.common.web.domain.bo.OAuthClientDetails;
import com.casic.generator.domain.GenCodeComponent;
import com.casic.generator.domain.GenCodeFunction;
import com.casic.generator.domain.GenCodeFunctionTable;
import com.casic.generator.mapper.GenCodeFunctionMapper;
import com.casic.generator.service.IGenCodeFunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述代码生成功能的配置 服务层实现
 * 
 * @author yuzengwen
 * @date 2019-04-22
 */
@Service
public class GenCodeFunctionServiceImpl implements IGenCodeFunctionService
{
	@Autowired
	private GenCodeFunctionMapper genCodeFunctionMapper;

	/**
     * 查询描述代码生成功能的配置信息
     * 
     * @param id 描述代码生成功能的配置ID
     * @return 描述代码生成功能的配置信息
     */
    @Override
	public GenCodeFunction selectGenCodeFunctionById(String id)
	{
	    return genCodeFunctionMapper.selectGenCodeFunctionById(id);
	}
	
	/**
     * 查询描述代码生成功能的配置列表
     * 
     * @param genCodeFunction 描述代码生成功能的配置信息
     * @return 描述代码生成功能的配置集合
     */
	@Override
	public List<GenCodeFunction> selectGenCodeFunctionList(GenCodeFunction genCodeFunction)
	{
	    return genCodeFunctionMapper.selectGenCodeFunctionList(genCodeFunction);
	}
	
    /**
     * 新增描述代码生成功能的配置
     * 
     * @param genCodeFunction 描述代码生成功能的配置信息
     * @return 结果
     */
	@Override
	public int insertGenCodeFunction(GenCodeFunction genCodeFunction)
	{
	    return genCodeFunctionMapper.insertGenCodeFunction(genCodeFunction);
	}
	
	/**
     * 修改描述代码生成功能的配置
     * 
     * @param genCodeFunction 描述代码生成功能的配置信息
     * @return 结果
     */
	@Override
	public int updateGenCodeFunction(GenCodeFunction genCodeFunction)
	{
	    return genCodeFunctionMapper.updateGenCodeFunction(genCodeFunction);
	}

	/**
     * 删除描述代码生成功能的配置对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteGenCodeFunctionByIds(String ids)
	{
		return genCodeFunctionMapper.deleteGenCodeFunctionByIds(Convert.toStrArray(ids));
	}

	/**
	 * 插入组件和功能关系
	 *
	 * @param params
	 * @return 受影响的行数
	 */
    @Override
    public int insertGenCodeComponentFunction(Map<String, Object> params) {
        return genCodeFunctionMapper.insertGenCodeComponentFunction(params);
    }

	/**
	 * 保存功能与表的关系
	 *
	 * @param list
	 * @return
	 */
	@Override
    public int insertGenCodeFunctionTable(List<GenCodeFunctionTable> list) {
        return genCodeFunctionMapper.insertGenCodeFunctionTable(list);
    }

	/**
	 * 通过功能id查找组件信息
	 *
	 * @param id 功能id
	 * @return
	 */
	@Override
    public GenCodeComponent selectGenCodeComponentByFunctionId(String id) {
        return genCodeFunctionMapper.selectGenCodeComponentByFunctionId(id);
    }

	/**
	 * 对象转菜单树
	 *
	 * @param menuList 菜单列表
	 * @param isCheck 是否需要选中
	 * @param roleMenuList 角色已存在菜单列表
	 * @param permsFlag 是否需要显示权限标识
	 * @return
	 */
	public List<Map<String, Object>> getTrees(List<AuthResource> menuList, boolean isCheck, List<String> roleMenuList,
											  boolean permsFlag)
	{
		List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
		for (AuthResource menu : menuList)
		{
			Map<String, Object> deptMap = new HashMap<String, Object>();
			deptMap.put("id", menu.getId());
			deptMap.put("pId", menu.getParentId());
			deptMap.put("name", transMenuName(menu, roleMenuList, permsFlag));
			deptMap.put("title", menu.getName());
			if (isCheck)
			{
				deptMap.put("checked", roleMenuList.contains(menu.getId() + menu.getPerms()));
			}
			else
			{
				deptMap.put("checked", false);
			}
			trees.add(deptMap);
		}
		return trees;
	}

	public String transMenuName(AuthResource menu, List<String> roleMenuList, boolean permsFlag)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(menu.getName());
		if (permsFlag)
		{
			sb.append("<font color=\"#888\">&nbsp;&nbsp;&nbsp;" + menu.getPerms() + "</font>");
		}
		return sb.toString();
	}

	/**
	 * 菜单列表
	 *
	 * @return
     * @param clientId
	 */
	@Override
	public List<Map<String, Object>> menuTreeData(String clientId) {
		List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
		List<AuthResource> menuList = genCodeFunctionMapper.selectAuthResourceAll(clientId);
		trees = getTrees(menuList, false, null, false);
		return trees;
	}

	/**
	 * 根据功能id查询所包含的表
	 *
	 * @param id 功能id
	 * @return
	 */
    @Override
    public List<Map> selectTableByFunctionId(String id) {
        return genCodeFunctionMapper.selectTableByFunctionId(id);
    }

	/**
	 * 根据功能id删除功能与表之间的关系
	 *
	 * @param functionId 功能id
	 * @return
	 */
	@Override
    public int deleteGenCodeFunctionTableByFunctionId(String functionId) {
        return genCodeFunctionMapper.deleteGenCodeFunctionTableByFunctionId(functionId);
    }

	/**
	 * 根据功能id批量删除功能与表之间的关系
	 *
	 * @param funtionIds
	 * @return
	 */
	@Override
    public int deleteGenCodeFunctionTableByFunctionIds(String[] funtionIds) {
        return genCodeFunctionMapper.deleteGenCodeFunctionTableByFunctionIds(funtionIds);
    }

	/**
	 * 根据组件id查询该组件下的所有功能
	 *
	 * @param params 查询参数集合
	 * @return 功能集合
	 */
    @Override
    public List<GenCodeFunction> selectGenCodeFunctionListByComponentId(Map<String, Object> params) {
        return genCodeFunctionMapper.selectGenCodeFunctionListByComponentId(params);
    }

	/**
	 * 根据组件查询所属应用信息
	 *
	 * @param componentId 组件id
	 * @return
	 */
    @Override
    public OAuthClientDetails selectOauthClientDetailsByComponentId(String componentId) {
        return genCodeFunctionMapper.selectOauthClientDetailsByComponentId(componentId);
    }

	/**
	 * 根据应用ID查询信息
	 *
	 * @param clientId 用户id
	 * @return
	 */
	@Override
	public AuthResource selectResourceByClientId(String clientId) {
		return genCodeFunctionMapper.selectResourceByClientId(clientId);
	}

}
