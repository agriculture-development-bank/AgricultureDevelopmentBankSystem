package com.casic.auth.mapper;

import com.casic.common.web.domain.bo.OAuthClientDetails;
import com.casic.common.web.domain.vo.GenCodeDomainVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OAuthClientDetailsMapper {
    /**
     * 查询应用配置信息
     *
     * @param clientId 参数配置ID
     * @return 参数配置信息
     */
    public OAuthClientDetails selectOAuthClientDetailsById(String clientId);

    /**
     * 查询应用配置列表
     *
     * @param oAuthClientDetails 应用配置信息
     * @return 应用配置集合
     */
    public List<OAuthClientDetails> selectOAuthClientDetailsList(OAuthClientDetails oAuthClientDetails);

    /**
     * 新增应用配置
     *
     * @param oAuthClientDetails 应用配置信息
     * @return 结果
     */
    public int insertOAuthClientDetails(OAuthClientDetails oAuthClientDetails);

    /**
     * 修改应用配置
     *
     * @param oAuthClientDetails 应用配置信息
     * @return 结果
     */
    public int updateOAuthClientDetails(OAuthClientDetails oAuthClientDetails);

    /**
     * 删除参数配置
     *
     * @param id 参数配置ID
     * @return 结果
     */
    public int deleteOAuthClientDetailsById(String id);

    /**
     * 批量删除应用配置
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteOAuthClientDetailsByIds(String[] ids);

    /**
     * 查询GenCodeDomainList
     */
    public List<GenCodeDomainVo> selectGenCodeDomainList();

    /**
     * 根据domainId 查询应用
     */
    public List<OAuthClientDetails> selectOAuthClientDetailsByDomainId(@Param("domainId") String domainId);
}
