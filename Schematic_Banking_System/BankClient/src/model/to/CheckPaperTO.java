package model.to;

import java.io.Serializable;

public class CheckPaperTO implements Serializable {

    private long bankCheckId;
    private int checkPaperId;
    private int money;
    private long receiveDate;
    private int passedFlag;
    private int returnFlag;
    private String forPerson;
    private String receiverName;
    private String receiverFamily;
    private long receiverNationalId;
    private int barredFlag;
    private long realReceiveDate;

    public long getRealReceiveDate() {
        return realReceiveDate;
    }

    public void setRealReceiveDate(long realReceiveDate) {
        this.realReceiveDate = realReceiveDate;
    }

    public int getBarredFlag() {
        return barredFlag;
    }

    public void setBarredFlag(int barredFlag) {
        this.barredFlag = barredFlag;
    }

    public long getBankCheckId() {
        return bankCheckId;
    }

    public void setBankCheckId(long bankCheckId) {
        this.bankCheckId = bankCheckId;
    }

    public int getCheckPaperId() {
        return checkPaperId;
    }

    public void setCheckPaperId(int checkPaperId) {
        this.checkPaperId = checkPaperId;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public long getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(long receiveDate) {
        this.receiveDate = receiveDate;
    }

    public int getPassedFlag() {
        return passedFlag;
    }

    public void setPassedFlag(int passedFlag) {
        this.passedFlag = passedFlag;
    }

    public int getReturnFlag() {
        return returnFlag;
    }

    public void setReturnFlag(int returnFlag) {
        this.returnFlag = returnFlag;
    }

    public String getForPerson() {
        return forPerson;
    }

    public void setForPerson(String forPerson) {
        this.forPerson = forPerson;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverFamily() {
        return receiverFamily;
    }

    public void setReceiverFamily(String receiverFamily) {
        this.receiverFamily = receiverFamily;
    }

    public long getReceiverNationalId() {
        return receiverNationalId;
    }

    public void setReceiverNationalId(long receiverNationalId) {
        this.receiverNationalId = receiverNationalId;
    }
}
