package com.example.ivrsolution.enums;

public enum AudioFiles {
    WELCOME_NEW_USER("welcome_new_user_7240.wav"),
    WELCOME_EXISTING_USER("welcome_existing_user_5635.wav"),
    SUBSCRIPTION_SUCCEEDED("subscription_succ_6118.wav"),
    NO_ACCOUNT_BALANCE("no_balance_1780.wav"),
    TRY_AGAIN_LATER("try_again_later_9038.wav"),
    CHARGING("charging_5906.wav"),
    WRONG_INPUT("wrongInput_6724.wav"),
    CONTENT_NAVIGATION("content_navigation_1600.wav"),
    BACK_TO_MAIN_MENU("back_to_main_menu_1588.wav"),
    TO_DISCONNECT("to_disconnect_8495.wav"),
    THANK_YOU("thank_you_6206.wav");

    public final String fileName;

    AudioFiles(String fileName) {
        this.fileName = fileName;
    }
}

//        welcome_new_user_7240.wav
//        welcome_existing_user_5635.wav
//        subscription_succ_6118.wav
//        no_balance_1780.wav
//        try_again_later_9038.wav
//        charging_5906.wav
//        wrongInput_6724.wav
//        content_navigation_1600.wav
//        back_to_main_menu_1588.wav
//        to_disconnect_8495.wav
//        thank_you_6206.wav