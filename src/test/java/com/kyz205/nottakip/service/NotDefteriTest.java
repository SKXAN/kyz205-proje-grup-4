package com.kyz205.nottakip.service;

import com.kyz205.nottakip.model.Ders;
import com.kyz205.nottakip.model.DersIstatistigi;
import com.kyz205.nottakip.model.Not;
import com.kyz205.nottakip.model.Ogrenci;
import com.kyz205.nottakip.model.Ogretmen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NotDefteriTest {

    private NotDefteri defter;
    private Ders oop;
    private Ders mat;

    @BeforeEach
    void hazirla() {
        defter = new NotDefteri();
        Ogretmen hoca = new Ogretmen("Ayşe", "Yılmaz", "Bilgisayar");
        oop = new Ders("KYZ205", "Nesne Tabanlı Programlama", 3, hoca);
        mat = new Ders("MAT101", "Matematik I", 1, hoca);
        defter.dersEkle(oop);
        defter.dersEkle(mat);
        defter.ogrenciEkle(new Ogrenci("1001", "Ali", "Demir"));
        defter.ogrenciEkle(new Ogrenci("1002", "Zeynep", "Çelik"));
        defter.ogrenciEkle(new Ogrenci("1003", "Can", "Aydın"));
    }

    @Test
    @DisplayName("Not atanır ve geri okunur; ders öğrencinin listesine eklenir")
    void notAtaVeGetir() {
        defter.notAta("1001", "KYZ205", 70, 80);
        Not not = defter.notGetir("1001", "KYZ205");
        assertEquals(70, not.getVize());
        assertEquals(80, not.getFinalNotu());
        assertTrue(defter.ogrenciBul("1001").dersAliyorMu(oop));
    }

    @Test
    @DisplayName("Ders kodu büyük/küçük harf duyarsızdır")
    void dersKoduHarfDuyarsiz() {
        defter.notAta("1001", "kyz205", 70, 80);
        assertEquals(76.0, defter.notGetir("1001", "KYZ205").ortalama(), 0.0001);
    }

    @Test
    @DisplayName("Aynı numarayla ikinci öğrenci eklenemez")
    void ayniNumaraReddedilir() {
        assertThrows(IllegalArgumentException.class,
                () -> defter.ogrenciEkle(new Ogrenci("1001", "Başka", "Kişi")));
    }

    @Test
    @DisplayName("Kayıtlı olmayan öğrenci veya derse not atanamaz")
    void olmayanKayitHataVerir() {
        assertThrows(KayitBulunamadiException.class, () -> defter.notAta("9999", "KYZ205", 50, 50));
        assertThrows(KayitBulunamadiException.class, () -> defter.notAta("1001", "YOK101", 50, 50));
        assertThrows(KayitBulunamadiException.class, () -> defter.notGetir("1001", "KYZ205"));
    }

    @Test
    @DisplayName("Sınıf ortalaması dersi alan öğrencilerin ortalamasıdır")
    void dersOrtalamasi() {
        defter.notAta("1001", "KYZ205", 80, 80); // 80
        defter.notAta("1002", "KYZ205", 60, 60); // 60
        // 1003 bu dersi almıyor
        assertEquals(70.0, defter.dersOrtalamasi("KYZ205"), 0.0001);
        assertEquals(2, defter.dersiAlanSayisi("KYZ205"));
        assertEquals(0.0, defter.dersOrtalamasi("MAT101"), 0.0001);
    }

    @Test
    @DisplayName("Öğrenci ortalaması kredi ağırlıklıdır")
    void krediAgirlikliOrtalama() {
        defter.notAta("1001", "KYZ205", 90, 90); // 90, 3 kredi
        defter.notAta("1001", "MAT101", 50, 50); // 50, 1 kredi
        // (90*3 + 50*1) / 4 = 320 / 4 = 80
        assertEquals(80.0, defter.ogrenciOrtalamasi("1001"), 0.0001);
        // GANO: (4.0*3 + 0.5*1) / 4 = 12.5 / 4 = 3.125
        assertEquals(3.125, defter.ogrenciGano("1001"), 0.0001);
    }

    @Test
    @DisplayName("Başarı sıralaması ortalamaya göre büyükten küçüğe yapılır")
    void basariSiralamasi() {
        defter.notAta("1001", "KYZ205", 70, 70);
        defter.notAta("1002", "KYZ205", 95, 95);
        defter.notAta("1003", "KYZ205", 40, 40);

        List<Ogrenci> sira = defter.basariSiralamasi();
        assertEquals("1002", sira.get(0).getOgrenciNo());
        assertEquals("1001", sira.get(1).getOgrenciNo());
        assertEquals("1003", sira.get(2).getOgrenciNo());
    }

    @Test
    @DisplayName("Ders bazlı sıralama yalnızca dersi alanları içerir")
    void dersBazliSiralama() {
        defter.notAta("1001", "KYZ205", 60, 60);
        defter.notAta("1002", "KYZ205", 90, 90);
        defter.notAta("1003", "MAT101", 100, 100);

        List<Ogrenci> sira = defter.basariSiralamasi("KYZ205");
        assertEquals(2, sira.size());
        assertEquals("1002", sira.get(0).getOgrenciNo());
        assertEquals("1001", sira.get(1).getOgrenciNo());
    }

    @Test
    @DisplayName("Öğrenci silinince notları da silinir")
    void ogrenciSil() {
        defter.notAta("1001", "KYZ205", 80, 80);
        defter.notAta("1002", "KYZ205", 60, 60);

        Ogrenci silinen = defter.ogrenciSil("1001");
        assertEquals("Ali Demir", silinen.tamAd());
        assertEquals(2, defter.tumOgrenciler().size());
        assertThrows(KayitBulunamadiException.class, () -> defter.ogrenciBul("1001"));
        assertEquals(1, defter.dersiAlanSayisi("KYZ205"));
        assertEquals(60.0, defter.dersOrtalamasi("KYZ205"), 0.0001);
    }

    @Test
    @DisplayName("Ders silinince öğrencilerin listesinden ve notlardan kalkar")
    void dersSil() {
        defter.notAta("1001", "KYZ205", 90, 90);
        defter.notAta("1001", "MAT101", 50, 50);

        defter.dersSil("kyz205");
        assertEquals(1, defter.tumDersler().size());
        assertThrows(KayitBulunamadiException.class, () -> defter.dersBul("KYZ205"));
        assertThrows(KayitBulunamadiException.class, () -> defter.notGetir("1001", "KYZ205"));
        assertTrue(!defter.ogrenciBul("1001").dersAliyorMu(oop));
        // Kalan tek ders MAT101 (50), ortalama ona göre hesaplanmalı
        assertEquals(50.0, defter.ogrenciOrtalamasi("1001"), 0.0001);
    }

    @Test
    @DisplayName("Kayıtlı olmayan öğrenci veya ders silinemez")
    void olmayanKayitSilinemez() {
        assertThrows(KayitBulunamadiException.class, () -> defter.ogrenciSil("9999"));
        assertThrows(KayitBulunamadiException.class, () -> defter.dersSil("YOK101"));
    }

    @Test
    @DisplayName("Ders istatistikleri doğru hesaplanır")
    void dersIstatistigi() {
        defter.notAta("1001", "KYZ205", 90, 90); // 90, geçer
        defter.notAta("1002", "KYZ205", 60, 60); // 60, geçer (DD)
        defter.notAta("1003", "KYZ205", 40, 40); // 40, kalır
        // MAT101'i kimse almıyor

        DersIstatistigi ist = defter.dersIstatistigi("KYZ205");
        assertEquals(3, ist.getOgrenciSayisi());
        assertEquals(63.3333, ist.getOrtalama(), 0.001);
        assertEquals("1001", ist.getEnYuksekOgrenci().getOgrenciNo());
        assertEquals(90.0, ist.getEnYuksekNot().ortalama(), 0.0001);
        assertEquals("1003", ist.getEnDusukOgrenci().getOgrenciNo());
        assertEquals(2, ist.getGecenSayisi());
        assertEquals(1, ist.getKalanSayisi());
        assertEquals(66.6667, ist.getBasariOrani(), 0.001);

        DersIstatistigi bos = defter.dersIstatistigi("MAT101");
        assertTrue(bos.bosMu());
        assertEquals(0.0, bos.getBasariOrani(), 0.0001);
    }

    @Test
    @DisplayName("Ders raporu istatistik satırlarını içerir")
    void dersRaporu() {
        defter.notAta("1001", "KYZ205", 85, 95);
        String rapor = defter.dersRaporu("KYZ205");
        assertTrue(rapor.contains("Öğrenci sayısı : 1"));
        assertTrue(rapor.contains("Ali Demir"));
        assertTrue(rapor.contains("Başarı oranı"));
        assertTrue(defter.dersRaporu("MAT101").contains("Bu dersi alan öğrenci yok"));
    }

    @Test
    @DisplayName("Öğrenci raporu ders ve ortalama bilgisini içerir")
    void ogrenciRaporu() {
        defter.notAta("1001", "KYZ205", 80, 90);
        String rapor = defter.ogrenciRaporu("1001");
        assertTrue(rapor.contains("Ali Demir"));
        assertTrue(rapor.contains("KYZ205"));
        assertTrue(rapor.contains("Genel Ortalama"));
    }
}
