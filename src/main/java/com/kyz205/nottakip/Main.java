package com.kyz205.nottakip;

import com.kyz205.nottakip.model.Ders;
import com.kyz205.nottakip.model.Ogrenci;
import com.kyz205.nottakip.model.Ogretmen;
import com.kyz205.nottakip.service.CsvDisaAktarici;
import com.kyz205.nottakip.service.DosyaYoneticisi;
import com.kyz205.nottakip.service.KayitBulunamadiException;
import com.kyz205.nottakip.service.NotDefteri;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Konsol tabanlı kullanıcı arayüzü. Menü üzerinden NotDefteri işlemlerini sunar.
 *
 * Girdi ve çıktı akışları ile veri dosyası dışarıdan verilir; böylece sınıf
 * gerçek konsol olmadan da (birim testlerinde) çalıştırılabilir.
 */
public class Main {

    private static final Path VARSAYILAN_DOSYA = Path.of("notdefteri.txt");
    private static final String CIKIS_TUSU = "0";

    /** Menüdeki bir işlemin gövdesi; dosya işlemleri IOException fırlatabilir. */
    @FunctionalInterface
    private interface Islem {
        void calistir() throws IOException;
    }

    /** Menüde gösterilen tek bir seçenek: tuş, başlık ve çalıştırılacak işlem. */
    private record MenuSecenegi(String tus, String baslik, Islem islem) {
    }

    /** Okunacak girdi kalmadığında (dosya sonu, Ctrl+Z) döngüden çıkmak için kullanılır. */
    private static final class GirdiSonuException extends RuntimeException {
    }

    private final Scanner giris;
    private final PrintStream cikti;
    private final Path veriDosyasi;
    private final Map<String, MenuSecenegi> menu = new LinkedHashMap<>();
    private final DosyaYoneticisi dosya = new DosyaYoneticisi();
    private final CsvDisaAktarici csv = new CsvDisaAktarici();
    private NotDefteri defter = new NotDefteri();

    public static void main(String[] args) {
        new Main(System.in, System.out, VARSAYILAN_DOSYA).calistir();
    }

    Main(InputStream girdi, PrintStream cikti, Path veriDosyasi) {
        this.giris = new Scanner(girdi);
        this.cikti = cikti;
        this.veriDosyasi = veriDosyasi;
        menuyuKur();
    }

    /** Menü seçeneklerini gösterilecekleri sırayla tanımlar. */
    private void menuyuKur() {
        secenekEkle("1", "Öğrenci ekle", this::ogrenciEkle);
        secenekEkle("2", "Öğretim üyesi ile ders ekle", this::dersEkle);
        secenekEkle("3", "Not ata (vize/final)", this::notAta);
        secenekEkle("4", "Öğrenci raporu", this::ogrenciRaporu);
        secenekEkle("5", "Ders sınıf ortalaması", this::dersOrtalamasi);
        secenekEkle("6", "Genel başarı sıralaması", this::genelSiralama);
        secenekEkle("7", "Ders bazlı başarı sıralaması", this::dersSiralamasi);
        secenekEkle("8", "Öğrenci ve ders listesi", this::listele);
        secenekEkle("9", "Dosyaya kaydet", this::kaydet);
        secenekEkle("10", "Dosyadan yükle", this::yukle);
        secenekEkle("11", "Örnek verileri yükle", this::ornekVeriYukle);
        secenekEkle("12", "Öğrenci sil", this::ogrenciSil);
        secenekEkle("13", "Ders sil", this::dersSil);
        secenekEkle("14", "Ders istatistikleri", this::dersIstatistikleri);
        secenekEkle("15", "Harf notu dağılımı", this::harfDagilimi);
        secenekEkle("16", "CSV olarak dışa aktar", this::csvAktar);
    }

    private void secenekEkle(String tus, String baslik, Islem islem) {
        menu.put(tus, new MenuSecenegi(tus, baslik, islem));
    }

    void calistir() {
        cikti.println("=== Öğrenci Not Takip Sistemi ===");
        otomatikYukle();
        boolean devam = true;
        while (devam) {
            menuyuYazdir();
            try {
                devam = secimiIsle(oku("Seçiminiz: "));
            } catch (GirdiSonuException girdiBitti) {
                devam = false;
            } catch (KayitBulunamadiException | IllegalArgumentException hata) {
                cikti.println("Hata: " + hata.getMessage());
            } catch (IOException hata) {
                cikti.println("Dosya hatası: " + hata.getMessage());
            }
            cikti.println();
        }
        otomatikKaydet();
        cikti.println("Program sonlandırıldı.");
    }

    /** Açılışta veri dosyası varsa sessizce yükler. */
    private void otomatikYukle() {
        if (!Files.exists(veriDosyasi)) {
            return;
        }
        try {
            defter = dosya.yukle(veriDosyasi);
            cikti.println(veriDosyasi + " dosyasından " + defter.tumOgrenciler().size()
                    + " öğrenci, " + defter.tumDersler().size() + " ders yüklendi.");
        } catch (IOException hata) {
            cikti.println("Uyarı: " + veriDosyasi + " okunamadı, boş defterle başlanıyor. ("
                    + hata.getMessage() + ")");
        }
    }

    /** Çıkışta mevcut defteri veri dosyasına kaydeder. */
    private void otomatikKaydet() {
        try {
            dosya.kaydet(defter, veriDosyasi);
            cikti.println("Veriler " + veriDosyasi + " dosyasına kaydedildi.");
        } catch (IOException hata) {
            cikti.println("Uyarı: veriler kaydedilemedi. (" + hata.getMessage() + ")");
        }
    }

    private void menuyuYazdir() {
        cikti.println();
        for (MenuSecenegi secenek : menu.values()) {
            cikti.println(secenek.tus() + ") " + secenek.baslik());
        }
        cikti.println(CIKIS_TUSU + ") Çıkış (veriler otomatik kaydedilir)");
    }

    /** Menü seçimini işler; program devam edecekse true döner. */
    private boolean secimiIsle(String secim) throws IOException {
        if (CIKIS_TUSU.equals(secim)) {
            return false;
        }
        MenuSecenegi secenek = menu.get(secim);
        if (secenek == null) {
            cikti.println("Geçersiz seçim.");
            return true;
        }
        secenek.islem().calistir();
        return true;
    }

    // ------------------------------------------------------------ işlemler

    private void ogrenciEkle() {
        String no = oku("Öğrenci no: ");
        String ad = oku("Ad: ");
        String soyad = oku("Soyad: ");
        defter.ogrenciEkle(new Ogrenci(no, ad, soyad));
        cikti.println("Öğrenci eklendi.");
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
        cikti.println("Ders eklendi.");
    }

    private void ogrenciSil() {
        String no = oku("Silinecek öğrenci no: ");
        Ogrenci ogrenci = defter.ogrenciBul(no);
        String onay = oku(ogrenci.tamAd() + " ve tüm notları silinecek. Emin misiniz? (E/h): ");
        if (!onay.equalsIgnoreCase("E")) {
            cikti.println("İptal edildi.");
            return;
        }
        defter.ogrenciSil(no);
        cikti.println("Öğrenci silindi: " + ogrenci.tamAd());
    }

    private void dersSil() {
        String kod = oku("Silinecek ders kodu: ");
        Ders ders = defter.dersBul(kod);
        int alan = defter.dersiAlanSayisi(kod);
        String onay = oku(ders + " silinecek (" + alan + " öğrencinin notu da silinir). Emin misiniz? (E/h): ");
        if (!onay.equalsIgnoreCase("E")) {
            cikti.println("İptal edildi.");
            return;
        }
        defter.dersSil(kod);
        cikti.println("Ders silindi: " + ders);
    }

    private void dersIstatistikleri() {
        String kod = oku("Ders kodu: ");
        cikti.print(defter.dersRaporu(kod));
    }

    private void harfDagilimi() {
        String kod = oku("Ders kodu (tüm dersler için boş bırakın): ");
        if (kod.isBlank()) {
            cikti.print(defter.harfDagilimiRaporu());
        } else {
            cikti.print(defter.harfDagilimiRaporu(kod));
        }
    }

    private void csvAktar() throws IOException {
        String kod = oku("Ders kodu (genel sıralama için boş bırakın): ");
        Path hedef;
        if (kod.isBlank()) {
            hedef = csvYoluOku("siralama.csv");
            csv.siralamaAktar(defter, hedef);
        } else {
            Ders ders = defter.dersBul(kod);
            hedef = csvYoluOku(ders.getKod() + "-notlar.csv");
            csv.dersNotlariAktar(defter, ders.getKod(), hedef);
        }
        cikti.println("CSV dosyası oluşturuldu: " + hedef.toAbsolutePath());
    }

    private Path csvYoluOku(String varsayilan) {
        String yol = oku("Dosya yolu [" + varsayilan + "]: ");
        return yol.isBlank() ? Path.of(varsayilan) : Path.of(yol);
    }

    private void notAta() {
        String no = oku("Öğrenci no: ");
        String kod = oku("Ders kodu: ");
        double vize = okuOndalik("Vize (0-100): ");
        double finalNotu = okuOndalik("Final (0-100): ");
        var not = defter.notAta(no, kod, vize, finalNotu);
        cikti.println("Not kaydedildi -> " + not);
    }

    private void ogrenciRaporu() {
        String no = oku("Öğrenci no: ");
        cikti.print(defter.ogrenciRaporu(no));
    }

    private void dersOrtalamasi() {
        String kod = oku("Ders kodu: ");
        Ders ders = defter.dersBul(kod);
        int alan = defter.dersiAlanSayisi(kod);
        if (alan == 0) {
            cikti.println(ders + " dersine henüz not girilmemiş.");
            return;
        }
        cikti.printf("%s dersi sınıf ortalaması: %.2f (%d öğrenci)%n",
                ders, defter.dersOrtalamasi(kod), alan);
    }

    private void genelSiralama() {
        List<Ogrenci> sira = defter.basariSiralamasi();
        if (sira.isEmpty()) {
            cikti.println("Kayıtlı öğrenci yok.");
            return;
        }
        cikti.println("Sıra  No         Ad Soyad                 Ortalama  GANO");
        int i = 1;
        for (Ogrenci o : sira) {
            cikti.printf("%-5d %-10s %-24s %8.2f  %.2f%n", i++, o.getOgrenciNo(), o.tamAd(),
                    defter.ogrenciOrtalamasi(o.getOgrenciNo()), defter.ogrenciGano(o.getOgrenciNo()));
        }
    }

    private void dersSiralamasi() {
        String kod = oku("Ders kodu: ");
        Ders ders = defter.dersBul(kod);
        List<Ogrenci> sira = defter.basariSiralamasi(kod);
        if (sira.isEmpty()) {
            cikti.println(ders + " dersine henüz not girilmemiş.");
            return;
        }
        cikti.println(ders + " başarı sıralaması:");
        int i = 1;
        for (Ogrenci o : sira) {
            cikti.printf("%-5d %-10s %-24s %s%n", i++, o.getOgrenciNo(), o.tamAd(),
                    defter.notGetir(o.getOgrenciNo(), kod));
        }
    }

    private void listele() {
        cikti.println("-- Dersler --");
        if (defter.tumDersler().isEmpty()) {
            cikti.println("(yok)");
        }
        for (Ders d : defter.tumDersler()) {
            cikti.println(d.rapor());
        }
        cikti.println("-- Öğrenciler --");
        if (defter.tumOgrenciler().isEmpty()) {
            cikti.println("(yok)");
        }
        for (Ogrenci o : defter.tumOgrenciler()) {
            cikti.println(o.rapor());
        }
    }

    private void kaydet() throws IOException {
        Path hedef = dosyaYoluOku();
        dosya.kaydet(defter, hedef);
        cikti.println("Kaydedildi: " + hedef.toAbsolutePath());
    }

    private void yukle() throws IOException {
        Path kaynak = dosyaYoluOku();
        defter = dosya.yukle(kaynak);
        cikti.println("Yüklendi: " + defter.tumOgrenciler().size() + " öğrenci, "
                + defter.tumDersler().size() + " ders.");
    }

    private void ornekVeriYukle() {
        defter = ornekDefter();
        cikti.println("Örnek veriler yüklendi. 4 ve 6 numaralı menüleri deneyin.");
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
        String yol = oku("Dosya yolu [" + veriDosyasi + "]: ");
        return yol.isBlank() ? veriDosyasi : Path.of(yol);
    }

    /** Mesajı yazıp bir satır okur. Girdi bittiyse GirdiSonuException fırlatır. */
    private String oku(String mesaj) {
        cikti.print(mesaj);
        if (!giris.hasNextLine()) {
            throw new GirdiSonuException();
        }
        return giris.nextLine().trim();
    }

    private int okuTamSayi(String mesaj) {
        while (true) {
            String metin = oku(mesaj);
            try {
                return Integer.parseInt(metin);
            } catch (NumberFormatException e) {
                cikti.println("Lütfen tam sayı girin.");
            }
        }
    }

    private double okuOndalik(String mesaj) {
        while (true) {
            String metin = oku(mesaj).replace(',', '.');
            try {
                return Double.parseDouble(metin);
            } catch (NumberFormatException e) {
                cikti.println("Lütfen sayı girin.");
            }
        }
    }
}
