package pro.sky.recommendation_service.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.stereotype.Service;
import pro.sky.recommendation_service.service.ManagementService;

@Service
public class ManagementServiceImpl implements ManagementService {

    private static final Logger log = LoggerFactory.getLogger(ManagementServiceImpl.class);

    private final CaffeineCacheManager cacheManager;

    public ManagementServiceImpl(CaffeineCacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public void clearCaches() {
        log.info("clear caches method was invoked");

        cacheManager.getCacheNames().forEach(cacheName -> {
            cacheManager.getCache(cacheName).clear();
            log.info("cache:{} was cleared", cacheName);
        });
    }

}
