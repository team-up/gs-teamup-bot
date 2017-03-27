package gs.teamup.bot.template.oauth2;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by thisno on 2016-04-12.
 */
@Component
public class Oauth2Template {
    @Autowired
    @Qualifier("restTemplate")
    private RestTemplate restTemplate;

    @Autowired
    private Oauth2PasswordProvider provider;

    private OAuth2AccessToken accessToken;

    public OAuth2AccessToken token() {
        if (accessToken == null) {
            ResponseEntity<OAuth2AccessToken> response = restTemplate.postForEntity(provider.tokenUrl(), provider.entity(),
                    OAuth2AccessToken.class);

            if (response.getStatusCode().equals(HttpStatus.OK)) {
                OAuth2AccessToken token = response.getBody();

                if (Strings.isNullOrEmpty(token.getValue())) {
                    return null;
                }

                accessToken = token;
            }
        } else if (accessToken.isExpired()) {
            refresh(accessToken.getRefreshToken().getValue());
        }

        return accessToken;
    }

    public void refresh(String refreshToken) {
        accessToken = null;
        provider.refresh(refreshToken);
        accessToken = token();
        provider.password();

        if (accessToken == null || accessToken.isExpired()) {
            accessToken = token();
        }
    }
}
