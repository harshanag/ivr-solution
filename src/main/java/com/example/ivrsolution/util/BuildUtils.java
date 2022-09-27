package com.example.ivrsolution.util;

import com.example.ivrsolution.dtos.CallbackData;
import com.example.ivrsolution.dtos.PlayFileAwaitInputRequest;
import com.example.ivrsolution.dtos.TransactionData;
import com.example.ivrsolution.enums.HttpHeadersEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@PropertySources(@PropertySource("classpath:application.properties"))
public class BuildUtils {

    @Autowired
    private static ObjectMapper mapper;

    @Value( "${app.client.id}" )
    private static String clientID;

    public static PlayFileAwaitInputRequest buildPlayFileAwaitInputRequest(CallbackData callbackData, String fileName){

        PlayFileAwaitInputRequest request = new PlayFileAwaitInputRequest();
        TransactionData txData = new TransactionData();
        request.setTimeout("0:30");
        request.setNumdigits("1");
        request.setFileName(fileName);
        txData.setTxid(callbackData.txid);
        txData.setCallerid(callbackData.callerid);
        txData.setIvrno(callbackData.callerid);
        request.setTxdata(txData);

        return request;
    }

    public static HttpHeaders buildHttpHeadersForCommonAPICalls(CallbackData callbackData) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(HttpHeadersEnum.X_IBM_Client_Id.value,clientID);
        headers.add(HttpHeadersEnum.CONTENT_TYPE.value, HttpHeadersEnum.APPLICATION_JSON.value);
        headers.add(HttpHeadersEnum.ACCEPT.value, HttpHeadersEnum.APPLICATION_JSON.value);
        return headers;
    }

    public static HttpHeaders buildHttpHeadersForPlayFileAndAwaitInput(CallbackData callbackData, String fileName) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(HttpHeadersEnum.X_IBM_Client_Id.value,clientID);
        headers.add(HttpHeadersEnum.CONTENT_TYPE.value, HttpHeadersEnum.APPLICATION_JSON.value);
        headers.add(HttpHeadersEnum.ACCEPT.value, HttpHeadersEnum.APPLICATION_JSON.value);
        headers.add(HttpHeadersEnum.DATA.value, mapper.writeValueAsString(buildPlayFileAwaitInputRequest(callbackData,fileName)));
        return headers;
    }

}
