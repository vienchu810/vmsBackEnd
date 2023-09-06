package com.example.vms.controller;

import com.example.vms.data.model.CategoryProduct;
import com.example.vms.data.repository.CategoryProductRepository;
import com.example.vms.data.repository.VMCategoryRepository;
import com.example.vms.entity.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class CategoryProductController {
    private final CategoryProductRepository categoryProductRepository;

    @GetMapping("/getCateProduct")
    public ResponseEntity findAll() {
        return Response.data(categoryProductRepository.findAll(Sort.by("id")));
    }


    @PostMapping("/addCateProduct")
    public ResponseEntity addCate(@RequestBody List<CategoryProduct> categoryRequests) {
        for (CategoryProduct categoryRequest : categoryRequests) {
            Long id = categoryRequest.getId();
            String nameCategory = categoryRequest.getNameCateProduct();
            String linkBackGround = categoryRequest.getLinkImgCateProduct();

            CategoryProduct vMcategory = new CategoryProduct();
            vMcategory.setId(id);
            vMcategory.setNameCateProduct(nameCategory);
            vMcategory.setLinkImgCateProduct(linkBackGround);

            vMcategory = categoryProductRepository.save(vMcategory);
        }
        return ResponseEntity.ok().build();
    }
}
