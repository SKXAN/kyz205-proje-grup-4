package com.kyz205.nottakip.service;

import com.kyz205.nottakip.model.Ders;
import com.kyz205.nottakip.model.Ogrenci;
import com.kyz205.nottakip.model.Ogretmen;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DosyaYoneticisiTest {

    @TempDir
    Path geciciKlasor;

    @Test
    @DisplayName("Kaydedilen defter aynı içerikle geri yüklenir")
    void kaydetVeYukle() throws IOException {
        NotDefteri orijinal = new NotDefteri();
        Ogretmen hoca = new Ogretmen("Ayşe", "Yılmaz", "Bilgisayar Mühendisliği");
        orijinal.dersEkle(new Ders("KYZ205", "Nesne Tabanlı Programlama", 4, hoca));
        orijinal.dersEkle(new Ders("MAT101", "Matematik I", 3));
        orijinal.ogrenciEkle(new Ogrenci("2024001", "Ali", "Demir"));
        orijinal.ogrenciEkle(new Ogrenci("2024002", "Zeynep", "Çelik"));
        orijinal.notAta("2024001", "KYZ205", 85.5, 90);
        orijinal.notAta("2024002", "MAT101", 70, 65);

        Path dosya = geciciKlasor.resolve("defter.txt");
        DosyaYoneticisi yonetici = new DosyaYoneticisi();
        yonetici.kaydet(orijinal, dosya);
        NotDefteri yuklenen = yonetici.yukle(dosya);

        assertEquals(2, yuklenen.tumOgrenciler().size());
        assertEquals(2, yuklenen.tumDersler().size());
        assertEquals(85.5, yuklenen.notGetir("2024001", "KYZ205").getVize());
        assertEquals(90.0, yuklenen.notGetir("2024001", "KYZ205").getFinalNotu());
        assertEquals("Ayşe Yılmaz", yuklenen.dersBul("KYZ205").getOgretimUyesi().tamAd());
        assertEquals("Zeynep Çelik", yuklenen.ogrenciBul("2024002").tamAd());
        assertEquals(orijinal.ogrenciOrtalamasi("2024001"),
                yuklenen.ogrenciOrtalamasi("2024001"), 0.0001);
    }

    @Test
    @DisplayName("Öğretim üyesi atanmamış ders de kaydedilip yüklenir")
    void hocasizDers() throws IOException {
        NotDefteri orijinal = new NotDefteri();
        orijinal.dersEkle(new Ders("FIZ101", "Fizik I", 2));
        Path dosya = geciciKlasor.resolve("hocasiz.txt");
        DosyaYoneticisi yonetici = new DosyaYoneticisi();
        yonetici.kaydet(orijinal, dosya);

        NotDefteri yuklenen = yonetici.yukle(dosya);
        assertEquals(null, yuklenen.dersBul("FIZ101").getOgretimUyesi());
    }

    @Test
    @DisplayName("Bozuk satır içeren dosya anlamlı hata verir")
    void bozukDosyaHataVerir() throws IOException {
        Path dosya = geciciKlasor.resolve("bozuk.txt");
        Files.writeString(dosya, "OGRENCI;1;Ali;Demir\nSACMA;x;y\n", StandardCharsets.UTF_8);
        DosyaYoneticisi yonetici = new DosyaYoneticisi();
        IOException hata = assertThrows(IOException.class, () -> yonetici.yukle(dosya));
        assertEquals(true, hata.getMessage().contains("Satır 2"));
    }

    @Test
    @DisplayName("Ayırıcı karakter içeren alanlar bozulmadan kaydedilip yüklenir")
    void ayiriciIcerenAlanlarKorunur() throws IOException {
        NotDefteri orijinal = new NotDefteri();
        Ogretmen hoca = new Ogretmen("Ayşe", "Yılmaz", "Bilgisayar; Yazılım");
        orijinal.dersEkle(new Ders("KYZ205", "Nesne; Tabanlı Programlama", 4, hoca));
        orijinal.ogrenciEkle(new Ogrenci("1", "Ali", "Demir;Kaya"));
        orijinal.notAta("1", "KYZ205", 80, 90);

        Path dosya = geciciKlasor.resolve("ayirici.txt");
        DosyaYoneticisi yonetici = new DosyaYoneticisi();
        yonetici.kaydet(orijinal, dosya);
        NotDefteri yuklenen = yonetici.yukle(dosya);

        assertEquals("Nesne; Tabanlı Programlama", yuklenen.dersBul("KYZ205").getAd());
        assertEquals("Bilgisayar; Yazılım", yuklenen.dersBul("KYZ205").getOgretimUyesi().getBrans());
        assertEquals("Demir;Kaya", yuklenen.ogrenciBul("1").getSoyad());
        assertEquals(86.0, yuklenen.notGetir("1", "KYZ205").ortalama(), 0.0001);
    }

    @Test
    @DisplayName("Ters bölü içeren alanlar bozulmadan kaydedilip yüklenir")
    void tersBoluIcerenAlanKorunur() throws IOException {
        NotDefteri orijinal = new NotDefteri();
        orijinal.ogrenciEkle(new Ogrenci("2", "Ali\\Veli", "De\\;mir"));

        Path dosya = geciciKlasor.resolve("tersbolu.txt");
        DosyaYoneticisi yonetici = new DosyaYoneticisi();
        yonetici.kaydet(orijinal, dosya);
        NotDefteri yuklenen = yonetici.yukle(dosya);

        assertEquals("Ali\\Veli", yuklenen.ogrenciBul("2").getAd());
        assertEquals("De\\;mir", yuklenen.ogrenciBul("2").getSoyad());
        assertEquals(List.of("a;b", "c\\d", ""), DosyaYoneticisi.alanlaraAyir("a\\;b;c\\\\d;"));
    }

    @Test
    @DisplayName("Eksik alanlı kayıt satır numarası ve beklenen alan sayısıyla hata verir")
    void eksikAlanHataVerir() throws IOException {
        Path dosya = geciciKlasor.resolve("eksik.txt");
        Files.writeString(dosya, "# yorum\nOGRENCI;1;Ali\n", StandardCharsets.UTF_8);
        DosyaYoneticisi yonetici = new DosyaYoneticisi();
        IOException hata = assertThrows(IOException.class, () -> yonetici.yukle(dosya));
        assertEquals(true, hata.getMessage().contains("Satır 2"));
        assertEquals(true, hata.getMessage().contains("4 alan içermeli"));
    }
}
