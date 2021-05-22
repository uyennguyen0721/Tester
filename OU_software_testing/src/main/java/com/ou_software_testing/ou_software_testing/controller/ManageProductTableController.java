package com.ou_software_testing.ou_software_testing.controller;

import com.ou_software_testing.ou_software_testing.Utils;
import com.ou_software_testing.ou_software_testing.pojo.ListProduct;
import com.ou_software_testing.ou_software_testing.pojo.Product;
import com.ou_software_testing.ou_software_testing.services.JdbcServices;
import com.ou_software_testing.ou_software_testing.services.ProductServices;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


public class ManageProductTableController extends Controller{
    @FXML protected TextField txt_search_keyword;
    @FXML protected TableView<Product> tb_search_product;
    
    protected ListProduct listProduct = new ListProduct();
    
    public void onChangeText(String kw){
        listProduct.getListProduct().clear();
        filterProductsByKeyword(kw);
        loadProducts();
    }
    
    private void filterProductsByKeyword(String kw){
        Connection conn = JdbcServices.getConnection();
        ProductServices productServices = new ProductServices(conn);
        boolean flag = false;
        Product p = null;
        try {
            int id = Utils.ParseIntWithTryCatch(kw);
            p = productServices.getProductById(id);
            if (p!=null)
                this.listProduct.addProduct(p);
        } catch (SQLException ex) {
            Logger.getLogger(SearchMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(flag == true)
            return;
        try {
            conn = JdbcServices.getConnection();
            productServices = new ProductServices(conn);
            ListProduct list = productServices.getProductByName(kw);
            if (p!=null){
                for (Product pro : list.getListProduct()) {
                    if(pro.getId() == p.getId()){
                        list.removeProductById(p.getId());
                        break;                        
                    }
                }
            }
            if (list != null)
                this.listProduct.concatList(list);
        } catch (SQLException ex) {
            Logger.getLogger(SearchMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //sử dụng trong order menu controller
    protected Product getProductByID(int id) {
        Connection conn = JdbcServices.getConnection();
        ProductServices productServices = new ProductServices(conn);
        try {
            
            Product p = productServices.getProductById(id);
            if(p != null) {
                return p;
            }
            
        } catch (Exception ex) {
            System.out.println(ex);
        }
        
        return null;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        txt_search_keyword.textProperty().addListener((observable, oldValue, newValue) -> {
            onChangeText(newValue);
        });  
        filterProductsByKeyword("");
        loadColumns();
        loadProducts();
    }
    
    protected void loadColumns() {
        TableColumn colId = new TableColumn("PID");
        colId.setCellValueFactory(new PropertyValueFactory("id"));

        TableColumn colName = new TableColumn("Name");
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        
        TableColumn colQuantity = new TableColumn("Quantity");
        colQuantity.setCellValueFactory(new PropertyValueFactory("count"));
        
        TableColumn colPrice = new TableColumn("Price");
        colPrice.setCellValueFactory(new PropertyValueFactory("price"));
        
        tb_search_product.getColumns().addAll(colId, colName, colQuantity, colPrice);
    }
    
    protected void loadColumns(TableView tableView) {
        TableColumn colId = new TableColumn("PID");
        colId.setCellValueFactory(new PropertyValueFactory("id"));

        TableColumn colName = new TableColumn("Name");
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        
        TableColumn colQuantity = new TableColumn("Quantity");
        colQuantity.setCellValueFactory(new PropertyValueFactory("count"));
        
        TableColumn colPrice = new TableColumn("Price");
        colPrice.setCellValueFactory(new PropertyValueFactory("price"));
        
        tableView.getColumns().addAll(colId, colName, colQuantity, colPrice);
    }
    
    protected void loadProducts(){
        tb_search_product.setItems(FXCollections.observableArrayList(this.listProduct.getListProduct()));
    }
    
    protected void loadProducts(TableView tableView, ListProduct listProduct){
        tableView.setItems(FXCollections.observableArrayList(listProduct.getListProduct()));
    }
}
