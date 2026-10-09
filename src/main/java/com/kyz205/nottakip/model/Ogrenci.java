package com.kyz205.nottakip.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Öğrenciyi temsil eder. Kisi sınıfından kalıtım alır; ek olarak öğrenci
 * numarası ve aldığı derslerin listesini tutar.
 */
public class Ogrenci extends Kisi {

    private final String ogrenciNo;
    private final List<Ders> dersler = new ArrayList<>();

    public Ogrenci(String ogrenciNo, String ad, String soyad) {
        super(ad, soyad);
        if (ogrenciNo == null || ogrenciNo.isBlank()) {
            throw new IllegalArgumentException("Öğrenci numarası boş olamaz.");
        }
        this.ogrenciNo = ogrenciNo.trim();
    }

    public String getOgrenciNo() {
        return ogrenciNo;
    }

    /** Öğrencinin aldığı derslerin değiştirilemez görünümü. */
    public List<Ders> getDersler() {
        return Collections.unmodifiableList(dersler);
    }

    /** Dersi öğrencinin listesine ekler; aynı ders ikinci kez eklenmez. */
    public void dersEkle(Ders ders) {
        Objects.requireNonNull(ders, "Ders null olamaz.");
        if (!dersler.contains(ders)) {
            dersler.add(ders);
        }
    }

    public boolean dersAliyorMu(Ders ders) {
        return dersler.contains(ders);
    }

    @Override
    public String rol() {
        return "Öğrenci";
    }

    @Override
    public String rapor() {
        return ogrenciNo + " - " + tamAd() + " (" + dersler.size() + " ders)";
    }

    /** Öğrenciler yalnızca numaralarına göre eşit kabul edilir. */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ogrenci)) return false;
        return ogrenciNo.equals(((Ogrenci) o).ogrenciNo);
    }

    @Override
    public int hashCode() {
        return ogrenciNo.hashCode();
    }
}
