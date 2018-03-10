package org.hni.order.service;

public interface LockingService<T> {

	boolean acquireLock(String key);
	boolean acquireLock(String key, Long ttlMinutes);
	boolean isLocked(String key);
	void releaseLock(String key);
	void shutdown();
	
	T getNativeClient();
}
