package com.reactivetutorial.springreactivetutorial.services;

import com.reactivetutorial.springreactivetutorial.exception.BookException;
import com.reactivetutorial.springreactivetutorial.model.Book;
import com.reactivetutorial.springreactivetutorial.model.Review;
import lombok.extern.slf4j.Slf4j;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
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
        }).onErrorMap(throwable -> {
            log.error("Exception - "+throwable);
            return new BookException("Exception occured while fetching books");
        })
        .log();
    }

    /**
     * retry() -> retry happens indefinite times
     * retry(3) -> retry 3 times
     * @return
     */
    public Flux<Book> getBooksRetry(){
        var books=bookInfoService.getBooks();

        return books.flatMap(bookInfo -> {
                    Mono<List<Review>> reviews = reviewService.getReviews(bookInfo.getBookId()).collectList();
                    return reviews.map(review->new Book(bookInfo,review));
                }).onErrorMap(throwable -> {
                    log.error("Exception - "+throwable);
                    return new BookException("Exception occured while fetching books");
                })
                .retry(3)
                .log();
    }

    /**
     * retryWhen() - retry for specific scenario
     * onRetryExhaustedThrow - propagateException
     * @return
     */
    public Flux<Book> getBooksRetryWhen(){

        var retrySpecs= Retry.backoff(
                3,
                Duration.ofMillis(1000)
        ).filter(throwable -> throwable instanceof BookException)
        .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) ->
            Exceptions.propagate(retrySignal.failure())
        );

        //
        var books=bookInfoService.getBooks();

        return books.flatMap(bookInfo -> {
                    Mono<List<Review>> reviews = reviewService.getReviews(bookInfo.getBookId()).collectList();
                    return reviews.map(review->new Book(bookInfo,review));
                }).onErrorMap(throwable -> {
                    log.error("Exception - "+throwable);
                    return new BookException("Exception occured while fetching books");
                })
                .retryWhen(retrySpecs)
                .log();
    }

    public Mono<Book> getBookById(long id){
        var book=bookInfoService.getBookById(id);
        var reviews=reviewService.getReviews(id).collectList();

        return book.zipWith(reviews,(b,r)->new Book(b,r));
    }
}
