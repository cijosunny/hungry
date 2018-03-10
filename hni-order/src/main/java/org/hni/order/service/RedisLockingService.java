package org.hni.order.service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.Lifecycle;
import org.springframework.stereotype.Component;

@Component
public class RedisLockingService implements LockingService<RedissonClient>, Lifecycle {
	private static final Logger logger = LoggerFactory.getLogger(RedisLockingService.class);
	
	protected RedissonClient redisson;
	
	public RedisLockingService(){
	}
	
	public void setConfigLocation(String configLocation)  throws IOException {
		Config config;
		config = Config.fromJSON(this.getClass().getResourceAsStream(configLocation));
		redisson = Redisson.create(config);	
	}
	
	@Override
	public boolean acquireLock(String key) {
		return acquireLock(key, null);
	}
	
	@Override
	public boolean acquireLock(String key, Long ttlMinutes) {
		if ( null != redisson ) {
			RLock lock = redisson.getLock(key);
			if (lock.isLocked()) {
				logger.warn(key + " is locked");
				return false;
			}
			if ( null != ttlMinutes) {
				lock.lock(ttlMinutes, TimeUnit.MINUTES);
			} else {
				lock.lock();
			}
		}
		return true;
	}

	@Override
	public boolean isLocked(String key) {
		if ( null != redisson ) {
			RLock lock = redisson.getLock(key);
			return lock.isLocked();
		}
		return false;
	}

	@Override
	public void releaseLock(String key) {
		if ( null != redisson ) {
			RLock lock = redisson.getLock(key);
			if (lock.isLocked()) {
				lock.forceUnlock();
			}
		}
	}

	@Override
	public void shutdown() {
		logger.info("Shutting down RedisLockingService...");
		if ( null != redisson ) {
			redisson.shutdown();
		}
	}

	@Override
	public boolean isRunning() {
		return true;
	}

	@Override
	public void start() {
	}

	@Override
	public void stop() {
		shutdown();
	}

	public RedissonClient getNativeClient() {
		return this.redisson;
	}
	
}
