package gs.teamup.bot.scheduler;

import gs.teamup.bot.component.EventQueue;
import gs.teamup.bot.controller.ChatController;
import gs.teamup.bot.pojo.edge.ChatMessage;
import gs.teamup.bot.pojo.event.TeamupEventChat;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Created by thisno on 2016-04-12.
 */
@CommonsLog
@Component
public class ChatScheduler {
    @Value("${teamup.edge}")
    private String edgeUrl;

    @Autowired
    @Qualifier("chatEventQueue")
    private EventQueue<TeamupEventChat> chatEventQueue;

    @Autowired
    private RestOperations restOperations;

    @Autowired
    private ChatController chatController;

    @Scheduled(fixedDelay = 100)
    public void chat() {
        TeamupEventChat teamupEventChat = chatEventQueue.poll();
        if (teamupEventChat == null) {
            return;
        }

        log.debug("chat pop");

        String url = edgeUrl + "/v3/message/summary/" + teamupEventChat.getRoom() + "/" + teamupEventChat.getMsg() + "/1";

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url).queryParam("all", 1);

        ChatMessage chatMessage = restOperations.getForObject(builder.toUriString(), ChatMessage.class);
        if (chatMessage == null) {
            return;
        }

        chatController.chat(chatMessage, teamupEventChat.getRoom());
    }
}
