package gs.teamup.bot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by thisno on 2016-04-12.
 */
@Configuration
@PropertySource(
        ignoreResourceNotFound = true,
        value = {
                "classpath:/version.properties"
                ,"classpath:/config.properties"
                ,"file:/data/etc/gs-teamup-bot/config.properties"
        }
)
public class ProductionProperties {
}
