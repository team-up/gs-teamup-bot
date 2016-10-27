package gs.teamup.bot.config;

import gs.teamup.bot.component.EventQueue;
import gs.teamup.bot.pojo.event.TeamupEventChat;
import gs.teamup.bot.pojo.event.TeamupEventFeed;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by thisno on 2016-10-27.
 */

@Configuration
public class EventConfig {

    @Bean(name = "chatEventQueue")
    public EventQueue<TeamupEventChat> getChatEventQueue(){
        return new EventQueue<>();
    }

    @Bean(name = "feedEventQueue")
    public EventQueue<TeamupEventFeed> getFeedReplyEventQueue(){
        return new EventQueue<>();
    }
}
