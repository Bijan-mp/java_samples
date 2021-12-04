package model.to;

import java.io.Serializable;

public class DepositAccountTO  implements Serializable {

    private long id;
    private long money;
    private long openingDate;
    //private int depositAccountTypeId;
    private long checkingAccountId;
    private long customerId;
    private int branchId;

    public DepositAccountTypeTO getDepositAccountTypeTO() {
        return depositAccountTypeTO;
    }

    public void setDepositAccountTypeTO(DepositAccountTypeTO depositAccountTypeTO) {
        this.depositAccountTypeTO = depositAccountTypeTO;
    }

    private DepositAccountTypeTO depositAccountTypeTO;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public long getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(long openingDate) {
        this.openingDate = openingDate;
    }

/*    public int getDepositAccountTypeId() {
        return depositAccountTypeId;
    }

    public void setDepositAccountTypeId(int depositAccountTypeId) {
        this.depositAccountTypeId = depositAccountTypeId;
    }*/

    public long getCheckingAccountId() {
        return checkingAccountId;
    }

    public void setCheckingAccountId(long checkingAccountId) {
        this.checkingAccountId = checkingAccountId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }
}
