package model.to;

import java.io.Serializable;

public class BankCheckRequestTO implements Serializable {

    private long id;
    private long requestDate;
    private long customerId;
    private long checkingAccountId;
    private int checkedFlag;

    public int getCheckedFlag() {
        return checkedFlag;
    }

    public void setCheckedFlag(int checkedFlag) {
        this.checkedFlag = checkedFlag;
    }

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

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long cutomerId) {
        this.customerId = cutomerId;
    }

    public long getCheckingAccountId() {
        return checkingAccountId;
    }

    public void setCheckingAccountId(long checkingAccountID) {
        this.checkingAccountId = checkingAccountID;
    }
}
