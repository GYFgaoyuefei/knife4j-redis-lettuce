//package com.github.it235.knife4j.redis.util;
//
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.github.it235.manager.Knife4jRedisManager;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.Collection;
//import java.util.List;
//
///**
// * @description: List集合相关操作
// * @author: jianjun.ren
// * @date: Created in 2020/9/27 11:50
// */
//@Component
//public class RedisListUtil extends RedisBaseUtil {
//
//    @Autowired
//    private Knife4jRedisManager knife4jRedisManager;
//
//    /**
//     * 在list集合最前面插入数据
//     * @param key  需要操作的key
//     * @param values 待添加的值
//     * @return
//     */
//    public Long insert(String key, Object ... values) {
//        return insert(defaultDB ,key , values);
//    }
//    public Long insert(int dbIndex ,String key, Object ... values) {
//        return knife4jRedisManager.redisTemplate(dbIndex).opsForList().leftPushAll(key ,values);
//
//    }
//    public Long insert(String key, Collection<?>... values) {
//        return insert(defaultDB , key ,values);
//    }
//    public Long insert(int dbIndex ,String key, Collection<?>... values) {
//        return knife4jRedisManager.redisTemplate(dbIndex).opsForList().leftPushAll(key ,values);
//    }
//
//    /**
//     * 在list集合最后面追加数据
//     * @param key  需要操作的key
//     * @param values 待添加的值
//     * @return
//     */
//    public Long append(String key, Object... values) {
//        return append(defaultDB , key , values);
//    }
//    public Long append(int dbIndex ,String key, Object... values) {
//        return knife4jRedisManager.redisTemplate(dbIndex).opsForList().rightPushAll(key, values);
//    }
//
//    /**
//     * 在指定索引处添加数据，如果存在则为替换
//     * @param key 需要操作的key
//     * @param index 指定索引，该索引从0开始
//     * @param value 待添加的值
//     */
//    public void set(String key, long index, Object value) {
//        set(defaultDB , key ,index,value);
//    }
//    public void set(int dbIndex ,String key, long index, Object value) {
//        knife4jRedisManager.redisTemplate(dbIndex).opsForList().set(key , index ,value);
//    }
//
//
//    /**
//     * 取出某个范围的集合数据
//     * @param key 需要操作的key
//     * @param start 集合起点，索引从0开始
//     * @param end 集合终点，若为-1则表示取出所有
//     * @return 返回所有对象
//     */
//    public <T> List<T> getList(String key, long start, long end , Class<T> t) {
//        return getList(defaultDB , key , start , end , t);
//    }
//    public <T> List<T> getList(int dbIndex ,String key, long start, long end , Class<T> t) {
//        //此处返回的真实类型是JSONArray
//        List<Object> list = knife4jRedisManager.redisTemplate(dbIndex).opsForList().range(key, start, end);
//        List<T> ts = JSONArray.parseArray(String.valueOf(list), t);
//        return ts;
//    }
//
//    /**
//     * 获取redis集合类型所有元素
//     * @param key
//     * @param t
//     * @param <T>
//     * @return
//     */
//    public <T> List<T> getListAll(String key , Class<T> t) {
//        return getListAll(defaultDB , key , t);
//    }
//    public <T> List<T> getListAll(int dbIndex ,String key , Class<T> t) {
//        List<Object> list = knife4jRedisManager.redisTemplate(dbIndex).opsForList().range(key, 0, -1);
//        List<T> ts = JSONArray.parseArray(JSONObject.toJSONString(list), t);
//        return ts;
//    }
//
//    /**
//     * 返回list集合的大小
//     * @param key 需要操作的key
//     * @return
//     */
//    public Long size(String key) {
//        return size(defaultDB , key);
//    }
//    public Long size(int dbIndex ,String key) {
//        return knife4jRedisManager.redisTemplate(dbIndex).opsForList().size(key);
//    }
//
//    /**
//     * 返回某个索引处的数据
//     * @param key  需要操作的key
//     * @param index 索引 下标从0开始
//     * @return
//     */
//    public <T> T getByIndex(String key , long index , Class<T> clazz) {
//        return getByIndex(defaultDB , key , index , clazz);
//    }
//    public <T> T getByIndex(int dbIndex ,String key , long index , Class<T> clazz) {
//        Object obj = knife4jRedisManager.redisTemplate(dbIndex).opsForList().index(key, index);
//        return JSONObject.parseObject(String.valueOf(obj) , clazz);
//    }
//
//    /**
//     * 弹出最左边数据，弹出后集合将不存在该数据
//     * @param key
//     * @return
//     */
//    public <T> T popLeft(String key , Class<T> clazz) {
//        return  popLeft(defaultDB , key , clazz);
//    }
//    public <T> T popLeft(int dbIndex ,String key , Class<T> clazz) {
//        Object obj = knife4jRedisManager.redisTemplate(dbIndex).opsForList().leftPop(key);
//        if(obj == null){
//            return null;
//        }
//        return JSONObject.parseObject(String.valueOf(obj) , clazz);
//    }
//
//    /**
//     * 弹出最右边数据，弹出后集合将不存在该数据
//     * @param key
//     * @return
//     */
//    public <T> T  popRight(String key , Class<T> clazz) {
//        return popRight(defaultDB , key , clazz);
//    }
//    public <T> T  popRight(int dbIndex ,String key , Class<T> clazz) {
//        Object obj = knife4jRedisManager.redisTemplate(dbIndex).opsForList().rightPop(key);
//        if(obj == null){
//            return null;
//        }
//        return JSONObject.parseObject(String.valueOf(obj) , clazz);
//    }
//
//    /**
//     * 直接删除数据，该数据从某个位置开始
//     * @param key   删除的key
//     * @param count
//     *          大于0，表示删除从左往右数，值为value的第一个数据
//     *          小于0，表示删除从右往左数，值为value的第一个数据
//     *          等于0，表示删除值为value的所有数据
//     * @param value 被删除的数据
//     * @return
//     */
//    public Long remove(String key, long count, Object value) {
//        return remove(defaultDB , key , count , value);
//    }
//    public Long remove(int dbIndex ,String key, long count, Object value) {
//        return knife4jRedisManager.redisTemplate(dbIndex).opsForList().remove(key, count , value);
//    }
//}
