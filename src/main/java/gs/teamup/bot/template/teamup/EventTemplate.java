package gs.teamup.bot.template.teamup;


import gs.teamup.bot.pojo.event.TeamupEventList;
import gs.teamup.bot.template.oauth2.Oauth2Template;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@CommonsLog
@Component
public class EventTemplate{

	@Autowired
	Oauth2Template oauth2Template;

	@Value("${teamup.event}")
	String evUrl;

	@Autowired
	@Qualifier("eventRestTemplate")
	RestTemplate eventRestTemplate;

	public TeamupEventList getEvent() {

		OAuth2AccessToken token = oauth2Template.token();

		if( token == null ){
			log.debug("token is null ");
			return null;
		}

		HttpEntity<Object> entity = getEntity(token.getValue());
		ResponseEntity<TeamupEventList> responseEntity = null;
		try {
			responseEntity = eventRestTemplate.exchange(evUrl, HttpMethod.GET, entity, TeamupEventList.class);
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
		if (responseEntity == null){
			log.error("responseEntity is null");
		}else if(responseEntity.getStatusCode().equals(HttpStatus.OK)) {
			return responseEntity.getBody();
		}else{
			log.error(responseEntity.getStatusCode());
		}
		return null;
	}

	private HttpEntity<Object> getEntity(String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "bearer " + token);
		return new HttpEntity<Object>(null, headers);
	}
}
