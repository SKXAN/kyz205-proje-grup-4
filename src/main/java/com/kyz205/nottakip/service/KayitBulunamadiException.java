package com.kyz205.nottakip.service;

/**
 * Aranan öğrenci, ders veya not kaydı bulunamadığında fırlatılır.
 */
public class KayitBulunamadiException extends RuntimeException {

    public KayitBulunamadiException(String mesaj) {
        super(mesaj);
    }
}
