# KYZ205 Proje Raporu: Öğrenci Not Takip Sistemi

**Grup:** 4
**Üyeler:** Kamer Kaan Şahin, Hasan Enes Yıldırım, Ayşe Eroğlu
**Repo:** https://github.com/SKXAN/kyz205-proje-grup-4
**Tarih:** [GG.AA.YYYY]

---

## 1. Proje Tanımı ve Amaç

[Ayşe doldurur]

Sistemin ne yaptığını 3–5 cümleyle anlat: hangi problemi çözüyor, kim kullanır,
girdi ve çıktılar neler. README'nin ilk paragrafından yararlanabilirsin.

## 2. Gereksinimler

[Ayşe doldurur]

Şartnamedeki "minimum özellikler" listesini maddeler halinde yaz ve her birinin
programda hangi menü seçeneğine karşılık geldiğini belirt.

| Gereksinim | Menü seçeneği |
|------------|---------------|
| Öğrenci kaydı (ad, no) | 1 |
| Ders tanımlama (ad, kredi, öğretim üyesi) | 2 |
| Öğrenciye ders ve not atama (vize, final) | 3 |
| Ortalama ve harf notu hesaplama | 4 |
| Sınıf ortalaması ve başarı sıralaması | 5, 6, 7 |

Şartnamenin ötesinde eklenen özellikler:

| Ek özellik | Menü seçeneği |
|------------|---------------|
| Dosyaya kaydetme, dosyadan yükleme, otomatik kaydet/yükle | 9, 10, açılış ve çıkış |
| Öğrenci ve ders silme | 12, 13 |
| Ders istatistikleri (en yüksek/en düşük not, başarı oranı) | 14 |
| Harf notu dağılımı (çubuk grafik) | 15 |
| CSV olarak dışa aktarma | 16 |

## 3. Tasarım

### 3.1 UML Sınıf Diyagramı

[docs/uml.png görselini buraya ekle]

Diyagramın kaynağı `docs/uml.puml` dosyasındadır; README'de Mermaid sürümü de bulunur.

### 3.2 Sınıfların Sorumlulukları

Kod iki pakete ayrılmıştır: `model` veri sınıflarını, `service` iş mantığını ve dosya
işlemlerini içerir. `Main` yalnızca kullanıcıyla konuşur, hesap yapmaz.

| Sınıf | Paket | Sorumluluk |
|-------|-------|------------|
| `Raporlanabilir` (arayüz) | model | Tek satırlık özet üretebilen nesnelerin ortak sözleşmesi |
| `Kisi` (soyut) | model | Ad, soyad; `Ogrenci` ve `Ogretmen` için ortak taban |
| `Ogrenci` | model | Öğrenci numarası ve aldığı dersler |
| `Ogretmen` | model | Branş bilgisi |
| `Ders` | model | Kod, ad, kredi, öğretim üyesi |
| `Not` | model | Vize, final, ağırlıklı ortalama, harf notu |
| `HarfNotu` (enum) | model | Harf aralıkları ve 4'lük katsayılar |
| `DersIstatistigi` | model | Bir dersin sınıf istatistiklerini taşıyan değiştirilemez nesne |
| `NotDefteri` | service | Koleksiyonlarla öğrenci-ders-not ilişkisi, tüm hesaplar ve raporlar |
| `DosyaYoneticisi` | service | Metin dosyasına kaydetme ve geri yükleme |
| `CsvDisaAktarici` | service | Sıralama ve ders notlarını CSV dosyasına yazma |
| `KayitBulunamadiException` | service | Olmayan öğrenci, ders veya not arandığında fırlatılan istisna |
| `Main` | (kök) | Konsol menüsü |

### 3.3 Uygulanan OOP İlkeleri

- **Kapsülleme:** Bütün alanlar `private` tanımlıdır. `Not` sınıfı vize ve finali yalnızca
  0–100 aralığında kabul eder; `Ogrenci.getDersler()` listenin değiştirilemez görünümünü
  döndürür, böylece ders listesi yalnızca `dersEkle` ve `dersCikar` ile değişebilir.
- **Kalıtım:** `Ogrenci` ve `Ogretmen`, `Kisi` soyut sınıfından türer; ad, soyad ve
  `tamAd()` tek yerde yazılmıştır.
- **Çok biçimlilik:** `Kisi.rol()` soyut metodu alt sınıflarda farklı değer döndürür.
  `Raporlanabilir.rapor()` metodu `Ogrenci`, `Ogretmen` ve `Ders` için farklı çalışır;
  menüdeki listeleme aynı çağrıyla hepsini yazdırır.
- **Soyutlama (soyut sınıf / arayüz):** `Kisi` doğrudan örneklenemez. `Raporlanabilir`
  arayüzü, birbirinden bağımsız sınıflara (`Kisi` ailesi ve `Ders`) ortak bir davranış kazandırır.
- **Koleksiyonlar:** `NotDefteri` öğrencileri ve dersleri ekleme sırasını koruyan
  `LinkedHashMap` içinde, notları iç içe `HashMap` içinde tutar. Sıralama için `ArrayList`
  ve `Comparator`, harf dağılımı için `EnumMap` kullanılır.
- **Dosya G/Ç:** `DosyaYoneticisi` verileri UTF-8 metin dosyasına yazar ve okur;
  `try-with-resources` ile dosya her durumda kapatılır. Alan içinde ayırıcı karakter
  geçmesi durumunda kaçış uygulanır. `CsvDisaAktarici` raporları Excel uyumlu CSV olarak üretir.
- **İstisna yönetimi:** Geçersiz girişler `IllegalArgumentException`, bulunamayan kayıtlar
  özel `KayitBulunamadiException` ile bildirilir; `Main` bunları yakalayıp kullanıcıya
  anlaşılır bir mesaj gösterir ve program kapanmaz.

## 4. Uygulama

### 4.1 Kullanılan Teknolojiler

| Araç | Kullanım amacı |
|------|----------------|
| Java 17 | Uygulama dili |
| Maven Wrapper (Maven 3.9.9) | Derleme ve test; ayrıca Maven kurulumu gerektirmez |
| JUnit 5 | Birim testleri |
| Git ve GitHub | Sürüm kontrolü, üye başına branch, Pull Request ile birleştirme |
| PlantUML ve Mermaid | UML sınıf diyagramı |

### 4.2 Önemli Kod Parçaları

**Ağırlıklı ortalama ve harf notu.** Ortalama vizenin %40'ı ile finalin %60'ının toplamıdır.
Harf notu, enum sabitlerinin büyükten küçüğe sıralı olmasından yararlanır: ortalamanın
sağladığı ilk alt sınır aranan harftir.

```java
public double ortalama() {
    return vize * VIZE_AGIRLIGI + finalNotu * FINAL_AGIRLIGI;
}

public static HarfNotu hesapla(double ortalama) {
    for (HarfNotu harf : values()) {
        if (ortalama >= harf.altSinir) {
            return harf;
        }
    }
    return FF;
}
```

**Başarı sıralaması.** Öğrenciler kredi ağırlıklı ortalamaya göre büyükten küçüğe,
eşitlik durumunda numaraya göre sıralanır.

```java
liste.sort(Comparator
        .comparingDouble((Ogrenci o) -> ogrenciOrtalamasi(o.getOgrenciNo())).reversed()
        .thenComparing(Ogrenci::getOgrenciNo));
```

**Harf dağılımı.** `EnumMap` önce bütün harfler için 0 ile doldurulur, sonra her not
için ilgili harfin sayacı bir artırılır; böylece hiç öğrencisi olmayan harfler de raporda görünür.

```java
dagilim.merge(not.harfNotu(), 1, Integer::sum);
```

### 4.3 Ekran Görüntüleri

[Menü, öğrenci raporu, başarı sıralaması, harf dağılımı: `docs/ekran-goruntuleri/` klasöründen eklenecek]

## 5. Test

Projede JUnit 5 ile yazılmış 45 birim testi vardır; tamamı geçmektedir.

| Test sınıfı | Test sayısı | Ne test ediyor |
|-------------|-------------|----------------|
| `NotTest` | 5 | Ağırlıklı ortalama, harf notu, geçme durumu, 0–100 doğrulaması |
| `HarfNotuTest` | 3 | Harf sınırları, sınırın hemen altı, katsayılar |
| `OgrenciTest` | 5 | Boş alan doğrulaması, ders ekleme ve çıkarma, eşitlik |
| `DersTest` | 4 | Kredi doğrulaması, ders kodunun büyük harfe çevrilmesi, rapor metni |
| `NotDefteriTest` | 17 | Not atama, silme, sınıf ortalaması, istatistikler, harf dağılımı, sıralama |
| `DosyaYoneticisiTest` | 6 | Kaydet/yükle döngüsü, ayırıcı karakter kaçışı, eksik alan, bozuk dosya |
| `CsvDisaAktariciTest` | 5 | CSV başlık ve satırları, özel karakter kaçışı, boş defter |

Birim testlerine ek olarak program elle de denenmiştir; koşum kayıtları
`docs/test-sonuclari.md`, elle yapılan denemelerin çıktıları `docs/manuel-test.md` dosyasındadır.

[`mvnw.cmd test` çıktısının ekran görüntüsü]

## 6. GitHub Süreci

- Branch yapısı: `main`, `kaan-gelistirme`, `hasan-test`, `ayse-dokumantasyon`
- `main` branch'ine doğrudan commit atılmaz; her değişiklik üyenin kendi branch'inden
  Pull Request ile birleştirilir.
- Çalışma haftalık commit'lerle yürütülmüştür. Hafta hafta yapılan işler ve ilgili PR'lar
  `docs/HAFTALIK_ILERLEME.md` dosyasında listelenir.
- Toplam commit ve PR sayısı: [teslimden önce repo → Insights sayfasından yazılacak]
- [Commit grafiği ekran görüntüsü: repo → Insights → Contributors]

## 7. Görev Dağılımı

| Üye | Roller | Yaptığı işler |
|-----|--------|---------------|
| Kamer Kaan Şahin | GitHub, Tasarım | Repo ve branch düzeni, PR birleştirme, UML, sınıf tasarımı, uygulama kodu ve birim testleri |
| Hasan Enes Yıldırım | Test/Kalite | Birim testlerinin koşulması ve kaydı, programın elle test edilmesi |
| Ayşe Eroğlu | Dokümantasyon | [Ayşe doldurur] |

## 8. Karşılaşılan Zorluklar ve Çözümler

[Ayşe doldurur: 2–3 madde. Aşağıdakiler teknik taraftan örneklerdir.]

- **Windows konsolunda Türkçe karakterler bozuk görünüyordu.** `calistir.cmd` içinde kod
  sayfası UTF-8 yapıldı (`chcp 65001`) ve Java'ya çıktı kodlaması parametreyle verildi.
- **Ad veya ders adında `;` geçince veri dosyası bozuluyordu.** Ayırıcı karakter için
  kaçış mekanizması eklendi ve alan sayısı doğrulaması yapıldı.
- **CSV dosyası Excel'de tek sütunda ve bozuk harflerle açılıyordu.** Ayırıcı `;`, ondalık
  ayırıcı `,` yapıldı ve dosyanın başına UTF-8 BOM eklendi.

## 9. Sonuç ve Gelecek Çalışmalar

Şartnamedeki bütün gereksinimler karşılanmış, üzerine silme, istatistik, harf dağılımı,
otomatik kaydetme ve CSV dışa aktarma eklenmiştir. İleride eklenebilecekler:

- Grafik arayüz (JavaFX veya Swing)
- Verilerin veritabanında tutulması
- Bütünleme sınavı ve devamsızlık takibi
- Dönem bazlı not dökümü (transkript)
