package ru.devmark.graphql.repository

import ru.devmark.graphql.model.Author

interface AuthorRepository {

    fun findById(id: Int): Author?

    fun findAllByIds(ids: Set<Int>): Map<Int, Author>
}