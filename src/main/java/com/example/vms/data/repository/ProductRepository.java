package com.example.vms.data.repository;



import com.example.vms.data.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select s from Product s where concat('',s.Detail) like concat('%', :searchKey, '%')")
    Iterable<Product> searchProduct(@Param("searchKey") String searchKey);
}
