package com.example.vms.controller;

import com.example.vms.data.model.VMcategory;
import com.example.vms.data.repository.VMCategoryRepository;
import com.example.vms.entity.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class CategoryController {
    private final VMCategoryRepository vmCategoryRepository;

    @GetMapping("/getCate")
    public ResponseEntity findAll() {
        return Response.data(vmCategoryRepository.findAll(Sort.by("id")));
    }


    @PostMapping("/addCate")
    public ResponseEntity addCate(@RequestBody List<VMcategory> categoryRequests) {
        for (VMcategory categoryRequest : categoryRequests) {
            Long id = categoryRequest.getId();
            String nameCategory = categoryRequest.getNameCategory();
            String linkBackGround = categoryRequest.getLinkBackGround();

            VMcategory vMcategory = new VMcategory();
            vMcategory.setId(id);
            vMcategory.setNameCategory(nameCategory);
            vMcategory.setLinkBackGround(linkBackGround);

            vMcategory = vmCategoryRepository.save(vMcategory);
        }
        return ResponseEntity.ok().build();
    }
}
