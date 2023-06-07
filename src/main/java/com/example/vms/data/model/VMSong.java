package com.example.vms.data.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VMSong")
@Data
public class VMSong {
    @Id
    @GeneratedValue
    long idSong;

    String nameSong;

    String singer;

    String song;

    long Cate ;

    String commentUser;

}
