package com.casic.auth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.casic.auth.mapper.AuthSystemCodeMapper;
import com.casic.auth.service.IAuthSystemCodeService;
import com.casic.common.support.Convert;
import com.casic.common.web.domain.bo.AuthSystemCode;

/**
 * 参数配置 服务层实现
 * 
 * @author yuzengwen
 * @date 2018-10-26
 */
@Service
public class AuthSystemCodeServiceImpl implements IAuthSystemCodeService 
{
	@Autowired
	private AuthSystemCodeMapper systemCodeMapper;

	/**
     * 查询参数配置信息
     * 
     * @param id 参数配置ID
     * @return 参数配置信息
     */
    @Override
	public AuthSystemCode selectSystemCodeById(Integer id)
	{
	    return systemCodeMapper.selectSystemCodeById(id);
	}
	
	/**
     * 查询参数配置列表
     * 
     * @param systemCode 参数配置信息
     * @return 参数配置集合
     */
	@Override
	public List<AuthSystemCode> selectSystemCodeList(AuthSystemCode systemCode)
	{
	    return systemCodeMapper.selectSystemCodeList(systemCode);
	}
	
    /**
     * 新增参数配置
     * 
     * @param systemCode 参数配置信息
     * @return 结果
     */
	@Override
	public int insertSystemCode(AuthSystemCode systemCode)
	{
	    return systemCodeMapper.insertSystemCode(systemCode);
	}
	
	/**
     * 修改参数配置
     * 
     * @param systemCode 参数配置信息
     * @return 结果
     */
	@Override
	public int updateSystemCode(AuthSystemCode systemCode)
	{
	    return systemCodeMapper.updateSystemCode(systemCode);
	}

	/**
	 * 删除参数配置对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteSystemCodeByIds(String ids)
	{
		return systemCodeMapper.deleteSystemCodeByIds(Convert.toStrArray(ids));
	}
	
}
