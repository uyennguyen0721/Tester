/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ou_software_testing.ou_software_testing.controller;

import com.ou_software_testing.ou_software_testing.Utils;
import com.ou_software_testing.ou_software_testing.pojo.Order;
import com.ou_software_testing.ou_software_testing.services.JdbcServices;
import com.ou_software_testing.ou_software_testing.services.OrderServices;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

/**
 *
 * @author Admin
 */
public class StatisticMenuController extends Controller{
    @FXML private TextField txt_month, txt_year;
    @FXML private TableView<Order> tb_orders;
    @FXML private Text txt_sum;
    private List<Order> listOrders = new ArrayList<>();
    
    
    private static final Pattern numberRegex = Pattern.compile("^(0?[1-9]|1[012])$") ;
    private static final Pattern yearRegex = Pattern.compile("^\\d{4}$") ;
    
    @FXML
    private void getStatistics() {
        Connection conn = JdbcServices.getConnection();
        
        String month = txt_month.getText().strip();
        String year = txt_year.getText().strip();
        
        
        if(Utils.checkValidRegex(yearRegex, year) == false) {
            Alert a =Utils.makeAlert(Alert.AlertType.INFORMATION, "Nhập sai thông tin", 
                    "Nhập sai thông tin", "Vui lòng nhập đúng thông tin năm (YYYY)");
            a.show();
            
            return;
        }
        
        if((month.length() != 0 && year.length() == 0) || (month.length() == 0 && year.length() == 0)) {
            Alert a =Utils.makeAlert(Alert.AlertType.INFORMATION, "Thiếu thông tin", 
                    "Thiếu thông tin", "Vui lòng nhập đầy đủ thông tin");
            a.show();
        } else {
                       
            OrderServices orderServices = new OrderServices(conn);
            //Thống kê đầy đủ theo tháng + năm
            if(month.length() != 0 && year.length() != 0) { 
                
                if(Utils.checkValidRegex(numberRegex, month) == false) {            
                    Alert a =Utils.makeAlert(Alert.AlertType.INFORMATION, "Nhập sai thông tin", 
                            "Nhập sai thông tin", "Vui lòng nhập đúng thông tin tháng");
                    a.show();
                    return;
                }
                
                listOrders = orderServices.getListOrders(month, year);
            } else { 
                listOrders = orderServices.getListOrders(year);   
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb); 
        loadColumns();
    }
    
    @FXML 
    private void getTable() {
        listOrders.clear();
        getStatistics();
        if(listOrders.isEmpty()) {
            Alert a = Utils.makeAlert(Alert.AlertType.INFORMATION, "Information", 
                    "Không tìm thấy dữ liệu", "Không tìm thấy dữ liệu nào phù hợp với dữ liệu đầu vào. Vui lòng thử lại dữ liệu đầu vào khác");            
            a.show();
        } else {
            loadOrders();
            UpdateSum();
        }
    }
    
    private void UpdateSum() {
        BigDecimal sum = new BigDecimal(0);
        for( Order o : this.listOrders) {
            sum = sum.add(o.getPrice().multiply(new BigDecimal(o.getCount())));
        }
        txt_sum.setText(sum.toString());
    }
    
    
    protected void loadColumns() {
        TableColumn colId = new TableColumn("Product Id");
        colId.setCellValueFactory(new PropertyValueFactory("product_id"));

        TableColumn colUID = new TableColumn("User Id");
        colUID.setCellValueFactory(new PropertyValueFactory("user_id"));
        
        TableColumn colDate = new TableColumn("Date");
        colDate.setCellValueFactory(new PropertyValueFactory<Order, Date>("date_time"));
        
        TableColumn colPaymentMethod = new TableColumn("Payment method");
        colPaymentMethod.setCellValueFactory(new PropertyValueFactory("payment_method"));
        
        TableColumn colPrice = new TableColumn("Price");
        colPrice.setCellValueFactory(new PropertyValueFactory("price"));
        
        TableColumn colCount = new TableColumn("Count");
        colCount.setCellValueFactory(new PropertyValueFactory("count"));
        
        tb_orders.getColumns().addAll(colId, colUID, colDate, colPaymentMethod, colPrice, colCount);
    }
    
    protected void loadOrders(){
        tb_orders.setItems(FXCollections.observableArrayList(this.listOrders));
    }
}
