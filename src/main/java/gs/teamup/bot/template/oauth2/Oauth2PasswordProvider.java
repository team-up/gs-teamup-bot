package gs.teamup.bot.template.oauth2;

import com.google.common.base.Strings;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Getter
@Component
public class Oauth2PasswordProvider {


	@Value("${teamup.auth}")
	private String url;

	@Value("${oauth.client.id}")
	private String clientId;

	@Value("${oauth.client.secret}")
	private String clientSecret;

	@Value("${teamup.id}")
	private String name;

	@Value("${teamup.pw}")
	private String password;

	private String grant_type;
	private String refreshToken;

	public String tokenUrl() {
		return url + "/oauth2/token";
	}

	public Oauth2PasswordProvider refresh(String token) {
		this.refreshToken = token;
		this.grant_type = "refresh_token";
		return this;
	}

	public Oauth2PasswordProvider password() {
		this.grant_type = "password";
		return this;
	}

	public HttpEntity<Object> entity() {

		String type = getGrant_type();
		if( Strings.isNullOrEmpty(type)){
			type = "password";
		}

		// TODO validate
		MultiValueMap<String, Object> data = new LinkedMultiValueMap<>();
		data.add("grant_type", type);

		if (type.equals("password")) {
			data.add("client_id", getClientId());
			data.add("client_secret", getClientSecret());
			data.add("username", getName());
			data.add("password", getPassword());
		} else if (type.equals("refresh_token")) {
			data.add("refresh_token", getRefreshToken());
		}
		return new HttpEntity<Object>(data, getHeader());
	}

	private HttpHeaders getHeader() {
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		return header;
	}
}
