package gs.teamup.bot.scheduler;

import gs.teamup.bot.component.EventQueue;
import gs.teamup.bot.pojo.event.TeamupEventFeed;
import gs.teamup.bot.template.teamup.EdgeTemplate;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by thisno on 2016-04-12.
 */
@CommonsLog
@Component
public class FeedScheduler {
    @Autowired
    @Qualifier("feedEventQueue")
    private EventQueue<TeamupEventFeed> feedEventQueue;

    @Autowired
    private EdgeTemplate edgeTemplate;

    @Scheduled(fixedDelay = 100)
    public void feed() {
        TeamupEventFeed teamupEventFeed = feedEventQueue.poll();
        if (teamupEventFeed == null) {
            return;
        }

        log.debug("feed pop");

        if ("feed.reply".equals(teamupEventFeed.getType())) {
            edgeTemplate.reply(teamupEventFeed.getFeed(), "테스트 답글입니다.");
        }
    }
}
