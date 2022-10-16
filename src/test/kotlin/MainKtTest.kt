import org.junit.Test

import org.junit.Assert.*
import java.time.Instant

class MainKtTest {

    @Test
    fun add() {
        val post = Post(
            0, authorId = 0, "Первый пост на сегодня",  null , Post.likes(0, true), null, time = Instant.now(), null
        )
        val result = WallService.add(post).id
        assertEquals(1, result)
    }

    @Test
    fun updateTrue() {
        val id = 1
        assertTrue(WallService.update(id))
    }
    @Test
    fun updateFalse() {
        val id = 195
        assertFalse(WallService.update(id))
    }
    @Test
    fun addComments(){
        var post = Post(
            1, authorId = 0, "Первый пост на сегодня",  null , Post.likes(0, true), null, time = Instant.now(), null
        )
        WallService.add(post)
        WallService.addComment(1, Post.comment(15, "Новый комментарий"))
        assertEquals(WallService.allPosts.last().Comment?.last(), Post.comment(15, "Новый комментарий"))
    }

        @Test(expected = PostNotFoundException::class)
        fun shouldThrow() {
            var post = Post(
                1, authorId = 0, "Первый пост на сегодня",  null , Post.likes(0, true), null, time = Instant.now(), null
            )
            WallService.add(post)
            WallService.findById(198)
        }
}