package ru.devmark.graphql.query

import graphql.kickstart.tools.GraphQLQueryResolver
import org.springframework.stereotype.Component
import ru.devmark.graphql.model.Book
import ru.devmark.graphql.repository.BookRepository

@Component
class QueryResolver(
    private val bookRepository: BookRepository
) : GraphQLQueryResolver {

    fun getBooks(limit: Int?): List<Book> =
        bookRepository.findAll(limit ?: DEFAULT_LIMIT)

    private companion object {
        const val DEFAULT_LIMIT = 10
    }
}