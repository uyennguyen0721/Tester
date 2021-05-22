package com.ou_software_testing.ou_software_testing.momopay;


public class QueryStatusTransactionRequest extends PayGateRequest {

    public QueryStatusTransactionRequest(String partnerCode, String orderId, String accessKey,  String signature, String requestId, String requestType) {
        super(partnerCode, orderId, "", accessKey, "", signature, "", requestId, "", "", requestType);
    }
}
