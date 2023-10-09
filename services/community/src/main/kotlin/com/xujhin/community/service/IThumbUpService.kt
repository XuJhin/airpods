package com.xujhin.community.service

interface IThumbUpService {

    fun thumbUpComment(commentID: String)
    fun thumbDownComment(commentID: String)

    fun thumbUpPost(postID: String)
    fun thumbDownPost(postID: String)

    fun favorListOfComment()
    fun favorListOfPost()
}