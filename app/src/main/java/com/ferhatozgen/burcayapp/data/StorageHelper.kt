package com.ferhatozgen.burcayapp.data

import android.content.Context
import com.ferhatozgen.burcayapp.model.BurcYorumu
import com.google.gson.Gson
import java.io.File

class StorageHelper(private val context: Context) {
    private val gson = Gson()
    private val fileName = "burc_yorumlari.json"

    fun kaydet(yorum: BurcYorumu) {
        val json = gson.toJson(yorum)
        // Her burç için ayrı dosya veya tek dosya olabilir. Basit olması için üzerine yazıyoruz.
        // Gelişmiş versiyonda List<BurcYorumu> tutulur.
        val file = File(context.filesDir, "${yorum.burcId}_$fileName")
        file.writeText(json)
    }

    fun oku(burcId: String): BurcYorumu? {
        val file = File(context.filesDir, "${burcId}_$fileName")
        if (!file.exists()) return null
        return try {
            gson.fromJson(file.readText(), BurcYorumu::class.java)
        } catch (e: Exception) {
            null
        }
    }
}