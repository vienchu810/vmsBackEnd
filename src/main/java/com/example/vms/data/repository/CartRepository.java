package com.example.vms.data.repository;



import com.example.vms.data.model.CartItem;
import com.example.vms.data.model.CategoryProduct;
import com.example.vms.data.model.Product;
import com.example.vms.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByUserId(String userId);

    @Query("SELECT c FROM CartItem c WHERE c.userId.id = :userId")
    List<CartItem> findCartItemsByUserId(@Param("userId") Long userId);
   /* @Query("select s from CartItem s where concat('',s.userId) like concat('%', :searchKey, '%')")
    Iterable<CartItem> searchCartItem(@Param("searchKey") String searchKey);*/
}


