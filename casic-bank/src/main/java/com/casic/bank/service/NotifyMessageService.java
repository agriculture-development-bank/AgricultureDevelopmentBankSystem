package com.casic.bank.service;

import com.casic.bank.domain.InCap;
import com.casic.bank.domain.NotifyMessage;
import com.casic.bank.domain.UseRecord;
import com.casic.bank.domain.vo.BankFileVo;
import com.casic.bank.domain.vo.BankReceiveFilesDetailVO;
import com.casic.bank.domain.vo.NotifyMessageVo;

import java.util.List;

public interface NotifyMessageService {

    /**
     * 获取报警记录列表
     * @param notifyMessage
     * @return
     */
    List<NotifyMessageVo> getNotifyMessageList(NotifyMessage notifyMessage);

    /**
     * 删除报警记录
     * @param ids
     * @return
     */
    Integer deleteMessage(String ids);


    List<InCap> getInCapList(InCap inCap);

    List<UseRecord> getUseRecordList(UseRecord useRecord);

    /**
     * 根据使用记录 查看详情
     * @param id
     * @return
     */
    List<BankFileVo> getBankFileVo(String id);

    List<BankReceiveFilesDetailVO> getDetailList(String id);

    Integer getInCapListCount(InCap inCap);



}
