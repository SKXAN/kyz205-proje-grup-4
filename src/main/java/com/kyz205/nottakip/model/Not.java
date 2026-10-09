package com.kyz205.nottakip.model;

/**
 * Bir öğrencinin bir dersteki vize ve final notlarını tutar;
 * ağırlıklı ortalama ve harf notunu hesaplar.
 */
public class Not {

    public static final double VIZE_AGIRLIGI = 0.4;
    public static final double FINAL_AGIRLIGI = 0.6;

    private double vize;
    private double finalNotu;

    public Not(double vize, double finalNotu) {
        setVize(vize);
        setFinalNotu(finalNotu);
    }

    private static double dogrula(double deger, String alanAdi) {
        if (deger < 0 || deger > 100) {
            throw new IllegalArgumentException(alanAdi + " 0 ile 100 arasında olmalıdır: " + deger);
        }
        return deger;
    }

    public double getVize() {
        return vize;
    }

    public void setVize(double vize) {
        this.vize = dogrula(vize, "Vize");
    }

    public double getFinalNotu() {
        return finalNotu;
    }

    public void setFinalNotu(double finalNotu) {
        this.finalNotu = dogrula(finalNotu, "Final");
    }

    /** Vize %40, final %60 ağırlıklı ortalama. */
    public double ortalama() {
        return vize * VIZE_AGIRLIGI + finalNotu * FINAL_AGIRLIGI;
    }

    public HarfNotu harfNotu() {
        return HarfNotu.hesapla(ortalama());
    }

    public boolean gectiMi() {
        return harfNotu().geciyorMu();
    }

    @Override
    public String toString() {
        return String.format("Vize: %5.1f  Final: %5.1f  Ortalama: %6.2f  Harf: %s",
                vize, finalNotu, ortalama(), harfNotu());
    }
}
