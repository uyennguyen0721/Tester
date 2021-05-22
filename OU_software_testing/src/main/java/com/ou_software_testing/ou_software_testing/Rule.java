/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ou_software_testing.ou_software_testing;

/**
 *
 * @author Admin
 */
public class Rule {
    private static final int MAX_PRODUCT = 200;
    private static final int MIN_PRODUCT = 3;
    private static final boolean IS_SAME_PRODUCT_NAME = true;
    private static final int MAX_PRODUCT_ORDER = 5;

    /**
     * @return the MAX_PRODUCT
     */
    public static int getMAX_PRODUCT() {
        return MAX_PRODUCT;
    }

    /**
     * @return the MIN_PRODUCT
     */
    public static int getMIN_PRODUCT() {
        return MIN_PRODUCT;
    }

    /**
     * @return the IS_SAME_PRODUCT_NAME
     */
    public static boolean isIS_SAME_PRODUCT_NAME() {
        return IS_SAME_PRODUCT_NAME;
    }

    /**
     * @return the MAX_PRODUCT_ORDER
     */
    public static int getMAX_PRODUCT_ORDER() {
        return MAX_PRODUCT_ORDER;
    }
}
