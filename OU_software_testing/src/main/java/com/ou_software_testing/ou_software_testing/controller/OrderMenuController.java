/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ou_software_testing.ou_software_testing.controller;

import com.ou_software_testing.ou_software_testing.DataTemporary;
import com.ou_software_testing.ou_software_testing.Utils;
import com.ou_software_testing.ou_software_testing.pojo.ListProduct;
import com.ou_software_testing.ou_software_testing.pojo.Product;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 *
 * @author Admin
 */
public class OrderMenuController extends ManageProductTableController{
    
    @FXML private Button btn_adjust, btn_delete, btn_abort, btn_confirm_order;
    @FXML private Text txt_sum;
    @FXML private TableView<Product> tb_orders;
    private ListProduct listProductOrder = DataTemporary.getListProductSelection();
    
    @FXML private TextField txt_quantity;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadColumns(tb_orders);
        updateTable();
        
        if(listProductOrder.getListProduct().size() > 0) {
            btn_confirm_order.setDisable(false);
            btn_abort.setDisable(false);
        }
        
        tb_orders.setOnMouseClicked(event -> {
            Product p = tb_orders.getSelectionModel().getSelectedItem();  
            if(p != null) {
                btn_adjust.setDisable(false);
                btn_delete.setDisable(false);
            } else {
                btn_adjust.setDisable(true);
                btn_delete.setDisable(true);
            }
            txt_quantity.setText(String.valueOf(p.getCount()));
        });
    }
    
    private void updateTable()  {
        tb_orders.getItems().clear();
        loadProducts(tb_orders, listProductOrder);
        UpdateSum();
    }
    
    private void UpdateSum() {
        BigDecimal sum = new BigDecimal(0);
        for( Product p : listProductOrder.getListProduct()) {
            sum = sum.add(p.getPrice().multiply(new BigDecimal(p.getCount())));
        }
        txt_sum.setText(sum.toString());
    }
    
    @FXML 
    private void onDelete() {
        for(Product p: listProductOrder.getListProduct()) {
            if  (p.getId() ==  tb_orders.getSelectionModel().getSelectedItem().getId()) {
                listProductOrder.removeProductById(p.getId());
            }
        }
        DataTemporary.setListProductSelection(listProductOrder);
        updateTable();
    }
    
    @FXML
    private void onAdjust() {
        int quantity = Integer.parseInt(txt_quantity.getText());
        int productCount = getProductByID(tb_orders.getSelectionModel().getSelectedItem().getId()).getCount();
        if(quantity > 0 &&  quantity <= productCount ) { 
            
            for(Product p: listProductOrder.getListProduct()) {
                if  (p.getId() ==  tb_orders.getSelectionModel().getSelectedItem().getId()) {
                    p.setCount(quantity);
                    break;
                }
            }
            
            DataTemporary.setListProductSelection(listProductOrder);
            updateTable();
        } else {
            Alert a = Utils.makeAlert(Alert.AlertType.ERROR, "Nhập sai thông tin", 
                    "Nhập sai thông tin số lượng", "Vui lòng nhập lại thông tin số lượng đúng");
            a.show();
        }
    }
    
    @FXML 
    private void onAbort() {
        Alert a = Utils.makeAlert(Alert.AlertType.CONFIRMATION, "Thông tin", "Hủy đơn hàng", "Bạn có thật sự muốn hủy đơn hàng này không?");
        
        Optional<ButtonType>  rs = a.showAndWait();
        
        if(rs.get() == ButtonType.OK) {
            listProductOrder.getListProduct().clear();
            DataTemporary.setListProductSelection(listProductOrder);
            updateTable();
        }       
    }
    
    
    //switch to Payment controller which dont exist yet - dung
    @FXML 
    private void onConfirmOrder() {
        
    }
}