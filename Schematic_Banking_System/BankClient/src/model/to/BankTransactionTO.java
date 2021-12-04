package model.to;

import java.io.Serializable;

public class BankTransactionTO implements Serializable {
    private Long id;
    private String title;
    private String description;
    private long customerId;
    private long date;
    private long sourceCheckingAccountId;
    private Long destinationCheckingAccountId = null;
    private Long sourceBankCardId = null;
    private Long destinationBankCardId = null;
    private long money;

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public long getSourceCheckingAccountId() {
        return sourceCheckingAccountId;
    }

    public void setSourceCheckingAccountId(long sourceCheckingAccountId) {
        this.sourceCheckingAccountId = sourceCheckingAccountId;
    }

    public Long getDestinationCheckingAccountId() {
        return destinationCheckingAccountId;
    }

    public void setDestinationCheckingAccountId(Long destinationCheckingAccountId) {
        this.destinationCheckingAccountId = destinationCheckingAccountId;
    }

    public Long getSourceBankCardId() {
        return sourceBankCardId;
    }

    public void setSourceBankCardId(Long sourceBankCardId) {
        this.sourceBankCardId = sourceBankCardId;
    }

    public Long getDestinationBankCardId() {
        return destinationBankCardId;
    }

    public void setDestinationBankCardId(Long destinationBankCardId) {
        this.destinationBankCardId = destinationBankCardId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }


    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
