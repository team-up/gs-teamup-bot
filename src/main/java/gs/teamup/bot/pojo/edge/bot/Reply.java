package gs.teamup.bot.pojo.edge.bot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Reply {
    @JsonProperty("content")
    String content;

    public static Reply create(String content){
        Reply c = new Reply();
        c.setContent(content);
        return c;
    }

}
