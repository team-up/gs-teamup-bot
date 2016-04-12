package gs.teamup.bot.template.teamup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import com.teamup.bot.pojo.api.event.Events;
import com.teamup.bot.template.oauth2.Oauth2Template;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@Component
public class EventTemplate {

	@Autowired
	Oauth2Template oauth2;

	@Value("${teamup.event}")
	String evUrl;

	@Autowired
	@Qualifier("eventRestTemplate")
	RestOperations event;

	public Events getEvent() {

		OAuth2AccessToken token = oauth2.token();

		if( token == null ){
			log.debug("token is null ");
			return null;
		}

		HttpEntity<Object> entity = getEntity(token.getValue());
		ResponseEntity<Events> responseEntity = null;
		try {
			responseEntity = event.exchange(evUrl, HttpMethod.GET, entity, Events.class);
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
