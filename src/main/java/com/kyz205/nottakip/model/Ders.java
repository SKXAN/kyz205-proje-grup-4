package com.kyz205.nottakip.model;

/**
 * Bir dersi temsil eder: kod, ad, kredi ve dersi veren öğretim üyesi.
 */
public class Ders implements Raporlanabilir {

    private final String kod;
    private final String ad;
    private final int kredi;
    private Ogretmen ogretimUyesi;

    public Ders(String kod, String ad, int kredi) {
        this(kod, ad, kredi, null);
    }

    public Ders(String kod, String ad, int kredi, Ogretmen ogretimUyesi) {
        if (kod == null || kod.isBlank()) {
            throw new IllegalArgumentException("Ders kodu boş olamaz.");
        }
        if (ad == null || ad.isBlank()) {
            throw new IllegalArgumentException("Ders adı boş olamaz.");
        }
        if (kredi <= 0) {
            throw new IllegalArgumentException("Kredi pozitif olmalıdır.");
        }
        this.kod = kod.trim().toUpperCase();
        this.ad = ad.trim();
        this.kredi = kredi;
        this.ogretimUyesi = ogretimUyesi;
    }

    public String getKod() {
        return kod;
    }

    public String getAd() {
        return ad;
    }

    public int getKredi() {
        return kredi;
    }

    public Ogretmen getOgretimUyesi() {
        return ogretimUyesi;
    }

    public void setOgretimUyesi(Ogretmen ogretimUyesi) {
        this.ogretimUyesi = ogretimUyesi;
    }

    @Override
    public String rapor() {
        String hoca = ogretimUyesi == null ? "Atanmamış" : ogretimUyesi.tamAd();
        return kod + " " + ad + " (" + kredi + " kredi) - " + hoca;
    }

    @Override
    public String toString() {
        return kod + " " + ad;
    }

    /** Dersler yalnızca kodlarına göre eşit kabul edilir. */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ders)) return false;
        return kod.equals(((Ders) o).kod);
    }

    @Override
    public int hashCode() {
        return kod.hashCode();
    }
}
