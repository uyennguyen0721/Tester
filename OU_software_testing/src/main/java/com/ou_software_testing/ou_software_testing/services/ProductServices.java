package com.ou_software_testing.ou_software_testing.services;

import com.ou_software_testing.ou_software_testing.Rule;
import com.ou_software_testing.ou_software_testing.pojo.ListProduct;
import com.ou_software_testing.ou_software_testing.pojo.Product;
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
        
        if(p.getCount() > 200 || p.getCount() <3) return false;
        
        if(checkUniqueName(p) == false) {
            System.out.println(p.getName());
        }
        
        System.out.println(conn);
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
            System.out.println(kq);
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
        if(productId <= 0)
            return false;
        String sql = "DELETE FROM product WHERE id = ?";
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
    
    public boolean deleleProductByName(String name) {
        if(name.length() <= 0)
            return false;
        String sql = "DELETE FROM product WHERE name = ?";
        PreparedStatement stm;
        try {
            stm = this.conn.prepareStatement(sql);
            stm.setString(1, name);
        
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
        if(product.getCount() > 200 || product.getCount() <3) return false;
        if(Rule.isIS_SAME_PRODUCT_NAME() && checkUniqueName(product) == false) return false;
        
        String sql = "INSERT INTO product (name, category, origin, price, size, count) "
                + "VALUES (?,?,?,?,?,?);";
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
    
    public Product getProductById (int productId, boolean disconnect) throws SQLException {
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
        if(disconnect)
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
    
    public ListProduct getAllProduct(boolean closeConnection) throws SQLException{
        ListProduct listProduct = new ListProduct();
        
        String sql = "SELECT * FROM product";
        
        PreparedStatement stm = this.conn.prepareStatement(sql);
        
        System.out.println(conn);
        
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
        if(closeConnection) {
            conn.close();
        }
        return listProduct;
    }
    
    public ListProduct getAllProduct() throws SQLException{
        ListProduct listProduct = new ListProduct();
        
        String sql = "SELECT * FROM product";
        
        PreparedStatement stm = this.conn.prepareStatement(sql);
        
        System.out.println(conn);
        
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
    
    public boolean checkUniqueName(Product p) {
        Boolean flag = true;
        try {
            ListProduct listProduct = getAllProduct(false);
            System.out.println(listProduct.getListProduct().get(0));
            for(Product pro: listProduct.getListProduct()) {
                if((p.getName()).equals(pro.getName())) {
                    flag = false;
                    break;
                }
                    
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return flag;
    }

    public boolean reduceProductCount(int productId, int count) {
        try {
            String sql = "update product set count = ? where id = ?";
            
            Product p_temp = getProductById(productId, false);
            if((p_temp.getCount() - count) <= 3)
                return false;
            
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(2, productId);
            stm.setInt(1, (p_temp.getCount() - count));
            
            int kq = stm.executeUpdate();
            return kq>0;
            
        } catch (SQLException ex) {
            Logger.getLogger(ProductServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
