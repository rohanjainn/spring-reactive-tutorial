package com.reactivetutorial.springreactivetutorial.services;

import com.reactivetutorial.springreactivetutorial.model.Review;
import reactor.core.publisher.Flux;

import java.util.List;

public class ReviewService {

    public Flux<Review> getReviews(long bookId){
        var reviewList= List.of(
                new Review(1,bookId,8.9,"Good"),
                new Review(2,bookId,4,"waste of time")
        );
        return Flux.fromIterable(reviewList);
    }
}
