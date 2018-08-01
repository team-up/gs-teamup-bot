package gs.teamup.bot.pojo.edge;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Button {
    private String id;

    @JsonProperty("button_text")
    private String buttonText;

    @JsonProperty("response_text")
    private String responseText;

    private String type;

    private String url = "";

    public static Button textButton(String id, String buttonText, String responseText) {
        Button button = new Button();
        button.type = "text";
        button.id = id;
        button.buttonText = buttonText;
        button.responseText = responseText;
        return button;
    }

    public static Button linkButton(String buttonText, String url) {
        Button button = new Button();
        button.buttonText = buttonText;
        button.url = url;
        return button;
    }

}
