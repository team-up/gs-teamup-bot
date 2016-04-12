package gs.teamup.bot.pojo.edge;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by thisno1 on 2016-01-20.
 */

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateRoom {

    @JsonProperty("room")
    int room;
}
