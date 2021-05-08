/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ou_software_testing.ou_software_testing;

import com.ou_software_testing.ou_software_testing.pojo.ListProduct;

/**
 *
 * @author Admin
 */
public class DataTemporary {
    private static ListProduct listProductSelection;
    
    private DataTemporary(){}

    /**
     * @return the listProductSelection
     */
    public static ListProduct getListProductSelection() {
        if (listProductSelection == null){
            listProductSelection = new ListProduct();
        }
        return listProductSelection;
    }

    /**
     * @param aListProductSelection the listProductSelection to set
     */
    public static void setListProductSelection(ListProduct aListProductSelection) {
        listProductSelection = aListProductSelection;
    }
    
    public static void clearListProductSelection() {
       if(listProductSelection != null) {
           listProductSelection = null;
       }
    }
}
