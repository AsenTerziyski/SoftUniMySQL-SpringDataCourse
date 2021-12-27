package com.example.json_exrcs.repository;

import com.example.json_exrcs.model.entity.Category;
import com.example.json_exrcs.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
//    List<Product> findAllByPriceBetweenAndBuyerIsNullOrderByPriceDesc(BigDecimal lowerPrice, BigDecimal upperPrice);
//    List<Product> findAllByCategories(List<Category> categories);

    List<Product> findAllByPriceBetweenAndBuyerIsNullOrderByPriceDesc(BigDecimal low, BigDecimal high);
    List<Product> findAllByBuyerIsNotNullAndSellerIsNotNullOrderByPriceDesc();
}
