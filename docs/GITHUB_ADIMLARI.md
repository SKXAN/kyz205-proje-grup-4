# GitHub Adımları

Repo adı şartnameye göre `kyz205-proje-grup-4` olmalı (X = grup numaranız).
Bu klasörün adını da aynı şekilde değiştirin.

## 1. İlk kurulum (Kamer, bir kez)

### GitHub'da repo aç

1. github.com → sağ üst **+** → **New repository**
2. Repository name: `kyz205-proje-grup-4`
3. Private seçin ve eğitmeni **Settings → Collaborators** kısmından ekleyin, ya da Public bırakın.
4. "Add a README" kutusunu **işaretlemeyin** (README zaten var).
5. **Create repository**

### Yerel klasörü repoya bağla

Proje klasöründe terminal açın:

```bash
git init
git add .
git commit -m "Proje iskeleti: model, servis, testler ve README eklendi"
git branch -M main
git remote add origin https://github.com/SKXAN/kyz205-proje-grup-4.git
git push -u origin main
```

### Hasan ve Ayşe'yi ekle

GitHub → repo → **Settings → Collaborators → Add people** → sırayla `hasanenes06` ve `ayseeroglu`. İkisi de e-postadaki daveti kabul etmeli.

### Kendi branch'ini aç

```bash
git checkout -b kaan-gelistirme
git push -u origin kaan-gelistirme
```

## 2. Hasan ve Ayşe için ilk kurulum (bir kez)

Önce github.com'da hesap açın (yoksa), sonra Kamer'in gönderdiği daveti e-postadan kabul edin.
Bilgisayarınızda Git yüklü değilse https://git-scm.com/download/win adresinden kurun.

Hasan:

```bash
git clone https://github.com/SKXAN/kyz205-proje-grup-4.git
cd kyz205-proje-grup-4
git checkout hasan-test
```

Ayşe:

```bash
git clone https://github.com/SKXAN/kyz205-proje-grup-4.git
cd kyz205-proje-grup-4
git checkout ayse-dokumantasyon
```

İlk commit'ten önce bir kez adınızı tanıtın:

```bash
git config --global user.name "Ad Soyad"
git config --global user.email "github-epostaniz@ornek.com"
```

## 3. Her değişiklikte yapılacaklar (her iki üye)

```bash
git checkout hasan-test                  # kendi branch'ine geç (Ayşe: ayse-dokumantasyon)
git pull origin main                     # main'deki yenilikleri al
# ... dosyaları düzenle ...
git add .
git commit -m "README: ekip tablosuna ad soyad bilgileri eklendi"
git push
```

Sonra GitHub'da:

1. Repo sayfasında sarı **Compare & pull request** düğmesine bas.
2. Başlık yaz (commit mesajı gibi açıklayıcı), **Create pull request**.
3. Kamer PR'ı açar, **Files changed** sekmesinde bakar, **Merge pull request** → **Confirm merge**.

## 4. İyi commit mesajı örnekleri

Rubrik "fix", "update" gibi mesajlara puan vermiyor. Ne değiştiğini söyleyin:

| Kötü | İyi |
|------|-----|
| `update` | `NotDefteri: kredi ağırlıklı GANO hesabı eklendi` |
| `fix` | `Not: 100 üzeri değer girildiğinde IllegalArgumentException fırlatılıyor` |
| `readme` | `README: kullanım kılavuzuna örnek akış ve ekran görüntüsü eklendi` |
| `test` | `NotDefteriTest: ders bazlı sıralama için test eklendi` |
| `son hali` | `Main: menüye ders bazlı sıralama seçeneği (7) eklendi` |

Kalıp: `Dosya/Alan: ne yapıldı` şeklinde, Türkçe, geçmiş zaman.

## 5. Haftalık rutin

Eğitmen "haftada en az 1 commit" arıyor. Her hafta:

- **Kamer**: kod değişikliği veya refaktör → commit → PR → merge
- **Hasan**: [GOREV_DAGILIMI.md](GOREV_DAGILIMI.md) Hasan tablosundan bir görev → commit → PR
- **Ayşe**: [GOREV_DAGILIMI.md](GOREV_DAGILIMI.md) Ayşe tablosundan bir görev → commit → PR
- Kamer PR'ı merge eder

## 6. Sık karşılaşılan sorunlar

**"rejected: non-fast-forward"**
Önce `git pull origin main` çalıştırın, sonra tekrar `git push`.

**Çakışma (conflict)**
Dosyayı açın, `<<<<<<<`, `=======`, `>>>>>>>` işaretleri arasından doğru olanı bırakın,
işaretleri silin, `git add .` ve `git commit`.

**Yanlış branch'e commit attım**
```bash
git log --oneline -1          # commit hash'ini not al
git checkout dogru-branch
git cherry-pick HASH
```

**`target/` klasörü repoya girdi**
`.gitignore` zaten var. Yine de girdiyse: `git rm -r --cached target` ve commit.
