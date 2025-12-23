package com.ferhatozgen.burcayapp.data

import com.google.ai.client.generativeai.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel

class GeminiHelper {
    // API Key'ini buraya yazmalısın. (Güvenlik için normalde local.properties kullanılır ama ders projesi için buraya yazabilirsin)
    private val apiKey = BuildConfig.GEMINI_API_KEY
    private val generativeModel = GenerativeModel(
        modelName = "gemini-2.0-flash",
        apiKey = apiKey
    )

    // Gemini'den yorum isteyen fonksiyon
    suspend fun yorumGetir(burcAdi: String, periyot: String): String {
        val prompt = if (burcAdi == "Astroloji") {
            // GÜNCELLENMİŞ PROMPT (Daha sert kurallar):
            "Astroloji, uzay, gezegenler veya burçlar tarihi hakkında 1 adet çok şaşırtıcı, " +
                    "ilginç ve rastgele bir ansiklopedik bilgi ver. " +
                    "KURALLAR: " +
                    "1. Cevaba ASLA 'Elbette', 'İşte bir bilgi', 'Biliyor muydunuz' gibi giriş cümleleriyle başlama. " +
                    "2. Doğrudan bilgiyi anlatan cümleyle başla. " +
                    "3. [Burç Adı] gibi şablonlar kullanma. " +
                    "4. Sanki bir ansiklopediden okunuyormuş gibi ciddi ama ilgi çekici olsun."
        } else {
            // Burç yorumu kısmı aynı kalabilir
            "$burcAdi burcu için astrolojik olarak $periyot yorumu yap. " +
                    "Lütfen samimi, motive edici ve mistik bir dil kullan. " +
                    "Cevap sadece yorumu içersin, başlık veya giriş cümlesi olmasın."
        }

        return try {
            val response = generativeModel.generateContent(prompt)
            response.text?.trim() ?: "Yıldızlar şu an sessiz... Lütfen tekrar dene."
        } catch (e: Exception) {
            "Bağlantı hatası: ${e.localizedMessage}"
        }
    }
}