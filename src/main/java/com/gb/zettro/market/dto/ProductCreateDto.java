package com.gb.zettro.market.dto;

import com.gb.zettro.market.entities.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductCreateDto {
    private String title;
    private int price;
    private Long categoryId;

}
