package com.casic.generator.service.impl;

import java.util.List;

import com.casic.generator.mapper.GenCodeCategoryMapper;
import com.casic.generator.service.IGenCodeCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.casic.generator.domain.GenCodeCategory;
import com.casic.common.support.Convert;

/**
 * 分类,
1:curd 单增删改查
2:mcurd 父子增删改查
3:mmcur 服务层实现
 * 
 * @author yuzengwen
 * @date 2019-04-22
 */
@Service
public class GenCodeCategoryServiceImpl implements IGenCodeCategoryService
{
	@Autowired
	private GenCodeCategoryMapper genCodeCategoryMapper;

	/**
     * 查询分类,
1:curd 单增删改查
2:mcurd 父子增删改查
3:mmcur信息
     * 
     * @param id 分类,
1:curd 单增删改查
2:mcurd 父子增删改查
3:mmcurID
     * @return 分类,
1:curd 单增删改查
2:mcurd 父子增删改查
3:mmcur信息
     */
    @Override
	public GenCodeCategory selectGenCodeCategoryById(String id)
	{
	    return genCodeCategoryMapper.selectGenCodeCategoryById(id);
	}
	
	/**
     * 查询分类,
1:curd 单增删改查
2:mcurd 父子增删改查
3:mmcur列表
     * 
     * @param genCodeCategory 分类,
1:curd 单增删改查
2:mcurd 父子增删改查
3:mmcur信息
     * @return 分类,
1:curd 单增删改查
2:mcurd 父子增删改查
3:mmcur集合
     */
	@Override
	public List<GenCodeCategory> selectGenCodeCategoryList(GenCodeCategory genCodeCategory)
	{
	    return genCodeCategoryMapper.selectGenCodeCategoryList(genCodeCategory);
	}
	
    /**
     * 新增分类,
1:curd 单增删改查
2:mcurd 父子增删改查
3:mmcur
     * 
     * @param genCodeCategory 分类,
1:curd 单增删改查
2:mcurd 父子增删改查
3:mmcur信息
     * @return 结果
     */
	@Override
	public int insertGenCodeCategory(GenCodeCategory genCodeCategory)
	{
	    return genCodeCategoryMapper.insertGenCodeCategory(genCodeCategory);
	}
	
	/**
     * 修改分类,
1:curd 单增删改查
2:mcurd 父子增删改查
3:mmcur
     * 
     * @param genCodeCategory 分类,
1:curd 单增删改查
2:mcurd 父子增删改查
3:mmcur信息
     * @return 结果
     */
	@Override
	public int updateGenCodeCategory(GenCodeCategory genCodeCategory)
	{
	    return genCodeCategoryMapper.updateGenCodeCategory(genCodeCategory);
	}

	/**
     * 删除分类,
1:curd 单增删改查
2:mcurd 父子增删改查
3:mmcur对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteGenCodeCategoryByIds(String ids)
	{
		return genCodeCategoryMapper.deleteGenCodeCategoryByIds(Convert.toStrArray(ids));
	}
	
}
