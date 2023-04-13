package org.emilia;
/*
  @author emilia
  @project crypto_1_caesar_cipher
  @class DES_CFB
  @version 1.0.0
  @since 11.04.2023 - 2:55
*/

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.security.spec.KeySpec;
import java.util.Base64;

public class DES_CFB {
    private static final String ALGORITHM = "DES";
    private static final String CIPHER_ALGORITHM = "DES/CFB/PKCS5Padding"; // Режим шифрування CFB з використанням PKCS5Padding
    private static final String INIT_VECTOR = "12345678"; // Вектор ініціалізації, повинен бути 8 байт
    private static final String SALT = "ThisIsASalt"; // Випадковий рядок, який додається до паролю перед шифруванням
    private static final String PASSWORD = "ThisIsAPassword";

    private static final String REPLACEMENT = "Karlivna Dragomyretska";

    public static String encrypt(String plaintext) throws Exception {
        // Перетворення пароля і солі в байтовий масив
        byte[] saltBytes = SALT.getBytes(StandardCharsets.UTF_8);
        byte[] passwordBytes = PASSWORD.getBytes(StandardCharsets.UTF_8);

        int iterationCount = 65536; // Кількість ітерацій для функції хешування
        int keySize = 64; // Розмір ключа в бітах

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1"); // Створення фабрики для генерації секретного ключа на основі PBKDF2
        KeySpec spec = new PBEKeySpec(new String(passwordBytes, StandardCharsets.UTF_8).toCharArray(), saltBytes, iterationCount, keySize); // Встановлення параметрів для генерації секретного ключа
        SecretKey tmp = factory.generateSecret(spec); // Генерація секретного ключа
        SecretKey secretKey = new SecretKeySpec(tmp.getEncoded(), ALGORITHM); // Створення секретного ключа з отриманого масиву байтів
        IvParameterSpec ivParameterSpec = new IvParameterSpec(INIT_VECTOR.getBytes(StandardCharsets.UTF_8)); // Створення об'єкту для встановлення вектора ініціалізації
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM); // Створення об'єкту шифрування
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec); // Ініціалізація шифрування з використанням режиму CBC, секретного ключа та вектора ініціалізації
        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes()); // Шифрування вхідних даних

/*        //Заміна в 0-му блоку в режимі CBC
        System.out.println("Replacement in the 0st block");
        byte[] replacementBytes0 = REPLACEMENT.getBytes(StandardCharsets.UTF_8);
        System.arraycopy(replacementBytes0, 0, encryptedBytes, 0, 16);*/

/*        //Заміна в 1-му блоку в режимі CBC
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
        System.arraycopy(replacementBytes1, 0, encryptedBytes, encryptedBytes.length - 17, 1);
*/

        return Base64.getEncoder().encodeToString(encryptedBytes); // Повернення зашифрованих даних у вигляді Base64-рядка
    }

    public static String decrypt(String ciphertext) throws Exception {
        // Перетворення пароля і солі в байтовий масив
        byte[] saltBytes = SALT.getBytes(StandardCharsets.UTF_8);
        byte[] passwordBytes = PASSWORD.getBytes(StandardCharsets.UTF_8);

        int iterationCount = 65536; // Кількість ітерацій для функції хешування
        int keySize = 64; // Розмір ключа в бітах

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1"); // Створення фабрики для генерації секретного ключа на основі PBKDF2
        KeySpec spec = new PBEKeySpec(new String(passwordBytes, StandardCharsets.UTF_8).toCharArray(), saltBytes, iterationCount, keySize); // Встановлення параметрів для генерації секретного ключа
        SecretKey tmp = factory.generateSecret(spec); // Генерація секретного ключа
        SecretKey secretKey = new SecretKeySpec(tmp.getEncoded(), ALGORITHM); // Створення секретного ключа з отриманого масиву байтів
        IvParameterSpec ivParameterSpec = new IvParameterSpec(INIT_VECTOR.getBytes(StandardCharsets.UTF_8)); // Створення об'єкту для встановлення вектора ініціалізації
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM); // Створення об'єкту шифрування
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec); // Ініціалізація дешифрування з використанням режиму CBC, секретного ключа та вектора ініціалізації
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(ciphertext)); // Дешифрування вхідних даних
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