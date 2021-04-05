package ru.devmark.graphql.resolver

import graphql.kickstart.execution.context.GraphQLContext
import graphql.kickstart.tools.GraphQLResolver
import graphql.schema.DataFetchingEnvironment
import org.dataloader.DataLoaderRegistry
import org.springframework.stereotype.Component
import ru.devmark.graphql.model.Author
import ru.devmark.graphql.model.Book
import ru.devmark.graphql.repository.AuthorRepository
import java.util.concurrent.CompletableFuture


@Component
class BookResolver(
    private val authorRepository: AuthorRepository
) : GraphQLResolver<Book> {

    fun author(book: Book): Author =
        authorRepository.findById(book.authorId)
            ?: throw RuntimeException("Author with id=${book.authorId} not found")

    fun author(book: Book, dfe: DataFetchingEnvironment): CompletableFuture<Author> {
        val registry: DataLoaderRegistry = (dfe.getContext() as GraphQLContext).dataLoaderRegistry
        val authorLoader = registry.getDataLoader<Int, Author>("authorLoader")
        return authorLoader?.let { authorLoader.load(book.authorId) }
            ?: throw RuntimeException("Author data loader not found")
    }
}