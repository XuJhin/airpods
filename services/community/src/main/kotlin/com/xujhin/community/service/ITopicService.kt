package com.xujhin.community.service

interface ITopicService {
    fun topicDetail(topicId: String)
    fun topicList()
    fun topicPostList()
}