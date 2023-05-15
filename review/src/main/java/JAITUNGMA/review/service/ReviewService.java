package JAITUNGMA.review.service;

import JAITUNGMA.review.dto.ReviewRequest;
import JAITUNGMA.review.dto.ReviewResponse;
import JAITUNGMA.review.model.Review;
import JAITUNGMA.review.repository.ReviewRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public List<Review> getAll() {
        return repository.findAll();
    }

    public List<ReviewResponse> getAllReviewsForProduct(UUID productId) {
        List<Review> reviews = repository.findByProductId(productId);

        List<ReviewResponse> dtos = reviews
                .stream()
                .map(review -> modelMapper.map(review, ReviewResponse.class))
                .collect(Collectors.toList());

        return dtos;
    }

    public void addReview(ReviewRequest reviewRequest) {
        Review review = modelMapper.map(reviewRequest, Review.class);
        review.setCreatedAt(Instant.now());
        repository.save(review);
    }
}
