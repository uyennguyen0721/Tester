package com.ou_software_testing.ou_software_testing.momopay;



public class CaptureMoMoRequest extends PayGateRequest {

    public CaptureMoMoRequest(String partnerCode, String orderId, String orderInfo, String accessKey, String amount, String signature, String extraData, String requestId, String notifyUrl, String returnUrl, String requestType) {
        super(partnerCode, orderId, orderInfo, accessKey, amount, signature, extraData, requestId, notifyUrl, returnUrl, requestType);
    }
}