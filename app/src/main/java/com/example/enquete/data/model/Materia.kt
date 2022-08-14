package com.example.enquete.data.model

import java.io.Serializable

data class Materia(
    var id: String? = "",
    var nome: String? = "",
    var voto: Int? = 0,
): Serializable