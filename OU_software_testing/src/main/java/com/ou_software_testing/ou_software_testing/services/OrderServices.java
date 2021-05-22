/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ou_software_testing.ou_software_testing.services;

import com.ou_software_testing.ou_software_testing.pojo.Order;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class OrderServices {
    private Connection conn;
    
    public OrderServices(Connection conn) {
        this.conn = conn;
    }
    public void insertOrder(List<Order> list) {
        String SQL = "INSERT INTO saledb.order_detail "
                + "VALUES(?,?,now(), ?,?,?)";
        try (
            PreparedStatement stm = conn.prepareStatement(SQL);) {
            int count = 0;
            for (Order order : list) {
                stm.setInt(1, order.getProduct_id());
                stm.setInt(2, order.getUser_id());
                stm.setInt(3, order.getPayment_method());
                stm.setBigDecimal(4, order.getPrice());
                stm.setInt(5, order.getCount());

                stm.addBatch();
                count++;
                // execute every 100 rows or less
                if (count % 100 == 0 || count == list.size()) {
                    stm.executeBatch();
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public boolean addOrder(int product_id, int user_id, int payment_method, BigDecimal price, int count) {
        try {
            String sql = "insert into saledb.order_detail values (?,?,now(), ?,?,?)";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, product_id);
            stm.setInt(2, user_id);
            stm.setInt(3, payment_method);
            stm.setBigDecimal(4, price);
            stm.setInt(5, count);
            
            int kq = stm.executeUpdate();
            if(kq == 1) return true;
           
        } catch (SQLException ex) {
            Logger.getLogger(OrderServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    
    public boolean addOrder(Order order) {
        try {
            String sql = "insert into saledb.order_detail values (?,?,now(), ?,?,?)";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, order.getProduct_id());
            stm.setInt(2, order.getUser_id());
            stm.setInt(3, order.getPayment_method());
            stm.setBigDecimal(4, order.getPrice());
            stm.setInt(5, order.getCount());
            
            int kq = stm.executeUpdate();
            if(kq == 1) return true;
           
        } catch (SQLException ex) {
            Logger.getLogger(OrderServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public List<Order> getListOrders(String month, String year) {
        List<Order> listOrders = new ArrayList<>();
        
        if(Integer.parseInt(month) > 12 || Integer.parseInt(month) < 1) return null;
        if(Integer.parseInt(year) < 1 ) return null;

        try {
            String sql = "SELECT * FROM order_detail where year(day_time) = ? and month(day_time) = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, year);
            stm.setString(2, month);
            
            ResultSet rs = stm.executeQuery();
                while(rs.next()){
                    Order o = new Order();
                    o.setUser_id(rs.getInt("user_id"));
                    o.setProduct_id(rs.getInt("product_id"));
                    o.setDate_time(rs.getDate("day_time"));
                    o.setPayment_method(rs.getInt("payment_method"));
                    o.setPrice(rs.getBigDecimal("price"));
                    o.setCount(rs.getInt("count"));
                    listOrders.add(o);
            }
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(OrderServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listOrders;
    }
    
    public List<Order> getListOrders(String year) {
        List<Order> listOrders = new ArrayList<>();
        if(Integer.parseInt(year) < 1 ) return null;
        
        try {
            String sql = "SELECT * FROM order_detail where year(day_time) = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, year);
            
            ResultSet rs = stm.executeQuery();
            while(rs.next()) {
                Order o = new Order();
                o.setUser_id(rs.getInt("user_id"));
                o.setProduct_id(rs.getInt("product_id"));
                o.setDate_time(rs.getDate("day_time"));
                o.setPayment_method(rs.getInt("payment_method"));
                o.setPrice(rs.getBigDecimal("price"));
                o.setCount(rs.getInt("count"));
                listOrders.add(o);
            }
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(OrderServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listOrders;
    }

}
