package com.wz.common.cache.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.wz.bean.exception.base.ErrorMsgException;
import com.wz.common.cache.Cache;
import com.wz.common.consts.Consts;
import com.wz.common.tools.Transcoder;


/**
 * redis缓存 实现类 ##使用方法 直接在类中 增加 注解配置 即可
 * 
 * @Autowired
 * @Qualifier("redisCache")
 * @author Johnson.Jia
 */
public class RedisCacheImpl implements Cache {

	private static Logger logger = Logger.getLogger(RedisCacheImpl.class);
	
	private StringRedisTemplate redisTemplate;

	public StringRedisTemplate getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void set(String key, Object value) {
		set(key, value, 0);
	}

	@Override
	public void set(String key, Object value, int expirationTime) {
		set(key, value, 0, expirationTime);
	}

	@Override
	public void set(String key, Object value, int timeToIdleSeconds, int expirationTime) {
		RedisConnection conn = null;
		try {
			conn = redisTemplate.getConnectionFactory().getConnection();
			if (!conn.isClosed() && value != null) {
				conn.set(key.getBytes(), Transcoder.serialize(value));
				if (expirationTime > 0) {
					conn.expire(key.getBytes(), expirationTime);
				}
			}
		} catch (Exception e) {
			logger.error(Consts.LOG_ERROR_REDIS_CACHE, e);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	@Override
	public void remove(String key) {
		RedisConnection conn = null;
		try {
			conn = redisTemplate.getConnectionFactory().getConnection();
			Set<byte[]> keys = conn.keys(key.getBytes());
			if (keys != null && !keys.isEmpty()) {
				conn.del(key.getBytes());
			}
		} catch (Exception e) {
			logger.error(Consts.LOG_ERROR_REDIS_CACHE, e);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	@Override
	public Object get(String key) {
		RedisConnection conn = null;
		try {
			Object obj = null;
			conn = redisTemplate.getConnectionFactory().getConnection();
			if (StringUtils.isNotEmpty(key) && !conn.isClosed()) {
				byte[] in = conn.get(key.getBytes());
				if (in != null && in.length > 0) {
					obj = Transcoder.deserialize(in);
				}
			}
			return obj;
		} catch (Exception e) {
			logger.error(Consts.LOG_ERROR_REDIS_CACHE, e);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return null;
	}

	@Override
	public void removeAll() {
		RedisConnection conn = null;
		try {
			conn = redisTemplate.getConnectionFactory().getConnection();
			conn.flushDb();
		} catch (Exception e) {
			logger.error(Consts.LOG_ERROR_REDIS_CACHE, e);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(String key, Class<T> clazz) {
		try {
			Object object = get(key);
			if (object == null) {
				return null;
			}
			if (object.getClass() == clazz) {
				return (T) object;
			} else {
				throw new ErrorMsgException(" 缓存类型不匹配  redis cache class !=  Class<T> clazz");
			}
		} catch (Exception e) {
			remove(key);
			logger.error(Consts.LOG_ERROR_REDIS_CACHE, e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> getList(String key, Class<T> clazz) {
		try {
			Object object = get(key);
			if (object == null) {
				return null;
			}
			List<?> list = ((List<?>) object);
			if (list.size() == 0) {
				return (List<T>) list;
			}
			if (list.get(0).getClass() == clazz) {
				return (List<T>) list;
			} else {
				throw new ErrorMsgException(" 缓存类型不匹配  redis cache class !=  Class<T> clazz");
			}
		} catch (Exception e) {
			remove(key);
			logger.error(Consts.LOG_ERROR_REDIS_CACHE, e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, T> Map<K, T> getMap(String key, Class<K> keyClazz, Class<T> valClazz) {
		try {
			Object object = get(key);
			if (object == null) {
				return null;
			}
			Map<?, ?> map = ((Map<?, ?>) object);
			if (map.size() == 0) {
				return (Map<K, T>) map;
			}
			Iterator<Map.Entry<?, ?>> iterator = (Iterator<Map.Entry<?, ?>>) ((Set<?>) map.entrySet()).iterator();
			if (iterator.hasNext()) {
				Map.Entry<?, ?> next = iterator.next();
				if (next.getKey().getClass() == keyClazz && next.getValue().getClass() == valClazz) {
					return (Map<K, T>) map;
				} else {
					throw new ErrorMsgException(" 缓存类型不匹配  redis cache class !=  Class<T> clazz");
				}
			}
		} catch (Exception e) {
			remove(key);
			logger.error(Consts.LOG_ERROR_REDIS_CACHE, e);
		}
		return null;
	}

	/**
	 * 获取 指定 key 模糊匹配集合
	 * 
	 * @author Johnson.Jia
	 * @date 2017年10月13日 下午3:27:04
	 * @param key
	 * @return
	 */
	public Set<byte[]> getKeysSet(String key) {
		RedisConnection conn = null;
		try {
			conn = redisTemplate.getConnectionFactory().getConnection();
			return conn.keys(key.getBytes());
		} catch (Exception e) {
			logger.error(Consts.LOG_ERROR_REDIS_CACHE, e);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return null;
	}

	@Override
	public List<String> getKeys(String key) {
		Set<byte[]> keysSet = getKeysSet(key);
		List<String> list = new ArrayList<String>(keysSet.size());
		for (byte[] bs : keysSet) {
			list.add(new String(bs));
		}
		return list;
	}

	@Override
	public Integer getKeysNum(String key) {
		return getKeysSet(key).size();
	}

	/**
	 * 批量获取 缓存数据
	 * 
	 * @see Transcoder
	 * @author Johnson.Jia
	 * @date 2017年8月21日 下午6:25:00
	 * @param keys
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> mGet(Set<byte[]> keys, Class<T> clazz) {
		try {
			List<byte[]> mGet = mGet(keys.toArray(new byte[][] {}));
			List<T> list = new ArrayList<T>();
			for (byte[] bs : mGet) {
				if (bs != null && bs.length > 0) {
					list.add((T) Transcoder.deserialize(bs));
				}
			}
			return list;
		} catch (Exception e) {
			logger.error(Consts.LOG_ERROR_REDIS_CACHE, e);
		}
		return null;
	}

	/**
	 * 批量获取 缓存数据
	 * 
	 * @author Johnson.Jia
	 * @date 2017年8月21日 下午7:00:01
	 * @param keys
	 * @return
	 */
	public List<byte[]> mGet(byte[]... keys) {
		RedisConnection conn = null;
		try {
			conn = redisTemplate.getConnectionFactory().getConnection();
			return conn.mGet(keys);
		} catch (Exception e) {
			logger.error(Consts.LOG_ERROR_REDIS_CACHE, e);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return null;
	}

	/**
	 * 添加 set 集合数据
	 * 
	 * @author Johnson.Jia
	 * @date 2017年8月21日 下午6:25:26
	 * @param key
	 * @param values
	 * @return
	 */
	public boolean sadd(String key, byte[]... values) {
		return sadd(key, -1, values);
	}

	/**
	 * 添加 set 集合数据
	 * 
	 * @author Johnson.Jia
	 * @date 2017年8月21日 下午6:25:26
	 * @param key
	 * @param values
	 * @return
	 */
	public boolean sadd(String key, int expirationTime, byte[]... values) {
		RedisConnection conn = null;
		try {
			conn = redisTemplate.getConnectionFactory().getConnection();
			if (conn.sAdd(key.getBytes(), values) == values.length) {
				if (expirationTime > 0) {
					conn.expire(key.getBytes(), expirationTime);
				}
				return true;
			}
			return false;
		} catch (Exception e) {
			logger.error(Consts.LOG_ERROR_REDIS_CACHE, e);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return false;
	}

	/**
	 * 获取 set 集合缓存数据
	 * 
	 * @author Johnson.Jia
	 * @date 2017年8月21日 下午6:26:14
	 * @param key
	 * @return
	 */
	public Set<byte[]> sMembers(String key) {
		RedisConnection conn = null;
		try {
			conn = redisTemplate.getConnectionFactory().getConnection();
			return conn.sMembers(key.getBytes());
		} catch (Exception e) {
			logger.error(Consts.LOG_ERROR_REDIS_CACHE, e);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return null;
	}

	/**
	 * 随机 获取 set 集合 内 指定大小 数据
	 * 
	 * @author Johnson.Jia
	 * @date 2017年9月25日 下午4:32:02
	 * @param key
	 *            缓存 key
	 * @param count
	 *            正数，且小于集合基数，那么命令返回一个包含 count 个元素的数组，数组中的元素各不相同。如果
	 *            大于等于集合基数，那么返回整个集合---------------------
	 *            负数，那么命令返回一个数组，数组中的元素可能会重复出现多次，而数组的长度为 count 的绝对值。
	 * @return
	 */
	public List<byte[]> sRandMember(String key, int count) {
		RedisConnection conn = null;
		try {
			conn = redisTemplate.getConnectionFactory().getConnection();
			return conn.sRandMember(key.getBytes(), count);
		} catch (Exception e) {
			logger.error(Consts.LOG_ERROR_REDIS_CACHE, e);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return null;
	}

	/**
	 * 删除 set 集合里面的key
	 * 
	 * @author Johnson.Jia
	 * @date 2017年8月25日 下午5:25:49
	 * @param key
	 * @param values
	 * @return
	 */
	public Long sRem(String key, byte[]... values) {
		RedisConnection conn = null;
		try {
			conn = redisTemplate.getConnectionFactory().getConnection();
			return conn.sRem(key.getBytes(), values);
		} catch (Exception e) {
			logger.error(Consts.LOG_ERROR_REDIS_CACHE, e);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return null;
	}

	/**
	 * 获取 set 内元素总个数
	 * 
	 * @author Johnson.Jia
	 * @date 2017年8月25日 下午5:28:14
	 * @param key
	 * @return
	 */
	public Long sCard(String key) {
		RedisConnection conn = null;
		try {
			conn = redisTemplate.getConnectionFactory().getConnection();
			return conn.sCard(key.getBytes());
		} catch (Exception e) {
			logger.error(Consts.LOG_ERROR_REDIS_CACHE, e);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return null;
	}

	/**
	 * 判断 value 元素是否是集合 key 的成员
	 * 
	 * @author Johnson.Jia
	 * @date 2017年10月13日 下午3:52:36
	 * @param key
	 * @param value
	 * @return
	 */
	public Boolean sIsMember(String key, byte[] value) {
		RedisConnection conn = null;
		try {
			conn = redisTemplate.getConnectionFactory().getConnection();
			return conn.sIsMember(key.getBytes(), value);
		} catch (Exception e) {
			logger.error(Consts.LOG_ERROR_REDIS_CACHE, e);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return false;
	}

	/**
	 * Redis Hset 命令用于为哈希表中的字段赋值 。 如果哈希表不存在，一个新的哈希表被创建并进行 HSET 操作。
	 * 如果字段已经存在于哈希表中，旧值将被覆盖。
	 * 
	 * @author Johnson.Jia
	 * @date 2017年10月31日 下午9:37:23
	 * @param key
	 *            redis 中Map 的key
	 * @param field
	 *            设置元素的key
	 * @param value
	 *            元素的 value
	 * @return
	 */
	public Boolean hSet(String key, byte[] field, byte[] value) {
		RedisConnection conn = null;
		try {
			conn = redisTemplate.getConnectionFactory().getConnection();
			return conn.hSet(key.getBytes(), field, value);
		} catch (Exception e) {
			logger.error(Consts.LOG_ERROR_REDIS_CACHE, e);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return false;
	}

	/**
	 * Redis Hsetnx 命令用于为哈希表中不存在的的字段赋值 。 如果哈希表不存在，一个新的哈希表被创建并进行 HSET 操作。
	 * 如果字段已经存在于哈希表中，操作无效。 如果 key 不存在，一个新哈希表被创建并执行 HSETNX 命令。
	 * 
	 * @author Johnson.Jia
	 * @date 2017年10月31日 下午9:36:31
	 * @param key
	 *            redis 中Map 的key
	 * @param field
	 *            设置元素的key
	 * @param value
	 *            元素的 value
	 * @return
	 */
	public Boolean hSetNX(String key, byte[] field, byte[] value) {
		RedisConnection conn = null;
		try {
			conn = redisTemplate.getConnectionFactory().getConnection();
			return conn.hSetNX(key.getBytes(), field, value);
		} catch (Exception e) {
			logger.error(Consts.LOG_ERROR_REDIS_CACHE, e);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return false;
	}

	/**
	 * Redis Hdel 命令用于删除哈希表 key 中的一个或多个指定字段，不存在的字段将被忽略。
	 * 
	 * @author Johnson.Jia
	 * @date 2017年10月31日 下午9:39:24
	 * @param key
	 *            redis 中Map 的key
	 * @param fields
	 *            删除元素的key
	 * @return
	 */
	public Long hDel(String key, byte[]... fields) {
		RedisConnection conn = null;
		try {
			conn = redisTemplate.getConnectionFactory().getConnection();
			return conn.hDel(key.getBytes(), fields);
		} catch (Exception e) {
			logger.error(Consts.LOG_ERROR_REDIS_CACHE, e);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return null;
	}

	/**
	 * Redis Hgetall 命令用于返回哈希表中，所有的字段和值。 在返回值里，紧跟每个字段名(field
	 * name)之后是字段的值(value)，所以返回值的长度是哈希表大小的两倍。
	 * 
	 * @author Johnson.Jia
	 * @date 2017年10月31日 下午9:40:57
	 * @param key
	 * @return
	 */
	public Map<byte[], byte[]> hGetAll(String key) {
		RedisConnection conn = null;
		try {
			conn = redisTemplate.getConnectionFactory().getConnection();
			return conn.hGetAll(key.getBytes());
		} catch (Exception e) {
			logger.error(Consts.LOG_ERROR_REDIS_CACHE, e);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return null;
	}

	/**
	 * Redis Hget 命令用于返回哈希表中指定字段的值。
	 * 
	 * @author Johnson.Jia
	 * @date 2017年10月31日 下午9:44:35
	 * @param key
	 * @return
	 */
	public byte[] hGet(String key, byte[] field) {
		RedisConnection conn = null;
		try {
			conn = redisTemplate.getConnectionFactory().getConnection();
			return conn.hGet(key.getBytes(), field);
		} catch (Exception e) {
			logger.error(Consts.LOG_ERROR_REDIS_CACHE, e);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return null;
	}

	/**
	 * Redis Hmget 命令用于返回哈希表中，一个或多个给定字段的值。 如果指定的字段不存在于哈希表，那么返回一个 nil 值。
	 * 
	 * @author Johnson.Jia
	 * @date 2017年10月31日 下午9:46:48
	 * @param key
	 * @param fields
	 * @return
	 */
	public List<byte[]> hMGet(String key, byte[]... fields) {
		RedisConnection conn = null;
		try {
			conn = redisTemplate.getConnectionFactory().getConnection();
			return conn.hMGet(key.getBytes(), fields);
		} catch (Exception e) {
			logger.error(Consts.LOG_ERROR_REDIS_CACHE, e);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return null;
	}

	/**
	 * Redis Hvals 命令返回哈希表所有域(field)的值。
	 * 
	 * @author Johnson.Jia
	 * @date 2017年10月31日 下午9:54:57
	 * @param key
	 * @return
	 */
	public List<byte[]> hVals(String key) {
		RedisConnection conn = null;
		try {
			conn = redisTemplate.getConnectionFactory().getConnection();
			return conn.hVals(key.getBytes());
		} catch (Exception e) {
			logger.error(Consts.LOG_ERROR_REDIS_CACHE, e);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return null;
	}

}
