package gs.teamup.bot.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import com.teamup.bot.template.oauth2.Oauth2Template;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@Component
public class BaseTemplate {

	@Autowired
	Oauth2Template oauth2;

	@Autowired
	@Qualifier("restTemplate")
	RestOperations rest;

	protected ResponseEntity<byte[]> getByte(String url) {

		HttpEntity<String> entity = getDownEntity();
		ResponseEntity<byte[]> response = rest.exchange(url, HttpMethod.GET, entity, byte[].class);

		return response;
	}

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
			responseEntity = rest.exchange(url, httpMethod, entity, p);
		} catch (Exception e) {
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

		OAuth2AccessToken token = oauth2.token();

		if (token == null) {
			log.debug("token is null ");
			return null;
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "bearer " + token.getValue());
		return new HttpEntity<Object>(request, headers);
	}

	private HttpEntity<String> getDownEntity() {

		OAuth2AccessToken token = oauth2.token();

		if (token == null) {
			log.debug("token is null ");
			return null;
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "bearer " + token.getValue());
		return new HttpEntity<String>(headers);
	}
}
