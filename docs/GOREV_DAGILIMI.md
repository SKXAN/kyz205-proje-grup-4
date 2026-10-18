# Görev Dağılımı ve Haftalık Plan

3 kişilik grup. Şartnameye göre her üye bir birincil rol üstlenir, kalan sorumluluklar ortaklaşa paylaşılır.

## Roller

| Üye | Birincil rol | Sorumluluk |
|-----|--------------|------------|
| **Kamer Kaan Şahin** (`SKXAN`) | GitHub Sorumlusu, Tasarım Sorumlusu | Repo ve branch düzeni, PR inceleme ve merge, UML, sınıf tasarımı, çekirdek kod |
| **Hasan Enes Yıldırım** (`hasanenes06`) | Test/Kalite Sorumlusu | Testlerin çalıştırılması, sonuçların kaydı, hata bildirimi |
| **Ayşe Eroğlu** (`ayseeroglu`) | Dokümantasyon Sorumlusu | README içeriği, rapor, sunum |

Kamer iki rol üstlenmiştir; GitHub ve tasarım işleri birbirine bağlı olduğu için tek kişide toplanması iş akışını kolaylaştırır.

## Çalışma düzeni

- `main` branch'i korunur, doğrudan commit atılmaz.
- Her üye kendi branch'inde çalışır:
  - Kamer: `kaan-gelistirme`
  - Hasan: `hasan-test`
  - Ayşe: `ayse-dokumantasyon`
- Her değişiklik Pull Request ile `main`'e birleşir. PR'ları Kamer inceler ve merge eder.
- Her üye **her hafta en az 1 commit** atar (GitHub puanının 5'i buna bağlı).
- Commit mesajları açıklayıcı olur. Örnekler [GITHUB_ADIMLARI.md](GITHUB_ADIMLARI.md) içinde.

## Hasan için görevler (Test/Kalite)

Her görev tek başına yapılabilir, 10–20 dakika sürer ve `hasan-test` branch'inden commit edilir.

| # | Görev | Dosya | Nasıl |
|---|-------|-------|-------|
| H1 | Testleri ilk kez çalıştır, sonucu kaydet | `docs/test-sonuclari.md` | Proje klasöründe `mvnw.cmd test` çalıştır, çıktının son 10 satırını dosyaya yapıştır, üstüne tarih yaz |
| H2 | Programı elle test et | `docs/manuel-test.md` | `calistir.cmd` ile aç, 11 → 6 → 4 (no: 2024001) → 5 (KYZ205) → 0 adımlarını uygula, her adımın çıktısını dosyaya yapıştır |
| H3 | Hatalı giriş testi | `docs/manuel-test.md` | Nota 150 gir, olmayan öğrenci numarası gir, boş ad gir; alınan hata mesajlarını dosyaya ekle |
| H4 | Her hafta testleri yeniden çalıştır | `docs/test-sonuclari.md` | Yeni kod geldikçe `mvnw.cmd test` çalıştır, tarih ve sonucu dosyanın altına ekle |
| H5 | Basit bir test ekle | `src/test/.../OgrenciTest.java` | Kamer'in hazırladığı şablonu doldur: boş öğrenci numarasının hata verdiğini doğrula |

## Ayşe için görevler (Dokümantasyon)

Her görev tek başına yapılabilir, 15–30 dakika sürer ve `ayse-dokumantasyon` branch'inden commit edilir.

| # | Görev | Dosya | Nasıl |
|---|-------|-------|-------|
| A1 | Ekran görüntüleri | `docs/ekran-goruntuleri/` | Programı çalıştır, menü / öğrenci raporu / sıralama ekranlarının görüntüsünü al, klasöre kaydet |
| A2 | Ekran görüntülerini README'ye ekle | `README.md` | Kullanım Kılavuzu bölümüne `![menü](docs/ekran-goruntuleri/menu.png)` satırları ekle |
| A3 | UML görselini üret | `docs/uml.png` | `docs/uml.puml` içeriğini plantuml.com/plantuml adresine yapıştır, PNG indir, repoya ekle |
| A4 | Raporun 1. ve 2. bölümü | `docs/RAPOR_TASLAGI.md` | Proje tanımı ve gereksinimler bölümlerini doldur |
| A5 | Raporun 7. ve 8. bölümü | `docs/RAPOR_TASLAGI.md` | Görev dağılımı tablosu ve karşılaşılan zorluklar |
| A6 | Sunum | `docs/sunum.pptx` | Başlık, ekip, amaç, özellikler, UML, ekran görüntüleri, test sonuçları (8–10 sayfa) |

## Haftalık plan

| Hafta | Kamer | Hasan | Ayşe |
|-------|-------|-------|------|
| 1 | Repo, iskelet kod, branch'ler | Repoyu klonla, branch'ine geç, H1 | Repoyu klonla, branch'ine geç, A1 |
| 2 | Silme işlemleri, otomatik kaydet/yükle, ek testler | H2 | A2 |
| 3 | Ders istatistik raporu | H3 | A3 |
| 4 | Harf notu dağılımı | H4 | A4 |
| 5 | CSV dışa aktarma | H4, H5 | A5 |
| 6 | Refaktör, kod yorumları, son PR'lar | H4 | A6 |
| 7 | Sunum teknik kısmı, demo provası | Rapor son okuma | Sunum son hali |

## Akran değerlendirmesi

Her üye diğer üyelere 1–5 arası katkı puanı verir ve bu puan proje notuna çarpan olarak yansır
(5 → ×1.00, 4 → ×0.90, 3 → ×0.75). Görevlerin zamanında tamamlanması ve her hafta commit atılması bu puanı korur.
