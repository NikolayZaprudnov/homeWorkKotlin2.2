import org.junit.Test

import org.junit.Assert.*
import java.time.Instant

class MainKtTest {

    @Test
    fun add() {
        val post = Post(
            0,
            authorId = 0,
            "Первый пост на сегодня",
            emptyArray(),
            Post.likes(0, true),
            null,
            time = Instant.now(),
            null
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
    fun addComments() {
        var post = Post(
            1,
            authorId = 0,
            "Первый пост на сегодня",
            emptyArray(),
            Post.likes(0, true),
            null,
            time = Instant.now(),
            null
        )
        WallService.add(post)
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
        WallService.addComment(2, Post.comment(15, "Новый комментарий"))
        assert(post2.Comment.isNotEmpty())
    }

    @Test(expected = PostNotFoundException::class)
    fun shouldThrow() {
        var post = Post(
            1,
            authorId = 0,
            "Первый пост на сегодня",
            emptyArray(),
            Post.likes(0, true),
            null,
            time = Instant.now(),
            null
        )
        WallService.add(post)
        WallService.findById(198)
    }
}