package com.kyz205.nottakip;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Konsol menüsünün uçtan uca testleri. Klavye girdisi yerine hazır metin
 * verilir, ekrana yazılanlar bellekte toplanıp kontrol edilir.
 */
class MainTest {

    @TempDir
    Path geciciKlasor;

    /** Programı verilen girdiyle çalıştırır ve ekrana yazılan her şeyi döndürür. */
    private String calistir(Path veriDosyasi, String girdi) {
        ByteArrayOutputStream tampon = new ByteArrayOutputStream();
        PrintStream cikti = new PrintStream(tampon, true, StandardCharsets.UTF_8);
        InputStream giris = new ByteArrayInputStream(girdi.getBytes(StandardCharsets.UTF_8));
        new Main(giris, cikti, veriDosyasi).calistir();
        return tampon.toString(StandardCharsets.UTF_8);
    }

    @Test
    @DisplayName("Menü bütün seçenekleri sırasıyla gösterir")
    void menuTumSecenekleriGosterir() {
        String ekran = calistir(geciciKlasor.resolve("veri.txt"), "0\n");
        assertTrue(ekran.contains("1) Öğrenci ekle"));
        assertTrue(ekran.contains("16) CSV olarak dışa aktar"));
        assertTrue(ekran.contains("0) Çıkış"));
        assertTrue(ekran.indexOf("1) Öğrenci ekle") < ekran.indexOf("16) CSV olarak dışa aktar"));
    }

    @Test
    @DisplayName("Örnek verilerle genel sıralama doğru sırada listelenir")
    void ornekVeriIleSiralama() {
        Path veri = geciciKlasor.resolve("veri.txt");
        String ekran = calistir(veri, "11\n6\n0\n");

        int baslik = ekran.indexOf("Ortalama  GANO");
        int zeynep = ekran.indexOf("Zeynep Çelik", baslik);
        int ali = ekran.indexOf("Ali Demir", baslik);
        int can = ekran.indexOf("Can Aydın", baslik);
        assertTrue(baslik >= 0 && zeynep > baslik);
        assertTrue(zeynep < ali && ali < can);
        assertTrue(ekran.contains("Program sonlandırıldı."));
    }

    @Test
    @DisplayName("Geçersiz menü seçimi uyarı verir, program çalışmaya devam eder")
    void gecersizSecim() {
        String ekran = calistir(geciciKlasor.resolve("veri.txt"), "99\nabc\n0\n");
        assertTrue(ekran.contains("Geçersiz seçim."));
        assertTrue(ekran.contains("Program sonlandırıldı."));
    }

    @Test
    @DisplayName("Olmayan öğrenci sorgusu hata mesajı gösterir, program kapanmaz")
    void olmayanOgrenciHataMesajiVerir() {
        String ekran = calistir(geciciKlasor.resolve("veri.txt"), "4\n9999\n8\n0\n");
        assertTrue(ekran.contains("Hata: Öğrenci bulunamadı: 9999"));
        assertTrue(ekran.contains("-- Öğrenciler --"));
    }

    @Test
    @DisplayName("Çıkışta kaydedilen veriler bir sonraki açılışta otomatik yüklenir")
    void verilerOturumlarArasindaKorunur() {
        Path veri = geciciKlasor.resolve("veri.txt");
        assertFalse(Files.exists(veri));

        calistir(veri, "1\n1001\nAli\nDemir\n0\n");
        assertTrue(Files.exists(veri));

        String ikinciAcilis = calistir(veri, "8\n0\n");
        assertTrue(ikinciAcilis.contains("1 öğrenci, 0 ders yüklendi."));
        assertTrue(ikinciAcilis.contains("1001 - Ali Demir"));
    }

    @Test
    @DisplayName("Girdi beklenmedik biçimde biterse veriler kaydedilir ve program düzgün kapanır")
    void girdiBitinceDuzgunKapanir() {
        Path veri = geciciKlasor.resolve("veri.txt");
        String ekran = calistir(veri, "11\n3\n2024001\n");
        assertTrue(ekran.contains("Örnek veriler yüklendi."));
        assertTrue(ekran.contains("Program sonlandırıldı."));
        assertTrue(Files.exists(veri));
    }
}
