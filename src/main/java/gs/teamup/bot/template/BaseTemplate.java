package gs.teamup.bot.template;


import gs.teamup.bot.template.oauth2.Oauth2Template;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@CommonsLog
@Component
public class BaseTemplate {

	@Autowired
	Oauth2Template oauth2Template;

	@Autowired
	@Qualifier("restTemplate")
	RestTemplate restTemplate;

	protected <T> T get(String url, ParameterizedTypeReference<T> p) {
		return send(url, null, p, HttpMethod.GET);
	}

	protected <T> T post(String url, Object request, ParameterizedTypeReference<T> p) {
		return send(url, request, p, HttpMethod.POST);
	}

	private <T> T send(String url, Object request, ParameterizedTypeReference<T> p, HttpMethod httpMethod) {

		HttpEntity<Object> entity = getEntity(request);
		if( entity == null){
			return null;
		}
		ResponseEntity<T> responseEntity = null;

		try {
			responseEntity = restTemplate.exchange(url, httpMethod, entity, p);
		} catch (RestClientException e) {
			log.error(url, e);
		}

		if (responseEntity != null && responseEntity.getStatusCode().equals(HttpStatus.OK)) {
			return responseEntity.getBody();
		} else {
			if(responseEntity != null){
				log.error(responseEntity.getStatusCode());
			}
		}
		return null;

	}

	private HttpEntity<Object> getEntity(Object request) {

		OAuth2AccessToken token = oauth2Template.token();

		if (token == null) {
			return null;
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "bearer " + token.getValue());
		return new HttpEntity<Object>(request, headers);
	}

}
