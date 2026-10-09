package com.kyz205.nottakip.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HarfNotuTest {

    @Test
    @DisplayName("Alt sınırlar doğru harfe karşılık gelir")
    void altSinirlar() {
        assertEquals(HarfNotu.AA, HarfNotu.hesapla(90));
        assertEquals(HarfNotu.BA, HarfNotu.hesapla(85));
        assertEquals(HarfNotu.BB, HarfNotu.hesapla(80));
        assertEquals(HarfNotu.CB, HarfNotu.hesapla(75));
        assertEquals(HarfNotu.CC, HarfNotu.hesapla(70));
        assertEquals(HarfNotu.DC, HarfNotu.hesapla(65));
        assertEquals(HarfNotu.DD, HarfNotu.hesapla(60));
        assertEquals(HarfNotu.FD, HarfNotu.hesapla(50));
        assertEquals(HarfNotu.FF, HarfNotu.hesapla(0));
    }

    @Test
    @DisplayName("Sınırın hemen altındaki değer bir alt harfe düşer")
    void sinirinAltiBirAltHarf() {
        assertEquals(HarfNotu.BA, HarfNotu.hesapla(89.99));
        assertEquals(HarfNotu.FD, HarfNotu.hesapla(59.99));
        assertEquals(HarfNotu.FF, HarfNotu.hesapla(49.99));
    }

    @Test
    @DisplayName("Katsayılar 4'lük sisteme uygundur")
    void katsayilar() {
        assertEquals(4.0, HarfNotu.AA.getKatsayi());
        assertEquals(2.0, HarfNotu.CC.getKatsayi());
        assertEquals(0.0, HarfNotu.FF.getKatsayi());
    }
}
