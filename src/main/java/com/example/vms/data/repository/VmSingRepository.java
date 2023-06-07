package com.example.vms.data.repository;

import com.example.vms.data.model.VMSong;
import com.example.vms.data.model.VMcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VmSingRepository extends JpaRepository<VMSong, Long> {

    @Query("select s from VMSong s where concat('',s.nameSong) like concat('%', :searchSong, '%')")
    Iterable<VMSong> searchSong(@Param("searchSong") String searchKey);
}
