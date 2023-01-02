package com.reactivetutorial.springreactivetutorial.services;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

public class BackPressureDemoTest {

    @Test
    public void testBackPressure(){
        var nums= Flux.range(1,100).log();
      /*  nums.subscribe(number->{
            System.out.println("no="+number);
        });*/

        nums.subscribe(new BaseSubscriber<Integer>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                request(5);
            }

            @Override
            protected void hookOnNext(Integer value) {
                System.out.println("value="+value);
                if(value==5) cancel();//after getting 5 records stream will be cancelled
            }

            @Override
            protected void hookOnComplete() {
                System.out.println("completed");
            }

            @Override
            protected void hookOnError(Throwable throwable) {
                super.hookOnError(throwable);
            }
        });
    }

    @Test
    public void testBackPressureDrop(){
        var nums= Flux.range(1,100).log();
      /*  nums.subscribe(number->{
            System.out.println("no="+number);
        });*/

        nums.onBackpressureDrop(n-> System.out.println("Dropped values="+n))
                .subscribe(new BaseSubscriber<Integer>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                request(5);
            }

            @Override
            protected void hookOnNext(Integer value) {
                System.out.println("value="+value);
                if(value==5) hookOnCancel();//after getting 5 records stream will be cancelled
            }

            @Override
            protected void hookOnComplete() {
                System.out.println("completed");
            }

            @Override
            protected void hookOnError(Throwable throwable) {
                super.hookOnError(throwable);
            }
        });
    }

    @Test
    public void testBackPressureBuffer(){
        var nums= Flux.range(1,100).log();
      /*  nums.subscribe(number->{
            System.out.println("no="+number);
        });*/

        nums.onBackpressureBuffer(10,n-> System.out.println("Buffered values="+n))
                .subscribe(new BaseSubscriber<Integer>() {
                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        request(5);
                    }

                    @Override
                    protected void hookOnNext(Integer value) {
                        System.out.println("value="+value);
                        if(value==5) hookOnCancel();//after getting 5 records stream will be cancelled
                    }

                    @Override
                    protected void hookOnComplete() {
                        System.out.println("completed");
                    }

                    @Override
                    protected void hookOnError(Throwable throwable) {
                        super.hookOnError(throwable);
                    }
                });
    }

    @Test
    public void testBackPressureError(){
        var nums= Flux.range(1,100).log();
      /*  nums.subscribe(number->{
            System.out.println("no="+number);
        });*/

        nums.onBackpressureError()
                .subscribe(new BaseSubscriber<Integer>() {
                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        request(5);
                    }

                    @Override
                    protected void hookOnNext(Integer value) {
                        System.out.println("value="+value);
                        if(value==5) hookOnCancel();//after getting 5 records stream will be cancelled
                    }

                    @Override
                    protected void hookOnComplete() {
                        System.out.println("completed");
                    }

                    @Override
                    protected void hookOnError(Throwable throwable) {
                        System.out.println("throwable="+throwable);
                    }
                });
    }
}
