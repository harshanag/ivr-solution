package com.example.ivrsolution.enums;

public enum ResponseCodesEnum {
    REQ_SUCCESS("1000","Request Successful"),
    PARAM_INVALID("1001","Invalid Parameters"),
    SUBSCRIBE_PENDING_ACTION("1002","Subscribe - Pending Action"),
    UNSUBSCRIBE_OPERATION_FAILED("1001","Unsubscribe - Operation Failed"),
    UNSUBSCRIBE_INVALID_PRODUCT_ID("1003","Unsubscribe - Invalid Product ID"),
    UNSUBSCRIBE_NOT_A_SUBSCRIBED_USER("1004","Unsubscribe - Not a subscribed User"),
    UNSUBSCRIBE_INVALID_PACKAGE("1005","Unsubscribed Invalid Package"),
    SUBSCRIBE_REQ_TIMEOUT("1007","Subscribe - Request timed out"),
    SUBSCRIBE_INVALID_REF_NO("1008","Subscribe - Invalid Reference Number"),
    IVR_NOT_AVAILABLE("7001","IVR Number does not exist"),
    CONNECTION_CLOSED_1("8001","Connection closed , Call already ended or Connection failure"),
    CONNECTION_CLOSED_2("8002","Connection closed , Call already ended or Connection failure"),
    CONNECTION_CLOSED_PREMATURELY("8003","Connection error or Call ended prematurely"),
    INVALID_PRODUCT("8004","Invalid Product Id"),
    SUBSCRIPTION_FAILED("8884","Subscription failed - getConsentAndSubscribe"),
    SUBSCRIPTION_CANCELLED("8885","Subscription cancelled - getConsentAndSubscribe"),
    ALREADY_SUBSCRIBED("8886","Already subscribed - getConsentAndSubscribe"),
    SUBSCRIPTION_CHECK_ERROR("8887","Error while checking Subscription - getConsentAndSubscribe"),
    INVALID_PRODUCT_SUBSCRIPTION("8888","Invalid product - getConsentAndSubscribe"),
    NO_DTMF_INPUT_FOR_SUBSCRIBE("8889","No DTMF input - getConsentAndSubscribe"),
    INVALID_ONE_TIME_CHARGE_AMOUNT("8005","Invalid one time charge amount - getConsentAndCharge"),
    ONE_TIME_CHARGE_ELIGIBILITY_FAILED("8006","One time charging eligibility check failed - getConsentAndCharge"),
    ONE_TIME_CHARGE_NOT_ELIGIBLE("8007","Not eligible for one time charging - getConsentAndCharge"),
    PAYMENT_PROCESSING_FAILED("8890","Payment processing failed - getConsentAndCharge"),
    PAYMENT_CANCELLED("8891","Payment cancelled - getConsentAndCharge"),
    NO_DTMF_INPUT_FOR_CHARGING("8892","No DTMF input - getConsentAndCharge"),
    INVALID_REQUEST("9999","Request Successful");

    public final String errorCode;
    public final String errorMessage;

    ResponseCodesEnum(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
