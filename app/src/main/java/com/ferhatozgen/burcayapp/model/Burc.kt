package com.ferhatozgen.burcayapp.model

data class Burc(
    val id: String,
    val ad: String,
    val tarih: String,
    val ozellik: String, // Kısa özellik (Listede görünen)
    val element: String,
    val detayliOzellik: String, // Yeni: Ansiklopedi için uzun yazı
    val unluler: String // Yeni: O burca sahip ünlüler
)

data class BurcYorumu(
    val burcId: String,
    val gunlukYorum: String,
    val haftalikYorum: String,
    val aylikYorum: String,
    val tarih: String // Hangi tarihte alındığı
)