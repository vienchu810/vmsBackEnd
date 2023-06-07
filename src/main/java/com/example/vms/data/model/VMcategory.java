package com.example.vms.data.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VMcategory")
@Data
public class VMcategory {
    @Id
    @GeneratedValue
    long id;

    String nameCategory;

    String linkBackGround;

}
