package org.emilia;
/*
  @author emilia
  @project crypto_1_caesar_cipher
  @class BBS
  @version 1.0.0
  @since 15.03.2023 - 22:49
*/

import java.math.BigInteger;
import java.util.Scanner;

public class BBSStreamCipher {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        BigInteger p = new BigInteger("19");
        BigInteger q = new BigInteger("23");

        BigInteger n = p.multiply(q);

        //випадкове число x
        BigInteger x = new BigInteger("233");

        // Вводимо повідомлення, яке потрібно зашифрувати
        System.out.print("Enter the message you want to encrypt: ");
        String plaintext = scanner.nextLine();

        // шифрування
        StringBuilder ciphertext = new StringBuilder();
        StringBuilder outPut = new StringBuilder();
        for (char c : plaintext.toCharArray()) {
            // генерування чергового числа з послідовності BBS
            x = x.modPow(BigInteger.valueOf(2), n); //ключова формула
            char encryptedCharOutPut = (char) (c ^ x.intValue() % 97 ) ;
            char encryptedChar = (char) (c ^ x.intValue()) ;
            outPut.append(encryptedCharOutPut);
            ciphertext.append(encryptedChar);
        }

        System.out.println("Encrypted message: " + outPut);

        // розшифрування
        StringBuilder decryptedText = new StringBuilder();
        x = new BigInteger("233"); // починаєтьс з початкового значення x
        for (char c : ciphertext.toString().toCharArray()) {
            // генерування чергового числа з послідовності BBS
            x = x.modPow(BigInteger.valueOf(2), n);
            char decryptedChar = (char) (c ^ x.intValue());
            decryptedText.append(decryptedChar);
        }

        System.out.println("Decrypted message: " + decryptedText);

        scanner.close();
    }
}