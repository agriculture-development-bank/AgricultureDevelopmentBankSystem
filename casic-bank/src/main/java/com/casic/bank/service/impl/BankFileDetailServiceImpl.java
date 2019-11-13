package com.casic.bank.service.impl;

import com.casic.bank.domain.BankAnalysisDetail;
import com.casic.bank.domain.BankReceiveFiles;
import com.casic.bank.domain.BankReceiveFilesDetail;
import com.casic.bank.domain.api.FileBase;
import com.casic.bank.domain.vo.BankReceiveFilesDetailVO;
import com.casic.bank.domain.vo.FilePositionVO;
import com.casic.bank.mapper.BankAnalysisDetailMapper;
import com.casic.bank.mapper.BankReceiveFilesDetailMapper;
import com.casic.bank.mapper.BankReceiveFilesMapper;
import com.casic.bank.service.BankAnalysisDetailService;
import com.casic.bank.service.BankFileDetailService;
import com.casic.common.base.AjaxResult;
import com.casic.common.support.Convert;
import com.casic.common.utils.StringUtils;
import com.casic.framework.util.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 文件台账详情业务逻辑实现
 */
@Service
public class BankFileDetailServiceImpl implements BankFileDetailService {

    private BankReceiveFilesDetailMapper bankReceiveFilesDetailMapper;

    private BankReceiveFilesMapper bankReceiveFilesMapper;

    private BankAnalysisDetailMapper bankAnalysisDetailMapper;

    @Autowired
    public BankFileDetailServiceImpl(BankReceiveFilesDetailMapper bankReceiveFilesDetailMapper, BankReceiveFilesMapper bankReceiveFilesMapper,BankAnalysisDetailMapper bankAnalysisDetailMapper) {
        this.bankReceiveFilesDetailMapper = bankReceiveFilesDetailMapper;
        this.bankReceiveFilesMapper = bankReceiveFilesMapper;
        this.bankAnalysisDetailMapper = bankAnalysisDetailMapper;
    }

    /**
     * 查询文件台账详情列表
     *
     * @param bankReceiveFilesDetail 文件台账详情
     * @return 列表数据
     */
    @Override
    public List<BankReceiveFilesDetailVO> findBankReceiveFilesDetail(BankReceiveFilesDetail bankReceiveFilesDetail) {
        List<BankReceiveFilesDetailVO> list = bankReceiveFilesDetailMapper.findBankReceiveFilesDetail(bankReceiveFilesDetail);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    /**
     * 根据id删除
     *
     * @param id 待删除的id字符串
     * @return 受影响行数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult deleteBankReceiveFilesDetailById(String id) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            BankReceiveFilesDetail bankReceiveFilesDetail = selectBankReceiveFilesDetailById(id);
            List<BankAnalysisDetail> bankAnalysisDetails = bankAnalysisDetailMapper.selectBankAnalysisDetailByFileId(id);
            if (StringUtils.isNotNull(bankReceiveFilesDetail)) {
                if (bankAnalysisDetails == null) {
                    String fileId = bankReceiveFilesDetail.getFileId();
                    int flag = bankReceiveFilesDetailMapper.deleteBankReceiveFilesDetailById(id);
                    //更新台账文件总份数
                    if (flag > 0) {
                        int count = selectBankReceiveFilesDetailCountByFileId(fileId);
                        BankReceiveFiles detail = new BankReceiveFiles();
                        detail.setId(fileId);
                        detail.setNumOfCopies(String.valueOf(count));
                        bankReceiveFilesMapper.updateBankReceiveFiles(detail);
                        ajaxResult.put("msg", "删除成功！");
                        ajaxResult.put("code", 0);
                    }
                } else {
                    ajaxResult.put("msg", "该文件已在流转中，不能删除！");
                    ajaxResult.put("code", 1);
                }
            } else {
                ajaxResult.put("msg", "删除失败,该文件不存在！");
                ajaxResult.put("code", 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.put("msg", "删除失败！");
            ajaxResult.put("code", 1);
        }
        return ajaxResult;
    }

    /**
     * 根据清退计划子表id查询文件信息
     *
     * @param fileDetailIds 清退计划子表id
     * @return
     */
    @Override
    public List<BankReceiveFilesDetail> selectBankFileDetailByAnalysisDetailIds(String fileDetailIds) {
        List<BankReceiveFilesDetail> list = bankReceiveFilesDetailMapper.selectBankFileDetailByAnalysisDetailIds(Convert.toStrArray(fileDetailIds));
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    /**
     * 根据文件id查询文件信息
     *
     * @param id 文件id
     * @return
     */
    @Override
    public BankReceiveFilesDetailVO selectBankReceiveFilesDetailVOById(String id) {
        return bankReceiveFilesDetailMapper.selectBankReceiveFilesDetailVOById(id);
    }

    /**
     * 修改文件位置
     *
     * @param filePositionVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updatePosition(FilePositionVO filePositionVO) {
        BankReceiveFilesDetail bankReceiveFilesDetail = new BankReceiveFilesDetail();
        bankReceiveFilesDetail.setId(filePositionVO.getId());
        bankReceiveFilesDetail.setLocationId(filePositionVO.getCapBoardId());
        bankReceiveFilesDetail.setUpdateBy(ShiroUtils.getUserId());
        bankReceiveFilesDetail.setUpdateTime(new Date());
        return bankReceiveFilesDetailMapper.updateBankReceiveFilesDetail(bankReceiveFilesDetail);
    }

    /**
     * 批量修改文件位置
     *
     * @param filePositionVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchUpdatePosition(FilePositionVO filePositionVO) {
        String[] ids = filePositionVO.getId().split(",");
        List<BankReceiveFilesDetail> bankReceiveFilesDetails = new ArrayList<>();
        Arrays.stream(ids).forEach(id -> {
            BankReceiveFilesDetail bankReceiveFilesDetail = new BankReceiveFilesDetail();
            bankReceiveFilesDetail.setId(id);
            bankReceiveFilesDetail.setLocationId(filePositionVO.getCapBoardId());
            bankReceiveFilesDetail.setUpdateBy(ShiroUtils.getUserId());
            bankReceiveFilesDetail.setUpdateTime(new Date());
            bankReceiveFilesDetails.add(bankReceiveFilesDetail);
        });
        return bankReceiveFilesDetailMapper.batchUpdateBankReceiveFilesDetail(bankReceiveFilesDetails);
    }

    /**
     * 根据台账id查询文件数量
     *
     * @param fileId 台账id
     * @return 台账数量
     */
    private int selectBankReceiveFilesDetailCountByFileId(String fileId) {
        return bankReceiveFilesDetailMapper.selectBankReceiveFilesDetailCountByFileId(fileId);
    }

    /**
     * 根据主键查询台账文件信息
     *
     * @param id 文件id
     * @return BankReceiveFilesDetail对象
     */
    public BankReceiveFilesDetail selectBankReceiveFilesDetailById(String id) {
        return bankReceiveFilesDetailMapper.selectBankReceiveFilesDetailById(id);
    }

    /**
     * 根据文件台账Id查詢文件信息
     *
     * @param fileIds
     * @return
     */
    @Override
    public List<BankReceiveFilesDetailVO> selectBankFileDetailByFileIds(String fileIds) {
        List<BankReceiveFilesDetailVO> list = bankReceiveFilesDetailMapper.selectBankReceiveFileDetailVOByFileIds(Convert.toStrArray(fileIds));
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    /**
     * 更新文件信息
     *
     * @param detail BankReceiveFilesDetail对象
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateBankReceiveFilesDetail(BankReceiveFilesDetail detail) {
        return bankReceiveFilesDetailMapper.updateBankReceiveFilesDetail(detail);
    }

    /**
     * 根据RFID编号查询文件信息
     *
     * @param rfid RFID编号
     * @return
     */
    @Override
    public BankReceiveFilesDetail selectBankFileDetailByRfid(String rfid) {
        return bankReceiveFilesDetailMapper.selectBankReceiveFileDetailRfidNum(rfid);
    }

    /**
     * 根据文件RFID编号更新文件信息
     *
     * @param detail
     * @return
     */
    @Override
    public int updateBankReceiveFilesDetailByRfid(BankReceiveFilesDetail detail) {
        return bankReceiveFilesDetailMapper.updateBankReceiveFilesDetailByRfid(detail);
    }

    /**
     * 查询基本信息
     *
     * @param fileBase
     * @return
     */
    @Override
    public List<Map<String, Object>> selectBankFileDetailAnsyc(FileBase fileBase) {
        return bankReceiveFilesDetailMapper.selectBankFileDetailAnsyc(fileBase);
    }

    /**
     * 查询可选的文件信息
     *
     * @param bankReceiveFilesDetail
     * @return
     */
    @Override
    public List<BankReceiveFilesDetailVO> selectOptionalBankReceiveFilesDetail(BankReceiveFilesDetailVO bankReceiveFilesDetail) {
        List<BankReceiveFilesDetailVO> list = bankReceiveFilesDetailMapper.selectOptionalBankReceiveFilesDetail(bankReceiveFilesDetail);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }
}
