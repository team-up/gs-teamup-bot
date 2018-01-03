package gs.teamup.bot.service;

import gs.teamup.bot.pojo.edge.ChatMessage;
import gs.teamup.bot.pojo.edge.bot.Say;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by thisno on 2016-04-12.
 */
@CommonsLog
@Component
public class ChatService {
    @Value("${teamup.edge}")
    private String edgeUrl;

    @Autowired
    private RestOperations restOperations;

    @Value("${project.version}")
    private String version;

    public void doChat(ChatMessage chatMessage, Long room) {
        String url = edgeUrl + "/v3/message/" + room;
        String content = chatMessage.getContent().trim();

        if (content.equals("?")) {
            restOperations.postForObject(url, Say.create(version), Say.class);
        } else if (content.equals("로또")) {
            List<Integer> number = new Random().ints(1, 45).distinct().limit(6).boxed().sorted()
                    .collect(Collectors.toList());
            restOperations.postForObject(url, Say.create(number.toString()), Say.class);
        }
    }
}
