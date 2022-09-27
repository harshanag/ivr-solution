package com.example.ivrsolution.controllers;

import com.example.ivrsolution.dtos.CallbackData;
import com.example.ivrsolution.dtos.IVRResponse;
import com.example.ivrsolution.dtos.PlayFileAwaitInputResponse;
import com.example.ivrsolution.enums.AudioFiles;
import com.example.ivrsolution.enums.ResponseCodesEnum;
import com.example.ivrsolution.enums.HttpHeadersEnum;
import com.example.ivrsolution.service.HumourService;
import com.example.ivrsolution.util.BuildUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RestController
@PropertySources(@PropertySource("classpath:application.properties"))
@RequestMapping("/tennysjokes")
public class HumourAppController {

    @Autowired
    private HumourService humourService;
    @Autowired
    private ObjectMapper mapper;

    @Value( "${api.base.url}" )
    private String apiBaseUrl;

    @Value( "${api.play.await.input.url}" )
    private String playAndAwaitInputUrl;

    @Value( "${api.play.file.url}" )
    private String playFileUrl;

    @Value( "${api.unsubscribe.url}" )
    private String unsubscribeUrl;

    @Value( "${api.hangup.url}" )
    private String hangUpUrl;

    @Value( "${api.test.hello.world.url}" )
    private String helloWorldUrl;

    @PostMapping("/")
    public void initiateFlow(@RequestBody CallbackData callbackData){

        String nextStep = "";
        String welcomeFile = "Main_Menu_4913";

        //Basic Test Call
        try {
            System.out.println("Starting test request");
            IVRResponse response = callHelloWorldEndpoint(callbackData, welcomeFile);
            nextStep = response.code;
            System.out.println("Ending test request");
        }catch (Exception e){

        }
        //TODO Check if the current caller is an existing customer, and initiate the correct flow
        boolean isNewCustomer = false;
        if(isNewCustomer){

        }else{
            initiateExistingCustomerFlow(callbackData);
        }

    }

    /**
     * New Customer Flow
     * Check with mobitel on the flow
     * @param callbackData
     */
    public void initiateNewCustomerFlow(CallbackData callbackData){

    }

    /**
     * <p>Existing Customer Flow logic - The system will play the top level navigation menu for an existing user. The user inputs will be
     * <br> 1 - Play Jokes - Entering 1 will allow the user to navigate to the below options</br>
     * <br> 9 - Unsubscribe - Entering 9 will allow the user to unsubscribe from the service</br>
     * <br> 0 - Hang up - Entering 0 will allow the user to hang up the call </br> </p>
     *
     * <p> If the user chooses 1 from the top level navigation, the following options will be available for the user for navigation. The system will loop through the inputs until the
     * user chooses to hang up the call
     * <br> 1 - Play Prev Joke - Entering 1 will allow the user to Play the current joke </br>
     * <br> 2 - Play Next Joke - Entering 2 will allow the user to Play the next joke </br>
     * <br> * - Previous Menu - Entering * will allow the user to navigate to the top level menu </br>
     * <br> # - Main Menu - Entering # will allow the user to navigate to the main menu </br>
     * <br> 0 - Hang Up - Entering 0 will allow the user to hang up the call </br>
     * </p>
     *
     * <p> There are separate method calls for each option, to make the flow clear and handle errors</p>
     */
    public void initiateExistingCustomerFlow(CallbackData callbackData){

        try {
            IVRResponse welcomeExistingCustomerResponse = playWelcomeExistingCustomerMessage(callbackData);
            String nextStep ="";
            do{ // Playing the Main Menu
                System.out.println("Starting Existing Customer Flow");
                PlayFileAwaitInputResponse mainMenuResponse = playExistingCustomerNavigationMenu(callbackData);
                nextStep = mainMenuResponse.data.input;

                if("1".equals(nextStep)){
                    // Playing the Jokes Menu
                    String secondStep = "";
                    PlayFileAwaitInputResponse jokesMenuResponse = playListenToJokesMenu(callbackData);
                    secondStep = jokesMenuResponse.data.input;
                    do {
                        if("1".equals(secondStep)){
                            //Playing the joke
                            secondStep = playNextJoke(callbackData).data.input;
                        } else if ("2".equals(secondStep)){
                            //Playing the next joke
                            secondStep = playPreviousJoke(callbackData).data.input;
                        }  else if ("*".equals(secondStep)){
                            //Playing the Jokes Menu
                            secondStep = playListenToJokesMenu(callbackData).data.input;
                        } else if ("#".equals(secondStep)){
                            //Playing the Main Menu, breaking this loop
                            secondStep = playMainMenu(callbackData).data.input;
                            break;
                        } else if ("0".equals(secondStep)){
                            //Hanging up call, breaking this loop
                            hangUp(callbackData);
                            break;
                        }
                    } while(!"0".equals(secondStep));

                } else if ("9".equals(nextStep)) {
                    unsubscribe(callbackData);
                    break;
                } else if ("0".equals(nextStep)) {
                    hangUp(callbackData);
                    break;
                }

            }while(!"0".equals(nextStep));


        }catch (Exception e){

        }

    }

    /**
     * <p>This method will play the Main Menu of the Jokes Application - This is the entry point</p>
     * @param callbackData
     * @return PlayFileAwaitInputResponse response
     * @throws JsonProcessingException
     */
    public PlayFileAwaitInputResponse playMainMenu(CallbackData callbackData) throws JsonProcessingException {
        String mainMenuFile = "MainMenu";
        return callPlayFileAndAwaitInputAPI(callbackData,mainMenuFile);
    }

    /**
     * <p>This method will play the Welcome message for existing customers</p>
     * @param callbackData
     * @return
     * @throws JsonProcessingException
     */
    public IVRResponse playWelcomeExistingCustomerMessage(CallbackData callbackData) throws JsonProcessingException {
        return callPlayFileAPI(callbackData,AudioFiles.WELCOME_EXISTING_USER.fileName);
    }

    /**
     * <p>This method will play the first level Navigation Menu. The user will have to enter one of the following numbers to successfully navigate to the next step
     * <br> 1 - Play Jokes</br>
     * <br> 9 - Unsubscribe</br>
     * <br> 0 - Hang up</br>
     * </p>
     * @param callbackData
     * @return PlayFileAwaitInputResponse response
     */
    public PlayFileAwaitInputResponse playExistingCustomerNavigationMenu(CallbackData callbackData) throws JsonProcessingException {
        return callPlayFileAndAwaitInputAPI(callbackData,AudioFiles.CONTENT_NAVIGATION.fileName);
    }

    /**
     * <p>This method will play the Listen to Jokes Menu, for an already subscribed user. The user will have to enter one of the following numbers to successfully navigate to the next step
     * <br> 1- Play Joke </br>
     * <br> 2- Play Next Joke </br>
     * <br> *- Previous Menu</br>
     * <br> #- Main Menu</br>
     * <br> 0- Hang Up</br>
     * </p>
     * @param callbackData
     * @return
     * @throws JsonProcessingException
     */
    public PlayFileAwaitInputResponse playListenToJokesMenu(CallbackData callbackData) throws JsonProcessingException {
        //TODO change file name according to the one provided for second level navigation
        return callPlayFileAndAwaitInputAPI(callbackData,AudioFiles.CONTENT_NAVIGATION.fileName);
    }

    /**
     * <p>This method will play the Joke, and then play the Listen to Jokes Menu, so that the user can navigate within
     * the Listen menu for the next joke, previous joke and finally end the the flow</p>
     * @param callbackData
     * @return
     * @throws JsonProcessingException
     */
    public PlayFileAwaitInputResponse playNextJoke(CallbackData callbackData) throws JsonProcessingException {
        //TODO - iterate the jokes and play accordingly, once the file names are avaialble
        String nextJokeFile = "NextJoke";
        String listenToJokesFile = "ListenToJokes";
        IVRResponse playFileResponse = callPlayFileAPI(callbackData, nextJokeFile);
        if(ResponseCodesEnum.REQ_SUCCESS.errorCode.equals(playFileResponse.code)){
            return callPlayFileAndAwaitInputAPI(callbackData,listenToJokesFile);
        }else{
            System.out.println(getErrorMessage(playFileResponse));
            return null;
        }
    }

    /**
     * <p>This method will play next Joke, and then play the Listen to Jokes Menu, so that the user can navigate within
     * the Listen menu for the previous joke, next joke and finally end the the flow</p>
     * @param callbackData
     * @return
     * @throws JsonProcessingException
     */
    public PlayFileAwaitInputResponse playPreviousJoke(CallbackData callbackData) throws JsonProcessingException {
        //TODO - iterate the jokes and play accordingly, once the file names are avaialble
        String previousJokeFile = "PreviousJoke";
        String listenToJokesFile = "ListenToJokes";
        IVRResponse playFileResponse = callPlayFileAPI(callbackData, previousJokeFile);
        if(ResponseCodesEnum.REQ_SUCCESS.errorCode.equals(playFileResponse.code)){
            return callPlayFileAndAwaitInputAPI(callbackData,listenToJokesFile);
        }else{
            System.out.println(getErrorMessage(playFileResponse));
            return null;
        }
    }

    /**
     * <p>This method will invoke the unsubscribe flow, which will remove this user from the service. We need to check with Mobitel on how this flow will go</p>
     * @param callbackData
     * @return
     * @throws JsonProcessingException
     */
    public void unsubscribe(CallbackData callbackData) throws JsonProcessingException {
        String unsubscribedMessageFile = "UnsubscribedMessageFile";
        String errorMessageFile = "errorMessageFile";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = BuildUtils.buildHttpHeadersForCommonAPICalls(callbackData);
        headers.add(HttpHeadersEnum.DATA.value, mapper.writeValueAsString(callbackData));
        HttpEntity<String> request = new HttpEntity<String>(headers);
        IVRResponse response = restTemplate.postForObject(unsubscribeUrl, request, IVRResponse.class);

        if(ResponseCodesEnum.REQ_SUCCESS.errorCode.equals(response.code)){
            callPlayFileAPI(callbackData,AudioFiles.THANK_YOU.fileName);
            hangUp(callbackData);
        }else if (ResponseCodesEnum.UNSUBSCRIBE_OPERATION_FAILED.errorCode.equals(response.code) ||
                ResponseCodesEnum.UNSUBSCRIBE_INVALID_PACKAGE.errorCode.equals(response.code) ||
                ResponseCodesEnum.UNSUBSCRIBE_INVALID_PRODUCT_ID.errorCode.equals(response.code) ||
                ResponseCodesEnum.UNSUBSCRIBE_NOT_A_SUBSCRIBED_USER.errorCode.equals(response.code)){
            callPlayFileAPI(callbackData,AudioFiles.TRY_AGAIN_LATER.fileName);
            hangUp(callbackData);
        } else{
            System.out.println(getErrorMessage(response));
        }
    }

    /**
     * <p>This method is invoked when the user opts in to hang up the call. A Thank you file is played, and then the API Call is invoked to hang up the call</p>
     * @param callbackData
     * @throws JsonProcessingException
     */
    public void hangUp(CallbackData callbackData) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = BuildUtils.buildHttpHeadersForCommonAPICalls(callbackData);
        headers.add(HttpHeadersEnum.DATA.value, mapper.writeValueAsString(callbackData));
        HttpEntity<String> request = new HttpEntity<String>(headers);
        IVRResponse response = restTemplate.postForObject(hangUpUrl, request, IVRResponse.class);
        System.out.println(response.code);
    }

    public IVRResponse callHelloWorldEndpoint(CallbackData callbackData, String fileName) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        IVRResponse ivrResponse;
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("X-IBM-Client-Id","984eca56-cc2e-48d2-aff4-d631c21a9292");
        headers.add("content-type","application/json");
        headers.add("accept","application/json");
        headers.add("data", mapper.writeValueAsString(callbackData));

        HttpEntity<String> request = new HttpEntity<String>(headers);

        String response = restTemplate.postForObject(helloWorldUrl, request, String.class);
        System.out.println(response);
        ivrResponse = mapper.readValue(response, IVRResponse.class);
        return ivrResponse;
    }

    private IVRResponse callPlayFileAPI(CallbackData callbackData, String fileName) throws JsonProcessingException {
        IVRResponse ivrResponse;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = BuildUtils.buildHttpHeadersForPlayFileAndAwaitInput(callbackData, fileName);
        HttpEntity<String> request = new HttpEntity<String>(headers);
        String response = restTemplate.postForObject(playAndAwaitInputUrl, request, String.class);
        System.out.println(response);
        ivrResponse = mapper.readValue(response, IVRResponse.class);
        return ivrResponse;
    }
    private PlayFileAwaitInputResponse callPlayFileAndAwaitInputAPI(CallbackData callbackData, String fileName) throws JsonProcessingException {
        PlayFileAwaitInputResponse playFileAwaitInputResponse;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = BuildUtils.buildHttpHeadersForPlayFileAndAwaitInput(callbackData, fileName);
        HttpEntity<String> request = new HttpEntity<String>(headers);
        String response = restTemplate.postForObject(playAndAwaitInputUrl, request, String.class);
        System.out.println(response);
        playFileAwaitInputResponse = mapper.readValue(response, PlayFileAwaitInputResponse.class);
        return playFileAwaitInputResponse;
    }

    private String getErrorMessage (IVRResponse ivrResponse) {
        ResponseCodesEnum a =  Arrays.stream(ResponseCodesEnum.values()).filter(e->e.errorCode.equals(ivrResponse.getCode())).findFirst().orElse(null);
        return a != null ? a.errorMessage : "Could not retrieve error message";
    }

}
