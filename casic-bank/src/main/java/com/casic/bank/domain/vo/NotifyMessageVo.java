package com.casic.bank.domain.vo;

import com.casic.bank.domain.NotifyMessage;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NotifyMessageVo extends NotifyMessage {

    private String userName;

    private String equipmentName;
}
