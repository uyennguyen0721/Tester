/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ou_software_testing.ou_software_testing.controller;

import com.ou_software_testing.ou_software_testing.DataTemporary;
import com.ou_software_testing.ou_software_testing.GlobalContext;
import com.ou_software_testing.ou_software_testing.Utils;
import com.ou_software_testing.ou_software_testing.momopay.MomoPay;
import com.ou_software_testing.ou_software_testing.pojo.Order;
import com.ou_software_testing.ou_software_testing.pojo.Product;
import com.ou_software_testing.ou_software_testing.services.JdbcServices;
import com.ou_software_testing.ou_software_testing.services.OrderServices;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 *
 * @author Admin
 */
public class MomopayController extends ManageProductTableController{
    @FXML private Text txt_orderId, txt_staff_name, txt_date, txt_time, 
            txt_total, txt_status, txt_amount_paid;
    @FXML private Button btn_print;
    @FXML private ImageView image_QR;
    
    String orderId;
    MomoPay momoPay;
    String amount = DataTemporary.getListProductSelection().getTotalPrice()
                .setScale(0, RoundingMode.UP).toString();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listProduct = DataTemporary.getListProductSelection();
        Date dNow = new Date();
        SimpleDateFormat dmy = new SimpleDateFormat ("dd-MM-yyyy");
        SimpleDateFormat hm = new SimpleDateFormat ("hh:mm");
        
        loadColumns();
        loadProducts();
        
        txt_staff_name.setText(GlobalContext.getUser().getName());
        txt_date.setText(dmy.format(dNow));
        txt_time.setText(hm.format(dNow));
        txt_total.setText(DataTemporary.getListProductSelection().getTotalPrice().toString());
        
        createQRcode();
    }
    
    @FXML
    public void createQRcode(){
        orderId = String.valueOf(System.currentTimeMillis());
        txt_orderId.setText(orderId);
        String URL = "https://google.com.vn";
        
        try {
            MomoPay.main(orderId, amount);
            InputStream stream = new FileInputStream(MomoPay.getPathQR());
            Image image = new Image(stream);
            image_QR.setImage(image);
        } catch (Exception ex) {
            Logger.getLogger(MomopayController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void cancelPay(ActionEvent actionEvent) throws IOException{
        DataTemporary.setListProductSelection(null);
        switchToMain(actionEvent);
    }
    
    @FXML
    public void checkTransaction(ActionEvent actionEvent) throws Exception{
        if (MomoPay.check(orderId)){
            insertOrder(2);
            txt_status.setText("Success");
            txt_status.setFill(Color.GREEN);
            txt_amount_paid.setText(amount);
            btn_print.setDisable(false);
            
        }
    }
    private void insertOrder(int payment_method) {
        try {
            Connection conn = JdbcServices.getConnection();
            OrderServices orderServices = new OrderServices(conn);
            
            List<Order> listOrder = new ArrayList<>();
            
            for (Product p : listProduct.getListProduct()) {
                listOrder.add(new Order(p.getCount(), 1, p.getId(), 
                        GlobalContext.getUser().getId(), p.getPrice()));
            }
            orderServices.insertOrder(listOrder);
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(NotifySuccessMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Alert a = Utils.makeAlert(Alert.AlertType.INFORMATION, "Orders", "Result of ordering", 
                "Order successful");
        a.show();
    }
}
