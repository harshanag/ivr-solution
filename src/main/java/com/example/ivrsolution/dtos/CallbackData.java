package com.example.ivrsolution.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CallbackData implements Serializable {

    public String callerid;
    public String txid;
    public String ivrno;

    public String getCallerid() {
        return callerid;
    }

    public void setCallerid(String callerid) {
        this.callerid = callerid;
    }

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    public String getIvrno() {
        return ivrno;
    }

    public void setIvrno(String ivrno) {
        this.ivrno = ivrno;
    }
}
