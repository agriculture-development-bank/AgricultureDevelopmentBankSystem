package com.casic.bank.mapper;

import com.casic.bank.domain.BankLocation;
import com.casic.bank.domain.vo.BankLocationVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 位置管理数据访问接口
 */
@Repository
public interface BankLocationMapper {
    /**
     * 获取列表数据
     *
     * @param bankLocation 位置实体
     * @return
     */
    List<BankLocationVo> selectBankLocationList(BankLocation bankLocation);

    /**
     * 根据主键查询位置实体
     *
     * @param id 主键id
     * @return
     */
    BankLocationVo selectBankLocationById(String id);

    /**
     * 新增位置
     *
     * @param bankLocation 位置实体
     * @return 受影响的行数
     */
    int insertBankLocation(BankLocation bankLocation);

    /**
     * 修改位置
     *
     * @param bankLocation 位置实体
     * @return 受影响的行数
     */
    int updateBankLocation(BankLocation bankLocation);

    /**
     * 删除
     *
     * @param ids 待删除id数组
     * @return 受影响的行数
     */
    int deleteBankLocationByIds(String[] ids);

    /**
     * 根据位置编码查询
     *
     * @param locationCode 位置编码
     * @return
     */
    BankLocation selectBankLocationByCode(String locationCode);

    /**
     * 根据位置id删除
     *
     * @param id 位置id
     * @return
     */
    int deleteBankLocationById(String id);

    /**
     * 根据部门id和位置名称查询位置信息
     *
     * @param locationName 位置名称
     * @param belongDept 部门id
     * @return
     */
    BankLocation selectBankLocationByNameAndDeptId(@Param("locationName") String locationName, @Param("belongDept") String belongDept);
}
