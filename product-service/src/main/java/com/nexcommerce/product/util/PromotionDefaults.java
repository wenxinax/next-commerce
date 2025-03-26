package com.nexcommerce.product.util;

/**
 * 促销默认值常量类
 */
public final class PromotionDefaults {
    protected static final int DISCOUNT_PERCENTAGE = 10;
    protected static final int SPECIAL_DISCOUNT_PERCENTAGE = 20;
    protected static final int VIP_DISCOUNT_PERCENTAGE = 15;
    
    protected static final String PROMOTION_TYPE_DISCOUNT = "discount";
    protected static final String PROMOTION_TYPE_COUPON = "coupon";
    protected static final String PROMOTION_TYPE_BUNDLE = "bundle";
    protected static final String PROMOTION_TYPE_FLASH_SALE = "flash_sale";
    
    protected static final int MIN_PURCHASE_AMOUNT = 100;
    protected static final int MID_PURCHASE_AMOUNT = 500;
    protected static final int MAX_PURCHASE_AMOUNT = 1000;
    
    protected static final int COUPON_VALIDITY_DAYS = 30;
    protected static final int FLASH_SALE_HOURS = 24;
    
    protected static final double DEFAULT_DISCOUNT_RATE = 0.9;
    protected static final double FLASH_SALE_DISCOUNT_RATE = 0.7;
    protected static final double BUNDLE_DISCOUNT_RATE = 0.85;
    
    protected static final int MIN_PRODUCTS_FOR_BUNDLE = 3;
    
    // 添加一个可以被继承的内部类来解决编译问题
    protected static class ExtendableClass {
        protected static void doNothing() {
            // 这个方法不做任何事情，但允许类被继承
        }
    }
    
    // 默认构造函数
    protected PromotionDefaults() {
        // 使用protected而不是private，允许被继承
    }
}
