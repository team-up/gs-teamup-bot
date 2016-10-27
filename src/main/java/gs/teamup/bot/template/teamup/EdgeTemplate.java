package gs.teamup.bot.template.teamup;

import com.google.common.base.Strings;

import gs.teamup.bot.pojo.edge.ChatMessage;
import gs.teamup.bot.pojo.edge.ChatMessageList;
import gs.teamup.bot.pojo.edge.Room;
import gs.teamup.bot.pojo.edge.bot.Reply;
import gs.teamup.bot.pojo.edge.bot.Say;
import gs.teamup.bot.template.BaseTemplate;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.util.List;

@CommonsLog
@Component
public class EdgeTemplate extends BaseTemplate {

    @Value("${teamup.edge}")
    String edgeUrl;


    public ChatMessage getMessage(int room, int msg) {

        String url = String.format("%s/messages/%s/1/2/%s", edgeUrl, room, msg-1);

        ParameterizedTypeReference<ChatMessageList> p = new ParameterizedTypeReference<ChatMessageList>() {
        };
        ChatMessageList m = get(url, p);

        if (m != null) {
            List<ChatMessage> chatMessageList = m.getChatMessageList();
            if (chatMessageList != null && chatMessageList.isEmpty() == false) {
                return chatMessageList.get(0);
            }
        }

        return null;
    }

    public String getMessageLong(int room, int msg) {

        String url = String.format("%s/message/%s/%s", edgeUrl, room, msg);

        ParameterizedTypeReference<String> p = new ParameterizedTypeReference<String>() {
        };
        String content = get(url, p);

        return content;

    }


    public void say(int room, String msg) {
        if (Strings.isNullOrEmpty(msg)) {
            return;
        }

        String url = String.format("%s/message/%s", edgeUrl, room);

        ParameterizedTypeReference<String> p = new ParameterizedTypeReference<String>() {
        };

        post(url, Say.create(msg), p);
    }


    public void reply(int feed, String msg) {
        String url = String.format("%s/feed/reply/%s", edgeUrl, feed);

        ParameterizedTypeReference<String> p = new ParameterizedTypeReference<String>() {
        };

        post(url, Reply.create(msg), p);
    }

}
