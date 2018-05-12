package com.wz.common.cache.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.wz.common.consts.Consts;
import com.wz.common.tools.PatternUtils;


/**
 * Ehcache缓存 实现类 ##使用方法 直接在类中 增加 注解配置 即可
 * 
 * @Autowired
 * @Qualifier("ehCache")
 * @author Johnson.Jia
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class EhcacheImpl implements com.wz.common.cache.Cache {

	/**
	 * 缓存 key 锁
	 */
	private final String EHCACHE_KEY_SYN = "EHCACHE_KEY_SYN";

	private static Logger logger = Logger.getLogger(EhcacheImpl.class);

	private Cache cache;

	public Cache getCache() {
		return cache;
	}

	public void setCache(Cache cache) {
		this.cache = cache;
	}

	public void set(String key, Object value) {
		set(key, value, 0);
	}

	public void set(String key, Object value, int expirationTime) {
		set(key, value, 0, expirationTime);
	}

	public void set(String key, Object value, int timeToIdleSeconds, int expirationTime) {
		try {
			String syn = EHCACHE_KEY_SYN + key;
			synchronized (syn.intern()) {
				Element element = null;
				if (cache.get(key.trim()) != null) {
					cache.remove(key.trim());
				}
				if (value != null) {
					element = new Element(key.trim(), value);
					if (expirationTime > 0) {
						element.setTimeToLive(expirationTime);
					}
					if (timeToIdleSeconds > 0) {
						element.setTimeToIdle(timeToIdleSeconds);
					}
					cache.put(element);
				}
			}
		} catch (Exception e) {
			logger.error(Consts.LOG_ERROR_EH_CACHE, e);
		}
	}

	public Object get(String key) {
		try {
			String syn = EHCACHE_KEY_SYN + key;
			synchronized (syn.intern()) {
				Object result = null;
				Element element = null;
				if (StringUtils.isNotEmpty(key) && cache.get(key.trim()) != null) {
					element = cache.get(key.trim());
					if (element.getObjectValue() != null) {
						result = element.getObjectValue();
					}
				}
				return result;
			}
		} catch (Exception e) {
			logger.error(Consts.LOG_ERROR_EH_CACHE, e);
		}
		return null;
	}

	public void remove(String key) {
		try {
			if (cache.get(key.trim()) != null) {
				cache.remove(key);
			}
		} catch (Exception e) {
			logger.error(Consts.LOG_ERROR_EH_CACHE, e);
		}
	}

	/**
	 * 把所有cache中的内容删除，但是cache对象还是保留. Clears the contents of all caches in the
	 * CacheManager, but without removing any caches.
	 */
	public void removeAll() {
		try {
			cache.removeAll();
		} catch (Exception e) {
			logger.error(Consts.LOG_ERROR_EH_CACHE, e);
		}
	}

	@Override
	public <T> T get(String key, Class<T> clazz) {
		try {
			Object object = get(key);
			return (T) object;
		} catch (Exception e) {
			remove(key);
			logger.error(Consts.LOG_ERROR_EH_CACHE, e);
		}
		return null;
	}

	@Override
	public <T> List<T> getList(String key, Class<T> clazz) {
		try {
			Object object = get(key);
			if (object == null) {
				return null;
			}
			return (List<T>) object;
		} catch (Exception e) {
			remove(key);
			logger.error(Consts.LOG_ERROR_EH_CACHE, e);
		}
		return null;
	}

	@Override
	public <K, T> Map<K, T> getMap(String key, Class<K> keyClazz, Class<T> valClazz) {
		try {
			Object object = get(key);
			if (object == null) {
				return null;
			}
			return (Map<K, T>) object;
		} catch (Exception e) {
			remove(key);
			logger.error(Consts.LOG_ERROR_EH_CACHE, e);
		}
		return null;
	}

	@Override
	public List<String> getKeys(String key) {
		try {
			List<?> allKeyList = cache.getKeys();
			boolean status = false;
			if (StringUtils.isNotEmpty(key)) {
				key = key.replaceAll("\\*", ".*");
				status = true;
			}
			ArrayList<String> nonExpiredKeys = new ArrayList<String>(allKeyList.size());
			for (Iterator iter = allKeyList.iterator(); iter.hasNext();) {
				String next = String.valueOf(iter.next());
				if (status && !PatternUtils.regex(key, next, true)) {
					continue;
				}
				Element element = cache.getQuiet(next);
				if (element != null) {
					nonExpiredKeys.add(next);
				}
			}
			nonExpiredKeys.trimToSize();
			return nonExpiredKeys;
		} catch (Exception e) {
			logger.error(Consts.LOG_ERROR_EH_CACHE, e);
		}
		return null;
	}

	@Override
	public Integer getKeysNum(String key) {
		return getKeys(key).size();
	}
}
