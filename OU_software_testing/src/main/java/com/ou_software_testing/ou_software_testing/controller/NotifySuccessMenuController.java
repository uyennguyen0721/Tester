/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ou_software_testing.ou_software_testing.controller;

import com.ou_software_testing.ou_software_testing.DataTemporary;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

/**
 *
 * @author Admin
 */
public class NotifySuccessMenuController extends Controller implements Initializable{
    @FXML private Text txt_order_price;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txt_order_price.setText(DataTemporary.getListProductSelection().getTotalPrice().toString() + " đ");
    }
}
