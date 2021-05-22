package com.ou_software_testing.ou_software_testing.controller;

import com.ou_software_testing.ou_software_testing.App;
import com.ou_software_testing.ou_software_testing.DataTemporary;
import com.ou_software_testing.ou_software_testing.GlobalContext;
import com.ou_software_testing.ou_software_testing.Rule;
import com.ou_software_testing.ou_software_testing.Utils;
import com.ou_software_testing.ou_software_testing.pojo.Category;
import com.ou_software_testing.ou_software_testing.pojo.ListCategory;
import com.ou_software_testing.ou_software_testing.pojo.ListProduct;
import com.ou_software_testing.ou_software_testing.pojo.Product;
import com.ou_software_testing.ou_software_testing.services.CategoryServices;
import com.ou_software_testing.ou_software_testing.services.JdbcServices;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
    private ListCategory listCategory = new ListCategory();
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
        
        getCategoryList();
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

                txt_quantity.setText("1");
                txt_pid.setText(String.valueOf(p.getId()));
                txt_product_name.setText(p.getName());
                txt_product_category.setText(listCategory.getNameById(p.getCategory()));
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
        if(count <= 0) {
            Alert a = Utils.makeAlert(Alert.AlertType.ERROR, "Nhập sai thông tin","Nhập sai thông tin số lượng", "Vui lòng nhập lại thông tin số lượng đúng.");
            a.show();
            return;
        }
        if((p.getCount() - count) < 3 ) {
            p = tb_search_product.getSelectionModel().getSelectedItem();
            Alert a = Utils.makeAlert(Alert.AlertType.ERROR, "Nhập sai thông tin", 
                "Nhập sai thông tin số lượng", "Số lượng hàng trong kho sau khi đặt phải lớn hơn 3. Vui lòng nhập lại thông tin số lượng đúng.");
            a.show();
            return;
        }
        if( count > p.getCount() )  {
            Alert a = Utils.makeAlert(Alert.AlertType.ERROR, "Nhập sai thông tin", 
                    "Nhập sai thông tin số lượng", "Vui lòng nhập lại thông tin số lượng đúng");
            a.show();
            return;
        }
        if(listProductsOrders.getListProduct().size() >= Rule.getMAX_PRODUCT_ORDER()) {
            Alert a = Utils.makeAlert(Alert.AlertType.ERROR, "Nhập sai thông tin", 
                    "Quá số lượng sản phẩm đặt cho phép", "Số lượng sản phẩm cho phép đặt trong một lần là " 
                            + String.valueOf(Rule.getMAX_PRODUCT_ORDER()));
            a.show();
            return;
        }
        DataTemporary.getListProductSelection().addProduct(p);
        DataTemporary.getListProductSelection().getProductById(p.getId()).setTotalProduct(p.getCount());
        DataTemporary.getListProductSelection().getProductById(p.getId()).setCount(count);
        
//        boolean rs  = false;
//        int oldCount = p.getCount();
//        if(listProductsOrders.getListProduct().size() <= 0) {
//            rs = listProductsOrders.addProduct(p);
//            getNotify(rs);
//        } else {
//            for (Product pro: listProductsOrders.getListProduct()) {
//                if(pro.getId() == p.getId())  {
//                    pro.setCount(pro.getCount() + count);
//                    getNotify(true);
//                    return;
//                }
//                rs = listProductsOrders.addProduct(p);
//                getNotify(rs);
//            }
//        }
//        p.setCount(oldCount);
        
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
    
    private void getCategoryList() {
        try {
            Connection conn = JdbcServices.getConnection();
            CategoryServices categoryServices = new CategoryServices(conn);
            List<Category> list = categoryServices.getCategorys();
            listCategory.setListCategory(list);
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductsMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
