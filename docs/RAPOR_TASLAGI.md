# KYZ205 Proje Raporu: Öğrenci Not Takip Sistemi

**Grup:** 4
**Üyeler:** Kamer Kaan Şahin, Hasan Enes Yıldırım
**Repo:** https://github.com/SKXAN/kyz205-proje-grup-4
**Tarih:** [GG.AA.YYYY]

---

## 1. Proje Tanımı ve Amaç

[Hasan doldurur]

Sistemin ne yaptığını 3–5 cümleyle anlat: hangi problemi çözüyor, kim kullanır,
girdi ve çıktılar neler. README'nin ilk paragrafından yararlanabilirsin.

## 2. Gereksinimler

[Hasan doldurur]

Şartnamedeki "minimum özellikler" listesini maddeler halinde yaz ve her birinin
programda hangi menü seçeneğine karşılık geldiğini belirt.

| Gereksinim | Menü seçeneği |
|------------|---------------|
| Öğrenci kaydı (ad, no) | 1 |
| Ders tanımlama (ad, kredi, öğretim üyesi) | 2 |
| Öğrenciye ders ve not atama (vize, final) | 3 |
| Ortalama ve harf notu hesaplama | 4 |
| Sınıf ortalaması ve başarı sıralaması | 5, 6, 7 |

## 3. Tasarım

### 3.1 UML Sınıf Diyagramı

[docs/uml.png görselini buraya ekle]

### 3.2 Sınıfların Sorumlulukları

| Sınıf | Sorumluluk |
|-------|------------|
| `Kisi` (soyut) | Ad, soyad; alt sınıflar için ortak taban |
| `Ogrenci` | Öğrenci numarası ve aldığı dersler |
| `Ogretmen` | Branş bilgisi |
| `Ders` | Kod, ad, kredi, öğretim üyesi |
| `Not` | Vize, final, ortalama, harf notu |
| `HarfNotu` (enum) | Harf aralıkları ve katsayılar |
| `NotDefteri` | Koleksiyonlarla öğrenci-ders-not ilişkisi, hesaplar |
| `DosyaYoneticisi` | Metin dosyasına kaydet / yükle |
| `Main` | Konsol menüsü |

### 3.3 Uygulanan OOP İlkeleri

Her ilke için kodda nerede kullanıldığını bir cümleyle açıkla:

- **Kapsülleme:** ...
- **Kalıtım:** ...
- **Çok biçimlilik:** ...
- **Soyutlama (soyut sınıf / arayüz):** ...
- **Koleksiyonlar:** ...
- **Dosya G/Ç:** ...

## 4. Uygulama

### 4.1 Kullanılan Teknolojiler

Java 17, Maven, JUnit 5, Git/GitHub.

### 4.2 Önemli Kod Parçaları

Kısa kod örnekleri ve açıklamaları (örneğin `Not.ortalama()` ve `HarfNotu.hesapla()`).

### 4.3 Ekran Görüntüleri

[Menü, öğrenci raporu, başarı sıralaması]

## 5. Test

| Test sınıfı | Test sayısı | Ne test ediyor |
|-------------|-------------|----------------|
| `NotTest` | 5 | Ortalama, harf notu, doğrulama |
| `HarfNotuTest` | 3 | Harf sınırları, katsayılar |
| `NotDefteriTest` | 9 | Kayıt, hesaplar, sıralama |
| `DosyaYoneticisiTest` | 3 | Kaydet/yükle |

[`mvnw.cmd test` çıktısının ekran görüntüsü]

## 6. GitHub Süreci

- Branch yapısı: `main`, `kaan-gelistirme`, `hasan-dokumantasyon`
- Toplam commit sayısı: [...]
- PR sayısı: [...]
- [Commit grafiği ekran görüntüsü: repo → Insights → Contributors]

## 7. Görev Dağılımı

| Üye | Roller | Yaptığı işler |
|-----|--------|---------------|
| Kamer Kaan Şahin | GitHub, Tasarım | ... |
| Hasan Enes Yıldırım | Dokümantasyon, Test/Kalite | ... |

## 8. Karşılaşılan Zorluklar ve Çözümler

[2–3 madde. Örnek: Windows konsolunda Türkçe karakter sorunu, UTF-8 ayarıyla çözüldü.]

## 9. Sonuç ve Gelecek Çalışmalar

Neler başarıldı, ne eklenebilir (grafik arayüz, veritabanı, devamsızlık takibi vb.).
