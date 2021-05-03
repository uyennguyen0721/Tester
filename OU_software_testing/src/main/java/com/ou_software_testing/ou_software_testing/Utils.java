/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ou_software_testing.ou_software_testing;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.Alert;

/**
 *
 * @author Admin
 */
public class Utils {
    
    public static Alert makeAlert(Alert.AlertType type, String header, String title, String content) {
        Alert a = new Alert(type);
        a.setHeaderText(header);
        a.setTitle(title);
        a.setContentText(content);
        return a;
    };
    
    public static Alert makeAlert(Alert.AlertType type, String content) {
        Alert a = new Alert(type);
        a.setContentText(content);
        return a;
    };
    
    public static boolean checkInfor(String txt_password, String txt_phone
            , String txt_email, String txt_user_name, String txt_location) {
        return txt_password.length() != 0 
                && txt_phone.length() != 0
                && txt_email.length() != 0
                && txt_user_name.length() != 0
                && txt_location.length() != 0;
    };
    
    public static boolean checkValidRegex(Pattern pattern, String text) {
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }
}
