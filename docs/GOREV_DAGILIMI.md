# Görev Dağılımı ve Haftalık Plan

2 kişilik grup. Şartnameye göre her üye iki rol üstlenir.

## Roller

| Üye | Birincil roller | Sorumluluk |
|-----|-----------------|------------|
| **Üye 1** | GitHub Sorumlusu, Tasarım Sorumlusu | Repo ve branch düzeni, PR inceleme ve merge, UML, sınıf tasarımı, çekirdek kod |
| **Üye 2** | Dokümantasyon Sorumlusu, Test/Kalite Sorumlusu | README içeriği, rapor, sunum, testlerin çalıştırılması ve sonuçların kaydı |

## Çalışma düzeni

- `main` branch'i korunur, doğrudan commit atılmaz.
- Üye 1 `kaan-gelistirme`, Üye 2 `uye2-dokumantasyon` branch'inde çalışır.
- Her değişiklik Pull Request ile `main`'e birleşir. PR'ları Üye 1 inceler ve merge eder.
- Her üye **her hafta en az 1 commit** atar (GitHub puanının 5'i buna bağlı).
- Commit mesajları açıklayıcı olur. Örnekler [GITHUB_ADIMLARI.md](GITHUB_ADIMLARI.md) içinde.

## Üye 2 için somut, sınırlı görevler

Her görev tek başına yapılabilir, 15–30 dakika sürer ve kendi branch'inden commit edilir.

| # | Görev | Dosya | Nasıl |
|---|-------|-------|-------|
| 1 | README'deki ekip tablosuna kendi adını yaz | `README.md` | `[Ad Soyad 2]` yerine ad soyadını yaz |
| 2 | Programı çalıştır, menü ekran görüntüsü al | `docs/ekran-goruntuleri/` | `calistir.cmd` ile aç, 11 → 6 → 4 adımlarını çalıştır, ekran görüntülerini klasöre kaydet |
| 3 | Ekran görüntülerini README'ye ekle | `README.md` | Kullanım Kılavuzu bölümüne `![menü](docs/ekran-goruntuleri/menu.png)` satırları ekle |
| 4 | Testleri çalıştır, sonucu kaydet | `docs/test-sonucu.md` | Proje klasöründe `mvnw.cmd test` çalıştır, çıktının son 10 satırını kopyala, tarih yaz |
| 5 | UML görselini üret ve repoya ekle | `docs/uml.png` | `docs/uml.puml` dosyasını plantuml.com/plantuml adresine yapıştır, PNG indir |
| 6 | Rapor taslağının 1. ve 2. bölümünü yaz | `docs/RAPOR_TASLAGI.md` | Proje tanımı ve amaç kısımlarını doldur |
| 7 | Sunum slaytlarının ilk 3 sayfası | `docs/sunum.pptx` | Başlık, ekip, proje amacı |
| 8 | Basit bir test ekle | `src/test/.../OgrenciTest.java` | Üye 1'in hazırladığı şablonu doldur: öğrenci numarası boşsa hata verdiğini doğrula |

## Haftalık plan (6 hafta)

| Hafta | Üye 1 | Üye 2 |
|-------|-------|-------|
| 1 | Repo açma, iskelet kodun ilk commit'i, branch'ler | Repoyu klonla, kendi branch'ini aç, README ekip tablosu (görev 1) |
| 2 | UML'yi gözden geçir, model sınıflarını tamamla | UML PNG üret ve ekle (görev 5) |
| 3 | NotDefteri hesapları, dosya G/Ç | Programı çalıştır, ekran görüntüleri (görev 2, 3) |
| 4 | Testleri genişlet, hata düzeltme, refaktör | Testleri çalıştır, sonucu kaydet (görev 4), basit test (görev 8) |
| 5 | Kod temizliği, yorumlar, son PR'lar | Rapor bölümleri (görev 6) |
| 6 | Sunum teknik kısmı, demo hazırlığı | Sunum ilk sayfaları (görev 7), rapor son okuma |

## Akran değerlendirmesi

Her üye diğerine katkı puanı verir ve bu puan çarpan olarak nota yansır (5 → ×1.00, 4 → ×0.90).
Görevlerin zamanında tamamlanması ve her hafta commit atılması bu puanı korur.
