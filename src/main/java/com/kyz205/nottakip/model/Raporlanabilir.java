package com.kyz205.nottakip.model;

/**
 * Kendisi hakkında tek satırlık özet rapor üretebilen nesneler için arayüz.
 * Ogrenci, Ogretmen ve Ders sınıfları bu arayüzü uygular; böylece
 * farklı tipteki nesneler aynı şekilde raporlanabilir (çok biçimlilik).
 */
public interface Raporlanabilir {

    /** Nesnenin okunabilir özetini döndürür. */
    String rapor();
}
