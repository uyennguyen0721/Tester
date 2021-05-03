package com.ou_software_testing.ou_software_testing.controller;

import com.ou_software_testing.ou_software_testing.App;
import com.ou_software_testing.ou_software_testing.GlobalContext;
import com.ou_software_testing.ou_software_testing.Utils;
import com.ou_software_testing.ou_software_testing.pojo.User;
import com.ou_software_testing.ou_software_testing.services.JdbcServices;
import com.ou_software_testing.ou_software_testing.services.UserServices;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController extends Controller{

    @FXML private PasswordField txt_password;
    @FXML private TextField txt_phone_email, txt_user_name, txt_phone, txt_location, txt_email;
    @FXML private Button btn_login;
    @FXML private ComboBox<String> cb_sex;
    
    private static final Pattern EMAIL_PATTERN = 
            Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern PHONE_PATTERN = 
            Pattern.compile("(?:[0-9] ?){9,13}[0-9]", Pattern.CASE_INSENSITIVE);
    private User user;
    private String info, pw;


    @Override
    public void initialize(URL url, ResourceBundle rb) {}
     
        
        
    @FXML
    private void loadgenderList() {
        List<String> sexList = new ArrayList<>();
        sexList.add("male");
        sexList.add("female");
        cb_sex.getItems().clear();
        ObservableList obList = FXCollections.observableList(sexList);
        cb_sex.setItems(obList);
    }
    
    @FXML 
    private void switchToRegister(ActionEvent actionEvent) throws IOException {
        App.setRoot("register");
    }
    @FXML 
    private void switchToLogin(ActionEvent actionEvent) throws IOException {
        App.setRoot("login");
    }
    
    @FXML
    private void getLoginInfo(ActionEvent actionEvent) {
        if(txt_password.getText().length() != 0 && txt_phone_email.getText().length() != 0) {       
              pw = txt_password.getText();
              info = txt_phone_email.getText();
              try {
                    Connection conn = JdbcServices.getConnection();
                    UserServices userServices = new UserServices(conn);
                    
                    user = userServices.getUserInfo(info, pw);
                    if(user != null) {
                        GlobalContext.setUser(user);
                        this.switchToMain(actionEvent);
                    } else {
                        Alert a = Utils.makeAlert(Alert.AlertType.ERROR, 
                                "Sai thông tin", "Dialog", 
                                "Sai mật khẩu hoặc tài khoản người dùng");
                        a.show();
                    }
                } catch (IOException | SQLException ex) {
                    System.err.println(ex);
                }
        }
    }

    @FXML 
    private void registerLoginInfo(ActionEvent actionEvent) {  
        String name = txt_user_name.getText();
        String password = txt_password.getText();
        String email =  txt_email.getText();
        String location = txt_location.getText();
        String phone = txt_phone.getText();
        String sex = cb_sex.getValue();
        
        if(Utils.checkInfor(password, phone, email, name, location) 
                && Utils.checkValidRegex(PHONE_PATTERN, phone) 
                && Utils.checkValidRegex(EMAIL_PATTERN, email)
                && sex != null) {
            try {
                Connection conn = JdbcServices.getConnection();
                UserServices userServices = new UserServices(conn);

                boolean kq = userServices.addUserInfo(name,sex,location,phone,email,password);
                if(kq) {
                    user = userServices.getUserInfo(phone, password);
                    GlobalContext.setUser(user);
                    switchToMain(actionEvent);

                    Alert a = Utils.makeAlert(Alert.AlertType.INFORMATION, "Đăng ký thành công"
                            , "Success", "Thành công");
                    a.show();
                }
            } catch (IOException | SQLException ex) {
                System.err.println(ex);
            }
        } else if(!Utils.checkValidRegex(PHONE_PATTERN, phone)) {
            Alert a = Utils.makeAlert(Alert.AlertType.ERROR, "Sai thông tin", "Dialog", 
                    "Sai thông tin số điện thoại(10-13 số), vui lòng nhập lại");
            a.show();
        } else if(!Utils.checkValidRegex(EMAIL_PATTERN, email)) {
            Alert a = Utils.makeAlert(Alert.AlertType.ERROR, "Sai thông tin", "Dialog", 
                    "Sai thông tin email, vui lòng nhập lại");
            a.show();
        } else  {
            Alert a = Utils.makeAlert(Alert.AlertType.ERROR, "Sai thông tin", "Dialog"
                    , "Sai thông tin người dùng, vui lòng nhập đầy đủ");
            a.show();
        }
    }
}
