package com.seatig.domain;

import lombok.Data;
import net.corda.core.contracts.Amount;
import net.corda.core.contracts.Issued;
import net.corda.core.contracts.LinearState;
import net.corda.core.contracts.UniqueIdentifier;
import net.corda.core.identity.Party;
import net.corda.core.schemas.QueryableState;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;

/**
 * @version V1.0
 * @className: GrantState
 * @description: 资金流类
 * @author: glenn
 * @create: 2019-07-10 10:23
 **/
@Data
public class GrantState {
    //监管方
    private  String monitor;
    //代理方
    private  String agent;
    //交易金额
    private  BigDecimal tradingAmount;
    //余额
    private  BigDecimal faceValue;
    //拥有者
    private  String owner;
    //交易对手
    private  String tradingCounterparty;
    //合同总额
    private  BigDecimal totalTokenizationAmount;
    //linearID
    private  String linearId;
    //手续费
    private  BigDecimal trasanctionFees;
    //手续费缴费方
    private  String feePayer;
    //开始时间
    private  String startTime;


}
