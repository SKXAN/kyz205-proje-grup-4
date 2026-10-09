package com.kyz205.nottakip.model;

/**
 * Harf notlarını ve karşılık gelen katsayıları tanımlar.
 * Sıralama önemlidir: hesapla() metodu en yüksek nottan başlayarak
 * ortalamanın ilk sağladığı alt sınırı seçer.
 */
public enum HarfNotu {
    AA(90, 4.0),
    BA(85, 3.5),
    BB(80, 3.0),
    CB(75, 2.5),
    CC(70, 2.0),
    DC(65, 1.5),
    DD(60, 1.0),
    FD(50, 0.5),
    FF(0, 0.0);

    private final int altSinir;
    private final double katsayi;

    HarfNotu(int altSinir, double katsayi) {
        this.altSinir = altSinir;
        this.katsayi = katsayi;
    }

    public int getAltSinir() {
        return altSinir;
    }

    public double getKatsayi() {
        return katsayi;
    }

    /** DD ve üzeri geçer notudur. */
    public boolean geciyorMu() {
        return katsayi >= 1.0;
    }

    /** 0-100 arası ortalamaya karşılık gelen harf notunu döndürür. */
    public static HarfNotu hesapla(double ortalama) {
        for (HarfNotu harf : values()) {
            if (ortalama >= harf.altSinir) {
                return harf;
            }
        }
        return FF;
    }
}
