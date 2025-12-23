package com.ferhatozgen.burcayapp.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ferhatozgen.burcayapp.model.Burc
import com.ferhatozgen.burcayapp.viewmodel.BurcViewModel
import kotlinx.coroutines.delay

val DarkBlue = Color(0xFF0D1B2A)
val GoldColor = Color(0xFFFFD700)
val PurpleMagic = Color(0xFF4B0082)
val TextWhite = Color(0xFFE0E1DD)


@Composable
fun ParchmentCard(
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(12.dp))
            .background(ParchmentColor, RoundedCornerShape(12.dp))
            .border(1.dp, GoldColor.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
            .padding(20.dp)
    ) {
        content()
    }
}

// --- ORTAK BİLEŞEN: ANA SAYFAYA DÖN BUTONU ---
@Composable
fun HomeButton(navController: NavController) {
    IconButton(
        onClick = {
            navController.navigate("main_menu") {
                popUpTo("main_menu") { inclusive = true }
            }
        },
        modifier = Modifier
            .background(PurpleMagic.copy(alpha = 0.3f), RoundedCornerShape(50))
    ) {
        Icon(Icons.Default.Home, contentDescription = "Ana Sayfa", tint = GoldColor)
    }
}

// --- AÇILIŞ EKRANI (SPLASH) ---
@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(key1 = true) {
        delay(2000)
        navController.navigate("main_menu") {
            popUpTo("splash_screen") { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(DarkBlue, PurpleMagic))),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Default.Star, "Logo", tint = GoldColor, modifier = Modifier.size(100.dp))
            Spacer(modifier = Modifier.height(16.dp))
            Text("BurcAyApp", color = TextWhite, fontSize = 32.sp, fontWeight = FontWeight.Bold)
        }
    }
}

// --- ANA MENÜ ---
@Composable
fun MainMenuScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()), // <--- EKLEMEN GEREKEN SİHİRLİ SATIR BU
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(Icons.Default.Star, contentDescription = null, tint = GoldColor, modifier = Modifier.size(80.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "BurcAyApp'e\nHoş Geldiniz",
            color = TextWhite,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(48.dp))

        // 1. Buton: Burç Yorumları
        MenuButton(text = "Burç Yorumları", icon = Icons.Default.Star) {
            navController.navigate("prediction_list")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 2. Buton: Günün Astro Bilgisi
        MenuButton(text = "Günün Astro Bilgisi", icon = Icons.Default.AutoAwesome) {
            navController.navigate("astro_info")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 3. Buton: Burç Ansiklopedisi
        MenuButton(text = "Burç Ansiklopedisi", icon = Icons.Default.Book) {
            navController.navigate("encyclopedia_list")
        }
    }
}

@Composable
fun MenuButton(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        colors = ButtonDefaults.buttonColors(containerColor = PurpleMagic),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = GoldColor)
            Spacer(modifier = Modifier.width(12.dp))
            Text(text, fontSize = 18.sp, color = TextWhite)
        }
    }
}

// --- BURÇ LİSTESİ  ---
@Composable
fun ZodiacListScreen(navController: NavController, viewModel: BurcViewModel, burcListesi: List<Burc>, isEncyclopedia: Boolean = false) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue)
            .padding(16.dp)
    ) {
        // Üst Bar: Geri Dön ve Ana Sayfa
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Geri", tint = TextWhite)
            }
            Text(if(isEncyclopedia) "Burç Ansiklopedisi" else "Burç Seçiniz", color = TextWhite, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            HomeButton(navController)
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 150.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(burcListesi) { burc ->
                BurcKarti(burc = burc) {
                    viewModel.burcSec(burc)
                    if (isEncyclopedia) {
                        navController.navigate("encyclopedia_detail")
                    } else {
                        navController.navigate("prediction_detail")
                    }
                }
            }
        }
    }
}

@Composable
fun BurcKarti(burc: Burc, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1B263B))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = burc.ad, color = GoldColor, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(text = burc.tarih, color = Color.Gray, fontSize = 12.sp)
        }
    }
}

// --- DETAY EKRANI (YORUMLAR) ---
@Composable
fun PredictionDetailScreen(navController: NavController, viewModel: BurcViewModel) {
    val seciliBurc by viewModel.seciliBurc.collectAsState()
    val yorumMetni by viewModel.yorumDurumu.collectAsState()
    val yukleniyor by viewModel.yukleniyor.collectAsState()

    if (seciliBurc == null) return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Geri", tint = TextWhite)
            }
            HomeButton(navController)
        }

        Text(
            text = seciliBurc!!.ad + " Yorumu",
            fontSize = 28.sp,
            color = GoldColor,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(vertical = 16.dp)
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            val buttonColors = ButtonDefaults.buttonColors(containerColor = PurpleMagic)
            Button(onClick = { viewModel.yorumGetir("Günlük") }, colors = buttonColors) { Text("Günlük") }
            Button(onClick = { viewModel.yorumGetir("Haftalık") }, colors = buttonColors) { Text("Haftalık") }
            Button(onClick = { viewModel.yorumGetir("Aylık") }, colors = buttonColors) { Text("Aylık") }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // PARŞÖMEN GÖRÜNÜMÜ
        ParchmentCard {
            if (yukleniyor) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = InkColor)
                }
            } else {
                Text(
                    text = yorumMetni,
                    color = InkColor,
                    fontSize = 18.sp,
                    lineHeight = 26.sp,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                )
            }
        }
    }
}

// --- RASTGELE ASTRO BİLGİ EKRANI ---
@Composable
fun AstroInfoScreen(navController: NavController, viewModel: BurcViewModel) {
    // Sayfa ilk açıldığında otomatik bilgi çeksin
    LaunchedEffect(Unit) {
        viewModel.rastgeleBilgiGetir()
    }

    val bilgi by viewModel.rastgeleBilgi.collectAsState()
    val yukleniyor by viewModel.yukleniyor.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()), // <--- BURAYA DA EKLE (Artık aşağı kaydırabilirsin)
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Geri", tint = TextWhite)
            }
            HomeButton(navController)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text("Günün Astro Bilgisi", color = GoldColor, fontSize = 26.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(32.dp))

        ParchmentCard {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                if (yukleniyor) {
                    CircularProgressIndicator(color = InkColor)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Yıldızlar taranıyor...", color = InkColor)
                } else {
                    Text(
                        text = bilgi.ifEmpty { "Bilgi alınamadı." },
                        color = InkColor,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 28.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.rastgeleBilgiGetir() },
            colors = ButtonDefaults.buttonColors(containerColor = PurpleMagic)
        ) {
            Text("Yeni Bilgi Getir")
        }
    }
}

// --- ANSİKLOPEDİ DETAY EKRANI ---
@Composable
fun EncyclopediaDetailScreen(navController: NavController, viewModel: BurcViewModel) {
    val seciliBurc by viewModel.seciliBurc.collectAsState()

    if (seciliBurc == null) return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Geri", tint = TextWhite)
            }
            HomeButton(navController)
        }

        Text(
            text = "${seciliBurc!!.ad} Özellikleri",
            fontSize = 28.sp,
            color = GoldColor,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(vertical = 16.dp)
        )

        // Genel Özellikler Parşömeni
        Text("Genel Özellikler", color = TextWhite, fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
        ParchmentCard {
            Text(text = seciliBurc!!.detayliOzellik, color = InkColor, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Ünlüler Parşömeni
        Text("Bu Burca Sahip Ünlüler", color = TextWhite, fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
        ParchmentCard {
            Text(text = seciliBurc!!.unluler, color = InkColor, fontSize = 16.sp)
        }
    }
}