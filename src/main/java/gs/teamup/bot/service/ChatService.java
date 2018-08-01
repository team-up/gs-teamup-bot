package gs.teamup.bot.service;

import gs.teamup.bot.pojo.edge.*;
import gs.teamup.bot.template.teamup.EdgeTemplate;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

    public void doChat(ChatMessage chatMessage, Long room) {
        String content = chatMessage.getContent().trim();
        String responseId = chatMessage.getResponseId();
        log.info(chatMessage.getResponseId());
        if (content.equals("?")) {
            edgeTemplate.say(room, version);
        } else if (content.equals("로또") || "lotto".equals(responseId)) {
            List<Integer> number = new Random().ints(1, 45).distinct().limit(6).boxed().sorted()
                    .collect(Collectors.toList());

            List<Button> buttons = new ArrayList<Button>();
            buttons.add(Button.textButton("lotto", "로또 번호 뽑기", "로또 번호 뽑아줘"));
            ExtraV2 extraV2 = new ExtraV2(buttons, null, buttons);

            edgeTemplate.say(room, number.toString(), extraV2);
        }
    }
}
