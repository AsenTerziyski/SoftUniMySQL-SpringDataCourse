package com.example.json_exrcs.service;

import com.example.json_exrcs.model.dto.ProductNameAndPriceDto;
import com.example.json_exrcs.model.dto.PR100.ProductsWithBuyerAndSeller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    void seedProducts() throws IOException;

    List<ProductNameAndPriceDto> findAllProductsInRangeAndOrderByPrice(BigDecimal low, BigDecimal upper);


    List<ProductsWithBuyerAndSeller> findAllProductsWithBuyerAndSellerAreNotNull();
}
