package com.seatig.domain;

/**
 * @version V1.0
 * @className: State
 * @description: corda -State表信息
 * @author: glenn
 * @create: 2019-04-11 15:06
 **/
public class State {
    private int outputIndex;
    private String transactionId;
    private String client;
    private String issuer;
    private String contractName;
    private String tradingCounterparty;
    private String tradingParty;
    private String monitorParty;
    private String dueDate;
    private String totalTokenizationAmount;
    private String tradingAmount;
    private String owner;
    private String faceValue;
    private String linearId;
    private String status;
    boolean isSettled;

    public State() {
    }

    public State(int outputIndex, String transactionId, String client, String issuer, String contractName, String tradingCounterparty, String tradingParty, String monitorParty, String dueDate, String totalTokenizationAmount, String tradingAmount, String owner, String faceValue, String linearId, String status, boolean isSettled) {
        this.outputIndex = outputIndex;
        this.transactionId = transactionId;
        this.client = client;
        this.issuer = issuer;
        this.contractName = contractName;
        this.tradingCounterparty = tradingCounterparty;
        this.tradingParty = tradingParty;
        this.monitorParty = monitorParty;
        this.dueDate = dueDate;
        this.totalTokenizationAmount = totalTokenizationAmount;
        this.tradingAmount = tradingAmount;
        this.owner = owner;
        this.faceValue = faceValue;
        this.linearId = linearId;
        this.status = status;
        this.isSettled = isSettled;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getOutputIndex() {
        return outputIndex;
    }

    public void setOutputIndex(int outputIndex) {
        this.outputIndex = outputIndex;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getTradingCounterparty() {
        return tradingCounterparty;
    }

    public void setTradingCounterparty(String tradingCounterparty) {
        this.tradingCounterparty = tradingCounterparty;
    }

    public String getTradingParty() {
        return tradingParty;
    }

    public void setTradingParty(String tradingParty) {
        this.tradingParty = tradingParty;
    }

    public String getMonitorParty() {
        return monitorParty;
    }

    public void setMonitorParty(String monitorParty) {
        this.monitorParty = monitorParty;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getTotalTokenizationAmount() {
        return totalTokenizationAmount;
    }

    public void setTotalTokenizationAmount(String totalTokenizationAmount) {
        this.totalTokenizationAmount = totalTokenizationAmount;
    }

    public String getTradingAmount() {
        return tradingAmount;
    }

    public void setTradingAmount(String tradingAmount) {
        this.tradingAmount = tradingAmount;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(String faceValue) {
        this.faceValue = faceValue;
    }

    public String getLinearId() {
        return linearId;
    }

    public void setLinearId(String linearId) {
        this.linearId = linearId;
    }

    public boolean isSettled() {
        return isSettled;
    }

    public void setSettled(boolean settled) {
        isSettled = settled;
    }
}
