package org.example;

import static cabinetmedical.util.CryptoUtils.decrypt;
import static cabinetmedical.util.CryptoUtils.encrypt;

public class App
{
    public static void main(String[] args) {
        String cnpOriginal = "5010101123456";
        String cnpCriptat = encrypt(cnpOriginal);
        String cnpDecriptat = decrypt(cnpCriptat);

        System.out.println("Original: " + cnpOriginal);
        System.out.println("Criptat (cum va arata in CSV): " + cnpCriptat);
        System.out.println("Decriptat: " + cnpDecriptat);
    }
}
