package com.ou_software_testing.ou_software_testing.momopay;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.ou_software_testing.ou_software_testing.pojo.CheckStatusTransaction;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MomoPay {
    public static void main(String... args) {
        try {
            LogUtils.init();
            String URL = "https://google.com.vn";
            String amount = args[1];
            String orderId = args[0];
            String requestId = orderId;
            
            Environment environment = Environment.selectEnv(Environment.EnvTarget.DEV, Environment.ProcessType.PAY_GATE);
            
            CaptureMoMoResponse captureMoMoResponse = CaptureMoMo.process(
                    environment, orderId, requestId, amount,
                    "Pay With MoMo", URL, URL, "");
            if (captureMoMoResponse != null)
                saveQR(captureMoMoResponse.getPayUrl());
            
//            QueryStatusTransactionResponse queryStatusTransactionResponse = QueryStatusTransaction.process(
//                    environment, orderId, requestId);
//            PayGateResponse payGateResponse = PaymentResult.process(environment,new PayGateResponse());
//            System.out.println("paylink:" + captureMoMoResponse.getPayUrl());
        } catch (Exception ex) {
            Logger.getLogger(MomoPay.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }
    
    public static boolean check(String orderId) throws Exception{
        Environment environment = Environment.selectEnv(Environment.EnvTarget.DEV, Environment.ProcessType.PAY_GATE);
        
        QueryStatusTransactionResponse queryStatus = QueryStatusTransaction.process(
                environment, orderId, orderId);
        if (queryStatus == null || queryStatus.getErrorCode()==0) return true;
        return false;
    }
    
    public static void saveQR(String linkPay){
        try {
            Path root = FileSystems.getDefault().getPath("").toAbsolutePath();
            String filePath = Paths.get(root.toString(),"src", "main", "resources", "QRPaycode.jpg").toString();
            BitMatrix matrix = new MultiFormatWriter().encode(linkPay, BarcodeFormat.QR_CODE, 400, 400);
            MatrixToImageWriter.writeToPath(matrix, "jpg", Paths.get(filePath));
        } catch (Exception ex) {
            Logger.getLogger(MomoPay.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static String getPathQR(){
        Path root = FileSystems.getDefault().getPath("").toAbsolutePath();
        return Paths.get(root.toString(),"src", "main", "resources", "QRPaycode.jpg").toString();
    }
}
// id store abcdefABCDEF0123
// id store abcdefABCDEF0189
// pass store: 2FdZ@kw2wZYUcyR

