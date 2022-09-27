package com.example.ivrsolution.dtos;

import java.io.Serializable;

public class PlayFileAwaitInputRequest implements Serializable {
    public String timeout;
    public String numdigits;
    public String fileName;
    public TransactionData txdata;

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public String getNumdigits() {
        return numdigits;
    }

    public void setNumdigits(String numdigits) {
        this.numdigits = numdigits;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public TransactionData getTxdata() {
        return txdata;
    }

    public void setTxdata(TransactionData txdata) {
        this.txdata = txdata;
    }


}
