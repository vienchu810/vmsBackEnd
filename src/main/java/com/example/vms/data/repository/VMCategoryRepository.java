package com.example.vms.data.repository;

import com.example.vms.data.model.VMcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VMCategoryRepository extends JpaRepository<VMcategory, Long> {

    @Query("select s from VMcategory s where concat('',s.nameCategory) like concat('%', :searchKey, '%')")
    Iterable<VMcategory> searchCate(@Param("searchKey") String searchKey);
}
