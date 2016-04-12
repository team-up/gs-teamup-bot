package gs.teamup.bot.scheduler;

import com.google.common.base.Strings;
import gs.teamup.bot.component.ChatEventQueue;
import gs.teamup.bot.controller.ChatController;
import gs.teamup.bot.pojo.edge.ChatMessage;
import gs.teamup.bot.pojo.event.TeamupEventChat;
import gs.teamup.bot.service.ChatService;
import gs.teamup.bot.template.teamup.EdgeTemplate;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by thisno on 2016-04-12.
 */

@CommonsLog
@Component
public class ChatScheduler {

    @Autowired
    private ChatEventQueue chatEventQueue;

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

        ChatMessage chatMsg = edgeTemplate.getMessgae(teamupEventChat.getRoom(), teamupEventChat.getMsg());
        if (chatMsg == null) {
            return;
        }

        // 길이가 다르면 장문 이니 long message 로 가지고 온다
        if (chatMsg.getContent().length() != chatMsg.getLen()) {
            String content = edgeTemplate.getMessageLong(teamupEventChat.getRoom(), teamupEventChat.getMsg());
            if (Strings.isNullOrEmpty(content) == false) {
                chatMsg.setContent(content);
            }
        }

        chatController.chat(chatMsg, teamupEventChat.getRoom());
    }

}
