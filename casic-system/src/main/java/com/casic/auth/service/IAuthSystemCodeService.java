package com.casic.auth.service;

import java.util.List;

import com.casic.common.web.domain.bo.AuthSystemCode;


/**
 * 参数配置 服务层
 * 
 * @author yuzengwen
 * @date 2018-10-26
 */
public interface IAuthSystemCodeService 
{
	/**
     * 查询参数配置信息
     * 
     * @param id 参数配置ID
     * @return 参数配置信息
     */
	public AuthSystemCode selectSystemCodeById(Integer id);
	
	/**
     * 查询参数配置列表
     * 
     * @param systemCode 参数配置信息
     * @return 参数配置集合
     */
	public List<AuthSystemCode> selectSystemCodeList(AuthSystemCode systemCode);
	
	/**
     * 新增参数配置
     * 
     * @param systemCode 参数配置信息
     * @return 结果
     */
	public int insertSystemCode(AuthSystemCode systemCode);
	
	/**
     * 修改参数配置
     * 
     * @param systemCode 参数配置信息
     * @return 结果
     */
	public int updateSystemCode(AuthSystemCode systemCode);
		
	/**
     * 删除参数配置信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteSystemCodeByIds(String ids);
	
}
