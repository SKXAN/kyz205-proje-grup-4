package com.kyz205.nottakip;

import com.kyz205.nottakip.model.Ders;
import com.kyz205.nottakip.model.Ogrenci;
import com.kyz205.nottakip.model.Ogretmen;
import com.kyz205.nottakip.service.DosyaYoneticisi;
import com.kyz205.nottakip.service.KayitBulunamadiException;
import com.kyz205.nottakip.service.NotDefteri;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

/**
 * Konsol tabanlı kullanıcı arayüzü. Menü üzerinden NotDefteri işlemlerini sunar.
 */
public class Main {

    private static final Path VARSAYILAN_DOSYA = Path.of("notdefteri.txt");

    private final Scanner giris = new Scanner(System.in);
    private final DosyaYoneticisi dosya = new DosyaYoneticisi();
    private NotDefteri defter = new NotDefteri();

    public static void main(String[] args) {
        new Main().calistir();
    }

    private void calistir() {
        System.out.println("=== Öğrenci Not Takip Sistemi ===");
        boolean devam = true;
        while (devam) {
            menuyuYazdir();
            String secim = oku("Seçiminiz: ");
            try {
                devam = secimiIsle(secim);
            } catch (KayitBulunamadiException | IllegalArgumentException hata) {
                System.out.println("Hata: " + hata.getMessage());
            } catch (IOException hata) {
                System.out.println("Dosya hatası: " + hata.getMessage());
            }
            System.out.println();
        }
        System.out.println("Program sonlandırıldı.");
    }

    private void menuyuYazdir() {
        System.out.println();
        System.out.println("1) Öğrenci ekle");
        System.out.println("2) Öğretim üyesi ile ders ekle");
        System.out.println("3) Not ata (vize/final)");
        System.out.println("4) Öğrenci raporu");
        System.out.println("5) Ders sınıf ortalaması");
        System.out.println("6) Genel başarı sıralaması");
        System.out.println("7) Ders bazlı başarı sıralaması");
        System.out.println("8) Öğrenci ve ders listesi");
        System.out.println("9) Dosyaya kaydet");
        System.out.println("10) Dosyadan yükle");
        System.out.println("11) Örnek verileri yükle");
        System.out.println("0) Çıkış");
    }

    /** Menü seçimini işler; program devam edecekse true döner. */
    private boolean secimiIsle(String secim) throws IOException {
        switch (secim) {
            case "1" -> ogrenciEkle();
            case "2" -> dersEkle();
            case "3" -> notAta();
            case "4" -> ogrenciRaporu();
            case "5" -> dersOrtalamasi();
            case "6" -> genelSiralama();
            case "7" -> dersSiralamasi();
            case "8" -> listele();
            case "9" -> kaydet();
            case "10" -> yukle();
            case "11" -> ornekVeriYukle();
            case "0" -> {
                return false;
            }
            default -> System.out.println("Geçersiz seçim.");
        }
        return true;
    }

    // ------------------------------------------------------------ işlemler

    private void ogrenciEkle() {
        String no = oku("Öğrenci no: ");
        String ad = oku("Ad: ");
        String soyad = oku("Soyad: ");
        defter.ogrenciEkle(new Ogrenci(no, ad, soyad));
        System.out.println("Öğrenci eklendi.");
    }

    private void dersEkle() {
        String kod = oku("Ders kodu: ");
        String ad = oku("Ders adı: ");
        int kredi = okuTamSayi("Kredi: ");
        String hocaAd = oku("Öğretim üyesi adı: ");
        String hocaSoyad = oku("Öğretim üyesi soyadı: ");
        String brans = oku("Branş: ");
        Ogretmen hoca = new Ogretmen(hocaAd, hocaSoyad, brans);
        defter.dersEkle(new Ders(kod, ad, kredi, hoca));
        System.out.println("Ders eklendi.");
    }

    private void notAta() {
        String no = oku("Öğrenci no: ");
        String kod = oku("Ders kodu: ");
        double vize = okuOndalik("Vize (0-100): ");
        double finalNotu = okuOndalik("Final (0-100): ");
        var not = defter.notAta(no, kod, vize, finalNotu);
        System.out.println("Not kaydedildi -> " + not);
    }

    private void ogrenciRaporu() {
        String no = oku("Öğrenci no: ");
        System.out.print(defter.ogrenciRaporu(no));
    }

    private void dersOrtalamasi() {
        String kod = oku("Ders kodu: ");
        Ders ders = defter.dersBul(kod);
        int alan = defter.dersiAlanSayisi(kod);
        if (alan == 0) {
            System.out.println(ders + " dersine henüz not girilmemiş.");
            return;
        }
        System.out.printf("%s dersi sınıf ortalaması: %.2f (%d öğrenci)%n",
                ders, defter.dersOrtalamasi(kod), alan);
    }

    private void genelSiralama() {
        List<Ogrenci> sira = defter.basariSiralamasi();
        if (sira.isEmpty()) {
            System.out.println("Kayıtlı öğrenci yok.");
            return;
        }
        System.out.println("Sıra  No         Ad Soyad                 Ortalama  GANO");
        int i = 1;
        for (Ogrenci o : sira) {
            System.out.printf("%-5d %-10s %-24s %8.2f  %.2f%n", i++, o.getOgrenciNo(), o.tamAd(),
                    defter.ogrenciOrtalamasi(o.getOgrenciNo()), defter.ogrenciGano(o.getOgrenciNo()));
        }
    }

    private void dersSiralamasi() {
        String kod = oku("Ders kodu: ");
        Ders ders = defter.dersBul(kod);
        List<Ogrenci> sira = defter.basariSiralamasi(kod);
        if (sira.isEmpty()) {
            System.out.println(ders + " dersine henüz not girilmemiş.");
            return;
        }
        System.out.println(ders + " başarı sıralaması:");
        int i = 1;
        for (Ogrenci o : sira) {
            System.out.printf("%-5d %-10s %-24s %s%n", i++, o.getOgrenciNo(), o.tamAd(),
                    defter.notGetir(o.getOgrenciNo(), kod));
        }
    }

    private void listele() {
        System.out.println("-- Dersler --");
        if (defter.tumDersler().isEmpty()) {
            System.out.println("(yok)");
        }
        for (Ders d : defter.tumDersler()) {
            System.out.println(d.rapor());
        }
        System.out.println("-- Öğrenciler --");
        if (defter.tumOgrenciler().isEmpty()) {
            System.out.println("(yok)");
        }
        for (Ogrenci o : defter.tumOgrenciler()) {
            System.out.println(o.rapor());
        }
    }

    private void kaydet() throws IOException {
        Path hedef = dosyaYoluOku();
        dosya.kaydet(defter, hedef);
        System.out.println("Kaydedildi: " + hedef.toAbsolutePath());
    }

    private void yukle() throws IOException {
        Path kaynak = dosyaYoluOku();
        defter = dosya.yukle(kaynak);
        System.out.println("Yüklendi: " + defter.tumOgrenciler().size() + " öğrenci, "
                + defter.tumDersler().size() + " ders.");
    }

    private void ornekVeriYukle() {
        defter = ornekDefter();
        System.out.println("Örnek veriler yüklendi. 4 ve 6 numaralı menüleri deneyin.");
    }

    /** Hızlı deneme için küçük bir veri kümesi oluşturur. */
    static NotDefteri ornekDefter() {
        NotDefteri d = new NotDefteri();
        Ogretmen ayse = new Ogretmen("Ayşe", "Yılmaz", "Bilgisayar Mühendisliği");
        Ogretmen mehmet = new Ogretmen("Mehmet", "Kaya", "Matematik");
        d.dersEkle(new Ders("KYZ205", "Nesne Tabanlı Programlama", 4, ayse));
        d.dersEkle(new Ders("MAT101", "Matematik I", 3, mehmet));
        d.dersEkle(new Ders("KYZ101", "Programlamaya Giriş", 3, ayse));

        d.ogrenciEkle(new Ogrenci("2024001", "Ali", "Demir"));
        d.ogrenciEkle(new Ogrenci("2024002", "Zeynep", "Çelik"));
        d.ogrenciEkle(new Ogrenci("2024003", "Can", "Aydın"));

        d.notAta("2024001", "KYZ205", 85, 90);
        d.notAta("2024001", "MAT101", 70, 65);
        d.notAta("2024002", "KYZ205", 95, 92);
        d.notAta("2024002", "MAT101", 88, 91);
        d.notAta("2024002", "KYZ101", 80, 85);
        d.notAta("2024003", "KYZ205", 45, 55);
        d.notAta("2024003", "KYZ101", 60, 58);
        return d;
    }

    // ---------------------------------------------------------- yardımcılar

    private Path dosyaYoluOku() {
        String yol = oku("Dosya yolu [" + VARSAYILAN_DOSYA + "]: ");
        return yol.isBlank() ? VARSAYILAN_DOSYA : Path.of(yol);
    }

    private String oku(String mesaj) {
        System.out.print(mesaj);
        return giris.nextLine().trim();
    }

    private int okuTamSayi(String mesaj) {
        while (true) {
            String metin = oku(mesaj);
            try {
                return Integer.parseInt(metin);
            } catch (NumberFormatException e) {
                System.out.println("Lütfen tam sayı girin.");
            }
        }
    }

    private double okuOndalik(String mesaj) {
        while (true) {
            String metin = oku(mesaj).replace(',', '.');
            try {
                return Double.parseDouble(metin);
            } catch (NumberFormatException e) {
                System.out.println("Lütfen sayı girin.");
            }
        }
    }
}
