package com.kyz205.nottakip.model;

import java.util.Objects;

/**
 * Sistemdeki tüm kişiler (öğrenci, öğretim üyesi) için ortak soyut taban sınıf.
 * Ad ve soyad alanlarını kapsüller; alt sınıflar rol bilgisini kendileri belirler.
 */
public abstract class Kisi implements Raporlanabilir {

    private final String ad;
    private final String soyad;

    protected Kisi(String ad, String soyad) {
        this.ad = bosOlmayan(ad, "Ad");
        this.soyad = bosOlmayan(soyad, "Soyad");
    }

    private static String bosOlmayan(String deger, String alanAdi) {
        if (deger == null || deger.isBlank()) {
            throw new IllegalArgumentException(alanAdi + " boş olamaz.");
        }
        return deger.trim();
    }

    public String getAd() {
        return ad;
    }

    public String getSoyad() {
        return soyad;
    }

    /** Ad ve soyadı tek bir metin olarak döndürür. */
    public String tamAd() {
        return ad + " " + soyad;
    }

    /** Kişinin sistemdeki rolü (örneğin Öğrenci). Alt sınıflar tarafından belirlenir. */
    public abstract String rol();

    @Override
    public String toString() {
        return rol() + ": " + tamAd();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kisi kisi = (Kisi) o;
        return ad.equalsIgnoreCase(kisi.ad) && soyad.equalsIgnoreCase(kisi.soyad);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ad.toLowerCase(), soyad.toLowerCase());
    }
}
