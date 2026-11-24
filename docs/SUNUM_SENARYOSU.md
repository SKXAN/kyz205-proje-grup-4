# Sunum Senaryosu

Dönem sonu sunumu için konuşma planı, canlı gösterim adımları ve olası sorular.
Süre 10 dakika sunum + 5 dakika soru olarak planlanmıştır; verilen süreye göre
"Canlı gösterim" bölümü kısaltılıp uzatılabilir.

## 1. Akış ve süre

| # | Bölüm | Süre | Anlatan | Slayt |
|---|-------|------|---------|-------|
| 1 | Açılış: proje adı, ekip ve roller | 0:30 | Ayşe | Başlık, Ekip |
| 2 | Problem ve amaç, şartnamedeki gereksinimler | 1:00 | Ayşe | Problem, Gereksinimler |
| 3 | Tasarım: UML diyagramı ve paket yapısı | 1:30 | Kamer | UML |
| 4 | OOP ilkeleri koddan örneklerle | 1:30 | Kamer | OOP İlkeleri |
| 5 | Canlı gösterim | 3:00 | Kamer | (program ekranı) |
| 6 | Testler: birim testleri ve elle yapılan testler | 1:00 | Hasan | Testler |
| 7 | GitHub süreci: branch, commit, PR | 1:00 | Kamer | GitHub |
| 8 | Sonuç ve gelecek çalışmalar | 0:30 | Ayşe | Sonuç |

## 2. Slayt içerikleri

1. **Başlık:** Öğrenci Not Takip Sistemi, KYZ205, Grup 4, üye adları.
2. **Ekip ve roller:** README'deki ekip tablosu.
3. **Problem ve amaç:** Notların elle takibi hataya açık; sistem ortalama, harf notu,
   sıralama ve raporu otomatik üretir.
4. **Gereksinimler:** Şartnamedeki 5 minimum özellik ve karşılık gelen menü seçenekleri;
   yanında ek özellikler (silme, istatistik, harf dağılımı, CSV, otomatik kaydetme).
5. **UML sınıf diyagramı:** `docs/uml.png`. Vurgulanacaklar: `Kisi` kalıtımı,
   `Raporlanabilir` arayüzü, `NotDefteri` merkezde, dosya sınıfları ayrı.
6. **OOP ilkeleri:** Raporun 3.3 bölümündeki tablo; her ilke için bir satır kod.
7. **Testler:** 51 birim testi, test sınıfı tablosu, `mvnw.cmd test` çıktısı.
8. **GitHub süreci:** Branch yapısı, PR listesi, Insights → Contributors grafiği.
9. **Sonuç:** Neler yapıldı, ileride ne eklenebilir.

## 3. Canlı gösterim adımları

Hazırlık (sunumdan önce):

- Proje klasöründe `notdefteri.txt` varsa silin; program boş başlasın.
- `calistir.cmd` dosyasını bir kez çalıştırıp derlendiğinden emin olun, sonra kapatın.
- Konsol yazı boyutunu büyütün (başlık çubuğu → Özellikler → Yazı Tipi → 20).

Gösterim:

| Adım | Yazılacak | Söylenecek |
|------|-----------|------------|
| 1 | `calistir.cmd` | Program menüyle açılıyor. Seçenekler bir tablodan üretiliyor. |
| 2 | `11` | Hız için örnek veri yüklüyoruz: 3 ders, 3 öğrenci, 7 not. |
| 3 | `1` → `2024010`, kendi adınız, soyadınız | Yeni öğrenci ekliyoruz. |
| 4 | `3` → `2024010`, `KYZ205`, `150`, `90` | Geçersiz not: 0–100 dışı değer reddediliyor, program kapanmıyor. |
| 5 | `3` → `2024010`, `KYZ205`, `80`, `90` | Geçerli not: ortalama 86, harf notu BA anında hesaplanıyor. |
| 6 | `4` → `2024010` | Öğrenci raporu: dersler, ortalama ve GANO. |
| 7 | `6` | Genel başarı sıralaması, kredi ağırlıklı ortalamaya göre. |
| 8 | `14` → `KYZ205` | Ders istatistikleri: en yüksek, en düşük, başarı oranı. |
| 9 | `15` → `KYZ205` | Harf notu dağılımı, çubuk grafik. |
| 10 | `16` → `KYZ205` → Enter | CSV dosyası üretiliyor; Excel'de açıp gösterin. |
| 11 | `0` | Çıkışta veriler otomatik kaydediliyor. |
| 12 | `calistir.cmd`, sonra `8` | Yeniden açınca kayıtlar geri geliyor. |

Zaman daralırsa 8, 9 ve 12. adımlar atlanabilir.

Yedek plan: Program sunum bilgisayarında açılmazsa `docs/ekran-goruntuleri/`
klasöründeki görüntüler ve `docs/manuel-test.md` çıktıları gösterilir.

## 4. Olası sorular ve cevaplar

**Kalıtımı nerede kullandınız?**
`Ogrenci` ve `Ogretmen` sınıfları `Kisi` soyut sınıfından türüyor. Ad, soyad ve `tamAd()`
tek yerde yazılı; alt sınıflar yalnızca kendilerine özgü alanı (numara, branş) ekliyor.

**Neden `Kisi` soyut sınıf, `Raporlanabilir` ise arayüz?**
`Kisi` ortak veri (ad, soyad) ve ortak davranış taşıdığı için soyut sınıf. `Raporlanabilir`
ise veri taşımıyor, yalnızca "özetini verebilir" sözleşmesi. `Ders` bir kişi değil ama
raporlanabilir; arayüz olmasaydı bu ilişkiyi kuramazdık.

**Çok biçimlilik örneği gösterir misiniz?**
Menüdeki 8. seçenek dersleri ve öğrencileri aynı `rapor()` çağrısıyla yazdırıyor; hangi
sınıfın metodu çalışacağı nesnenin türüne göre belirleniyor. `Kisi.rol()` da öğrenci için
"Öğrenci", öğretim üyesi için "Öğretim Üyesi" döndürüyor.

**Kapsüllemeyi nasıl sağladınız?**
Bütün alanlar `private`. `Not` sınıfına 0–100 dışında değer verilemiyor, kontrol setter
içinde. `Ogrenci.getDersler()` listenin değiştirilemez görünümünü döndürüyor.

**Neden `HashMap` kullandınız, `ArrayList` yetmez miydi?**
Öğrenciyi numarasıyla, dersi koduyla arıyoruz. `HashMap` ile bu arama listenin tamamını
dolaşmadan yapılıyor. Sıralama gibi sıranın önemli olduğu yerlerde `ArrayList` kullanıyoruz.

**Notlar nerede tutuluyor?**
`NotDefteri` içinde iç içe iki `Map`: öğrenci numarası → (ders kodu → `Not`). Böylece bir
öğrencinin bir dersteki notuna iki adımda ulaşılıyor.

**Program kapanınca veriler kayboluyor mu?**
Hayır. Çıkışta `DosyaYoneticisi` verileri `notdefteri.txt` dosyasına yazıyor, açılışta
geri yüklüyor. Dosya düz metin, her satır bir kayıt.

**Kullanıcı hatalı giriş yaparsa ne oluyor?**
Model sınıfları `IllegalArgumentException`, arama işlemleri `KayitBulunamadiException`
fırlatıyor. `Main` bunları yakalayıp mesaj gösteriyor, program çalışmaya devam ediyor.

**Testleri nasıl yazdınız?**
JUnit 5 ile 51 test. Hesaplar (ortalama, harf notu, sıralama), dosya kaydet/yükle döngüsü
ve menünün kendisi test ediliyor. Menü testinde klavye yerine hazır girdi veriliyor,
ekrana yazılanlar kontrol ediliyor.

**Ortalama nasıl hesaplanıyor?**
Ders ortalaması vizenin %40'ı ile finalin %60'ı. Genel ortalama derslerin kredi ağırlıklı
ortalaması. GANO, harf notlarının 4'lük katsayılarının kredi ağırlıklı ortalaması.

**Projeyi nasıl paylaştınız?**
Her üyenin kendi branch'i var. `main`'e doğrudan yazılmıyor, her değişiklik Pull Request
ile giriyor. Hafta hafta yapılanlar `docs/HAFTALIK_ILERLEME.md` dosyasında.

## 5. Sunum öncesi kontrol listesi

- [ ] `mvnw.cmd test` çalıştırıldı, bütün testler geçiyor
- [ ] `docs/uml.png` üretildi ve slayta eklendi
- [ ] Ekran görüntüleri slaytlarda
- [ ] Canlı gösterim en az bir kez baştan sona prova edildi
- [ ] Sunum bilgisayarında Java kurulu ve `calistir.cmd` açılıyor
- [ ] Herkes kendi bölümünü ve olası soruları okudu
