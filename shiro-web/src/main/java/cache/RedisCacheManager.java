package cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 缓存角色和权限数据
 */

@Component
public class RedisCacheManager<K, V> implements CacheManager {

    @Resource
    private RedisCache redisCache;


    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
         return redisCache;
    }
}
