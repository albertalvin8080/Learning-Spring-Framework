package org.albert.publishersubscribersubscription.publisher;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;

public class DemoPublisher implements Publisher {

    private List<Integer> list;

    public DemoPublisher(List<Integer> list) {
        this.list = list;
    }

    @Override
    public void subscribe(Subscriber subscriber)
    {
        subscriber.onSubscribe(new Subscription() {
            private int currentPosition = 0;

            @Override
            public void request(long l) {
                if(currentPosition >= list.size()) {
                    subscriber.onComplete();
                    return;
                }

                while(currentPosition < list.size() && l > 0) {
                    subscriber.onNext(list.get(currentPosition++));
                    --l;
                }
            }

            @Override
            public void cancel() {

            }
        });
    }
}
