package model.to;

import java.io.Serializable;

public class BankCardTO implements Serializable {

    private long id;
    private String password;
    private String internetPassword;
    private int CVV2;
    private int expireDate;
    private int branchId;
    private long checkingAccountId;
    private int blockedFlag;

    public int getBlockedFlag() {
        return blockedFlag;
    }

    public void setBlockedFlag(int blockedFlag) {
        this.blockedFlag = blockedFlag;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInternetPassword() {
        return internetPassword;
    }

    public void setInternetPassword(String internetPassword) {
        this.internetPassword = internetPassword;
    }

    public int getCVV2() {
        return CVV2;
    }

    public void setCVV2(int CVV2) {
        this.CVV2 = CVV2;
    }

    public int getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(int expireDate) {
        this.expireDate = expireDate;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public long getCheckingAccountId() {
        return checkingAccountId;
    }

    public void setCheckingAccountId(long checkingAccountId) {
        this.checkingAccountId = checkingAccountId;
    }
}
