package iuh.vn.week07.ids;

import java.io.Serializable;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()
@Data
public class ProductPriceId implements Serializable {
    private Long product;
    private Date priceDateTime;
}
