package gs.teamup.bot.component;

import gs.teamup.bot.pojo.event.TeamupEventChat;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by thisno on 2016-04-12.
 */

@Component
public class EventQueue<T> {
    final Queue<T> queue = new ConcurrentLinkedQueue<T>();

    public void offer(T e) {
        if (e == null) {
            return;
        }
        queue.offer(e);
    }

    public T poll() {
        return queue.poll();
    }
}
