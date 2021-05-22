/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ou_software_testing.ou_software_testing.services;

import com.ou_software_testing.ou_software_testing.pojo.Category;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class CategoryServices {
    
    private Connection conn;
    
    public CategoryServices(Connection conn) {
        this.conn = conn;
    }
    
    public List<Category> getCategorys() {
        List<Category> cates = new ArrayList<>();
        try {
            conn = JdbcServices.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("Select * from category limit 50");
            
            
            while(rs.next()) {
                Category c = new Category();
                c.setId(rs.getInt("id"));
                c.setName(rs.getString("name"));
                c.setSex(rs.getString("sex"));
                cates.add(c);
            }
            conn.close();
        } catch (SQLException ex) {
            System.err.println("Error while getting category");
            Logger.getLogger(CategoryServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cates;
    }
}
