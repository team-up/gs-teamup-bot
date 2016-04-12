package gs.teamup.bot.pojo.edge.bot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Say {
    @JsonProperty("content")
    String content;

    public static Say create(String content){
        Say c = new Say();
        c.setContent(content);
        return c;
    }

}
