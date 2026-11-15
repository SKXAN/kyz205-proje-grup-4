package com.kyz205.nottakip.service;

import com.kyz205.nottakip.model.Ders;
import com.kyz205.nottakip.model.Not;
import com.kyz205.nottakip.model.Ogrenci;
import com.kyz205.nottakip.model.Ogretmen;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * NotDefteri içeriğini düz metin dosyasına kaydeder ve geri yükler (dosya G/Ç).
 *
 * Dosya biçimi, her satır bir kayıt, alanlar ';' ile ayrılır:
 * <pre>
 * DERS;KOD;Ad;kredi;ogretmenAd;ogretmenSoyad;brans
 * OGRENCI;no;ad;soyad
 * NOT;ogrenciNo;dersKodu;vize;final
 * </pre>
 * '#' ile başlayan satırlar yorumdur ve yoksayılır.
 *
 * Bir alanın içinde ';' veya '\' geçiyorsa önüne '\' konarak yazılır
 * (ör. "Demir;Kaya" -> "Demir\;Kaya"); okurken bu kaçış geri çözülür.
 * Böylece ad veya ders adında ayırıcı karakter bulunması dosyayı bozmaz.
 */
public class DosyaYoneticisi {

    private static final char AYIRICI = ';';
    private static final char KACIS = '\\';

    private static final int DERS_ALAN_SAYISI = 7;
    private static final int OGRENCI_ALAN_SAYISI = 4;
    private static final int NOT_ALAN_SAYISI = 5;

    public void kaydet(NotDefteri defter, Path dosya) throws IOException {
        try (BufferedWriter yazici = Files.newBufferedWriter(dosya, StandardCharsets.UTF_8)) {
            yazici.write("# Öğrenci Not Takip Sistemi veri dosyası");
            yazici.newLine();

            for (Ders ders : defter.tumDersler()) {
                Ogretmen hoca = ders.getOgretimUyesi();
                yazici.write(satir("DERS", ders.getKod(), ders.getAd(),
                        Integer.toString(ders.getKredi()),
                        hoca == null ? "" : hoca.getAd(),
                        hoca == null ? "" : hoca.getSoyad(),
                        hoca == null ? "" : hoca.getBrans()));
                yazici.newLine();
            }

            for (Ogrenci ogrenci : defter.tumOgrenciler()) {
                yazici.write(satir("OGRENCI", ogrenci.getOgrenciNo(),
                        ogrenci.getAd(), ogrenci.getSoyad()));
                yazici.newLine();
            }

            for (Map.Entry<String, Map<String, Not>> ogrenciGirisi : defter.tumNotlar().entrySet()) {
                for (Map.Entry<String, Not> notGirisi : ogrenciGirisi.getValue().entrySet()) {
                    Not not = notGirisi.getValue();
                    yazici.write(satir("NOT", ogrenciGirisi.getKey(),
                            notGirisi.getKey(),
                            Double.toString(not.getVize()),
                            Double.toString(not.getFinalNotu())));
                    yazici.newLine();
                }
            }
        }
    }

    public NotDefteri yukle(Path dosya) throws IOException {
        NotDefteri defter = new NotDefteri();
        try (BufferedReader okuyucu = Files.newBufferedReader(dosya, StandardCharsets.UTF_8)) {
            String satir;
            int satirNo = 0;
            while ((satir = okuyucu.readLine()) != null) {
                satirNo++;
                satir = satir.strip();
                if (satir.isEmpty() || satir.startsWith("#")) {
                    continue;
                }
                try {
                    satiriIsle(defter, satir);
                } catch (RuntimeException hata) {
                    throw new IOException("Satır " + satirNo + " okunamadı: " + hata.getMessage(), hata);
                }
            }
        }
        return defter;
    }

    private void satiriIsle(NotDefteri defter, String satir) {
        List<String> alanlar = alanlaraAyir(satir);
        String tur = alanlar.get(0);
        switch (tur) {
            case "DERS" -> {
                alanSayisiniDogrula(alanlar, DERS_ALAN_SAYISI, tur);
                Ogretmen hoca = alanlar.get(4).isBlank() ? null
                        : new Ogretmen(alanlar.get(4), alanlar.get(5), alanlar.get(6));
                defter.dersEkle(new Ders(alanlar.get(1), alanlar.get(2),
                        Integer.parseInt(alanlar.get(3)), hoca));
            }
            case "OGRENCI" -> {
                alanSayisiniDogrula(alanlar, OGRENCI_ALAN_SAYISI, tur);
                defter.ogrenciEkle(new Ogrenci(alanlar.get(1), alanlar.get(2), alanlar.get(3)));
            }
            case "NOT" -> {
                alanSayisiniDogrula(alanlar, NOT_ALAN_SAYISI, tur);
                defter.notAta(alanlar.get(1), alanlar.get(2),
                        Double.parseDouble(alanlar.get(3)), Double.parseDouble(alanlar.get(4)));
            }
            default -> throw new IllegalArgumentException("Bilinmeyen kayıt türü: " + tur);
        }
    }

    private static void alanSayisiniDogrula(List<String> alanlar, int beklenen, String tur) {
        if (alanlar.size() != beklenen) {
            throw new IllegalArgumentException(tur + " kaydı " + beklenen + " alan içermeli, "
                    + alanlar.size() + " alan bulundu.");
        }
    }

    // ------------------------------------------------------- kaçış işlemleri

    /** Alanları kaçışlayıp ayırıcı ile birleştirerek tek satır üretir. */
    private static String satir(String... alanlar) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < alanlar.length; i++) {
            if (i > 0) {
                sb.append(AYIRICI);
            }
            sb.append(kacisla(alanlar[i]));
        }
        return sb.toString();
    }

    /** Alandaki ayırıcı ve kaçış karakterlerinin önüne kaçış karakteri koyar. */
    static String kacisla(String alan) {
        StringBuilder sb = new StringBuilder(alan.length());
        for (int i = 0; i < alan.length(); i++) {
            char c = alan.charAt(i);
            if (c == AYIRICI || c == KACIS) {
                sb.append(KACIS);
            }
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * Satırı ayırıcıya göre alanlara böler. Kaçış karakterinden sonra gelen
     * karakter olduğu gibi alınır; böylece kaçışlanmış ayırıcılar alanı bölmez.
     * Sondaki boş alanlar korunur.
     */
    static List<String> alanlaraAyir(String satir) {
        List<String> alanlar = new ArrayList<>();
        StringBuilder alan = new StringBuilder();
        for (int i = 0; i < satir.length(); i++) {
            char c = satir.charAt(i);
            if (c == KACIS && i + 1 < satir.length()) {
                alan.append(satir.charAt(++i));
            } else if (c == AYIRICI) {
                alanlar.add(alan.toString());
                alan.setLength(0);
            } else {
                alan.append(c);
            }
        }
        alanlar.add(alan.toString());
        return alanlar;
    }
}
