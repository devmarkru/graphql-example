package ru.devmark.graphql.repository.impl

import org.springframework.stereotype.Repository
import ru.devmark.graphql.model.Author
import ru.devmark.graphql.repository.AuthorRepository

@Repository
class AuthorRepositoryImpl : AuthorRepository {

    override fun findById(id: Int): Author? =
        AUTHORS[id]

    override fun findAllByIds(ids: Set<Int>): Map<Int, Author> =
        AUTHORS.filterKeys { id -> id in ids }

    // в реальном проекте эти данные хранятся в БД
    private companion object {
        val AUTHORS = mapOf(
            1 to Author(
                id = 1,
                name = "Уильям",
                surname = "Шекспир"
            ),
            2 to Author(
                id = 2,
                name = "Александр",
                surname = "Пушкин"
            )
        )
    }
}