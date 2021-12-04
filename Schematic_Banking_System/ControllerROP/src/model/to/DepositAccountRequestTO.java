package model.to;


import java.io.Serializable;

public class  DepositAccountRequestTO implements Serializable {
    private long id;
    private long requestDate;
    private int depositAccountTypeId;
    private long customerId;
    private long supportCheckingAccountId;
    private long money;
    private Long check_flag;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(long requestDate) {
        this.requestDate = requestDate;
    }

    public int getDepositAccountTypeId() {
        return depositAccountTypeId;
    }

    public void setDepositAccountTypeId(int depositAccountTypeId) {
        this.depositAccountTypeId = depositAccountTypeId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public long getSupportCheckingAccountId() {
        return supportCheckingAccountId;
    }

    public void setSupportCheckingAccountId(long supportCheckingAccountId) {
        this.supportCheckingAccountId = supportCheckingAccountId;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public Long getCheck_flag() {
        return check_flag;
    }

    public void setCheck_flag(Long check_flag) {
        this.check_flag = check_flag;
    }
}
