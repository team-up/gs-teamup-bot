package gs.teamup.bot.config;

import gs.teamup.bot.pojo.event.TeamupConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.client.RestOperations;

import javax.annotation.PostConstruct;

/**
 * Created by thisno on 2016-04-12.
 */
@Configuration
@EnableOAuth2Client
public class RestTemplateConfig {
    @Value("${teamup.auth}")
    private String url;

    @Value("${teamup.ev}")
    private String evUrl;

    @Value("${oauth.client.id}")
    private String clientId;

    @Value("${oauth.client.secret}")
    private String clientSecret;

    @Value("${teamup.id}")
    private String username;

    @Value("${teamup.pw}")
    private String password;

    @Bean
    public RestOperations restOperations() {
        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resourceDetails());

        TeamupConfig teamupConfig = restTemplate.getForObject(evUrl, TeamupConfig.class);
        if (teamupConfig != null) {
            restTemplate.setRequestFactory(requestFactory(teamupConfig.getWaitTimeout()));
        }

        return restTemplate;
    }

    private ClientHttpRequestFactory requestFactory(int waitTimeout) {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(1000 * 3);
        if (waitTimeout > 0) {
            // 설정 값보다 read timeout 을 길게
            waitTimeout = (1000 * waitTimeout) + (100 * waitTimeout);
            requestFactory.setReadTimeout(waitTimeout);
        }

        return requestFactory;
    }

    private OAuth2ProtectedResourceDetails resourceDetails() {
        ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
        resourceDetails.setPassword(password);
        resourceDetails.setUsername(username);
        resourceDetails.setAccessTokenUri(url + "/oauth2/token");
        resourceDetails.setClientId(clientId);
        resourceDetails.setClientSecret(clientSecret);
        resourceDetails.setClientAuthenticationScheme(AuthenticationScheme.form);
        return resourceDetails;
    }

    @PostConstruct
    private void contextAuthentication() {
        // fake authentication for refresh token
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(null, null, AuthorityUtils.NO_AUTHORITIES);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
