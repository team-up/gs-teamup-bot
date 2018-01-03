package gs.teamup.bot.scheduler;

import gs.teamup.bot.component.EventQueue;
import gs.teamup.bot.pojo.event.TeamupEvent;
import gs.teamup.bot.pojo.event.TeamupEventChat;
import gs.teamup.bot.pojo.event.TeamupEventFeed;
import gs.teamup.bot.pojo.event.TeamupEvents;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestOperations;

import java.util.List;

/**
 * Created by thisno on 2016-04-12.
 */
@CommonsLog
@Component
public class EventListenScheduler {
    @Value("${teamup.ev}")
    private String evUrl;

    @Autowired
    @Qualifier("chatEventQueue")
    private EventQueue<TeamupEventChat> chatEventQueue;

    @Autowired
    @Qualifier("feedEventQueue")
    private EventQueue<TeamupEventFeed> feedEventQueue;

    @Autowired
    private RestOperations restOperations;

    @Value("${teamup.bot.feed}")
    private boolean botFeed;

    @Scheduled(fixedDelay = 100)
    public void getEvents() {
        log.debug("waiting get events...");

        String url = evUrl + "/v3/events";

        TeamupEvents teamupEvents = restOperations.getForObject(url, TeamupEvents.class);
        if (teamupEvents == null) {
            log.error("events is null");
            return;
        }

        List<TeamupEvent> events = teamupEvents.getEvents();
        if (CollectionUtils.isEmpty(events)) {
            return;
        }

        events.forEach(e -> doEvent(e));
    }

    private void doEvent(TeamupEvent ev) {
        String t = ev.getType();
        switch (t) {
            case "chat.message":
                chatEventQueue.offer(ev.getChat());
                break;

            // TODO feed
            case "feed.feed":
            case "feed.reply":
                if (botFeed) {
                    TeamupEventFeed teamupEventFeed = ev.getFeed();
                    teamupEventFeed.setType(t);
                    feedEventQueue.offer(teamupEventFeed);
                }
                break;

            case "user.drop":
            case "user.password":
                log.error("account expired");
                System.exit(0);
                break;

            default:
                break;
        }
    }
}
