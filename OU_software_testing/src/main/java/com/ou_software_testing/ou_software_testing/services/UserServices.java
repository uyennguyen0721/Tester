/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ou_software_testing.ou_software_testing.services;

import com.ou_software_testing.ou_software_testing.pojo.User;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Admin
 */
public class UserServices {
    private Connection conn;
    
    public UserServices(Connection conn) {
        this.conn = conn;
    }
    
    public User getUserInfo (String info, String pw) throws SQLException {
        String sql = "SELECT * FROM user WHERE email = ? or phone = ? and password = ?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, info);
        stm.setString(2, info);
        stm.setString(3, pw);

        ResultSet rs = stm.executeQuery();
        if (!rs.isBeforeFirst() ) {    
            return null;
        } 
        
        User u = new User();
        if (rs.next()) {
            u.setId(rs.getInt("id"));
            u.setName(rs.getString("name"));
            u.setEmail(rs.getString("email"));
            u.setLocation(rs.getString("location"));
            u.setPhone(rs.getString("phone"));
            u.setSex(rs.getString("sex"));
            u.setRole(rs.getObject("role").toString());
        }  
        
        return u;
    }
    
    public boolean addUserInfo(String name, String sex, String location, 
            String phone, String email, String password)  {
        
        try {
            String sql = "Insert into saledb.user (name, sex, location, phone, email, password, role) "
                    + "values (?, ?, ?, ?, ?, ?, 'user');";
            PreparedStatement stm = this.conn.prepareStatement(sql);
            stm.setString(1, name);
            stm.setString(2, sex);
            stm.setString(3, location);
            stm.setString(4, phone);
            stm.setString(5, email);
            stm.setString(6, password);
            
            int rs = stm.executeUpdate();
            
            return rs > 0;
            
        } catch (SQLException ex) {
            Logger.getLogger(UserServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
