package gs.teamup.bot.service;

import gs.teamup.bot.pojo.edge.ChatMessage;
import gs.teamup.bot.template.teamup.EdgeTemplate;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by thisno on 2016-04-12.
 */
@CommonsLog
@Component
public class ChatService {

    @Autowired
    private EdgeTemplate edgeTemplate;

    @Value("${project.version}")
    private String version;

    public void doChat(ChatMessage chatMessage, int room) {
        String content = chatMessage.getContent().trim();
        if (content.equals("?")) {
            edgeTemplate.say(room, version);
        } else if (content.equals("로또")) {
            List<Integer> number = new Random().ints(1, 45).distinct().limit(6).boxed().sorted()
                    .collect(Collectors.toList());
            edgeTemplate.say(room, number.toString());
        }

    }
}
