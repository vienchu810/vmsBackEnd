package com.example.vms.controller;


import com.example.vms.data.model.Product;
import com.example.vms.data.repository.ProductRepository;
import com.example.vms.entity.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class ProductController {
    @Autowired
    private  final ProductRepository productRepository;

    @GetMapping("/getProducts")
    public ResponseEntity findAll(){
        return
                Response.data(productRepository.findAll(Sort.by("id")));
    }
    @GetMapping("/getProductByName/{nameProduct}")
    public ResponseEntity<Product> getProductByName(@PathVariable String nameProduct) {
        String decodedName = UriUtils.decode(nameProduct, "UTF-8");
        // Thay thế dấu "-" bằng dấu cách
        String finalName = decodedName.replace("-", " ");
        System.out.println("decodedProductName" + finalName);
        Product product = productRepository.findByNameProduct(finalName);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/search")
    public ResponseEntity searchProduct(@RequestParam String searchKey) {
        return Response.data(productRepository.searchProduct(searchKey));
    }

    @PostMapping("/postProducts")
    public ResponseEntity creatProdcut(@RequestBody String product) {
        JSONObject requestParsed = new JSONObject(product);
        Optional<Product> productOpt = productRepository.findById(requestParsed.optLong("id", 0));
        Product product1;
        product1 = productOpt.get();
        product1 = productRepository.save(product1);

        return Response.data(product1);
    }
}
