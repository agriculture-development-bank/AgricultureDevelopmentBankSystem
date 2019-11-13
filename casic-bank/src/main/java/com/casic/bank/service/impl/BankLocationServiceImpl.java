package com.casic.bank.service.impl;

import com.casic.bank.domain.BankLocation;
import com.casic.bank.domain.vo.BankLocationVo;
import com.casic.bank.mapper.BankLocationMapper;
import com.casic.bank.service.BankLocationService;
import com.casic.common.support.Convert;
import com.casic.common.utils.StringUtils;
import com.casic.common.utils.UuidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 位置管理业务逻辑访问实现
 */
@Service
public class BankLocationServiceImpl implements BankLocationService {

    /**
     * 位置编码是否唯一的返回结果码
     */
    public final static String LOCATION_CODE_UNIQUE = "0";
    public final static String LOCATION_CODE_NOT_UNIQUE = "1";

    private BankLocationMapper bankLocationMapper;

    @Autowired
    public BankLocationServiceImpl(BankLocationMapper bankLocationMapper) {
        this.bankLocationMapper = bankLocationMapper;
    }

    /**
     * 获取列表数据
     *
     * @param bankLocation 位置实体
     * @return
     */
    @Override
    public List<BankLocationVo> selectBankLocationList(BankLocation bankLocation) {
        List<BankLocationVo> list = bankLocationMapper.selectBankLocationList(bankLocation);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    /**
     * 根据主键查询位置实体
     *
     * @param id 主键id
     * @return
     */
    @Override
    public BankLocationVo selectBankLocationById(String id) {
        return bankLocationMapper.selectBankLocationById(id);
    }

    /**
     * 新增位置
     *
     * @param bankLocation 位置实体
     * @return 受影响的行数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertBankLocation(BankLocation bankLocation) {
        if (bankLocation != null) {
            if ("".equals(bankLocation.getId()) || bankLocation.getId() == null) {
                String uuid = UuidUtils.getUUIDString();
                bankLocation.setId(uuid);
            }
            bankLocation.setCreateTime(new Date());
        }
        return bankLocationMapper.insertBankLocation(bankLocation);
    }

    /**
     * 修改位置
     *
     * @param bankLocation 位置实体
     * @return 受影响的行数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateBankLocation(BankLocation bankLocation) {
        bankLocation.setUpdateTime(new Date());
        return bankLocationMapper.updateBankLocation(bankLocation);
    }

    /**
     * 删除
     *
     * @param ids 待删除id字符串
     * @return 受影响的行数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBankLocationByIds(String ids) {
        return bankLocationMapper.deleteBankLocationByIds(Convert.toStrArray(ids));
    }

    /**
     * 检测位置编码的唯一性
     *
     * @param bankLocation 位置实体
     * @return
     */
    @Override
    public String checkLocationCodeUnique(BankLocation bankLocation) {
        String locationId = "-1";
        if (StringUtils.isNotNull(bankLocation) && StringUtils.isNotEmpty(bankLocation.getId())) {
            locationId = bankLocation.getId();
        }
        BankLocation location = selectBankLocationByCode(bankLocation.getLocationCode());
        if (StringUtils.isNotNull(location) && !locationId.equals(location.getId())) {
            return LOCATION_CODE_NOT_UNIQUE;
        }
        return LOCATION_CODE_UNIQUE;
    }

    /**
     * 根据位置id删除
     *
     * @param id 位置id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBankLocationById(String id) {
        return bankLocationMapper.deleteBankLocationById(id);
    }

    /**
     * 检测同一所属机构位置名称不能重复
     *
     * @param bankLocation 位置信息
     * @return
     */
    @Override
    public String checkDeptLocationUnique(BankLocation bankLocation) {
        String locationId = "-1";
        if (StringUtils.isNotNull(bankLocation) && StringUtils.isNotEmpty(bankLocation.getId())) {
            locationId = bankLocation.getId();
        }
        BankLocation location = selectBankLocationByNameAndDeptId(bankLocation.getLocationName(), bankLocation.getBelongDept());
        if (StringUtils.isNotNull(location) && !locationId.equals(location.getId())) {
            return "1";
        }
        return "0";
    }

    /**
     * 根据部门id和位置名称查询位置信息
     *
     * @param locationName 位置名称
     * @param belongDept 部门id
     * @return
     */
    private BankLocation selectBankLocationByNameAndDeptId(String locationName, String belongDept) {
        return bankLocationMapper.selectBankLocationByNameAndDeptId(locationName, belongDept);
    }

    /**
     * 根据位置编码查询
     *
     * @param locationCode 位置编码
     * @return
     */
    private BankLocation selectBankLocationByCode(String locationCode) {
        return bankLocationMapper.selectBankLocationByCode(locationCode);
    }
}
