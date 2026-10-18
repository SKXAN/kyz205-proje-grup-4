package com.kyz205.nottakip.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DersTest {

    @Test
    @DisplayName("Kredi sıfır veya negatif olamaz")
    void krediPozitifOlmali() {
        assertThrows(IllegalArgumentException.class, () -> new Ders("MAT101", "Matematik I", 0));
        assertThrows(IllegalArgumentException.class, () -> new Ders("MAT101", "Matematik I", -3));
    }

    @Test
    @DisplayName("Ders kodu büyük harfe çevrilir ve kırpılır")
    void kodBuyukHarfeCevrilir() {
        Ders ders = new Ders(" kyz205 ", "Nesne Tabanlı Programlama", 4);
        assertEquals("KYZ205", ders.getKod());
    }

    @Test
    @DisplayName("Öğretim üyesi atanmamışsa rapor bunu belirtir")
    void ogretimUyesiAtanmamis() {
        Ders ders = new Ders("FIZ101", "Fizik I", 3);
        assertNull(ders.getOgretimUyesi());
        assertEquals("FIZ101 Fizik I (3 kredi) - Atanmamış", ders.rapor());

        ders.setOgretimUyesi(new Ogretmen("Ayşe", "Yılmaz", "Fizik"));
        assertEquals("FIZ101 Fizik I (3 kredi) - Ayşe Yılmaz", ders.rapor());
    }

    @Test
    @DisplayName("Dersler koda göre eşit sayılır")
    void esitlikKodaGore() {
        assertEquals(new Ders("KYZ205", "A", 1), new Ders("kyz205", "B", 9));
    }
}
