package gs.teamup.bot.pojo.edge.bot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import gs.teamup.bot.pojo.edge.Extras;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Say {
    @JsonProperty("content")
    String content;

    @JsonProperty("extras")
    Extras extras;

    public static Say create(String content, @Nullable Extras extras) {
        Say c = new Say();
        c.setContent(content);
        c.setExtras(extras);
        return c;
    }

}
