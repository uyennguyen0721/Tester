/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ou_software_testing.ou_software_testing.controller;

import com.ou_software_testing.ou_software_testing.App;
import com.ou_software_testing.ou_software_testing.DataTemporary;
import com.ou_software_testing.ou_software_testing.GlobalContext;
import com.ou_software_testing.ou_software_testing.Rule;
import com.ou_software_testing.ou_software_testing.Utils;
import com.ou_software_testing.ou_software_testing.pojo.ListProduct;
import com.ou_software_testing.ou_software_testing.pojo.Product;
import java.io.IOException;
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
                txt_quantity.setText(String.valueOf(p.getCount()));
            } else {
                btn_adjust.setDisable(true);
                btn_delete.setDisable(true);
            }
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
        if (Utils.ParseIntWithTryCatch(txt_quantity.getText())== -1){
            Alert a = Utils.makeAlert(Alert.AlertType.ERROR, "Nhập sai thông tin", 
                    "Nhập sai thông tin số lượng", "Vui lòng nhập đúng định dạng số");
            a.show();
            return;
        }
        int quantity = Integer.parseInt(txt_quantity.getText());
        Product product = tb_orders.getSelectionModel().getSelectedItem();
        int totalProduct = product.getTotalProduct();
        if(quantity <= 0) {
            Alert a = Utils.makeAlert(Alert.AlertType.ERROR, "Nhập sai thông tin","Nhập sai thông tin số lượng", "Vui lòng nhập lại thông tin số lượng đúng.");
            a.show();
            return;
        }
        
        if((totalProduct - quantity) < 3 ) {
            Alert a = Utils.makeAlert(Alert.AlertType.ERROR, "Nhập sai thông tin", 
                "Nhập sai thông tin số lượng", "Số lượng hàng trong kho sau khi đặt phải lớn hơn 3.\n"
                        + "Số lượng hàng trong kho sau khi đặt là " +  
                        String.valueOf(totalProduct - quantity)
                        + ".\n Vui lòng nhập lại thông tin số lượng đúng.");
            a.show();
            return;
        }
        if(quantity > 0 &&  quantity <= totalProduct ) { 
            
//            product.setCount(quantity);
            listProductOrder.getProductById(product.getId()).setCount(quantity);
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
    private void onConfirmOrder() throws IOException {
        if(GlobalContext.getUser().getRole() == "user" 
                && listProductOrder.getListProduct().size() > Rule.getMAX_PRODUCT_ORDER()) {
            Alert a = Utils.makeAlert(Alert.AlertType.ERROR, "Thông tin không hợp lệ", 
                "Số lượng sản phẩm sai", "Khách hàng chỉ được đặt không quá "+ String.valueOf(Rule.getMAX_PRODUCT_ORDER())
                        + " sản phẩm trong một hóa đơn");
            a.show();
            return;
        }
        DataTemporary.setListProductSelection(listProductOrder);
        App.setRoot("notify_success_menu");
    }
}
