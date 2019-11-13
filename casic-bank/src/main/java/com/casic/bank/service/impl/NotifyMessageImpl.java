package com.casic.bank.service.impl;

import com.casic.bank.domain.BankReceiveFiles;
import com.casic.bank.domain.InCap;
import com.casic.bank.domain.NotifyMessage;
import com.casic.bank.domain.UseRecord;
import com.casic.bank.domain.vo.BankFileVo;
import com.casic.bank.domain.vo.BankReceiveFilesDetailVO;
import com.casic.bank.domain.vo.NotifyMessageVo;
import com.casic.bank.mapper.BankReceiveFilesDetailMapper;
import com.casic.bank.mapper.InCapMapper;
import com.casic.bank.mapper.NotifyMessageMapper;
import com.casic.bank.mapper.UseRecordMapper;
import com.casic.bank.service.NotifyMessageService;
import com.casic.common.support.Convert;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotifyMessageImpl implements NotifyMessageService {

    private NotifyMessageMapper notifyMessageMapper;

    private InCapMapper inCapMapper;

    private UseRecordMapper useRecordMapper;

    private BankReceiveFilesDetailMapper bankReceiveFilesDetailMapper;

    public NotifyMessageImpl(NotifyMessageMapper notifyMessageMapper,
                             InCapMapper inCapMapper,
                             UseRecordMapper useRecordMapper,
                             BankReceiveFilesDetailMapper bankReceiveFilesDetailMapper) {
        this.notifyMessageMapper = notifyMessageMapper;
        this.bankReceiveFilesDetailMapper = bankReceiveFilesDetailMapper;
        this.inCapMapper = inCapMapper;
        this.useRecordMapper = useRecordMapper;
    }

    @Override
    public List<NotifyMessageVo> getNotifyMessageList(NotifyMessage notifyMessage) {
        List<NotifyMessageVo> notifyMessageList = notifyMessageMapper.selectInfoByVO2(notifyMessage);


        return notifyMessageList;
    }



    @Override
    public List<InCap> getInCapList(InCap inCap) {


       List<InCap> inCapList= inCapMapper.selectInCap(inCap);


        return inCapList;
    }

    @Override
    public List<UseRecord> getUseRecordList(UseRecord useRecord) {
        List<UseRecord> selectPageByUseRecords= useRecordMapper.selectPageByUseRecord(useRecord);
        return selectPageByUseRecords;
    }

    @Override
    public List<BankFileVo> getBankFileVo(String id) {
        return useRecordMapper.getBankFileVo(id);
    }

    @Override
    public Integer deleteMessage(String ids) {
        return notifyMessageMapper.deleteMessage(Convert.toStrArray(ids));
    }

    @Override
    public List<BankReceiveFilesDetailVO> getDetailList(String id) {

        return bankReceiveFilesDetailMapper.getDetailList(id);
    }

    @Override
    public Integer getInCapListCount(InCap inCap) {
        return inCapMapper.getInCapListCount(inCap);
    }
}
