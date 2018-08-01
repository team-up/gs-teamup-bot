package gs.teamup.bot.pojo.edge;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Bottom {
    private String type;

    @JsonProperty("buttons")
    private List<Button> inputButtons = new ArrayList<>();

    @JsonProperty("range")
    private Boolean range;

    public Bottom (List<Button> inputButton) {
        this.type = "button";
        this.inputButtons = inputButton;
    }

    public Bottom (Boolean range) {
        this.type = "calendar";
        this.range = range;
    }

}
