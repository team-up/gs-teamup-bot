package gs.teamup.bot.scheduler;

import gs.teamup.bot.component.EventQueue;
import gs.teamup.bot.controller.ChatController;
import gs.teamup.bot.pojo.edge.ChatMessage;
import gs.teamup.bot.pojo.event.TeamupEventChat;
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
public class ChatScheduler {
    @Autowired
    @Qualifier("chatEventQueue")
    private EventQueue<TeamupEventChat> chatEventQueue;

    @Autowired
    private EdgeTemplate edgeTemplate;

    @Autowired
    private ChatController chatController;

    @Scheduled(fixedDelay = 100)
    public void chat() {
        TeamupEventChat teamupEventChat = chatEventQueue.poll();
        if (teamupEventChat == null) {
            return;
        }

        log.debug("chat pop");

        ChatMessage chatMsg = edgeTemplate.getMessage(teamupEventChat.getRoom(), teamupEventChat.getMsg());
        if (chatMsg == null) {
            return;
        }

        chatController.chat(chatMsg, teamupEventChat.getRoom());
    }
}
