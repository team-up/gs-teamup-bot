package gs.teamup.bot.scheduler;

import gs.teamup.bot.component.EventQueue;
import gs.teamup.bot.pojo.event.TeamupEvent;
import gs.teamup.bot.pojo.event.TeamupEventChat;
import gs.teamup.bot.pojo.event.TeamupEventFeed;
import gs.teamup.bot.pojo.event.TeamupEventList;
import gs.teamup.bot.template.teamup.EventTemplate;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by thisno on 2016-04-12.
 */
@CommonsLog
@Component
public class EventListenScheduler {
    @Autowired
    @Qualifier("chatEventQueue")
    private EventQueue<TeamupEventChat> chatEventQueue;

    @Autowired
    @Qualifier("feedEventQueue")
    private EventQueue<TeamupEventFeed> feedEventQueue;

    @Autowired
    private EventTemplate eventTemplate;

    @Value("${teamup.bot.feed}")
    private boolean botFeed;

    @Scheduled(fixedDelay = 100)
    public void getEvent() {
        log.debug("waiting get event...");
        TeamupEventList teamupEventList = eventTemplate.getEvent();
        if (teamupEventList == null) {
            return;
        }

        List<TeamupEvent> events = teamupEventList.getEvents();
        if (events == null || events.isEmpty()) {
            return;
        }

        events.forEach(e -> {
            doEvent(e);
        });
    }

    private void doEvent(TeamupEvent ev) {
        String t = ev.getType();
        switch (t) {
            case "chat.initbot":
            case "chat.message":
                TeamupEventChat chat = ev.getChat();
                chat.setType(t);
                chatEventQueue.offer(chat);
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

            default:
                break;
        }
    }
}
