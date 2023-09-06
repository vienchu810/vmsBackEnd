package com.example.vms.data.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CategoryProduct")
@Data
public class CategoryProduct {
    @Id
    @GeneratedValue
    long id;

    String nameCateProduct;

    String linkImgCateProduct;

}
