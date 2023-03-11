package org.emilia;
/*
  @author emilia
  @project crypto_1_caesar_cipher
  @class RSA
  @version 1.0.0
  @since 08.03.2023 - 20:42
*/

import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;

public class RSA
{
    private BigInteger p; // Перше велике просте число
    private BigInteger q; // Друге велике просте число
    private BigInteger n; // Модуль, добуток двох простих чисел p та q
    private BigInteger phi; // Функція Ейлера (φ(N) = (p - 1) * (q - 1))
    private BigInteger E; // Публічний ключ
    private BigInteger D; // Приватний ключ
    private int bitlength = 1024; // Довжина ключа
    private Random r;

    public RSA()
    {
        r = new Random();
        p = BigInteger.probablePrime(bitlength, r); // Генерація випадкового простого числа p
        q = BigInteger.probablePrime(bitlength, r); // Генерація випадкового простого числа q
        n = p.multiply(q); // Обчислення модуля
        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)); // Обчислення функції Ейлера
        E = BigInteger.probablePrime(bitlength / 2, r); // Генерація випадкового публічного ключа
        while (phi.gcd(E).compareTo(BigInteger.ONE) > 0 && E.compareTo(phi) < 0)
        {
            E.add(BigInteger.ONE); // Пошук публічного ключа, який не має спільних дільників з функцією Ейлера
        }
        D = E.modInverse(phi); // Обчислення приватного ключа за допомогою оберненого елементу до публічного ключа відносно функції Ейлера
    }

    public RSA(BigInteger E, BigInteger D, BigInteger n)
    {
        this.E = E;
        this.D = D;
        this.n = n;
    }

    public static void main(String[] args) throws IOException
    {
        RSA rsa = new RSA(); // Генерація нової пари ключів
        DataInputStream in = new DataInputStream(System.in);
        String testString;
        System.out.println("Enter the plain text:");
        testString = in.readLine();
        //System.out.println("Encrypting String: " + testString);
        System.out.println("String in Bytes: "
                + bytesToString(testString.getBytes()));
        // encrypt
        byte[] encrypted = rsa.encrypt(testString.getBytes()); // Шифрування повідомлення
        System.out.println("Encrypted: " + rsa.encrypt(testString.getBytes()));
        // decrypt
        byte[] decrypted = rsa.decrypt(encrypted); // Розшифрування повідомлення
        System.out.println("Decrypting Bytes: " + bytesToString(decrypted));
        System.out.println("Decrypted String: " + new String(decrypted));
    }

    // конвертує масив байтів в рядок, де кожен байт представлений своєю десятковою формою
    private static String bytesToString(byte[] encrypted)
    {
        String test = "";
        for (byte b : encrypted)
        {
            test += Byte.toString(b);
        }
        return test;
    }


    // байт повідомлення конвертується в об'єкт BigInteger, який потім шифрується з використанням публічного ключа e і модуля N
    public byte[] encrypt(byte[] message)
    {
        return  (new BigInteger(message)).modPow(E, n).toByteArray();
    }

    //зашифрований текст конвертується в об'єкт BigInteger, який потім дешифрується з використанням приватного ключа d і модуля N
    public byte[] decrypt(byte[] message)
    {
        return (new BigInteger(message)).modPow(D, n).toByteArray();
    }
}