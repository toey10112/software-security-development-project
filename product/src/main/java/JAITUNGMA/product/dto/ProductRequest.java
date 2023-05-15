package JAITUNGMA.product.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ProductRequest {
    @NotBlank
    private String productName;

    @NotBlank
    private String imgLink;

    @NotNull
    @Range(min=1)
    private Integer price;

    @NotBlank
    private String category;

    @NotNull
    @Range(min=1, max=999)
    private Integer stock;

    private String recaptcha;
}
