package gs.teamup.bot.pojo.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by vicious on 2017-03-15.
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamupEventConfig {
    @JsonProperty("version")
    String version;

    @JsonProperty("lp_idle_time")
    Integer idleTime;

    @JsonProperty("lp_wait_timeout")
    Integer waitTimeout;
}
