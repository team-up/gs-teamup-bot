package gs.teamup.bot.scheduler;

import gs.teamup.bot.component.EventQueue;
import gs.teamup.bot.pojo.edge.ChatMessage;
import gs.teamup.bot.pojo.edge.bot.Reply;
import gs.teamup.bot.pojo.event.TeamupEventFeed;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

/**
 * Created by thisno on 2016-04-12.
 */
@CommonsLog
@Component
public class FeedScheduler {
    @Value("${teamup.edge}")
    private String edgeUrl;

    @Autowired
    @Qualifier("feedEventQueue")
    private EventQueue<TeamupEventFeed> feedEventQueue;

    @Autowired
    private RestOperations restOperations;

    @Scheduled(fixedDelay = 100)
    public void feed() {
        TeamupEventFeed teamupEventFeed = feedEventQueue.poll();
        if (teamupEventFeed == null) {
            return;
        }

        log.debug("feed pop");

        if ("feed.reply".equals(teamupEventFeed.getType())) {
            String url = edgeUrl + "/v3/feed/reply/" + teamupEventFeed.getFeed();
            restOperations.postForObject(url, Reply.create("테스트 답글입니다."), ChatMessage.class);
        }
    }
}
