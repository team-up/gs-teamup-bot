package gs.teamup.bot.pojo.edge;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Room {
	Integer team;
	Integer roomtype; // 1개인 2 단체
	Integer state;
	String name;
	List<Integer> users;
	Integer user;
	Integer msgtype; // 1 일반 2 파일
}
