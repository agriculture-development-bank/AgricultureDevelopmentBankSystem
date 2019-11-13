package com.casic.bank.service.impl;

import com.casic.bank.domain.BankAnalysisDetail;
import com.casic.bank.domain.vo.BankAnalysisDetailVO;
import com.casic.bank.domain.vo.BankAnalysisVO;
import com.casic.bank.domain.vo.BankReceiveFilesDetailVO;
import com.casic.bank.mapper.BankAnalysisDetailMapper;
import com.casic.bank.service.BankAnalysisDetailService;
import com.casic.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 文件清退计划子表业务逻辑实现
 */
@Service
public class BankAnalysisDetailServiceImpl implements BankAnalysisDetailService {

    private BankAnalysisDetailMapper bankAnalysisDetailMapper;

    @Autowired
    public BankAnalysisDetailServiceImpl(BankAnalysisDetailMapper bankAnalysisDetailMapper) {
        this.bankAnalysisDetailMapper = bankAnalysisDetailMapper;
    }

    /**
     * 根据文件清退计划id查询清退文件列表数据
     *
     * @param id 文件清退计划id
     * @return list列表
     */
    @Override
    public List<BankAnalysisDetail> selectBankAnalysisDetail(String id) {
        List<BankAnalysisDetail> list = bankAnalysisDetailMapper.selectBankAnalysisDetail(id);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    /**
     * 查询台账信息
     *
     * @param bankAnalysisVO 台账继承类
     * @return
     */
    @Override
    public List<BankAnalysisVO> selectBankReceiveFilesList(BankAnalysisVO bankAnalysisVO) {
        List<BankAnalysisVO> list = bankAnalysisDetailMapper.selectBankReceiveFilesList(bankAnalysisVO);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    /**
     * 根据清退计划id查询待清退的文件
     *
     * @param id 清退计划id
     * @return
     */
    @Override
    public List<Map<String, Object>> selectBankAnalysisDetailMap(String id) {
        return bankAnalysisDetailMapper.selectBankAnalysisDetailMap(id);
    }

    /**
     * 查询待清退的文件
     *
     * @param bankAnalysisDetailVO
     * @return
     */
    @Override
    public List<BankAnalysisDetailVO> selectBankAnalysisDetailVO(BankAnalysisDetailVO bankAnalysisDetailVO) {
        List<BankAnalysisDetailVO> list = bankAnalysisDetailMapper.selectBankAnalysisDetailVO(bankAnalysisDetailVO);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    /**
     * 根据计划id查询台账信息
     *
     * @param bankAnalysisVO
     * @return
     */
    @Override
    public List<BankAnalysisVO> selectBankReceiveFilesListByPlanId(BankAnalysisVO bankAnalysisVO) {
        List<BankAnalysisVO> list = bankAnalysisDetailMapper.selectBankReceiveFilesListByPlanId(bankAnalysisVO);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    /**
     * 根据台账id和清退计划id查询数据
     *
     * @param bankReceiveFilesDetail
     * @return
     */
    @Override
    public List<BankReceiveFilesDetailVO> selectBankReceiveFilesDetailByAccountAndPlanId(BankReceiveFilesDetailVO bankReceiveFilesDetail) {
        List<BankReceiveFilesDetailVO> list =  bankAnalysisDetailMapper.selectBankReceiveFilesDetailByAccountAndPlanId(bankReceiveFilesDetail);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    /**
     * 根据计划id查询
     *
     * @param bankReceiveFilesDetail
     * @return
     */
    @Override
    public List<Map<String, Object>> selectBankReceiveFilesDetailByPlanId(BankReceiveFilesDetailVO bankReceiveFilesDetail) {
        List<Map<String, Object>> list = bankAnalysisDetailMapper.selectBankReceiveFilesDetailByPlanId(bankReceiveFilesDetail);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    @Override
    public List<BankReceiveFilesDetailVO> selectBankReceiveFilesDetailByAccountAndPlanIdOnEdit(BankReceiveFilesDetailVO bankReceiveFilesDetail) {
        List<BankReceiveFilesDetailVO> list =  bankAnalysisDetailMapper.selectBankReceiveFilesDetailByAccountAndPlanIdOnEdit(bankReceiveFilesDetail);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }
}
