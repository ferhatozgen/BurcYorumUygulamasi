package com.ferhatozgen.burcayapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ferhatozgen.burcayapp.model.Burc
import com.ferhatozgen.burcayapp.viewmodel.BurcViewModel
import com.ferhatozgen.burcayapp.ui.theme.AstroInfoScreen
import com.ferhatozgen.burcayapp.ui.theme.EncyclopediaDetailScreen
import com.ferhatozgen.burcayapp.ui.theme.MainMenuScreen
import com.ferhatozgen.burcayapp.ui.theme.PredictionDetailScreen
import com.ferhatozgen.burcayapp.ui.theme.SplashScreen
import com.ferhatozgen.burcayapp.ui.theme.ZodiacListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val burcListesi = listOf(
            Burc(
                "koc", "Koç", "21 Mart - 20 Nisan", "Cesur ve enerjik", "Ateş",
                "Zodyak'ın ilk burcu olan Koçlar, her zaman en önde olmayı severler. Doğuştan lider ruhlu, enerjik ve rekabetçidirler. Hızlı karar verirler ve harekete geçmekten asla korkmazlar. Bazen sabırsız ve fevri olabilirler, ancak dürüstlükleri ve çocuksu heyecanları onları karşı konulamaz kılar. Onlar için hayat, kazanılması gereken bir macera gibidir.",
                "Lady Gaga, Robert Downey Jr., Cem Karaca, Okan Bayülgen, Sakıp Sabancı, Vincent van Gogh"
            ),
            Burc(
                "boga", "Boğa", "21 Nisan - 20 Mayıs", "Güvenilir ve sabırlı", "Toprak",
                "Boğa burçları, ayakları yere sağlam basan, güvenilir ve huzur arayan karakterlerdir. Lükse, lezzetli yemeklere ve konfora düşkünlükleriyle bilinirler. Bir şeye karar verdiklerinde onları yollarından döndürmek neredeyse imkansızdır; bu inatçılıkları aslında kararlılıklarının bir göstergesidir. Sadakat onlar için her şeyden önemlidir.",
                "Adele, George Clooney, Halit Ergenç, Nükhet Duru, Demet Akalın, Karl Marx"
            ),
            Burc(
                "ikizler", "İkizler", "21 Mayıs - 21 Haziran", "Zeki ve uyumlu", "Hava",
                "Merkür tarafından yönetilen İkizler, Zodyak'ın en hızlı düşünen ve en iyi iletişim kuran burcudur. Çift karakterli olarak bilinirler; bir anları diğerine uymayabilir ancak bu onların uyum yeteneğinden kaynaklanır. Meraklı yapıları sayesinde her konuda bilgi sahibidirler. Sıkılgan olabilirler ama girdikleri ortamı anında neşelendirirler.",
                "Angelina Jolie, Kanye West, Kenan Doğulu, Acun Ilıcalı, Nil Karaibrahimgil, Marilyn Monroe"
            ),
            Burc(
                "yengec", "Yengeç", "22 Haziran - 22 Temmuz", "Duygusal ve koruyucu", "Su",
                "Yengeçler, sert kabuklarının altında yumuşacık bir kalp taşırlar. Evlerine ve ailelerine aşırı düşkündürler; geçmişe ve anılara sıkı sıkıya bağlıdırlar. Sezgileri o kadar kuvvetlidir ki, karşılarındaki insanın ne hissettiğini konuşmadan anlayabilirler. Bazen alıngan olabilirler ancak sevdiklerini korumak için dünyayı karşılarına alırlar.",
                "Elon Musk, Tom Cruise, Sezen Aksu, Türkan Şoray, Yıldız Tilbe, Prenses Diana"
            ),
            Burc(
                "aslan", "Aslan", "23 Temmuz - 22 Ağustos", "Cömert ve yaratıcı", "Ateş",
                "Ormanın kralı Aslanlar, yönetmek ve parlamak için doğmuşlardır. Özgüvenleri, karizmaları ve sıcakkanlılıklarıyla girdikleri her ortamda dikkat çekerler. Cömertlikleri dillere destandır; sevdiklerini şımartmayı severler. Eleştirilmekten pek hoşlanmazlar ve gururları kırıldığında sertleşebilirler. Ancak kalpleri altın gibidir.",
                "Jennifer Lopez, Barack Obama, Meryem Uzerli, Sibel Can, Murat Dalkılıç, Napoleon Bonaparte"
            ),
            Burc(
                "basak", "Başak", "23 Ağustos - 22 Eylül", "Analitik ve çalışkan", "Toprak",
                "Detayların efendisi Başaklar, mükemmeliyetçilikleriyle tanınırlar. Analitik zekaları sayesinde başkalarının göremediği en ufak hataları bile fark ederler. Düzenli, tertipli ve hijyenik olmak onlar için bir yaşam biçimidir. Çalışkan ve üretkendirler, ancak bazen kendilerini ve çevrelerini fazla eleştirebilirler.",
                "Beyonce, Keanu Reeves, Tarkan, Cüneyt Arkın, Gülben Ergen, Michael Jackson"
            ),
            Burc(
                "terazi", "Terazi", "23 Eylül - 23 Ekim", "Diplomatik ve adil", "Hava",
                "Zodyak'ın estetik ve denge uzmanlarıdır. Hayatlarında her zaman uyum, adalet ve güzellik ararlar. Çatışmadan kaçınırlar ve mükemmel birer arabulucudurlar. Yalnız kalmaktan hiç hoşlanmazlar, sosyal ilişkiler onlar için hayati önem taşır. Karar vermekte zorlanabilirler çünkü her olayın iki yüzünü de aynı anda görürler.",
                "Kim Kardashian, Will Smith, Ebru Gündeş, Tarkan, Hülya Avşar, Gandhi"
            ),
            Burc(
                "akrep", "Akrep", "24 Ekim - 22 Kasım", "Tutkulu ve kararlı", "Su",
                "Gizemli, tutkulu ve derin... Akrepler, hislerini uçlarda yaşarlar. Ya hep ya hiç mantığıyla hareket ederler. Sezgileri korkutucu derecede güçlüdür, onlardan bir şey saklamak neredeyse imkansızdır. Kin tutma özellikleri meşhurdur ancak dostlarına karşı ölümüne sadıktırlar. Küllerinden yeniden doğma yetenekleri vardır.",
                "Bill Gates, Leonardo DiCaprio, Kıvanç Tatlıtuğ, Teoman, Şevval Sam, Pablo Picasso"
            ),
            Burc(
                "yay", "Yay", "23 Kasım - 21 Aralık", "İdealist ve esprili", "Ateş",
                "Özgürlüklerine en düşkün burçtur. Yaylar gezgin ruhludur; yeni kültürler, yeni felsefeler ve yeni yerler keşfetmek onlar için nefes almak gibidir. İyimserlikleri bazen gerçekçilikten uzaklaşmalarına neden olsa da şansları hep yaver gider. Patavatsızlık derecesinde dürüst olabilirler ama kötü niyet taşımazlar.",
                "Brad Pitt, Taylor Swift, Sertab Erener, Haluk Levent, Mehmet Ali Birand, Walt Disney"
            ),
            Burc(
                "oglak", "Oğlak", "22 Aralık - 20 Ocak", "Disiplinli ve sorumlu", "Toprak",
                "Zodyak'ın en çalışkan ve disiplinli üyeleridir. Hedeflerine ulaşmak için sabırla ve inatla tırmanırlar. Ciddiyetleri ve sorumluluk bilinçleri, genç yaşta bile olgun görünmelerini sağlar. Duygularını göstermekte zorlanabilirler ve işkolik olmaya meyillidirler. Başarı ve statü onlar için güven demektir.",
                "Elvis Presley, Kate Middleton, Burak Özçivit, Mustafa Sandal, Çağla Şıkel, Isaac Newton"
            ),
            Burc(
                "kova", "Kova", "21 Ocak - 18 Şubat", "Özgün ve bağımsız", "Hava",
                "Çağın ötesinde yaşayan Kovalar, sıra dışı fikirleri ve isyankar ruhlarıyla bilinirler. Toplumsal olaylara duyarlıdırlar ve dünyayı değiştirmek isterler. Duygusal bağ kurmakta zorlanabilir, mesafeli görünebilirler ancak dostlukları çok sağlamdır. Teknolojiye ve bilime doğal bir yetenekleri vardır.",
                "Shakira, Cristiano Ronaldo, Ajda Pekkan, Candan Erçetin, İbrahim Çelikkol, Thomas Edison"
            ),
            Burc(
                "balik", "Balık", "19 Şubat - 20 Mart", "Şefkatli ve artistik", "Su",
                "Hayal dünyasında yaşayan Balıklar, Zodyak'ın en duygusal ve merhametli burcudur. Sınırsız bir hayal güçleri ve sanatsal yetenekleri vardır. Başkalarının acılarını kendi acıları gibi hissederler. Gerçek dünyadan kaçma eğilimleri olabilir. Sezgileri rehberleridir ve ruhsal konulara derin bir ilgi duyarlar.",
                "Rihanna, Justin Bieber, Murat Boz, Beyazıt Öztürk, Beren Saat, Albert Einstein"
            )
        )

        setContent {
            val navController = rememberNavController()
            val viewModel: BurcViewModel = viewModel()

            NavHost(navController = navController, startDestination = "splash_screen") {
                // 1. Açılış Ekranı
                composable("splash_screen") {
                    SplashScreen(navController = navController)
                }
                // 2. Ana Menü
                composable("main_menu") {
                    MainMenuScreen(navController = navController)
                }

                // --- Rota 1: Burç Yorumları ---
                composable("prediction_list") {
                    // Burç seçme listesi (Yorum için)
                    ZodiacListScreen(navController, viewModel, burcListesi, isEncyclopedia = false)
                }
                composable("prediction_detail") {
                    PredictionDetailScreen(navController, viewModel)
                }

                // --- Rota 2: Astro Bilgi ---
                composable("astro_info") {
                    AstroInfoScreen(navController, viewModel)
                }

                // --- Rota 3: Ansiklopedi ---
                composable("encyclopedia_list") {
                    // Burç seçme listesi (Bilgi için - Aynı ekranı kullanıyoruz ama modu farklı)
                    ZodiacListScreen(navController, viewModel, burcListesi, isEncyclopedia = true)
                }
                composable("encyclopedia_detail") {
                    EncyclopediaDetailScreen(navController, viewModel)
                }
            }
        }
    }
}