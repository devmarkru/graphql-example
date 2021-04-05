package ru.devmark.graphql.repository

import ru.devmark.graphql.model.Book

interface BookRepository {

    fun findAll(limit: Int): List<Book>
}