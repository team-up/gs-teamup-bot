package gs.teamup.bot.pojo.edge;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatMessage {
    @JsonProperty("msg")
    Long msg;

    @JsonProperty("user")

    Integer user;

    @JsonProperty("type")
    Integer type;

    @JsonProperty("len")
    Integer len;

    @JsonProperty("content")
    String content;

    @JsonProperty("file")
    ChatFile file;

    @JsonProperty("extras")
    Extras extras;

    @Nullable
    public String getResponseId() {
        if(extras != null && extras.getExtraV2() != null) {
            return extras.getExtraV2().getResponseId();
        }

        return null;
    }
}
