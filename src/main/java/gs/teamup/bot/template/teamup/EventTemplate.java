package gs.teamup.bot.template.teamup;

import gs.teamup.bot.pojo.event.TeamupEventConfig;
import gs.teamup.bot.pojo.event.TeamupEventList;
import gs.teamup.bot.template.BaseTemplate;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@CommonsLog
@Component
public class EventTemplate extends BaseTemplate {
    TeamupEventConfig eventConfig;

    @Value("${teamup.ev}")
    String evUrl;

    @Autowired
    @Qualifier("eventRestTemplate")
    RestTemplate eventRestTemplate;

    public TeamupEventList getEvent() {
        OAuth2AccessToken token = oauth2Template.token();
        if (token == null) {
            log.debug("token is null ");
            return null;
        }

        if (eventConfig == null) {
            ParameterizedTypeReference<TeamupEventConfig> p = new ParameterizedTypeReference<TeamupEventConfig>() {
            };

            eventConfig = get(evUrl, p);
            int waitTimeout = eventConfig.getWaitTimeout();
            if (waitTimeout > 0) {
                // 설정 값보다 read timeout 을 길게
                waitTimeout = (1000 * waitTimeout) + (100 * waitTimeout);
                ((HttpComponentsClientHttpRequestFactory) eventRestTemplate.getRequestFactory()).setReadTimeout(waitTimeout);
            }
        }

        String url = evUrl + "/v3/events";

        HttpEntity<Object> entity = getEntity(token.getValue());
        ResponseEntity<TeamupEventList> responseEntity = null;
        try {
            responseEntity = eventRestTemplate.exchange(url, HttpMethod.GET, entity, TeamupEventList.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }

        if (responseEntity == null) {
            log.error("responseEntity is null");
        } else if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return responseEntity.getBody();
        } else {
            log.error(responseEntity.getStatusCode());
        }

        return null;
    }
}
