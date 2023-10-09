package com.xujhin.lib.db

import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.UpdateResult
import com.xujhin.lib.entity.BaseEntity
import com.xujhin.lib.entity.BaseResp
import com.xujhin.lib.utils.IdUtil
import jakarta.annotation.Resource
import org.bson.Document
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.FindAndModifyOptions
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Repository

open class BaseDao {
    @Autowired
    lateinit var mongoTemplate: MongoTemplate

    fun getCollectionName(clazz: Class<*>): String {
        return mongoTemplate.getCollectionName(clazz)
    }

    open fun <T : Any> save(obj: T): T {
        if (obj is BaseResp) {
            obj.id = IdUtil.genId()
        }
        return mongoTemplate.save(obj)
    }


    inline fun <reified T> delete(criteria: Criteria): DeleteResult {
        return mongoTemplate.remove(Query.query(criteria), T::class.java)
    }

    // 根据ID更新对象
    inline fun <reified T> updateOne(map: HashMap<String, Any?>): UpdateResult {
        return updateEntity(T::class.java, map, Update())
    }

    // 更新首个对象
    fun updateFirst(entityClass: Class<*>, criteria: Criteria, update: Update): UpdateResult {
        return mongoTemplate.updateFirst(Query(criteria), update, entityClass)
    }

    // 更新多个对象
    fun updateMulti(entityClass: Class<*>, criteria: Criteria, update: Update): UpdateResult {
        return mongoTemplate.updateMulti(Query(criteria), update, entityClass)
    }

    fun updateEntity(entityClass: Class<*>, map: HashMap<String, Any?>, update: Update): UpdateResult {
        // 自动设置更新时间
        map["updatedAt"] = System.currentTimeMillis()
        val doc = Document()
        mongoTemplate.converter.write(map, doc)
        // 组装更新对象，排除null值
        for (key in doc.keys) {
            val value = doc[key]
            if (value != null) {
                update[key] = value
            }
        }
        return mongoTemplate.updateFirst(
            Query(
                Criteria.where("id").`is`(map["id"].toString())
            ), update, entityClass
        )
    }


    // 更新或新增对象
    fun upsert(entityClass: Class<*>, criteria: Criteria, update: Update): UpdateResult {
        update["updatedAt"] = System.currentTimeMillis()
        update["createdAt"] = System.currentTimeMillis()
        return mongoTemplate.upsert(Query(criteria), update, entityClass)
    }

    // 更新或新增对象
    fun <T> findAndModify(entityClass: Class<T>, criteria: Criteria, update: Update): T? {
        return mongoTemplate.findAndModify(Query(criteria), update, entityClass)
    }

    // 更新或新增对象
    fun <T> findAndModify(
        entityClass: Class<T>,
        criteria: Criteria,
        update: Update,
        findAndModifyOptions: FindAndModifyOptions
    ): T? {
        return mongoTemplate.findAndModify(Query(criteria), update, findAndModifyOptions, entityClass)
    }

    // 根据ID查询对象
    fun <T> findOne(entityClass: Class<T>, id: String?): T? {
        return mongoTemplate.findOne(Query(Criteria.where("id").`is`(id)), entityClass)
    }

    // 根据条件查询对象
    inline fun <reified T> findOne(criteria: Criteria): T? {
        return mongoTemplate.findOne(Query(criteria), T::class.java)
    }


    // 查询所有对象
    fun <T> findAll(entityClass: Class<T>): List<T> {
        return mongoTemplate.findAll(entityClass)
    }

    // 查询对象(排序)
    fun <T> find(entityClass: Class<T>, criteria: Criteria, sort: Sort): List<T> {
        return mongoTemplate.find(Query(criteria).with(sort), entityClass)
    }

    // 查询对象
    fun <T> find(entityClass: Class<T>, criteria: Criteria): List<T> {
        return mongoTemplate.find(Query(criteria), entityClass)
    }

    // 查询对象
    fun <T> find(entityClass: Class<T>, query: Query): List<T> {
        return mongoTemplate.find(query, entityClass)
    }

    // 查询对象
    fun <T> find(entityClass: Class<T>, query: Query, collectionName: String): List<T> {
        return mongoTemplate.find(query, entityClass, collectionName)
    }
}