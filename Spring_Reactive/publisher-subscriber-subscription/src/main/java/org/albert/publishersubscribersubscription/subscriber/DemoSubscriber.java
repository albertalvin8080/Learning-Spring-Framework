package org.albert.publishersubscribersubscription.subscriber;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class DemoSubscriber implements Subscriber<Integer> {

    private Subscription subscription;
    private int counter = 0;

    @Override
    public void onSubscribe(Subscription subscription) {
        System.out.println("onSubscribe");
        this.subscription = subscription;
        subscription.request(2);
    }

    @Override
    public void onNext(Integer integer) {
        System.out.println("onNext: " + integer);
//        subscription.request(1);

        /*
        *   used so every onNext() call doesnt call other subscription.request(2),
        *   which breaks the purpose of the backpressure.
        */
        counter++;
        if(counter % 2 == 0)
            subscription.request(2);
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("onError: " + throwable);
    }

    @Override
    public void onComplete() {
        System.out.println("onComplete");
    }
}
