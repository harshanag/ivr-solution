package com.example.ivrsolution.dtos;

import java.io.Serializable;

public class GetConsentAndSubscribeRequest implements Serializable {
    public String productid;
    public String language;
    public TransactionData txdata;
}
