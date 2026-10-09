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
 */
public class DosyaYoneticisi {

    private static final String AYIRICI = ";";

    public void kaydet(NotDefteri defter, Path dosya) throws IOException {
        try (BufferedWriter yazici = Files.newBufferedWriter(dosya, StandardCharsets.UTF_8)) {
            yazici.write("# Öğrenci Not Takip Sistemi veri dosyası");
            yazici.newLine();

            for (Ders ders : defter.tumDersler()) {
                Ogretmen hoca = ders.getOgretimUyesi();
                yazici.write(String.join(AYIRICI, "DERS", ders.getKod(), ders.getAd(),
                        Integer.toString(ders.getKredi()),
                        hoca == null ? "" : hoca.getAd(),
                        hoca == null ? "" : hoca.getSoyad(),
                        hoca == null ? "" : hoca.getBrans()));
                yazici.newLine();
            }

            for (Ogrenci ogrenci : defter.tumOgrenciler()) {
                yazici.write(String.join(AYIRICI, "OGRENCI", ogrenci.getOgrenciNo(),
                        ogrenci.getAd(), ogrenci.getSoyad()));
                yazici.newLine();
            }

            for (Map.Entry<String, Map<String, Not>> ogrenciGirisi : defter.tumNotlar().entrySet()) {
                for (Map.Entry<String, Not> notGirisi : ogrenciGirisi.getValue().entrySet()) {
                    Not not = notGirisi.getValue();
                    yazici.write(String.join(AYIRICI, "NOT", ogrenciGirisi.getKey(),
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
        String[] alanlar = satir.split(AYIRICI, -1);
        switch (alanlar[0]) {
            case "DERS" -> {
                Ogretmen hoca = alanlar[4].isBlank() ? null
                        : new Ogretmen(alanlar[4], alanlar[5], alanlar[6]);
                defter.dersEkle(new Ders(alanlar[1], alanlar[2], Integer.parseInt(alanlar[3]), hoca));
            }
            case "OGRENCI" -> defter.ogrenciEkle(new Ogrenci(alanlar[1], alanlar[2], alanlar[3]));
            case "NOT" -> defter.notAta(alanlar[1], alanlar[2],
                    Double.parseDouble(alanlar[3]), Double.parseDouble(alanlar[4]));
            default -> throw new IllegalArgumentException("Bilinmeyen kayıt türü: " + alanlar[0]);
        }
    }
}
