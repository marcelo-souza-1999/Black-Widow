package com.marcelo.blackwindow.data.response.genre

import com.marcelo.blackwindow.data.model.Genery

data class GenreResultResponse(
    val id: Int,
    val name: String
)
{
    fun getGenre() = Genery(
        id = this.id,
        name = this.name
    )
}
