package com.casic.auth.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.casic.auth.mapper.AuthResourceMapper;
import com.casic.auth.service.IAuthResourceService;
import com.casic.common.support.Convert;
import com.casic.common.web.domain.bo.AuthResource;

/**
 * 资源(菜单,资源) 服务层实现
 * 
 * @author yuzengwen
 * @date 2018-10-25
 */
@Service
public class AuthResourceServiceImpl implements IAuthResourceService 
{
	@Autowired
	private AuthResourceMapper resourceMapper;

	/**
     * 查询资源(菜单,资源)信息
     * 
     * @param id 资源(菜单,资源)ID
     * @return 资源(菜单,资源)信息
     */
    @Override
	public AuthResource selectResourceById(Integer id)
	{
	    return resourceMapper.selectResourceById(id);
	}
	
	/**
     * 查询资源(菜单,资源)列表
     * 
     * @param resource 资源(菜单,资源)信息
     * @return 资源(菜单,资源)集合
     */
	@Override
	public List<AuthResource> selectResourceList(AuthResource resource)
	{
	    return resourceMapper.selectResourceList(resource);
	}
	
    /**
     * 新增资源(菜单,资源)
     * 
     * @param resource 资源(菜单,资源)信息
     * @return 结果
     */
	@Override
	public int insertResource(AuthResource resource)
	{
	    return resourceMapper.insertResource(resource);
	}
	
	/**
     * 修改资源(菜单,资源)
     * 
     * @param resource 资源(菜单,资源)信息
     * @return 结果
     */
	@Transactional(rollbackFor=Exception.class)
	@Override
	public int updateResource(AuthResource resource)
	{
	    return resourceMapper.updateResource(resource);
	}

	/**
     * 删除资源(菜单,资源)对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteResourceByIds(String ids)
	{
		return resourceMapper.deleteResourceByIds(Convert.toStrArray(ids));
	}
	
	

    /**
     * 查询所有菜单
     * 
     * @return 菜单列表
     */
    @Override
    public List<Map<String, Object>> menuTreeData(String sysCode)
    {
        List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
        List<AuthResource> menuList = resourceMapper.selectResourceAll(sysCode);
        trees = getTrees(menuList, false, null, false);
        return trees;
    }

    /**
     * 对象转菜单树
     * 
     * @param resourceList 菜单列表
     * @param isCheck 是否需要选中
     * @param roleMenuList 角色已存在菜单列表
     * @param permsFlag 是否需要显示权限标识
     * @return
     */
    public List<Map<String, Object>> getTrees(List<AuthResource> resourceList, boolean isCheck, List<String> roleMenuList, boolean permsFlag)
    {
        List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
        for (AuthResource res : resourceList)
        {
            Map<String, Object> deptMap = new HashMap<String, Object>();
            deptMap.put("id", res.getId());
            deptMap.put("pId", res.getParentId());
            deptMap.put("name", transMenuName(res, permsFlag));
            deptMap.put("title", res.getName());
            if (isCheck)
            {
                deptMap.put("checked", roleMenuList.contains(res.getId() + res.getMethod()));
            }
            else
            {
                deptMap.put("checked", false);
            }
            trees.add(deptMap);
        }
        return trees;
    }
    
    public String transMenuName(AuthResource res, boolean permsFlag)
    {
        StringBuffer sb = new StringBuffer();
        sb.append(res.getName());
        if (permsFlag)
        {
            sb.append("<font color=\"#888\">&nbsp;&nbsp;&nbsp;" + res.getMethod() + "</font>");
        }
        return sb.toString();
    }


    @Override
    public int selectCountResourceByParentId(Integer parentId){
        return resourceMapper.selectCountResourceByParentId(parentId);
    }
}
