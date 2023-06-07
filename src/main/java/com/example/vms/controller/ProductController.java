package com.example.vms.controller;


import com.example.vms.data.model.Product;
import com.example.vms.data.repository.ProductRepository;
import com.example.vms.entity.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/product")
@Slf4j
@RequiredArgsConstructor
public class ProductController {
    private  final ProductRepository productRepository;

    @GetMapping
    public ResponseEntity findAll(){
        return Response.data(productRepository.findAll(Sort.by("createAt")));
    }

    @GetMapping("/search")
    public ResponseEntity searchProduct(@RequestParam String searchKey) {
        return Response.data(productRepository.searchProduct(searchKey));
    }

    @PostMapping("/createProduct")
    public ResponseEntity creatProdcut(@RequestBody String product) {
        JSONObject requestParsed = new JSONObject(product);
        Optional<Product> productOpt = productRepository.findById(requestParsed.optLong("id", 0));
        Product product1;
        product1 = productOpt.get();
        product1 = productRepository.save(product1);

        return Response.data(product1);
    }
}
