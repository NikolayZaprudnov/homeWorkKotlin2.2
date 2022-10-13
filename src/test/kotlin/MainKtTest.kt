import org.junit.Test

import org.junit.Assert.*
import java.time.Instant

class MainKtTest {

    @Test
    fun add() {
        val post = Post(
            0, authorId = 0, "Первый пост на сегодня", Post.comment(
                0,
                "Нет комментариев"
            ), Post.likes(0, true), null, time = Instant.now(), null
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
}