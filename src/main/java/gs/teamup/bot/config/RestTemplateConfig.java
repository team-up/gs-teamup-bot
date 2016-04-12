package gs.teamup.bot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thisno on 2016-04-12.
 */
@Configuration
public class RestTemplateConfig {

    @Bean(name="eventRestTemplate")
    public RestTemplate eventRestTemplate() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(1000*2);
        factory.setReadTimeout(1000*30);
        return getRestTemplate(factory);
    }

    @Bean(name="restTemplate")
    public RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(1000*2);
        factory.setReadTimeout(1000*5);
        return getRestTemplate(factory);
    }

    private RestTemplate getRestTemplate(HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory){
        RestTemplate restTemplate = new RestTemplate(httpComponentsClientHttpRequestFactory);
        Charset charset = Charset.forName("UTF-8");

        List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
        converters.add(new StringHttpMessageConverter(charset));
        converters.add(new MappingJackson2HttpMessageConverter());
        converters.add(new ByteArrayHttpMessageConverter());

        FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
        formHttpMessageConverter.setCharset(charset);
        converters.add(formHttpMessageConverter);

        restTemplate.setMessageConverters(converters);
        return restTemplate;
    }
}
