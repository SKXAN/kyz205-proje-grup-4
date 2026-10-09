package com.kyz205.nottakip.service;

import com.kyz205.nottakip.model.Ders;
import com.kyz205.nottakip.model.HarfNotu;
import com.kyz205.nottakip.model.Not;
import com.kyz205.nottakip.model.Ogrenci;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Sistemin merkezi sınıfı. Öğrencileri, dersleri ve öğrenci-ders-not
 * ilişkilerini koleksiyonlar (HashMap, ArrayList) ile yönetir;
 * ortalama, sınıf ortalaması ve başarı sıralaması hesaplar.
 */
public class NotDefteri {

    /** Öğrenci numarası -> Ogrenci. Ekleme sırası korunur. */
    private final Map<String, Ogrenci> ogrenciler = new LinkedHashMap<>();

    /** Ders kodu -> Ders. Ekleme sırası korunur. */
    private final Map<String, Ders> dersler = new LinkedHashMap<>();

    /** Öğrenci numarası -> (Ders kodu -> Not). */
    private final Map<String, Map<String, Not>> notlar = new HashMap<>();

    // ---------------------------------------------------------------- kayıt

    public void ogrenciEkle(Ogrenci ogrenci) {
        Objects.requireNonNull(ogrenci, "Öğrenci null olamaz.");
        if (ogrenciler.containsKey(ogrenci.getOgrenciNo())) {
            throw new IllegalArgumentException(
                    "Bu numaralı öğrenci zaten kayıtlı: " + ogrenci.getOgrenciNo());
        }
        ogrenciler.put(ogrenci.getOgrenciNo(), ogrenci);
    }

    public void dersEkle(Ders ders) {
        Objects.requireNonNull(ders, "Ders null olamaz.");
        if (dersler.containsKey(ders.getKod())) {
            throw new IllegalArgumentException("Bu kodlu ders zaten kayıtlı: " + ders.getKod());
        }
        dersler.put(ders.getKod(), ders);
    }

    public Ogrenci ogrenciBul(String ogrenciNo) {
        Ogrenci ogrenci = ogrenciler.get(ogrenciNo);
        if (ogrenci == null) {
            throw new KayitBulunamadiException("Öğrenci bulunamadı: " + ogrenciNo);
        }
        return ogrenci;
    }

    public Ders dersBul(String dersKodu) {
        Ders ders = dersler.get(dersKodu == null ? null : dersKodu.trim().toUpperCase());
        if (ders == null) {
            throw new KayitBulunamadiException("Ders bulunamadı: " + dersKodu);
        }
        return ders;
    }

    public Collection<Ogrenci> tumOgrenciler() {
        return Collections.unmodifiableCollection(ogrenciler.values());
    }

    public Collection<Ders> tumDersler() {
        return Collections.unmodifiableCollection(dersler.values());
    }

    // ------------------------------------------------------------------ not

    /**
     * Öğrenciye bir dersten not atar. Daha önce not varsa üzerine yazar.
     * Dersi öğrencinin ders listesine de ekler.
     */
    public Not notAta(String ogrenciNo, String dersKodu, double vize, double finalNotu) {
        Ogrenci ogrenci = ogrenciBul(ogrenciNo);
        Ders ders = dersBul(dersKodu);
        Not not = new Not(vize, finalNotu);
        ogrenci.dersEkle(ders);
        notlar.computeIfAbsent(ogrenci.getOgrenciNo(), k -> new HashMap<>())
              .put(ders.getKod(), not);
        return not;
    }

    public Not notGetir(String ogrenciNo, String dersKodu) {
        Ogrenci ogrenci = ogrenciBul(ogrenciNo);
        Ders ders = dersBul(dersKodu);
        Map<String, Not> ogrencininNotlari = notlar.get(ogrenci.getOgrenciNo());
        Not not = ogrencininNotlari == null ? null : ogrencininNotlari.get(ders.getKod());
        if (not == null) {
            throw new KayitBulunamadiException(
                    ogrenci.tamAd() + " için " + ders.getKod() + " dersine not girilmemiş.");
        }
        return not;
    }

    /** Öğrencinin aldığı derslerin notlarını ders sırasıyla döndürür. */
    public Map<Ders, Not> ogrencininNotlari(String ogrenciNo) {
        Ogrenci ogrenci = ogrenciBul(ogrenciNo);
        Map<String, Not> ham = notlar.getOrDefault(ogrenci.getOgrenciNo(), Map.of());
        Map<Ders, Not> sonuc = new LinkedHashMap<>();
        for (Ders ders : ogrenci.getDersler()) {
            Not not = ham.get(ders.getKod());
            if (not != null) {
                sonuc.put(ders, not);
            }
        }
        return sonuc;
    }

    // ------------------------------------------------------------ hesaplar

    /**
     * Öğrencinin kredi ağırlıklı genel not ortalaması (0-100 ölçeği).
     * Not girilmemişse 0 döner.
     */
    public double ogrenciOrtalamasi(String ogrenciNo) {
        Map<Ders, Not> notlari = ogrencininNotlari(ogrenciNo);
        if (notlari.isEmpty()) {
            return 0.0;
        }
        double toplamPuan = 0;
        int toplamKredi = 0;
        for (Map.Entry<Ders, Not> giris : notlari.entrySet()) {
            int kredi = giris.getKey().getKredi();
            toplamPuan += giris.getValue().ortalama() * kredi;
            toplamKredi += kredi;
        }
        return toplamPuan / toplamKredi;
    }

    /**
     * Öğrencinin kredi ağırlıklı GANO değeri (0-4 ölçeği).
     * Not girilmemişse 0 döner.
     */
    public double ogrenciGano(String ogrenciNo) {
        Map<Ders, Not> notlari = ogrencininNotlari(ogrenciNo);
        if (notlari.isEmpty()) {
            return 0.0;
        }
        double toplamPuan = 0;
        int toplamKredi = 0;
        for (Map.Entry<Ders, Not> giris : notlari.entrySet()) {
            int kredi = giris.getKey().getKredi();
            HarfNotu harf = giris.getValue().harfNotu();
            toplamPuan += harf.getKatsayi() * kredi;
            toplamKredi += kredi;
        }
        return toplamPuan / toplamKredi;
    }

    /**
     * Bir dersin sınıf ortalaması: o dersten not almış tüm öğrencilerin
     * ortalamalarının aritmetik ortalaması. Kimse not almamışsa 0 döner.
     */
    public double dersOrtalamasi(String dersKodu) {
        Ders ders = dersBul(dersKodu);
        double toplam = 0;
        int sayac = 0;
        for (Map<String, Not> ogrencininNotlari : notlar.values()) {
            Not not = ogrencininNotlari.get(ders.getKod());
            if (not != null) {
                toplam += not.ortalama();
                sayac++;
            }
        }
        return sayac == 0 ? 0.0 : toplam / sayac;
    }

    /** Dersi alan öğrenci sayısı. */
    public int dersiAlanSayisi(String dersKodu) {
        Ders ders = dersBul(dersKodu);
        int sayac = 0;
        for (Map<String, Not> ogrencininNotlari : notlar.values()) {
            if (ogrencininNotlari.containsKey(ders.getKod())) {
                sayac++;
            }
        }
        return sayac;
    }

    /**
     * Tüm öğrencileri genel ortalamaya göre büyükten küçüğe sıralar.
     * Eşitlikte öğrenci numarasına göre sıralanır.
     */
    public List<Ogrenci> basariSiralamasi() {
        List<Ogrenci> liste = new ArrayList<>(ogrenciler.values());
        liste.sort(Comparator
                .comparingDouble((Ogrenci o) -> ogrenciOrtalamasi(o.getOgrenciNo())).reversed()
                .thenComparing(Ogrenci::getOgrenciNo));
        return liste;
    }

    /**
     * Belirli bir dersi alan öğrencileri o dersteki ortalamalarına göre
     * büyükten küçüğe sıralar.
     */
    public List<Ogrenci> basariSiralamasi(String dersKodu) {
        Ders ders = dersBul(dersKodu);
        List<Ogrenci> liste = new ArrayList<>();
        for (Ogrenci ogrenci : ogrenciler.values()) {
            Map<String, Not> ogrencininNotlari = notlar.get(ogrenci.getOgrenciNo());
            if (ogrencininNotlari != null && ogrencininNotlari.containsKey(ders.getKod())) {
                liste.add(ogrenci);
            }
        }
        liste.sort(Comparator
                .comparingDouble((Ogrenci o) -> notGetir(o.getOgrenciNo(), ders.getKod()).ortalama())
                .reversed()
                .thenComparing(Ogrenci::getOgrenciNo));
        return liste;
    }

    // -------------------------------------------------------------- rapor

    /** Öğrencinin tüm derslerini, notlarını ve genel ortalamasını içeren metin raporu. */
    public String ogrenciRaporu(String ogrenciNo) {
        Ogrenci ogrenci = ogrenciBul(ogrenciNo);
        StringBuilder sb = new StringBuilder();
        sb.append("=== ").append(ogrenci.rapor()).append(" ===").append(System.lineSeparator());
        Map<Ders, Not> notlari = ogrencininNotlari(ogrenciNo);
        if (notlari.isEmpty()) {
            sb.append("Henüz not girilmemiş.").append(System.lineSeparator());
            return sb.toString();
        }
        for (Map.Entry<Ders, Not> giris : notlari.entrySet()) {
            sb.append(String.format("%-8s %-30s %s", giris.getKey().getKod(),
                            giris.getKey().getAd(), giris.getValue()))
              .append(System.lineSeparator());
        }
        sb.append(String.format("Genel Ortalama: %.2f   GANO: %.2f",
                        ogrenciOrtalamasi(ogrenciNo), ogrenciGano(ogrenciNo)))
          .append(System.lineSeparator());
        return sb.toString();
    }

    /** Dosyaya kaydetme için tüm not kayıtlarının salt okunur görünümü. */
    public Map<String, Map<String, Not>> tumNotlar() {
        Map<String, Map<String, Not>> kopya = new LinkedHashMap<>();
        for (Map.Entry<String, Map<String, Not>> giris : notlar.entrySet()) {
            kopya.put(giris.getKey(), Collections.unmodifiableMap(giris.getValue()));
        }
        return Collections.unmodifiableMap(kopya);
    }
}
