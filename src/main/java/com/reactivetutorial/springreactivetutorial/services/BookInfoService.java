package com.reactivetutorial.springreactivetutorial.services;

import com.reactivetutorial.springreactivetutorial.model.BookInfo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class BookInfoService {

    public Flux<BookInfo> getBooks(){
        var books= List.of(
                new BookInfo(1,"book1","Author1","123"),
                new BookInfo(2,"book2","Author2","456"),
                new BookInfo(3,"book3","Author3","789")
        );

        return Flux.fromIterable(books);
    }

    public Mono<BookInfo> getBookById(long bookId){
        var book=new BookInfo(bookId,"book"+bookId,"author4","53246");
        return Mono.just(book);
    }
}
