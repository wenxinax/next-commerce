package com.nexcommerce.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * 地址数据传输对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressDto {

    private Long id;
    
    @NotBlank(message = "地址行1不能为空")
    private String addressLine1;
    
    private String addressLine2;
    
    @NotBlank(message = "城市不能为空")
    private String city;
    
    @NotBlank(message = "州/省不能为空")
    private String state;
    
    @NotBlank(message = "国家不能为空")
    private String country;
    
    @NotBlank(message = "邮政编码不能为空")
    private String postalCode;
    
    private Boolean isDefault;
    
    private Boolean isBilling;
    
    private Boolean isShipping;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
}
