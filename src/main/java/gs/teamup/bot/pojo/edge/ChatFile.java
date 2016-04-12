package gs.teamup.bot.pojo.edge;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatFile {

	@JsonProperty("file")
	Integer file;

	@JsonProperty("type")
	Integer type; // 1일반, 2이미지, 3동영상

	@JsonProperty("name")
	String name;

	@JsonProperty("w")
	Integer w;

	@JsonProperty("h")
	Integer h;

	@JsonProperty("size")
	Integer size;

}
