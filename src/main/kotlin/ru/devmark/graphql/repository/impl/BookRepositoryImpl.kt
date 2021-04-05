package ru.devmark.graphql.repository.impl

import org.springframework.stereotype.Repository
import ru.devmark.graphql.model.Book
import ru.devmark.graphql.repository.BookRepository

@Repository
class BookRepositoryImpl : BookRepository {

    override fun findAll(limit: Int): List<Book> =
        BOOKS.take(limit)

    // в реальном проекте эти данные хранятся в БД
    private companion object {
        val BOOKS = listOf(
            Book(
                id = 1,
                title = "Ромео и Джульетта",
                authorId = 1
            ),
            Book(
                id = 2,
                title = "Гамлет",
                authorId = 1
            ),
            Book(
                id = 3,
                title = "Отелло",
                authorId = 1
            ),
            Book(
                id = 4,
                title = "Евгений Онегин",
                authorId = 2
            )
        )
    }
}