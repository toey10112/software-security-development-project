package JAITUNGMA.product.controller;

import JAITUNGMA.product.dto.ReviewRequest;
import JAITUNGMA.product.service.JwtAccessTokenService;
import JAITUNGMA.product.service.ProductService;
import JAITUNGMA.product.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.UUID;


@Controller
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private JwtAccessTokenService jwtService;

    @GetMapping("/{productId}")
    public String getReviewPage(@PathVariable UUID productId,
                                Model model) {

        model.addAttribute("product",
                productService.getProductById(productId));
        model.addAttribute("reviews",
                reviewService.getProductReview(productId));

        return "review";
    }

    @GetMapping("/add/{productId}")
    public String getReviewForm(@PathVariable UUID productId,
                                Model model, ReviewRequest reviewRequest) {
        model.addAttribute("productId", productId);
        return "review-add";
    }

    @PostMapping("/add")
    public String createReview(@Valid ReviewRequest review,BindingResult result,
                               Model model, Principal principal) {
        String username = principal.getName();
        review.setUsername(username);

        if (result.hasErrors()) {
            model.addAttribute("productId", review.getProductId());
            return "review-add";
        }

        reviewService.createReview(review);
        return "redirect:/review/" + review.getProductId();
    }
}

