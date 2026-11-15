package com.kyz205.nottakip.service;

import com.kyz205.nottakip.model.Ders;
import com.kyz205.nottakip.model.Not;
import com.kyz205.nottakip.model.Ogrenci;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Raporları CSV dosyasına aktarır. Dosyalar Türkçe ayarlı Excel ile doğrudan
 * açılabilecek biçimdedir: alan ayırıcı ';', ondalık ayırıcı ',' ve Türkçe
 * karakterlerin doğru görünmesi için dosya başında UTF-8 BOM bulunur.
 */
public class CsvDisaAktarici {

    private static final String AYIRICI = ";";
    private static final char BOM = '﻿';
    private static final String SATIR_SONU = "\r\n";

    private final DecimalFormat ondalik;

    public CsvDisaAktarici() {
        DecimalFormatSymbols semboller = new DecimalFormatSymbols(Locale.ROOT);
        semboller.setDecimalSeparator(',');
        ondalik = new DecimalFormat("0.00", semboller);
    }

    /**
     * Genel başarı sıralamasını yazar: sıra, numara, ad, soyad, notu girilen
     * ders sayısı, kredi ağırlıklı ortalama ve GANO.
     */
    public void siralamaAktar(NotDefteri defter, Path dosya) throws IOException {
        List<String> satirlar = new ArrayList<>();
        satirlar.add(satir("Sıra", "Öğrenci No", "Ad", "Soyad", "Ders Sayısı", "Ortalama", "GANO"));
        int sira = 1;
        for (Ogrenci ogrenci : defter.basariSiralamasi()) {
            String no = ogrenci.getOgrenciNo();
            satirlar.add(satir(
                    Integer.toString(sira++),
                    no,
                    ogrenci.getAd(),
                    ogrenci.getSoyad(),
                    Integer.toString(defter.ogrencininNotlari(no).size()),
                    ondalik.format(defter.ogrenciOrtalamasi(no)),
                    ondalik.format(defter.ogrenciGano(no))));
        }
        yaz(dosya, satirlar);
    }

    /**
     * Bir dersin not listesini başarı sırasına göre yazar: sıra, numara,
     * ad soyad, vize, final, ortalama, harf notu ve geçme durumu.
     * Ders kayıtlı değilse KayitBulunamadiException fırlatır.
     */
    public void dersNotlariAktar(NotDefteri defter, String dersKodu, Path dosya) throws IOException {
        Ders ders = defter.dersBul(dersKodu);
        List<String> satirlar = new ArrayList<>();
        satirlar.add(satir("Sıra", "Öğrenci No", "Ad Soyad", "Vize", "Final", "Ortalama", "Harf", "Durum"));
        int sira = 1;
        for (Ogrenci ogrenci : defter.basariSiralamasi(ders.getKod())) {
            Not not = defter.notGetir(ogrenci.getOgrenciNo(), ders.getKod());
            satirlar.add(satir(
                    Integer.toString(sira++),
                    ogrenci.getOgrenciNo(),
                    ogrenci.tamAd(),
                    ondalik.format(not.getVize()),
                    ondalik.format(not.getFinalNotu()),
                    ondalik.format(not.ortalama()),
                    not.harfNotu().name(),
                    not.gectiMi() ? "Geçti" : "Kaldı"));
        }
        yaz(dosya, satirlar);
    }

    /**
     * Ayırıcı, çift tırnak veya satır sonu içeren alanı CSV kurallarına göre
     * çift tırnak içine alır; alandaki çift tırnaklar ikilenir.
     */
    static String kacis(String alan) {
        boolean tirnakGerekli = alan.contains(AYIRICI) || alan.contains("\"")
                || alan.contains("\n") || alan.contains("\r");
        if (!tirnakGerekli) {
            return alan;
        }
        return "\"" + alan.replace("\"", "\"\"") + "\"";
    }

    private static String satir(String... alanlar) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < alanlar.length; i++) {
            if (i > 0) {
                sb.append(AYIRICI);
            }
            sb.append(kacis(alanlar[i]));
        }
        return sb.toString();
    }

    private static void yaz(Path dosya, List<String> satirlar) throws IOException {
        try (BufferedWriter yazici = Files.newBufferedWriter(dosya, StandardCharsets.UTF_8)) {
            yazici.write(BOM);
            for (String satir : satirlar) {
                yazici.write(satir);
                yazici.write(SATIR_SONU);
            }
        }
    }
}
