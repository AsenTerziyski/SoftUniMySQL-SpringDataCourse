package com.example.json_exrcs.repository;

import com.example.json_exrcs.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(value = "select c.id, c.name, count(distinct product_id) as p_count, avg(p.price) as avg_price, sum(p.price) as totalrevenue  from categories as c\n" +
            "join products_categories pc on c.id = pc.categories_id\n" +
            "join products p on p.id = pc.product_id\n" +
            "group by c.id\n" +
            "order by p_count;", nativeQuery = true )
    List<Category> problem03();

}
