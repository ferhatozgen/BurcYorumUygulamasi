package com.ferhatozgen.burcayapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ferhatozgen.burcayapp.data.GeminiHelper
import com.ferhatozgen.burcayapp.data.StorageHelper
import com.ferhatozgen.burcayapp.model.Burc
import com.ferhatozgen.burcayapp.model.BurcYorumu
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class BurcViewModel(application: Application) : AndroidViewModel(application) {
    private val geminiHelper = GeminiHelper()
    private val storageHelper = StorageHelper(application)

    // Ekranda gösterilecek durumlar
    private val _seciliBurc = MutableStateFlow<Burc?>(null)
    val seciliBurc = _seciliBurc.asStateFlow()

    private val _yorumDurumu = MutableStateFlow<String>("Yükleniyor...")
    val yorumDurumu = _yorumDurumu.asStateFlow()

    // Yükleme yapılıyor mu?
    private val _yukleniyor = MutableStateFlow(false)
    val yukleniyor = _yukleniyor.asStateFlow()

    fun burcSec(burc: Burc) {
        _seciliBurc.value = burc
        // Seçilince hemen kayıtlı veriyi kontrol et, yoksa API'den çek
        verileriGetir(burc)
    }

    fun yorumGetir(periyot: String) {
        val burc = _seciliBurc.value ?: return

        viewModelScope.launch {
            _yukleniyor.value = true
            _yorumDurumu.value = "Yıldızlar inceleniyor..."

            val yeniYorum = geminiHelper.yorumGetir(burc.ad, periyot)
            _yorumDurumu.value = yeniYorum
            _yukleniyor.value = false
        }
    }

    private val _rastgeleBilgi = MutableStateFlow<String>("")
    val rastgeleBilgi = _rastgeleBilgi.asStateFlow()

    fun rastgeleBilgiGetir() {
        viewModelScope.launch {
            _yukleniyor.value = true
            _rastgeleBilgi.value = "" // Önce temizle
            // Gemini'ye kısa ve ilginç bir bilgi soruyoruz
            val bilgi = geminiHelper.yorumGetir("Astroloji", "hakkında şaşırtıcı, ilginç ve kısa bir bilgi")
            _rastgeleBilgi.value = bilgi
            _yukleniyor.value = false
        }
    }
    private fun verileriGetir(burc: Burc) {
        // Önce hafızadan bak
        val kayitliVeri = storageHelper.oku(burc.id)
        val bugun = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())

        if (kayitliVeri != null && kayitliVeri.tarih == bugun) {
            // Bugün zaten veri çekmişiz, onu göster
            _yorumDurumu.value = "Günlük: " + kayitliVeri.gunlukYorum
        } else {
            // Veri yok veya eski, varsayılan olarak günlük yorum iste
            yorumGetir("Günlük")
        }
    }
}