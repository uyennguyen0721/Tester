package com.ou_software_testing.ou_software_testing.controller;

import com.ou_software_testing.ou_software_testing.Rule;
import com.ou_software_testing.ou_software_testing.Utils;
import com.ou_software_testing.ou_software_testing.pojo.Category;
import com.ou_software_testing.ou_software_testing.pojo.ListCategory;
import com.ou_software_testing.ou_software_testing.pojo.Product;
import com.ou_software_testing.ou_software_testing.services.CategoryServices;
import com.ou_software_testing.ou_software_testing.services.JdbcServices;
import com.ou_software_testing.ou_software_testing.services.ProductServices;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class ProductsMenuController extends ManageProductTableController{
    @FXML protected TextField txt_product_name;
    @FXML protected TextField txt_pid;
    @FXML protected TextField txt_quantity;
    @FXML protected TextField txt_price;
    @FXML protected ComboBox cb_category;
    
    ListCategory listCategory;
    
    @FXML
    public void HandleAdd(ActionEvent actionEvent) {
        Product p = new Product();
        
        if(cb_category.getSelectionModel().isEmpty()) {
            Utils.makeAlert(Alert.AlertType.ERROR, "Chưa chọn loại sản phẩm, vui lòng chọn lại").show();
            return;
        } else if (txt_product_name.getText().strip().isEmpty()) {
            Utils.makeAlert(Alert.AlertType.ERROR, "Chưa nhập tên sản phẩm, vui lòng nhập lại").show();
            return;
        } else if (txt_quantity.getText().isEmpty()) {
            Utils.makeAlert(Alert.AlertType.ERROR, "Chưa nhập số lượng, vui lòng nhập lại").show();
            return;
        } else if (txt_price.getText().isEmpty()) {
            Utils.makeAlert(Alert.AlertType.ERROR, "Chưa nhập đơn giá, vui lòng nhập lại").show();
            return;
        } else if (Integer.parseInt(txt_quantity.getText()) < Rule.getMIN_PRODUCT() || Integer.parseInt(txt_quantity.getText()) > Rule.getMAX_PRODUCT() ) {
            Utils.makeAlert(Alert.AlertType.ERROR, 
                    String.format("số lượng sản phẩm phải trong khoảng từ %d tới %d, vui lòng nhập lại", 
                            Rule.getMIN_PRODUCT(),
                            Rule.getMAX_PRODUCT()
                    )).show();
            return;
        } else if (txt_pid.getText().isEmpty()) {
            Utils.makeAlert(Alert.AlertType.ERROR, "Chưa nhập id sản phẩm, vui lòng nhập lại").show();
            return;
        }
        
        p.setId(Integer.parseInt(txt_pid.getText()));
        Category cate = (Category) cb_category.getSelectionModel().getSelectedItem();
        p.setCategory(cate.getId());
        p.setCount(Integer.parseInt(txt_quantity.getText()));
        p.setName(txt_product_name.getText());
        p.setOrigin("VN-DEFAULT");
        p.setPrice(new BigDecimal(txt_price.getText()));
        p.setSize("NULL");
        try {
            Connection conn = JdbcServices.getConnection();
            ProductServices productServices = new ProductServices(conn);
            
            if (productServices.insertProduct(p)){
                listProduct.addProduct(p);
                loadProducts();
            } else {
                Utils.makeAlert(Alert.AlertType.ERROR, "ADD FAILED").show();
            }
            
        } catch (NumberFormatException | SQLException ex) {
            System.err.println(ex);
        }
    } 
    
    @FXML
    public void HandleDelete(ActionEvent actionEvent) {
        Product p = tb_search_product.getSelectionModel().getSelectedItem();

        Connection conn = JdbcServices.getConnection();
        ProductServices productServices = new ProductServices(conn);

        if (productServices.deleleProductById(p.getId())){
            listProduct.removeProductById(p.getId());
            loadProducts();
        } else{
            Utils.makeAlert(Alert.AlertType.ERROR, "DELETE FAILED").show();
        };                
    }
    
    @FXML
    public void HandleEdit(ActionEvent actionEvent) {
        Product p = new Product();
        
        p.setId(Integer.parseInt(txt_pid.getText()));
        Category cate = (Category) cb_category.getSelectionModel().getSelectedItem();
        p.setCategory(cate.getId());
        p.setCount(Integer.parseInt(txt_quantity.getText()));
        p.setName(txt_product_name.getText());
        p.setOrigin("VN-DEFAULT");
        p.setPrice(new BigDecimal(txt_price.getText()));
        p.setSize("NULL");
        Connection conn = JdbcServices.getConnection();
        ProductServices productServices = new ProductServices(conn);
        if (productServices.editProductById(p)){
            listProduct.removeProductById(p.getId());
            listProduct.addProduct(p);
            loadProducts();
        } else {
            Utils.makeAlert(Alert.AlertType.ERROR, "ADD FAILED").show();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb); 
        this.getCategoryList();
        txt_quantity.textProperty().addListener((ObservableValue<? extends String> ov, String t, String t1) -> {
                if(!t1.matches("\\d*"))  {
                    txt_quantity.setText(t1.replaceAll("[^\\d]", ""));
                }
        });
        txt_price.textProperty().addListener((ObservableValue<? extends String> ov, String t, String t1) -> {
                if(!t1.matches("\\d*"))  {
                    txt_price.setText(t1.replaceAll("[^\\d]", ""));
                }
        });
        tb_search_product.setOnMouseClicked(event -> {
            Product p = tb_search_product.getSelectionModel().getSelectedItem();
            
            cb_category.getSelectionModel().select(listCategory.getCategoryById(p.getCategory()));
            txt_quantity.setText(String.valueOf(p.getCount()));
            txt_pid.setText(String.valueOf(p.getId()));
            txt_product_name.setText(p.getName());
            txt_price.setText(p.getPrice().toString());
        });
    }
    
    @FXML
    private void getCategoryList() {
        try {
            Connection conn = JdbcServices.getConnection();
            CategoryServices categoryServices = new CategoryServices(conn);

            List<Category> catesList = new ArrayList<>();
            catesList = categoryServices.getCategorys();
            listCategory = new ListCategory(catesList);
            ObservableList obList = FXCollections.observableList(catesList);
            cb_category.setItems(obList);

            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductsMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
