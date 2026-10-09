package com.kyz205.nottakip.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NotTest {

    @Test
    @DisplayName("Ortalama vize %40 + final %60 olarak hesaplanır")
    void ortalamaAgirlikliHesaplanir() {
        Not not = new Not(50, 80);
        // 50*0.4 + 80*0.6 = 20 + 48 = 68
        assertEquals(68.0, not.ortalama(), 0.0001);
    }

    @Test
    @DisplayName("Harf notu ortalamaya göre doğru belirlenir")
    void harfNotuDogruBelirlenir() {
        assertEquals(HarfNotu.AA, new Not(90, 90).harfNotu());
        assertEquals(HarfNotu.BB, new Not(80, 80).harfNotu());
        assertEquals(HarfNotu.DD, new Not(60, 60).harfNotu());
        assertEquals(HarfNotu.FF, new Not(40, 40).harfNotu());
    }

    @Test
    @DisplayName("DD ve üzeri geçer, FD ve FF kalır")
    void gecmeDurumu() {
        assertTrue(new Not(60, 60).gectiMi());
        assertFalse(new Not(50, 50).gectiMi());
        assertFalse(new Not(0, 0).gectiMi());
    }

    @Test
    @DisplayName("0-100 dışındaki notlar reddedilir")
    void gecersizNotHataVerir() {
        assertThrows(IllegalArgumentException.class, () -> new Not(-1, 50));
        assertThrows(IllegalArgumentException.class, () -> new Not(50, 101));
        Not not = new Not(50, 50);
        assertThrows(IllegalArgumentException.class, () -> not.setVize(150));
    }

    @Test
    @DisplayName("Sınır değerler 0 ve 100 kabul edilir")
    void sinirDegerlerKabulEdilir() {
        assertEquals(0.0, new Not(0, 0).ortalama(), 0.0001);
        assertEquals(100.0, new Not(100, 100).ortalama(), 0.0001);
    }
}
