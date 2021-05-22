/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ou_software_testing.ou_software_testing.controller;

import com.ou_software_testing.ou_software_testing.App;
import com.ou_software_testing.ou_software_testing.DataTemporary;
import java.io.IOException;
import javafx.event.ActionEvent;
import com.ou_software_testing.ou_software_testing.GlobalContext;
import com.ou_software_testing.ou_software_testing.Utils;
import com.ou_software_testing.ou_software_testing.pojo.ListProduct;
import com.ou_software_testing.ou_software_testing.pojo.Product;
import com.ou_software_testing.ou_software_testing.services.JdbcServices;
import com.ou_software_testing.ou_software_testing.services.OrderServices;
import com.ou_software_testing.ou_software_testing.services.ProductServices;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 *
 * @author Admin
 */
public class NotifySuccessMenuController extends Controller implements Initializable{
    @FXML private Text txt_order_price,txt_money_received, txt_money_left;
    @FXML private TextField txt_money_amount;
    @FXML private Button btn_pay, btn_pay_momo;
    @FXML private GridPane gp_count_excess;
    private ListProduct ListProduct = DataTemporary.getListProductSelection();
    
    @FXML
    private void switchToMomopay(ActionEvent actionEvent) throws IOException {
        App.setRoot("momopay");
    }    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txt_order_price.setText(ListProduct.getTotalPrice().toString());
        
        if(!"user".equals(GlobalContext.getUser().getRole())) {
            txt_money_amount.textProperty()
                    .addListener((ObservableValue<? extends String> ov, String t, String t1) -> {
                if(!t1.matches("\\d*"))  {
                    txt_money_amount.setText(t1.replaceAll("[^\\d]", ""));
                    System.out.println(txt_money_amount.getText());
                }
            });
            txt_money_amount.setOnKeyPressed( event -> {
                checkIfNumber();
                if(event.getCode() == KeyCode.ENTER) {
                    countExcessAmount();
                }
            });
            btn_pay.setOnMouseClicked(event -> {
                Alert a = Utils.makeAlert(Alert.AlertType.INFORMATION, "Đang in hóa đơn");
                a.show();
            });
            btn_pay.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    insertOrder(1);
                }
            });
        } else {
            btn_pay.setText("Thanh toán khi nhận hàng");
            gp_count_excess.setDisable(true);
            gp_count_excess.setStyle("-fx-opacity: 0");
        }
    }
    
    @FXML
    public void checkOutOrder() {
        insertOrder(1);
    }
    
    private void insertOrder(int payment_method) {
        Connection conn;
        String successString = "Order successful: \n", failString = "Order fail: \n";
        try {
            conn = JdbcServices.getConnection();
            OrderServices orderServices = new OrderServices(conn);
            ProductServices productServices = new ProductServices(conn);
            for(Product p : ListProduct.getListProduct()) {
                boolean kqr = productServices.reduceProductCount(p.getId(),p.getCount());
                if(kqr) {
                    boolean kq = orderServices.addOrder(p.getId(), GlobalContext.getUser().getId(), 
                            payment_method, p.getPrice(), p.getCount());
                    if(kq)  {
                        successString += String.format("Success in ordering %s \n", p.getName());
                    }  
                }
                else {
                    failString += String.format("Failure in ordering %s \n", p.getName());
                }
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(NotifySuccessMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Alert a = Utils.makeAlert(Alert.AlertType.INFORMATION, "Orders", "Result of ordering", 
                String.format("%s \n ----- \n %s", successString,failString));
        a.show();
    }
    
    @FXML 
    private void checkIfNumber()  {
        if(txt_money_amount.getText().length() != 0)  { 
            double amount = Double.parseDouble(txt_money_amount.getText());
            txt_money_received.setText(String.format("%,.2f", amount));
        }
    }
    
    private void countExcessAmount() {
        double amount = Double.parseDouble(txt_money_amount.getText()) - Double.parseDouble(txt_order_price.getText());
        txt_money_left.setText(String.format("%,.2f", amount));
    }
}
