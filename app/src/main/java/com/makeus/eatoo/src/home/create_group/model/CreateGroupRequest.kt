package com.makeus.eatoo.src.home.create_group.model

data class CreateGroupRequest(
    val name : String,
    val color : Int,
    val latitude : Double,
    val longitude : Double,
    val keyword : List<Keyword>
)
