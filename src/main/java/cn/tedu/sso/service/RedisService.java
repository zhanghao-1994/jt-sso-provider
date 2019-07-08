package cn.tedu.sso.service;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	public void set(String key, String value) {
		redisTemplate.opsForValue().set(key, value);
	}
	
	public void set(String key, String value, Integer timeout) {
		redisTemplate.opsForValue().set(key, value);
		redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
	}
	
	public void expire(String key, Integer timeout) {
		redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
	}
	
	public String get(String key) {
		return redisTemplate.opsForValue().get(key);
	}
	
}
