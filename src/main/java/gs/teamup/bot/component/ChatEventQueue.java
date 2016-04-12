package gs.teamup.bot.component;

import gs.teamup.bot.pojo.event.TeamupEventChat;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by thisno on 2016-04-12.
 */

@Component
public class ChatEventQueue {
    final Queue<TeamupEventChat> queue = new ConcurrentLinkedQueue<TeamupEventChat>();

    public void offer(TeamupEventChat e) {
        if (e == null) {
            return;
        }
        queue.offer(e);
    }

    public TeamupEventChat poll() {
        return queue.poll();
    }
}
