package JAITUNGMA.review.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
public class Review {

    @Id
    @GeneratedValue
    private UUID id;

    private UUID productId;
    private String username;
    private String reviewText;
    private Instant createdAt;
}
