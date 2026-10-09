package com.kyz205.nottakip.model;

/**
 * Öğretim üyesini temsil eder. Kisi sınıfından kalıtım alır ve branş bilgisi ekler.
 */
public class Ogretmen extends Kisi {

    private final String brans;

    public Ogretmen(String ad, String soyad, String brans) {
        super(ad, soyad);
        if (brans == null || brans.isBlank()) {
            throw new IllegalArgumentException("Branş boş olamaz.");
        }
        this.brans = brans.trim();
    }

    public String getBrans() {
        return brans;
    }

    @Override
    public String rol() {
        return "Öğretim Üyesi";
    }

    @Override
    public String rapor() {
        return tamAd() + " (" + brans + ")";
    }
}
