package com.itsv.gbp.core.cache.key;

/**
 * 缓存对象的key生成器接口。<br> 
 * 对象要存放进缓存，必须有一个对应的key。一般，这个key的值取决于对象的某些属性。<br>
 * 这儿，提供了一个方便的key生成功能，用户利用key生成类自动用对象的属性生成key。
 * 
 * @author <a href="mailto:Ace8@live.cn">admin</a>
 * @since 2006-8-9 下午01:04:59
 * @version 1.0
 */
public interface IKeyGenerator {

	/**
	 * 根据给定对象和生成参数，生成key值
	 * 
	 * @param cachingObject
	 * @return
	 */
	public Object generateKey(Object cachingObject);

	/**
	 * 根据给定对象和生成参数，生成key值
	 * 
	 * @param cachingObject 待缓存的对象
	 * @param generateConfig 生成需要的额外参数
	 * @return
	 */
	public Object generateKey(Object cachingObject, Object generateConfig);
}
