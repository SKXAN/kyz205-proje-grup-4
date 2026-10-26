package com.kyz205.nottakip.model;

import java.util.Objects;

/**
 * Bir dersin sınıf geneli istatistiklerini taşıyan değer nesnesi.
 * NotDefteri tarafından hesaplanır; alanlar değiştirilemez.
 */
public final class DersIstatistigi {

    private final Ders ders;
    private final int ogrenciSayisi;
    private final double ortalama;
    private final Ogrenci enYuksekOgrenci;
    private final Not enYuksekNot;
    private final Ogrenci enDusukOgrenci;
    private final Not enDusukNot;
    private final int gecenSayisi;

    public DersIstatistigi(Ders ders, int ogrenciSayisi, double ortalama,
                           Ogrenci enYuksekOgrenci, Not enYuksekNot,
                           Ogrenci enDusukOgrenci, Not enDusukNot, int gecenSayisi) {
        this.ders = Objects.requireNonNull(ders);
        this.ogrenciSayisi = ogrenciSayisi;
        this.ortalama = ortalama;
        this.enYuksekOgrenci = enYuksekOgrenci;
        this.enYuksekNot = enYuksekNot;
        this.enDusukOgrenci = enDusukOgrenci;
        this.enDusukNot = enDusukNot;
        this.gecenSayisi = gecenSayisi;
    }

    /** Dersi hiç kimse almamışsa kullanılır. */
    public static DersIstatistigi bos(Ders ders) {
        return new DersIstatistigi(ders, 0, 0.0, null, null, null, null, 0);
    }

    public Ders getDers() {
        return ders;
    }

    public int getOgrenciSayisi() {
        return ogrenciSayisi;
    }

    public double getOrtalama() {
        return ortalama;
    }

    public Ogrenci getEnYuksekOgrenci() {
        return enYuksekOgrenci;
    }

    public Not getEnYuksekNot() {
        return enYuksekNot;
    }

    public Ogrenci getEnDusukOgrenci() {
        return enDusukOgrenci;
    }

    public Not getEnDusukNot() {
        return enDusukNot;
    }

    public int getGecenSayisi() {
        return gecenSayisi;
    }

    public int getKalanSayisi() {
        return ogrenciSayisi - gecenSayisi;
    }

    /** Geçen öğrenci yüzdesi (0-100). Öğrenci yoksa 0. */
    public double getBasariOrani() {
        return ogrenciSayisi == 0 ? 0.0 : 100.0 * gecenSayisi / ogrenciSayisi;
    }

    public boolean bosMu() {
        return ogrenciSayisi == 0;
    }
}
