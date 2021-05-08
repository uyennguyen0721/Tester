package com.ou_software_testing.ou_software_testing.services;

import com.ou_software_testing.ou_software_testing.pojo.ListProduct;
import com.ou_software_testing.ou_software_testing.pojo.Product;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ProductServices {
    private Connection conn;
    
    public ProductServices(Connection conn) {
        this.conn = conn;
    }
    
    public boolean editProductById(Product p){
        String sql = "UPDATE product " +
                    "SET name=?, category=?, origin=?, price=?, size=?, count=? " +
                    "WHERE id = ?;";
        PreparedStatement stm;
        try {
            stm = this.conn.prepareStatement(sql);
            
            stm.setString(1 , p.getName());
            stm.setString(3 , p.getOrigin());
            stm.setString(5 , p.getSize());
            
            stm.setInt(7, p.getId());
            stm.setInt(2, p.getCategory());
            stm.setInt(6, p.getCount());
        
            stm.setBigDecimal(4, p.getPrice());
            
            int kq = stm.executeUpdate();
            if (kq == 1) {
                return true;
            }    
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean deleleProductById(int productId) {
        String sql = "DELETE FROM product WHERE id = ?;";
        PreparedStatement stm;
        try {
            stm = this.conn.prepareStatement(sql);
            stm.setInt(1, productId);
        
            int kq = stm.executeUpdate();
            if (kq == 1) {
                return true;
            }    
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean insertProduct(Product product) throws SQLException{
        String sql = "INSERT INTO product (name, category, origin, price, size, count) VALUES (?,?,?,?,?,?);";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        
        stm.setString(1,product.getName());
        stm.setString(3,product.getOrigin());
        stm.setString(5,product.getSize());
        stm.setInt(2,product.getCategory());
        stm.setInt(6,product.getCount());
        stm.setBigDecimal(4, product.getPrice());
        
        int kq = stm.executeUpdate();
        
        if (kq == 1) {
            return true;
        }    
        conn.close();
        return false;
    }
    
    public Product getProductById (int productId) throws SQLException {
        String sql = "SELECT * FROM product WHERE id = ?";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setInt(1, productId);

        Product p = new Product();
        ResultSet rs = stm.executeQuery();
        if (!rs.isBeforeFirst() ) {    
            return null;
        } 
        if (rs.next()) {            
            p.setId(rs.getInt("id"));
            p.setName(rs.getString("name"));
            p.setOrigin(rs.getString("origin"));
            p.setSize(rs.getString("size"));
            p.setCount(rs.getInt("count"));
            p.setCategory(rs.getInt("category"));
            p.setPrice(rs.getBigDecimal("price"));
        }  
        conn.close();
        return p;
    }
    
    public ListProduct getProductByName(String productName) throws SQLException{
        ListProduct listProduct = new ListProduct();
        
        String sql = "SELECT * FROM product WHERE name like ?";
        
        PreparedStatement stm = this.conn.prepareStatement(sql);
        stm.setString(1, "%" + productName + "%");
        
        
        ResultSet rs = stm.executeQuery();
        if (!rs.isBeforeFirst() ) {    
            return null;
        } 
        while (rs.next()) {            
            Product p = new Product();
            
            p.setId(rs.getInt("id"));
            p.setName(rs.getString("name"));
            p.setOrigin(rs.getString("origin"));
            p.setSize(rs.getString("size"));
            p.setCount(rs.getInt("count"));
            p.setCategory(rs.getInt("category"));
            p.setPrice(rs.getBigDecimal("price"));
            
            listProduct.addProduct(p);
        }
        conn.close();
        return listProduct;
    }
    
    public ListProduct getAllProduct() throws SQLException{
        ListProduct listProduct = new ListProduct();
        
        String sql = "SELECT * FROM product";
        
        PreparedStatement stm = this.conn.prepareStatement(sql);
        
        ResultSet rs = stm.executeQuery();
        if (!rs.isBeforeFirst() ) {    
            return null;
        } 
        while (rs.next()) {            
            Product p = new Product();
            
            p.setId(rs.getInt("id"));
            p.setName(rs.getString("name"));
            p.setOrigin(rs.getString("origin"));
            p.setSize(rs.getString("size"));
            p.setCount(rs.getInt("count"));
            p.setCategory(rs.getInt("category"));
            p.setPrice(rs.getBigDecimal("price"));
            
            listProduct.addProduct(p);
        }
        conn.close();
        return listProduct;
    }
    
}
