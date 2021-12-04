package model.to;

import java.io.Serializable;

public class BankCheckTO implements Serializable {

    private long id;
    private int paperMaxNumber;
    private long checkingAccountId;

    public long getCheckingAccountId() {
        return checkingAccountId;
    }

    public void setCheckingAccountId(long checkingAccountId) {
        this.checkingAccountId = checkingAccountId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPaperMaxNumber() {
        return paperMaxNumber;
    }

    public void setPaperMaxNumber(int paperMaxNumber) {
        this.paperMaxNumber = paperMaxNumber;
    }
}
