package com.casic.generator.service.impl;

import com.casic.common.support.Convert;
import com.casic.common.utils.StringUtils;
import com.casic.generator.domain.GenCodeTableColumns;
import com.casic.generator.mapper.GenCodeTableColumnsMapper;
import com.casic.generator.service.IGenCodeTableColumnsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 模块对应的字段 服务层实现
 * 
 * @author yuzengwen
 * @date 2019-04-22
 */
@Service
public class GenCodeTableColumnsServiceImpl implements IGenCodeTableColumnsService
{
	@Autowired
	private GenCodeTableColumnsMapper genCodeTableColumnsMapper;

	/**
     * 查询模块对应的字段信息
     * 
     * @param id 模块对应的字段ID
     * @return 模块对应的字段信息
     */
    @Override
	public GenCodeTableColumns selectGenCodeTableColumnsById(String id)
	{
	    return genCodeTableColumnsMapper.selectGenCodeTableColumnsById(id);
	}
	
	/**
     * 查询模块对应的字段列表
     * 
     * @param genCodeTableColumns 模块对应的字段信息
     * @return 模块对应的字段集合
     */
	@Override
	public List<GenCodeTableColumns> selectGenCodeTableColumnsList(GenCodeTableColumns genCodeTableColumns)
	{
	    return genCodeTableColumnsMapper.selectGenCodeTableColumnsList(genCodeTableColumns);
	}
	
    /**
     * 新增模块对应的字段
     * 
     * @param genCodeTableColumns 模块对应的字段信息
     * @return 结果
     */
	@Override
	public int insertGenCodeTableColumns(GenCodeTableColumns genCodeTableColumns)
	{
	    return genCodeTableColumnsMapper.insertGenCodeTableColumns(genCodeTableColumns);
	}
	
	/**
     * 修改模块对应的字段
     * 
     * @param genCodeTableColumns 模块对应的字段信息
     * @return 结果
     */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int updateGenCodeTableColumns(GenCodeTableColumns genCodeTableColumns)
	{
	    return genCodeTableColumnsMapper.updateGenCodeTableColumns(genCodeTableColumns);
	}

	/**
     * 删除模块对应的字段对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int deleteGenCodeTableColumnsByIds(String ids)
	{
		return genCodeTableColumnsMapper.deleteGenCodeTableColumnsByIds(Convert.toStrArray(ids));
	}

	/**
	 * 根据表id查询表中的字段
	 *
	 * @param tableId 表id
	 * @return
	 */
    @Override
    public List<GenCodeTableColumns> selectGenCodeTableColumnsByTableId(String tableId) {
        return genCodeTableColumnsMapper.selectGenCodeTableColumnsByTableId(tableId);
    }

	/**
	 * 校验字段的唯一性
	 *
	 * @param codeTableColumns
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 */
    @Override
    public String checkFieldUnique(GenCodeTableColumns codeTableColumns, String fieldName, String fieldValue) {
		String id = codeTableColumns.getId();
		String tableId = codeTableColumns.getTableId();
		GenCodeTableColumns genCodeTableColumns = genCodeTableColumnsMapper.checkFieldUnique(tableId, fieldName, fieldValue);
		if (genCodeTableColumns != null && !genCodeTableColumns.getId().equals(id)) {
			return "1";
		} else {
			return "0";
		}
    }

}
