package com.example.ivrsolution.dtos;

import java.io.Serializable;

public class GetConsentAndChargeRequest implements Serializable {
    public String amount;
    public String language;
    public TransactionData txdata;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public TransactionData getTxdata() {
        return txdata;
    }

    public void setTxdata(TransactionData txdata) {
        this.txdata = txdata;
    }
}
