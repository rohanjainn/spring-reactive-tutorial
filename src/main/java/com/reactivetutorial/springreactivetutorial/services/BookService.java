package com.reactivetutorial.springreactivetutorial.services;

import com.reactivetutorial.springreactivetutorial.model.Book;
import com.reactivetutorial.springreactivetutorial.model.Review;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
public class BookService {

    private BookInfoService bookInfoService;
    private ReviewService reviewService;

    public BookService(BookInfoService bookInfoService, ReviewService reviewService) {
        this.bookInfoService = bookInfoService;
        this.reviewService = reviewService;
    }

    public Flux<Book> getBooks(){
        var books=bookInfoService.getBooks();

        return books.flatMap(bookInfo -> {
            Mono<List<Review>> reviews = reviewService.getReviews(bookInfo.getBookId()).collectList();
            return reviews.map(review->new Book(bookInfo,review));
        }).log();
    }

    public Mono<Book> getBookById(long id){
        var book=bookInfoService.getBookById(id);
        var reviews=reviewService.getReviews(id).collectList();

        return book.zipWith(reviews,(b,r)->new Book(b,r));
    }
}
