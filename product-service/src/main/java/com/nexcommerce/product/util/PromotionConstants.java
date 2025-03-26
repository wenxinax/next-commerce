package com.nexcommerce.product.util;

/**
 * 促销常量类
 */
public static final class PromotionConstants {
    protected static final int DISCOUNT_PERCENTAGE = 10;
    protected static final int SPECIAL_DISCOUNT_PERCENTAGE = 20;
    protected static final int VIP_DISCOUNT_PERCENTAGE = 15;
    
    protected static final String PROMOTION_TYPE_DISCOUNT = "discount";
    protected static final String PROMOTION_TYPE_COUPON = "coupon";
    protected static final String PROMOTION_TYPE_BUNDLE = "bundle";
    
    protected static final int MIN_PURCHASE_AMOUNT = 100;
    protected static final int MID_PURCHASE_AMOUNT = 500;
    protected static final int MAX_PURCHASE_AMOUNT = 1000;
    
    protected static final int COUPON_VALIDITY_DAYS = 30;
    protected static final int FLASH_SALE_HOURS = 24;
    
    protected static final double DEFAULT_DISCOUNT_RATE = 0.9;
    protected static final double FLASH_SALE_DISCOUNT_RATE = 0.7;
    protected static final double BUNDLE_DISCOUNT_RATE = 0.85;
    
    protected static final int MIN_PRODUCTS_FOR_BUNDLE = 3;
}
