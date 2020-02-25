package com.casic.bank.service.impl;

import com.casic.bank.domain.BankFileSignOpinion;
import com.casic.bank.domain.vo.BankFileSignOpinionVO;
import com.casic.bank.mapper.BankFileSignOpinionMapper;
import com.casic.bank.service.BankFileSignOpinionService;
import com.casic.common.utils.UuidUtils;
import com.casic.framework.util.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 文件签署意见业务逻辑实现
 */
@Service
public class BankFileSignOpinionServiceImpl implements BankFileSignOpinionService {

    private BankFileSignOpinionMapper bankFileSignOpinionMapper;

    @Autowired
    public BankFileSignOpinionServiceImpl(BankFileSignOpinionMapper bankFileSignOpinionMapper) {
        this.bankFileSignOpinionMapper = bankFileSignOpinionMapper;
    }

    /**
     * 根据文件id查询行领导审批意见
     *
     * @param id 文件id
     * @return BankFileSignOpinion对象
     */
    @Override
    public List<BankFileSignOpinion> selectBankFileSignOpinionByFileDetailId(String id) {
        List<BankFileSignOpinion> list = bankFileSignOpinionMapper.selectBankFileSignOpinionByFileDetailId(id);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }
    /**
     * 根据台账流水号查询行领导审批意见
     *
     * @param registrationNum 台账流水号
     * @return BankFileSignOpinion对象
     */
    @Override
    public List<BankFileSignOpinion> selectBankFileSignOpinionByRegistrationum(String registrationNum) {
        List<BankFileSignOpinion> list = bankFileSignOpinionMapper.selectBankFileSignOpinionByRegistrationum(registrationNum);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    /**
     * 查询文件签署意见列表数据
     *
     * @param bankFileSignOpinion 文件签署意见
     * @return list
     */
    @Override
    public List<BankFileSignOpinion> selectBankFileSignOpinionList(BankFileSignOpinion bankFileSignOpinion) {
        List<BankFileSignOpinion> list = bankFileSignOpinionMapper.selectBankFileSignOpinionList(bankFileSignOpinion);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    /**
     * 查询文件签署意见列表数据
     *
     * @param bankFileSignOpinion 文件签署意见
     * @return list
     */
    @Override
    public List<BankFileSignOpinionVO> selectBankFileSignOpinionVOList(BankFileSignOpinion bankFileSignOpinion) {
        List<BankFileSignOpinionVO> list = bankFileSignOpinionMapper.selectBankFileSignOpinionVOList(bankFileSignOpinion);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    /**
     * 根据主键查询行领导文件签署意见
     *
     * @param id 主键id
     * @return
     */
    @Override
    public BankFileSignOpinionVO selectBankFileSignOpinionById(String id) {
        return bankFileSignOpinionMapper.selectBankFileSignOpinionById(id);
    }

    /**
     * 新增文件签署意见
     *
     * @param bankFileSignOpinion 文件签署意见
     * @return 受影响行数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertBankFileSignOpinion(BankFileSignOpinion bankFileSignOpinion) {
        if (bankFileSignOpinion !=  null) {
            if ("".equals(bankFileSignOpinion.getId()) || bankFileSignOpinion.getId() == null) {
                String uuid = UuidUtils.getUUIDString();
                bankFileSignOpinion.setId(uuid);
            }
            bankFileSignOpinion.setCreateBy(ShiroUtils.getUserId());
            bankFileSignOpinion.setCreateTime(new Date());
        }
        return bankFileSignOpinionMapper.insertBankFileSignOpinion(bankFileSignOpinion);
    }

    /**
     * 删除文件签署意见
     *
     * @param id 签署意见id
     * @return 受影响行数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBankFileSignOpinionById(String id) {
        return bankFileSignOpinionMapper.deleteBankFileSignOpinionById(id);
    }

    /**
     * 批量編輯文件签署意见
     *
     * @param list 文件签署意见
     * @return 受影响行数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateBankFileSignOpinions(List<BankFileSignOpinion> list) {
        return bankFileSignOpinionMapper.updateBankFileSignOpinions(list);
    }
}
