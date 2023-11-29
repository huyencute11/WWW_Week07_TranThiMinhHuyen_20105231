package iuh.vn.week07.Models;

import java.sql.Date;

import iuh.vn.week07.ids.ProductPriceId;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_price")
@NoArgsConstructor
@AllArgsConstructor
@Data
@IdClass(ProductPriceId.class)
public class ProductPrice{
    @Id
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product;

    @Id
    @Column(name = "price_date_time")
    private Date priceDateTime;

    @Column(name = "price")
    private double price;

    @Column(name = "note")
    private String note;
}
