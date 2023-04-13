package org.emilia;
/*
  @author emilia
  @project crypto_1_caesar_cipher
  @class DES_OFB
  @version 1.0.0
  @since 11.04.2023 - 2:55
*/

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.*;

public class DES_OFB {
    private static final String ALGORITHM = "DES";
    private static final String CIPHER_ALGORITHM = "DES/OFB/PKCS5Padding"; // Режим шифрування OFB з використанням PKCS5Padding
    private static final String INIT_VECTOR = "12345678"; // Вектор инициализации, должен быть 8 байт

    private static final String SALT = "ThisIsASalt"; // Випадковий рядок, який додається до паролю перед шифруванням
    private static final String PASSWORD = "ThisIsAPassword";
    private static final String REPLACEMENT = "Karlivna Dragomyretska";

    public static String encrypt(String plaintext) throws Exception {
        // Перетворення пароля і солі в байтовий масив
        byte[] saltBytes = SALT.getBytes(StandardCharsets.UTF_8);
        byte[] passwordBytes = PASSWORD.getBytes(StandardCharsets.UTF_8);

        int iterationCount = 65536; // Кількість ітерацій для функції хешування
        int keySize = 64; // Розмір ключа в бітах

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec spec = new PBEKeySpec(new String(passwordBytes, StandardCharsets.UTF_8).toCharArray(), saltBytes, iterationCount, keySize);
        SecretKey tmp = factory.generateSecret(spec);
        SecretKey secretKey = new SecretKeySpec(tmp.getEncoded(), ALGORITHM);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(INIT_VECTOR.getBytes(StandardCharsets.UTF_8));
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());

/*        //Заміна в 0-му блоку в режимі CBC
        System.out.println("Replacement in the 0st block");
        byte[] replacementBytes0 = REPLACEMENT.getBytes(StandardCharsets.UTF_8);
        System.arraycopy(replacementBytes0, 0, encryptedBytes, 0, 16);*/

/*       //Заміна в 1-му блоку в режимі CBC
        System.out.println("Replacement in the 1st block");
        byte[] replacementBytes1 = REPLACEMENT.getBytes(StandardCharsets.UTF_8);
        System.arraycopy(replacementBytes1, 0, encryptedBytes, 16, 16);*/

/*
        //Заміна в передостаннього блоку в режимі CBC
        System.out.println("Replacement in the last but one block");
        byte[] replacementBytes1 = REPLACEMENT.getBytes(StandardCharsets.UTF_8);
        System.arraycopy(replacementBytes1, 0, encryptedBytes, encryptedBytes.length-40, 5);
*/

/*
        //Заміна в останнього блоку в режимі CBC
        System.out.println("Replacement in the last block");
        byte[] replacementBytes1 = REPLACEMENT.getBytes(StandardCharsets.UTF_8);
        System.arraycopy(replacementBytes1, 0, encryptedBytes, encryptedBytes.length - 11, 1);
*/

        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String ciphertext) throws Exception {
        // Перетворення пароля і солі в байтовий масив
        byte[] saltBytes = SALT.getBytes(StandardCharsets.UTF_8);
        byte[] passwordBytes = PASSWORD.getBytes(StandardCharsets.UTF_8);

        int iterationCount = 65536; // Кількість ітерацій для функції хешування
        int keySize = 64; // Розмір ключа в бітах

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec spec = new PBEKeySpec(new String(passwordBytes, StandardCharsets.UTF_8).toCharArray(), saltBytes, iterationCount, keySize);
        SecretKey tmp = factory.generateSecret(spec);
        SecretKey secretKey = new SecretKeySpec(tmp.getEncoded(), ALGORITHM);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(INIT_VECTOR.getBytes(StandardCharsets.UTF_8));
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(ciphertext));
        return new String(decryptedBytes);
    }

    public static void main(String[] args) throws Exception {
        String plaintext = "Emilia Emilia Emilia Emilia Karlivna Dragomyretska!";
        System.out.println("Plaintext: " + plaintext);

        String encryptedText = encrypt(plaintext);
        System.out.println("Encrypted Text: " + encryptedText);

        String decryptedText = decrypt(encryptedText);
        System.out.println("Decrypted Text: " + decryptedText);
    }
}
