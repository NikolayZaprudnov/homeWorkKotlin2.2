@file:Suppress("DEPRECATED_IDENTITY_EQUALS")

import java.time.Instant
import java.util.*

fun main() {
    val post = Post(
        0, authorId = 0, "Первый пост на сегодня", Post.comment(
            0,
            "Нет комментариев"
        ), Post.likes(0, true), time = Instant.now()
    )
    WallService.addLikes(post)
    WallService.addComment(post)
    WallService.add(post)
    println(post)
    val post2 = Post(
        0, 1, "New post", Post.comment(0, "no"),
        Post.likes(0, true), Instant.now()
    )
    WallService.add(post2)
    val post3 = Post(
        0, 1, "New post2", Post.comment(0, "no"),
        Post.likes(0, true), Instant.now()
    )
    WallService.add(post3)
    WallService.update(1)
    println(Arrays.toString(WallService.allPosts))

}

data class Post(
    var id: Int,
    val authorId: Int,
    var text: String,
    val Comment: comment,
    val Likes: likes,
    val time: Instant,
) {
    data class comment(var authorCommentsId: Int, var textComment: String)
    data class likes(var likesAmount: Int = 0, var canLikes: Boolean = true)
}

object WallService {
    var allPosts = emptyArray<Post>()
    fun add(post: Post): Post {
        post.id = allPosts.lastIndex + 2
        allPosts += post
        return allPosts.last()
    }

    fun update(id: Int): Boolean {
        val updateId = id
        var result: Boolean
        for (post in allPosts) if (post.id === updateId) {
            post.text = "post updated"
           result = true
        } else { result = false}
       return result
    }

    fun addComment(post: Post) {
        post.Comment.authorCommentsId += 1
        post.Comment.textComment = "Comment"
    }

    fun addLikes(post: Post) {
        if (post.Likes.canLikes === true) {
            post.Likes.likesAmount += 1
            post.Likes.canLikes = false
        } else {
        }
    }
}