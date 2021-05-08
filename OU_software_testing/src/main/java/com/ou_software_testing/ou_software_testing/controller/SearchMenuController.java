/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ou_software_testing.ou_software_testing.controller;

import com.ou_software_testing.ou_software_testing.App;
import com.ou_software_testing.ou_software_testing.DataTemporary;
import com.ou_software_testing.ou_software_testing.GlobalContext;
import com.ou_software_testing.ou_software_testing.Utils;
import com.ou_software_testing.ou_software_testing.pojo.ListProduct;
import com.ou_software_testing.ou_software_testing.pojo.Product;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TablePosition;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;


/**
 *
 * @author Admin
 */
public class SearchMenuController extends ManageProductTableController{
    @FXML protected Text txt_product_name;
    @FXML protected Text txt_pid;
    @FXML protected TextField txt_quantity;
    @FXML protected Text txt_price;
    @FXML protected Text txt_product_category;
    
    private ListProduct listProductsOrders = DataTemporary.getListProductSelection();
    private ListProduct listChoose = new ListProduct();
    ObservableList<TablePosition> selectedCells = FXCollections.observableArrayList();
    
    @FXML
    private void switchToSellMenu(ActionEvent actionEvent) throws IOException {
        try {
            listChoose.setCount1();
            DataTemporary.setListProductSelection(listChoose);
            this.getNotify(true);
            App.setRoot("sell_menu");
        } catch (IOException ex) {
            this.getNotify(false);
            System.out.println("Error while switching to sell_menu");
            System.err.println(ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb); //To change body of generated methods, choose Tools | Templates.
        
        tb_search_product.getSelectionModel().setSelectionMode(
            SelectionMode.MULTIPLE
        );
        tb_search_product.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                Product p = tb_search_product.getSelectionModel().getSelectedItem();
            
                List<Product> list = tb_search_product.getSelectionModel().getSelectedItems();

                listChoose.getListProduct().clear();
                for (int i = 0; i < list.size(); i++)
                    listChoose.addProduct(list.get(i));

                txt_quantity.setText(String.valueOf(p.getCount()));
                txt_pid.setText(String.valueOf(p.getId()));
                txt_product_name.setText(p.getName());
                txt_price.setText(p.getPrice().toString());
            }
        });
    }
    
    @FXML 
    private void addProductsInProductsList() {
        Product p = tb_search_product.getSelectionModel().getSelectedItem();
        int count = 0;
        try {
            count = Integer.parseInt(txt_quantity.getText());
        } catch(Exception ex) {
            Alert a = Utils.makeAlert(Alert.AlertType.ERROR, "Nhập sai thông tin", 
                    "Nhập sai thông tin số lượng", "Vui lòng nhập lại thông tin số lượng đúng định dang số");
            a.show();
            return;
        }
        if( count > p.getCount() || count <= 0 )  {
            Alert a = Utils.makeAlert(Alert.AlertType.ERROR, "Nhập sai thông tin", 
                    "Nhập sai thông tin số lượng", "Vui lòng nhập lại thông tin số lượng đúng");
            a.show();
            return;
        }
        
        boolean rs  = false;
        p.setCount(count);
        if(listProductsOrders.getListProduct().size() <= 0) {
            rs = listProductsOrders.addProduct(p);
            getNotify(rs);
        } else { 
            for (Product pro: listProductsOrders.getListProduct()) {
                if(pro.getId() == p.getId())  {
                    pro.setCount(pro.getCount() + count);
                    getNotify(true);
                    return;
                }
                rs = listProductsOrders.addProduct(p);
                getNotify(rs);
            }
        }
        
        
    }
    
    @FXML 
    private void switchToOrderOrSellMenu() {
        try {
            DataTemporary.setListProductSelection(listProductsOrders);
            if("user".equals(GlobalContext.getUser().getRole())) 
                App.setRoot("order_list");
            else 
                App.setRoot("sell_menu");
        } catch (IOException ex) {
            Logger.getLogger(SearchMenuController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    //Use for checking order orders success or not.
    private void getNotify(Boolean success) {
        Alert a;
        if(success) {
            a = Utils.makeAlert(Alert.AlertType.INFORMATION, "Order success", 
                    "Information", "Order successful, please go to order to check list");
        } else 
            a = Utils.makeAlert(Alert.AlertType.ERROR, "Fail ordering ", 
                    "Error", "Order fail, please check products list");
        
        a.show();
    }
}
