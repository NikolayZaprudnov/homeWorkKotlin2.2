@file:Suppress("DEPRECATED_IDENTITY_EQUALS")

import java.time.Duration
import java.time.Instant
import java.util.*
import kotlin.concurrent.timer

fun main() {
    val post = Post(
        0, authorId = 0, "Первый пост на сегодня", emptyArray(), Post.likes(0, true), null, time = Instant.now(), null
    )
    WallService.addLikes(post)
    WallService.add(post)
    WallService.addComment(1, Post.comment(15 , "Новый комментарий0"))
    println(post)
    val post2 = Post(
        0,
        1,
        "New post",
        emptyArray(),
        Post.likes(0, true),
        arrayOf(
            Post.ImageAttachment(
                Post.Image(
                    12,
                    "Stich",
                    "https://avatarko.ru/img/kartinka/33/multfilm_lyagushka_32117.jpg",
                    "Стич с лягушкой"
                )
            )
        ),
        time = Instant.now(),
        null
    )
    WallService.add(post2)
    val post3 = Post(
        0, 1, "New post2", emptyArray(),
        Post.likes(0, true), null, time = Instant.now(), null
    )
    WallService.add(post3)
    WallService.update(1)
    WallService.add(WallService.repost(post2))
    WallService.addComment(2, Post.comment(15, "Новый комментарий"))
    WallService.addComment(2, Post.comment(15, "Новый комментарий 2"))
    println(Arrays.toString(WallService.allPosts))

}

data class Post(
    var id: Int,
    val authorId: Int,
    var text: String,
    var Comment: Array<comment> = emptyArray(),
    val Likes: likes,
    val attachment: Array<Attachment>?,
    val time: Instant,
    val original: Post?,

    ) {
    data class comment(var authorCommentsId: Int, var textComment: String)
    data class likes(var likesAmount: Int = 0, var canLikes: Boolean = true)
    open class Attachment(type: String)
    class Video(val id: Int, val name: String, val duration: Duration, val title: String)
    class VideoAttachment(val video: Video) : Attachment("video")
    class Audio(val id: Int, val name: String, val duration: Duration, val title: String)
    class AudioAttachment(val audio: Audio) : Attachment("audio")
    class Image(val id: Int, val name: String, val URL: String, val title: String)
    class ImageAttachment(val image: Image) : Attachment("image")
    class File(val id: Int, val name: String, val formate: String, val title: String)
    class FileAttachment(val file: File) : Attachment("file")
    class Sticker(val id: Int, val imageURL: String)
    class StickerAttachment(val sticker: Sticker) : Attachment("sticker")

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
        var result = false
        for (post in allPosts) if (post.id === updateId) {
            post.text = "post updated"
            result = true
        }
        return result
    }

    fun addComment(id: Int, newComment: Post.comment) {
        findById(id)?.Comment = findById(id)?.Comment!! + newComment
    }

    fun addLikes(post: Post) {
        if (post.Likes.canLikes === true) {
            post.Likes.likesAmount += 1
            post.Likes.canLikes = false
        } else {
        }
    }
    fun findById(id: Int): Post?{
        var result = false
        for (post in allPosts){
            if(post.id === id){
                result = true
                return post
            }
        }
        if (result===false){ throw PostNotFoundException("Не найден пост с номером ID $id")}
        return null

    }

    fun repost(post: Post): Post {
        if (post.original === null) {
            return post.copy(
                post.id,
                post.authorId,
                post.text,
                post.Comment,
                Post.likes(0, true),
                time = Instant.now(),
                original = post
            )
        } else return post
    }
}
 class PostNotFoundException (message: String): RuntimeException(message)