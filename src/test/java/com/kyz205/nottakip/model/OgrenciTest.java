package com.kyz205.nottakip.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OgrenciTest {

    @Test
    @DisplayName("Boş öğrenci numarası, ad veya soyad reddedilir")
    void bosAlanlarReddedilir() {
        assertThrows(IllegalArgumentException.class, () -> new Ogrenci("", "Ali", "Demir"));
        assertThrows(IllegalArgumentException.class, () -> new Ogrenci("1001", "  ", "Demir"));
        assertThrows(IllegalArgumentException.class, () -> new Ogrenci("1001", "Ali", null));
    }

    @Test
    @DisplayName("Ad, soyad ve numara baştaki/sondaki boşluklardan arındırılır")
    void bosluklarKirpilir() {
        Ogrenci o = new Ogrenci(" 1001 ", " Ali ", " Demir ");
        assertEquals("1001", o.getOgrenciNo());
        assertEquals("Ali Demir", o.tamAd());
    }

    @Test
    @DisplayName("Aynı ders ikinci kez eklenmez, çıkarılınca listeden gider")
    void dersEkleVeCikar() {
        Ogrenci o = new Ogrenci("1001", "Ali", "Demir");
        Ders ders = new Ders("KYZ205", "Nesne Tabanlı Programlama", 4);
        o.dersEkle(ders);
        o.dersEkle(new Ders("kyz205", "Aynı ders farklı yazım", 4));
        assertEquals(1, o.getDersler().size());
        assertTrue(o.dersAliyorMu(ders));

        assertTrue(o.dersCikar(ders));
        assertFalse(o.dersAliyorMu(ders));
        assertFalse(o.dersCikar(ders));
    }

    @Test
    @DisplayName("Rol ve rapor metinleri doğru üretilir")
    void rolVeRapor() {
        Ogrenci o = new Ogrenci("1001", "Ali", "Demir");
        assertEquals("Öğrenci", o.rol());
        assertEquals("1001 - Ali Demir (0 ders)", o.rapor());
    }

    @Test
    @DisplayName("Öğrenciler numaraya göre eşit sayılır")
    void esitlikNumarayaGore() {
        assertEquals(new Ogrenci("1001", "Ali", "Demir"), new Ogrenci("1001", "Veli", "Kaya"));
    }
}
