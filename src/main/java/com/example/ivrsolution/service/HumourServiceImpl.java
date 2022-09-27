package com.example.ivrsolution.service;

import com.example.ivrsolution.dtos.CallbackData;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class HumourServiceImpl implements HumourService{

    @Override
    public String initiateFlow(CallbackData callbackData) {
        //check if the customer is already in the system
        // Customer not in system

        // Customer in the system, play the prompt for jokes

        do {

            return null;
        }while(true);


    }

    @Override
    public String getConsentAndRegister() {
        return null;
    }

    @Override
    public String unsubscribe() {
        return null;
    }

    @Override
    public String hangUpCall() {
        return null;
    }

    @Override
    public String playFile() {
        List<String> fileNames = Arrays.asList("File1", "File2","File3");
        return null;
    }

    @Override
    public String playFileAndAwaitResponse() {
        return null;
    }
}
