## Manuel Test (04.11.2026)

Test adımları: 11 → 6 → 4 (öğrenci no: 2024001) → 14 (ders kodu: KYZ205) → 15 (ders kodu: KYZ205) → 0

```
=== Öğrenci Not Takip Sistemi ===

1) Öğrenci ekle
2) Öğretim üyesi ile ders ekle
3) Not ata (vize/final)
4) Öğrenci raporu
5) Ders sınıf ortalaması
6) Genel başarı sıralaması
7) Ders bazlı başarı sıralaması
8) Öğrenci ve ders listesi
9) Dosyaya kaydet
10) Dosyadan yükle
11) Örnek verileri yükle
12) Öğrenci sil
13) Ders sil
14) Ders istatistikleri
15) Harf notu dağılımı
0) Çıkış (veriler otomatik kaydedilir)
Seçiminiz: Örnek veriler yüklendi. 4 ve 6 numaralı menüleri deneyin.


1) Öğrenci ekle
2) Öğretim üyesi ile ders ekle
3) Not ata (vize/final)
4) Öğrenci raporu
5) Ders sınıf ortalaması
6) Genel başarı sıralaması
7) Ders bazlı başarı sıralaması
8) Öğrenci ve ders listesi
9) Dosyaya kaydet
10) Dosyadan yükle
11) Örnek verileri yükle
12) Öğrenci sil
13) Ders sil
14) Ders istatistikleri
15) Harf notu dağılımı
0) Çıkış (veriler otomatik kaydedilir)
Seçiminiz: Sıra  No         Ad Soyad                 Ortalama  GANO
1     2024002    Zeynep Çelik                89,12  3,55
2     2024001    Ali Demir                   79,00  2,64
3     2024003    Can Aydın                   54,34  0,50


1) Öğrenci ekle
2) Öğretim üyesi ile ders ekle
3) Not ata (vize/final)
4) Öğrenci raporu
5) Ders sınıf ortalaması
6) Genel başarı sıralaması
7) Ders bazlı başarı sıralaması
8) Öğrenci ve ders listesi
9) Dosyaya kaydet
10) Dosyadan yükle
11) Örnek verileri yükle
12) Öğrenci sil
13) Ders sil
14) Ders istatistikleri
15) Harf notu dağılımı
0) Çıkış (veriler otomatik kaydedilir)
Seçiminiz: Öğrenci no: === 2024001 - Ali Demir (2 ders) ===
KYZ205   Nesne Tabanlı Programlama      Vize:  85,0  Final:  90,0  Ortalama:  88,00  Harf: BA
MAT101   Matematik I                    Vize:  70,0  Final:  65,0  Ortalama:  67,00  Harf: DC
Genel Ortalama: 79,00   GANO: 2,64


1) Öğrenci ekle
2) Öğretim üyesi ile ders ekle
3) Not ata (vize/final)
4) Öğrenci raporu
5) Ders sınıf ortalaması
6) Genel başarı sıralaması
7) Ders bazlı başarı sıralaması
8) Öğrenci ve ders listesi
9) Dosyaya kaydet
10) Dosyadan yükle
11) Örnek verileri yükle
12) Öğrenci sil
13) Ders sil
14) Ders istatistikleri
15) Harf notu dağılımı
0) Çıkış (veriler otomatik kaydedilir)
Seçiminiz: Ders kodu: === KYZ205 Nesne Tabanlı Programlama (4 kredi) - Ayşe Yılmaz ===
Öğrenci sayısı : 3
Sınıf ortalaması: 77,40
En yüksek       : 93,20 (Zeynep Çelik)
En düşük        : 51,00 (Can Aydın)
Geçen / Kalan   : 2 / 1
Başarı oranı    : %66,7


1) Öğrenci ekle
2) Öğretim üyesi ile ders ekle
3) Not ata (vize/final)
4) Öğrenci raporu
5) Ders sınıf ortalaması
6) Genel başarı sıralaması
7) Ders bazlı başarı sıralaması
8) Öğrenci ve ders listesi
9) Dosyaya kaydet
10) Dosyadan yükle
11) Örnek verileri yükle
12) Öğrenci sil
13) Ders sil
14) Ders istatistikleri
15) Harf notu dağılımı
0) Çıkış (veriler otomatik kaydedilir)
Seçiminiz: Ders kodu (tüm dersler için boş bırakın): === Harf Dağılımı: KYZ205 Nesne Tabanlı Programlama ===
AA    1   33,3%  *
BA    1   33,3%  *
BB    0    0,0%  
CB    0    0,0%  
CC    0    0,0%  
DC    0    0,0%  
DD    0    0,0%  
FD    1   33,3%  *
FF    0    0,0%  
Toplam not sayısı: 3


1) Öğrenci ekle
2) Öğretim üyesi ile ders ekle
3) Not ata (vize/final)
4) Öğrenci raporu
5) Ders sınıf ortalaması
6) Genel başarı sıralaması
7) Ders bazlı başarı sıralaması
8) Öğrenci ve ders listesi
9) Dosyaya kaydet
10) Dosyadan yükle
11) Örnek verileri yükle
12) Öğrenci sil
13) Ders sil
14) Ders istatistikleri
15) Harf notu dağılımı
0) Çıkış (veriler otomatik kaydedilir)
Seçiminiz: 
Veriler notdefteri.txt dosyasına kaydedildi.
Program sonlandırıldı.
```


## Hatalı Giriş ve Yeni Özellik Testi (17.11.2026)

Test adımları ve kontroller:
- `11`: Örnek veriler yüklendi.
- `3`: Öğrenci `2024001`, ders `KYZ205`, vize `150` girildiğinde sistemin `Hata: Vize 0 ile 100 arasında olmalıdır: 150.0` uyarısı verdiği doğrulandı.
- `4`: Olmayan öğrenci numarası `9999` girildiğinde `Hata: Öğrenci bulunamadı: 9999` uyarısının alındığı doğrulandı.
- `14` & `15`: KYZ205 ders istatistikleri ve harf notu dağılım grafiği test edildi.
- `16`: KYZ205 dersi için CSV dışa aktarımı çalıştırıldı ve `KYZ205-notlar.csv` dosyası üretildi.
- Üretilen CSV dosyası Excel'de açıldı; noktalı virgül (;) ayırıcısı, ondalık virgül (,) ve UTF-8 BOM desteği sayesinde sütunlar düzgün ayrılmış ve Türkçe karakterler Excel'de hatasız görüntülenmiştir.

```
=== Öğrenci Not Takip Sistemi ===

1) Öğrenci ekle
2) Öğretim üyesi ile ders ekle
3) Not ata (vize/final)
4) Öğrenci raporu
5) Ders sınıf ortalaması
6) Genel başarı sıralaması
7) Ders bazlı başarı sıralaması
8) Öğrenci ve ders listesi
9) Dosyaya kaydet
10) Dosyadan yükle
11) Örnek verileri yükle
12) Öğrenci sil
13) Ders sil
14) Ders istatistikleri
15) Harf notu dağılımı
16) CSV olarak dışa aktar
0) Çıkış (veriler otomatik kaydedilir)
Seçiminiz: Örnek veriler yüklendi. 4 ve 6 numaralı menüleri deneyin.


1) Öğrenci ekle
2) Öğretim üyesi ile ders ekle
3) Not ata (vize/final)
4) Öğrenci raporu
5) Ders sınıf ortalaması
6) Genel başarı sıralaması
7) Ders bazlı başarı sıralaması
8) Öğrenci ve ders listesi
9) Dosyaya kaydet
10) Dosyadan yükle
11) Örnek verileri yükle
12) Öğrenci sil
13) Ders sil
14) Ders istatistikleri
15) Harf notu dağılımı
16) CSV olarak dışa aktar
0) Çıkış (veriler otomatik kaydedilir)
Seçiminiz: Öğrenci no: Ders kodu: Vize (0-100): Final (0-100): Hata: Vize 0 ile 100 arasında olmalıdır: 150.0


1) Öğrenci ekle
2) Öğretim üyesi ile ders ekle
3) Not ata (vize/final)
4) Öğrenci raporu
5) Ders sınıf ortalaması
6) Genel başarı sıralaması
7) Ders bazlı başarı sıralaması
8) Öğrenci ve ders listesi
9) Dosyaya kaydet
10) Dosyadan yükle
11) Örnek verileri yükle
12) Öğrenci sil
13) Ders sil
14) Ders istatistikleri
15) Harf notu dağılımı
16) CSV olarak dışa aktar
0) Çıkış (veriler otomatik kaydedilir)
Seçiminiz: Öğrenci no: Hata: Öğrenci bulunamadı: 9999


1) Öğrenci ekle
2) Öğretim üyesi ile ders ekle
3) Not ata (vize/final)
4) Öğrenci raporu
5) Ders sınıf ortalaması
6) Genel başarı sıralaması
7) Ders bazlı başarı sıralaması
8) Öğrenci ve ders listesi
9) Dosyaya kaydet
10) Dosyadan yükle
11) Örnek verileri yükle
12) Öğrenci sil
13) Ders sil
14) Ders istatistikleri
15) Harf notu dağılımı
16) CSV olarak dışa aktar
0) Çıkış (veriler otomatik kaydedilir)
Seçiminiz: Ders kodu: === KYZ205 Nesne Tabanlı Programlama (4 kredi) - Ayşe Yılmaz ===
Öğrenci sayısı : 3
Sınıf ortalaması: 77,40
En yüksek       : 93,20 (Zeynep Çelik)
En düşük        : 51,00 (Can Aydın)
Geçen / Kalan   : 2 / 1
Başarı oranı    : %66,7


1) Öğrenci ekle
2) Öğretim üyesi ile ders ekle
3) Not ata (vize/final)
4) Öğrenci raporu
5) Ders sınıf ortalaması
6) Genel başarı sıralaması
7) Ders bazlı başarı sıralaması
8) Öğrenci ve ders listesi
9) Dosyaya kaydet
10) Dosyadan yükle
11) Örnek verileri yükle
12) Öğrenci sil
13) Ders sil
14) Ders istatistikleri
15) Harf notu dağılımı
16) CSV olarak dışa aktar
0) Çıkış (veriler otomatik kaydedilir)
Seçiminiz: Ders kodu (tüm dersler için boş bırakın): === Harf Dağılımı: KYZ205 Nesne Tabanlı Programlama ===
AA    1   33,3%  *
BA    1   33,3%  *
BB    0    0,0%  
CB    0    0,0%  
CC    0    0,0%  
DC    0    0,0%  
DD    0    0,0%  
FD    1   33,3%  *
FF    0    0,0%  
Toplam not sayısı: 3


1) Öğrenci ekle
2) Öğretim üyesi ile ders ekle
3) Not ata (vize/final)
4) Öğrenci raporu
5) Ders sınıf ortalaması
6) Genel başarı sıralaması
7) Ders bazlı başarı sıralaması
8) Öğrenci ve ders listesi
9) Dosyaya kaydet
10) Dosyadan yükle
11) Örnek verileri yükle
12) Öğrenci sil
13) Ders sil
14) Ders istatistikleri
15) Harf notu dağılımı
16) CSV olarak dışa aktar
0) Çıkış (veriler otomatik kaydedilir)
Seçiminiz: Ders kodu (genel sıralama için boş bırakın): Dosya yolu [KYZ205-notlar.csv]: CSV dosyası oluşturuldu: KYZ205-notlar.csv


1) Öğrenci ekle
2) Öğretim üyesi ile ders ekle
3) Not ata (vize/final)
4) Öğrenci raporu
5) Ders sınıf ortalaması
6) Genel başarı sıralaması
7) Ders bazlı başarı sıralaması
8) Öğrenci ve ders listesi
9) Dosyaya kaydet
10) Dosyadan yükle
11) Örnek verileri yükle
12) Öğrenci sil
13) Ders sil
14) Ders istatistikleri
15) Harf notu dağılımı
16) CSV olarak dışa aktar
0) Çıkış (veriler otomatik kaydedilir)
Seçiminiz: 
Veriler notdefteri.txt dosyasına kaydedildi.
Program sonlandırıldı.
```
