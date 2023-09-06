package com.example.vms.data.repository;

import com.example.vms.data.model.CategoryProduct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryProductRepository extends JpaRepository<CategoryProduct, Long> {

    @Query("select s from CategoryProduct s where concat('',s.nameCateProduct) like concat('%', :searchKey, '%')")
    Iterable<CategoryProduct> searchCate(@Param("searchKey") String searchKey);
}
