package gs.teamup.bot.pojo.edge;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@ToString
public class ExtraV2 {
    private String type = "bot"; // or user

    // type이 "bot"인 경우
    @JsonProperty("message_buttons")
    private List<Button> msgButtons;
    @JsonProperty("bottom")
    private Bottom bottom;
    @JsonProperty("scroll_buttons")
    private List<Button> scrollButtons;

    // type이 "user"인 경우
    @JsonProperty("response_id")
    private String responseId;

    public ExtraV2(List<Button> msgButtons, Bottom bottom, List<Button> scrollButtons) {
        this.msgButtons = msgButtons;
        this.bottom = bottom;
        this.scrollButtons = scrollButtons;
    }
}
