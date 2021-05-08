package com.ou_software_testing.ou_software_testing.controller;

import com.ou_software_testing.ou_software_testing.App;
import com.ou_software_testing.ou_software_testing.DataTemporary;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainController extends Controller{   
    private Stage notifyStage;
    private Scene notifyScene;
    private String[] name = {
        "sell_menu", 
        "statistic_menu", 
        "products_menu", 
        "search_menu", 
        "login",
        "main", 
        "main_user",
        "order_list", 
        "notify_success",
        "notify_fail"
    };
    
    @FXML
    private void switchToSellMenu(ActionEvent actionEvent) {       
        switchMenu(actionEvent, name[0]);
    }
    @FXML
    private void switchToStatisticMenu(ActionEvent actionEvent) {
        switchMenu(actionEvent, name[1]);
    }
    @FXML
    private void switchToProductsMenu(ActionEvent actionEvent) {
        switchMenu(actionEvent, name[2]);
    }
    @FXML
    private void switchToSearchMenu(ActionEvent actionEvent) {
        switchMenu(actionEvent, name[3]);
    }
    //Chuyển sang login thì clear list những sản phẩm đã chọn để chuyển sang user khác.
    @FXML
    private void switchToLogin(ActionEvent actionEvent){
        DataTemporary.clearListProductSelection();
        switchMenu(actionEvent, name[4]);
    }
    @FXML
    private void switchMain(ActionEvent actionEvent) {
        switchMenu(actionEvent, name[5]);
    }
    @FXML
    private void switchMainUser(ActionEvent actionEvent) {
        switchMenu(actionEvent, name[6]);
    }
    @FXML
    private void switchOrderList(ActionEvent actionEvent) {
        switchMenu(actionEvent, name[7]);
    }
    @FXML
    private void switchNotifySucess(ActionEvent actionEvent) {
        switchMenu(actionEvent, name[8]);
    }
    @FXML
    private void switchNotifyFail(ActionEvent actionEvent) {
        switchMenu(actionEvent, name[9]);
    }
    
    private void switchMenu(ActionEvent actionEvent, String name) {
        try {
            App.setRoot(name);
        } catch (IOException ex) {
            System.out.println("Error while switching to " + name);
            System.err.println(ex);
        }
    }
    
    
    @FXML
    private void getNotifyMenu(boolean success) {
        notifyStage = new Stage();
        notifyStage.setAlwaysOnTop(true);
        notifyStage.setResizable(false);
        notifyStage.setTitle("Notify");
        
        try {
            if(success) {
                notifyScene = new Scene(App.loadFXML("notify_success_menu"));
            } else {
                notifyScene = new Scene(App.loadFXML("notify_fail_menu"));
            }
        } catch (IOException ex) {
            System.out.println("Error while switching to notify menu");
            ex.printStackTrace();
        } 
        
        notifyStage.setScene(notifyScene);
        notifyStage.show();
    }
    @FXML
    private void closeNotifyMenu() {
        notifyStage.hide();
    }
}