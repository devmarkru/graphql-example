package ru.devmark.graphql.dataloader

import graphql.kickstart.execution.context.DefaultGraphQLContext
import graphql.kickstart.execution.context.GraphQLContext
import graphql.kickstart.servlet.context.DefaultGraphQLServletContext
import graphql.kickstart.servlet.context.DefaultGraphQLWebSocketContext
import graphql.kickstart.servlet.context.GraphQLServletContextBuilder
import org.dataloader.DataLoader
import org.dataloader.DataLoaderRegistry
import org.springframework.stereotype.Component
import ru.devmark.graphql.repository.AuthorRepository
import java.util.concurrent.CompletableFuture.supplyAsync
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.websocket.Session
import javax.websocket.server.HandshakeRequest

@Component
class CustomGraphQLContextBuilder(
    private val authorRepository: AuthorRepository
) : GraphQLServletContextBuilder {

    override fun build(): GraphQLContext {
        return DefaultGraphQLContext(buildDataLoaderRegistry(), null)
    }

    override fun build(request: HttpServletRequest, response: HttpServletResponse): GraphQLContext {
        return DefaultGraphQLServletContext.createServletContext(buildDataLoaderRegistry(), null)
            .with(request)
            .with(response)
            .build()
    }

    override fun build(session: Session, request: HandshakeRequest): GraphQLContext {
        return DefaultGraphQLWebSocketContext.createWebSocketContext(buildDataLoaderRegistry(), null)
            .with(session)
            .with(request)
            .build()
    }

    private fun buildDataLoaderRegistry(): DataLoaderRegistry {
        val dataLoaderRegistry = DataLoaderRegistry()
        val authorLoader = DataLoader { authorIds: List<Int> ->
            supplyAsync {
                val authors = authorRepository.findAllByIds(authorIds.toSet())
                authorIds.map { id -> authors[id] }
            }
        }
        dataLoaderRegistry.register("authorLoader", authorLoader)
        return dataLoaderRegistry
    }
}