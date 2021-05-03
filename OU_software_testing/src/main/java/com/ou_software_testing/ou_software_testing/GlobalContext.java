/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ou_software_testing.ou_software_testing;

import com.ou_software_testing.ou_software_testing.pojo.User;

/**
 *
 * @author Admin
 */
//  DESIGN Parent Singleton 
public class GlobalContext {
    private static User user;
    
    private GlobalContext(){}  // Private constructor to prevent instantiation
    
    /**
     * @return the user
     */
    public static User getUser() {
        return user;
    }

    /**
     * @param aUser the user to set
     */
    public static void setUser(User aUser) {
        user = aUser;
    }
}
