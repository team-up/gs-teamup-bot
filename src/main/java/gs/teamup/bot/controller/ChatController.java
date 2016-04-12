package gs.teamup.bot.controller;

import gs.teamup.bot.pojo.edge.ChatMessage;
import gs.teamup.bot.service.ChatService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by thisno on 2016-04-12.
 */

@CommonsLog
@Component
public class ChatController {

    @Autowired
    ChatService chatService;

    public void chat(ChatMessage chatMessage, int room){

        log.debug("chat : " + chatMessage.getContent());

        switch (chatMessage.getType()) {
            case 1: // chart
                chatService.doChat(chatMessage, room);
                break;
            case 2: // TODO file
                break;
            default:
                break;
        }

    }


}
