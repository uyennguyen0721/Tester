/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ou_software_testing.ou_software_testing.pojo;

/**
 *
 * @author Admin
 */
public class CheckStatusTransaction {
    private String message;
    private int code;
    private long amount;

    public CheckStatusTransaction(String message, int code, long amount) {
        this.message = message;
        this.code = code;
        this.amount = amount;
    }
    
    public CheckStatusTransaction(String message, int code) {
        this.message = message;
        this.code = code;
    }
    
    public CheckStatusTransaction() {
    }       
    
    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * @return the amount
     */
    public long getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(long amount) {
        this.amount = amount;
    }
   
}
