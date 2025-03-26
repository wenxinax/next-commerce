package com.nexcommerce.product.util;

/**
 * 促销常量类
 */
public final class PromotionConstants {
    public static final int DISCOUNT_PERCENTAGE = 10;
    public static final int SPECIAL_DISCOUNT_PERCENTAGE = 20;
    public static final int VIP_DISCOUNT_PERCENTAGE = 15;
    
    public static final String PROMOTION_TYPE_DISCOUNT = "discount";
    public static final String PROMOTION_TYPE_COUPON = "coupon";
    public static final String PROMOTION_TYPE_BUNDLE = "bundle";
    
    public static final int MIN_PURCHASE_AMOUNT = 100;
    public static final int MID_PURCHASE_AMOUNT = 500;
    public static final int MAX_PURCHASE_AMOUNT = 1000;
    
    public static final int COUPON_VALIDITY_DAYS = 30;
    public static final int FLASH_SALE_HOURS = 24;
    
    public static final double DEFAULT_DISCOUNT_RATE = 0.9;
    public static final double FLASH_SALE_DISCOUNT_RATE = 0.7;
    public static final double BUNDLE_DISCOUNT_RATE = 0.85;
    
    public static final int MIN_PRODUCTS_FOR_BUNDLE = 3;
    
    // 私有构造函数，防止实例化
    private PromotionConstants() {
    }
}
