# Görev Dağılımı ve Haftalık Plan

2 kişilik grup. Şartnameye göre her üye iki rol üstlenir.

## Roller

| Üye | Birincil roller | Sorumluluk |
|-----|-----------------|------------|
| **Kamer Kaan Şahin** | GitHub Sorumlusu, Tasarım Sorumlusu | Repo ve branch düzeni, PR inceleme ve merge, UML, sınıf tasarımı, çekirdek kod |
| **Hasan Enes Yıldırım** | Dokümantasyon Sorumlusu, Test/Kalite Sorumlusu | README içeriği, rapor, sunum, testlerin çalıştırılması ve sonuçların kaydı |

## Çalışma düzeni

- `main` branch'i korunur, doğrudan commit atılmaz.
- Kamer `kaan-gelistirme`, Hasan `hasan-dokumantasyon` branch'inde çalışır.
- Her değişiklik Pull Request ile `main`'e birleşir. PR'ları Kamer inceler ve merge eder.
- Her üye **her hafta en az 1 commit** atar (GitHub puanının 5'i buna bağlı).
- Commit mesajları açıklayıcı olur. Örnekler [GITHUB_ADIMLARI.md](GITHUB_ADIMLARI.md) içinde.

## Hasan için somut, sınırlı görevler

Her görev tek başına yapılabilir, 15–30 dakika sürer ve kendi branch'inden commit edilir.

| # | Görev | Dosya | Nasıl |
|---|-------|-------|-------|
| 1 | Programı ilk kez çalıştır ve çıktıyı kaydet | `docs/ilk-deneme.md` | `calistir.cmd` ile aç, 11 → 6 → 4 (öğrenci no 2024001) → 0 adımlarını uygula, ekrandaki metni dosyaya yapıştır |
| 2 | Programı çalıştır, menü ekran görüntüsü al | `docs/ekran-goruntuleri/` | `calistir.cmd` ile aç, 11 → 6 → 4 adımlarını çalıştır, ekran görüntülerini klasöre kaydet |
| 3 | Ekran görüntülerini README'ye ekle | `README.md` | Kullanım Kılavuzu bölümüne `![menü](docs/ekran-goruntuleri/menu.png)` satırları ekle |
| 4 | Testleri çalıştır, sonucu kaydet | `docs/test-sonucu.md` | Proje klasöründe `mvnw.cmd test` çalıştır, çıktının son 10 satırını kopyala, tarih yaz |
| 5 | UML görselini üret ve repoya ekle | `docs/uml.png` | `docs/uml.puml` dosyasını plantuml.com/plantuml adresine yapıştır, PNG indir |
| 6 | Rapor taslağının 1. ve 2. bölümünü yaz | `docs/RAPOR_TASLAGI.md` | Proje tanımı ve amaç kısımlarını doldur |
| 7 | Sunum slaytlarının ilk 3 sayfası | `docs/sunum.pptx` | Başlık, ekip, proje amacı |
| 8 | Basit bir test ekle | `src/test/.../OgrenciTest.java` | Kamer'in hazırladığı şablonu doldur: öğrenci numarası boşsa hata verdiğini doğrula |

## Haftalık plan (6 hafta)

| Hafta | Kamer | Hasan |
|-------|-------|-------|
| 1 | Repo açma, iskelet kodun ilk commit'i, branch'ler | Repoyu klonla, kendi branch'ine geç, ilk deneme (görev 1) |
| 2 | UML'yi gözden geçir, model sınıflarını tamamla | UML PNG üret ve ekle (görev 5) |
| 3 | NotDefteri hesapları, dosya G/Ç | Programı çalıştır, ekran görüntüleri (görev 2, 3) |
| 4 | Testleri genişlet, hata düzeltme, refaktör | Testleri çalıştır, sonucu kaydet (görev 4), basit test (görev 8) |
| 5 | Kod temizliği, yorumlar, son PR'lar | Rapor bölümleri (görev 6) |
| 6 | Sunum teknik kısmı, demo hazırlığı | Sunum ilk sayfaları (görev 7), rapor son okuma |

## Akran değerlendirmesi

Her üye diğerine katkı puanı verir ve bu puan çarpan olarak nota yansır (5 → ×1.00, 4 → ×0.90).
Görevlerin zamanında tamamlanması ve her hafta commit atılması bu puanı korur.
