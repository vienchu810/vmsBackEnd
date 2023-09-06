package com.example.vms.data.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "cart_items")
@Data
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduct;
    private String nameProduct;
    private int price;
    private int sl;

    private String imgProduct;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User userId;

    @Override
    public String toString() {
        return "CartItem{" +
                "idProduct=" + idProduct +
                ", nameProduct='" + nameProduct + '\'' +
                ", price=" + price +
                ", sl=" + sl +
                ", imgProduct='" + imgProduct + '\'' +
                // Không in đối tượng User ở đây
                '}';
    }
}
