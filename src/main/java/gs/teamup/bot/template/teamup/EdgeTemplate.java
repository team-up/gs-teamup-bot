package gs.teamup.bot.template.teamup;

import com.google.common.base.Strings;

import gs.teamup.bot.pojo.edge.ChatMessage;
import gs.teamup.bot.pojo.edge.Room;
import gs.teamup.bot.pojo.edge.bot.Say;
import gs.teamup.bot.template.BaseTemplate;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

@CommonsLog
@Component
public class EdgeTemplate extends BaseTemplate {

    @Value("${teamup.edge}")
    String edgeUrl;

    public Room getRoom(int room) {
        String url = String.format("%s/room/%s", edgeUrl, room);

        ParameterizedTypeReference<Room> p = new ParameterizedTypeReference<Room>() {
        };
        Room r = get(url, p);
        return r;
    }

    public ChatMessage getMessgae(int room, int msg) {

        String url = String.format("%s/message/summary/%s/%s", edgeUrl, room, msg);

        ParameterizedTypeReference<ChatMessage> p = new ParameterizedTypeReference<ChatMessage>() {
        };
        ChatMessage m = get(url, p);

        return m;
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

}
