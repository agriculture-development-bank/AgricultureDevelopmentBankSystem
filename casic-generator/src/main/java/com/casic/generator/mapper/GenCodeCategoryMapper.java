package com.casic.generator.mapper;

import com.casic.generator.domain.GenCodeCategory;

import java.util.List;

/**
 * 分类,
1:curd 单增删改查
2:mcurd 父子增删改查
3:mmcur 数据层
 * 
 * @author yuzengwen
 * @date 2019-04-22
 */
public interface GenCodeCategoryMapper 
{
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
	public GenCodeCategory selectGenCodeCategoryById(String id);
	
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
	public List<GenCodeCategory> selectGenCodeCategoryList(GenCodeCategory genCodeCategory);
	
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
	public int insertGenCodeCategory(GenCodeCategory genCodeCategory);
	
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
	public int updateGenCodeCategory(GenCodeCategory genCodeCategory);
	
	/**
     * 删除分类,
1:curd 单增删改查
2:mcurd 父子增删改查
3:mmcur
     * 
     * @param id 分类,
1:curd 单增删改查
2:mcurd 父子增删改查
3:mmcurID
     * @return 结果
     */
	public int deleteGenCodeCategoryById(String id);
	
	/**
     * 批量删除分类,
1:curd 单增删改查
2:mcurd 父子增删改查
3:mmcur
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteGenCodeCategoryByIds(String[] ids);
	
}