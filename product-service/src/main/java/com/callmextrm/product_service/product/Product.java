package com.callmextrm.product_service.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "inv_product")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String name;
    @Positive
    private BigDecimal price;
    @Enumerated
    private Status status;

    @Override
    public String toString() {
        return
                "Product : " + name +

                        ", price=" + price + " DH" +
                        ", status=" + status + ".";
    }
}
