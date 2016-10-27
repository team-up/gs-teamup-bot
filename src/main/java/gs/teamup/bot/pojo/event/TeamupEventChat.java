package gs.teamup.bot.pojo.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by thisno on 2016-04-12.
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamupEventChat {

    @JsonProperty("team")
    Integer team;

    @JsonProperty("room")
    Integer room;

    @JsonProperty("msg")
    Integer msg;

    @JsonProperty("user")
    Integer user;
}
