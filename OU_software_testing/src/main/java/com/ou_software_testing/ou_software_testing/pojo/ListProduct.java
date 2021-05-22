/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ou_software_testing.ou_software_testing.pojo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ListProduct {
    private List<Product> listProduct;
    
    public ListProduct(List<Product> listProduct){
        this.listProduct = listProduct;
    }
    public ListProduct(){
        this.listProduct = new ArrayList<>();
    }
    
    public BigDecimal getTotalPrice(){
        BigDecimal totalPrice = new BigDecimal(0);
        
        for (Product p : this.getListProduct()) {
            totalPrice = totalPrice.add(p.getPrice().multiply(new BigDecimal(p.getCount())));
        };
        
        return totalPrice;
    }
    public boolean addProduct(Product p){
        if (listProduct == null) listProduct = new ArrayList<>();
        boolean rs = this.listProduct.add(p);
        return rs;
    }
    
    public void concatList(ListProduct list){
        this.listProduct.addAll(list.getListProduct());
    }
    
    public void removeProductById(int id){
        int index = 0;
        
        for (Product p: listProduct){
            if (p.getId() == id){
                this.listProduct.remove(index);
                break;
            }
            index++;
        }
    }
    public Product getProductById(int id){
        Product product = new Product();
        
        for (Product p: listProduct){
            if (p.getId() == id){
                product = p;
                break;
            }
        }
        return product;
    }
    
    public void setCount1(){
        for (Product p: listProduct){
            p.setCount(1);
        }
    }
    /**
     * @return the listProduct
     */
    public List<Product> getListProduct() {
        return listProduct;
    }

    /**
     * @param listProduct the listProduct to set
     */
    public void setListProduct(List<Product> listProduct) {
        this.listProduct = listProduct;
    }
}
