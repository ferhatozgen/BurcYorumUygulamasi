package com.ferhatozgen.burcayapp.model

data class Burc(
    val id: String,
    val ad: String,
    val tarih: String,
    val ozellik: String,
    val element: String,
    val detayliOzellik: String,
    val unluler: String
)

data class BurcYorumu(
    val burcId: String,
    val gunlukYorum: String,
    val haftalikYorum: String,
    val aylikYorum: String,
    val tarih: String
)