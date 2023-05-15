package JAITUNGMA.review.dto;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
public class ReviewRequest {

    private UUID productId;

    @NotBlank
    private String username;

    @NotBlank
    private String reviewText;
}
