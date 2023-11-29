package iuh.vn.week07.ids;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()
@Data
public class OrderDetailId implements Serializable {
    private Long order;
    private Long product;
}
