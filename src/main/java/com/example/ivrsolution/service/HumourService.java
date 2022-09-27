package com.example.ivrsolution.service;

import com.example.ivrsolution.dtos.CallbackData;
import org.springframework.stereotype.Service;

@Service
public interface HumourService {

    public String initiateFlow(CallbackData callbackData);
    public String getConsentAndRegister();
    public String unsubscribe();
    public String hangUpCall();

    public String playFile();
    public String playFileAndAwaitResponse();

}
