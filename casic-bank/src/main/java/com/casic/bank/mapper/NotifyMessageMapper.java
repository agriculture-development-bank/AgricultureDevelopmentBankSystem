package com.casic.bank.mapper;

import com.casic.bank.domain.NotifyMessage;
import com.casic.bank.domain.vo.NotifyMessageVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotifyMessageMapper {

    List<NotifyMessageVo> selectInfoByVO2(NotifyMessage notifyMessage);

    /**
     * 根据Id 删除报警记录
     * @param ids
     * @return  受影响的行数
     */
    Integer deleteMessage(String[] ids);
}
