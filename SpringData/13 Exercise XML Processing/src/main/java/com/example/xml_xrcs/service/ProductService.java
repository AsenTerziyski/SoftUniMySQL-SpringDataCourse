package com.example.xml_xrcs.service;

import com.example.xml_xrcs.model.dto.ProductSeedDto;

import java.util.List;

public interface ProductService {
    long getProductEntitiesCount();

    void seedProducts(List<ProductSeedDto> products);
}
