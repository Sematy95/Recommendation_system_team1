package pro.sky.recommendation_service.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import jdk.jfr.Enabled;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Configuration class for setting up caching using Caffeine.
 * This class enables caching and defines a primary CacheManager bean.
 */
@EnableCaching
@Configuration
public class CacheConfiguration {
    /**
     * It will be used by default for caching operations in the application.
     *
     * @return A CaffeineCacheManager instance configured with specific settings.
     */
    @Bean
    @Primary
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("dogsInHouse");
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .maximumSize(1));
        return cacheManager;
    }
}
