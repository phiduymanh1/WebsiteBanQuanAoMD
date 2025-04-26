package org.example.shopquanao.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Nationalized;

import java.util.LinkedHashSet;
import java.util.Set;


@Entity
@Table(name = "colors")
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Nationalized
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @OneToMany(mappedBy = "color",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductDetail> productDetails = new LinkedHashSet<>();

    public Color() {
    }

    public Color(Integer id, String name, Set<ProductDetail> productDetails) {
        this.id = id;
        this.name = name;
        this.productDetails = productDetails;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ProductDetail> getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(Set<ProductDetail> productDetails) {
        this.productDetails = productDetails;
    }

    @Override
    public String toString() {
        return "Color{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", productDetails=" + productDetails +
                '}';
    }
}