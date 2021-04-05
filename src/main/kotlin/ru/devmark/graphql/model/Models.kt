package ru.devmark.graphql.model

data class Author(
    val id: Int,
    val name: String,
    val surname: String
)

data class Book(
    val id: Int,
    val title: String,
    val authorId: Int
)