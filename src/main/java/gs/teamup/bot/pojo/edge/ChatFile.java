package gs.teamup.bot.pojo.edge;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatFile {
    @JsonProperty("id")
    String id;

    @JsonProperty("type")
    String type; // normal,image,video

    @JsonProperty("name")
    String name;

    @JsonProperty("size")
    Long size;
}
