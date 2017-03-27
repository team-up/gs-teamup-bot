package gs.teamup.bot.template.teamup;

import com.google.common.base.Strings;

import gs.teamup.bot.pojo.edge.ChatMessage;
import gs.teamup.bot.pojo.edge.bot.Reply;
import gs.teamup.bot.pojo.edge.bot.Say;
import gs.teamup.bot.template.BaseTemplate;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@CommonsLog
@Component
public class EdgeTemplate extends BaseTemplate {
    @Value("${teamup.edge}")
    String edgeUrl;

    public ChatMessage getMessage(Long room, Long msg) {
        String url = edgeUrl + "/v3/message/summary/" + room + "/" + msg;

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url).queryParam("all", 1);

        ParameterizedTypeReference<ChatMessage> p = new ParameterizedTypeReference<ChatMessage>() {
        };

        return get(builder.toUriString(), p);
    }

    public void say(Long room, String content) {
        if (Strings.isNullOrEmpty(content)) {
            return;
        }

        String url = edgeUrl + "/v3/message/" + room;

        ParameterizedTypeReference<String> p = new ParameterizedTypeReference<String>() {
        };

        post(url, Say.create(content), p);
    }

    public void reply(Long feed, String content) {
        if (Strings.isNullOrEmpty(content)) {
            return;
        }

        String url = edgeUrl + "/v3/feed/reply/" + feed;

        ParameterizedTypeReference<String> p = new ParameterizedTypeReference<String>() {
        };

        post(url, Reply.create(content), p);
    }
}
