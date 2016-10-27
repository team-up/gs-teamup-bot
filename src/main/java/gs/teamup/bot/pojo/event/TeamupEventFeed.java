package gs.teamup.bot.pojo.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by thisno on 2016-10-27.
 */

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamupEventFeed {

    String type;

    @JsonProperty("team")
    Integer team;

    @JsonProperty("feedgroup")
    Integer feedgroup;

    @JsonProperty("feed")
    Integer feed;

    @JsonProperty("reply")
    Integer reply;

}
