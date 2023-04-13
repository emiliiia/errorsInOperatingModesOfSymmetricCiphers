package org.emilia;
/*
  @author emilia
  @project crypto_1_caesar_cipher
  @class AES_CTS
  @version 1.0.0
  @since 11.04.2023 - 2:09
*/

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

public class AES_CTS {
    private static final String ALGORITHM = "AES"; // Використовуваний алгоритм шифрування
    private static final String SECRET_KEY = "ThisIsASecretKey"; // Секретний ключ для шифрування (довжина 128 біт)
    private static final String IV = "ThisIsAnIV12345"; // Вектор ініціалізації (IV) для CTS режиму
    private static final String CIPHER_ALGORITHM = "AES/CTS/NoPadding"; // Режим шифрування CTS з використанням NoPadding (відсутність доповнення)

    private static final String REPLACEMENT = "Karlivna Dragomyretska";

    public static byte[] encrypt(String plaintext, SecretKeySpec secretKey, IvParameterSpec ivParameterSpec) throws Exception {
        Cipher cipherEncrypt = Cipher.getInstance(CIPHER_ALGORITHM); // Створення екземпляру шифру з використанням вказаного режиму
        cipherEncrypt.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec); // Ініціалізація шифру для режиму шифрування
        byte[] encryptedBytes = cipherEncrypt.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));

/*        //Заміна в 0-му блоку в режимі CBC
        System.out.println("Replacement in the 0st block");
        byte[] replacementBytes0 = REPLACEMENT.getBytes(StandardCharsets.UTF_8);
        System.arraycopy(replacementBytes0, 0, encryptedBytes, 0, 16);*/

/*        //Заміна в 1-му блоку в режимі CBC
        System.out.println("Replacement in the 1st block");
        byte[] replacementBytes1 = REPLACEMENT.getBytes(StandardCharsets.UTF_8);
        System.arraycopy(replacementBytes1, 0, encryptedBytes, 16, 16);*/

/*        //Заміна в передостаннього блоку в режимі CBC
        System.out.println("Replacement in the last but one block");
        byte[] replacementBytes1 = REPLACEMENT.getBytes(StandardCharsets.UTF_8);
        System.arraycopy(replacementBytes1, 0, encryptedBytes, encryptedBytes.length-40, 5);*/


/*
        //Заміна в останнього блоку в режимі CBC
        System.out.println("Replacement in the last block");
        byte[] replacementBytes1 = REPLACEMENT.getBytes(StandardCharsets.UTF_8);
        System.arraycopy(replacementBytes1, 0, encryptedBytes, encryptedBytes.length - 16, 16);
*/

        return  encryptedBytes;// Шифрування тексту в байтовому масиві
    }

    public static String decrypt(byte[] ciphertext, SecretKeySpec secretKey, IvParameterSpec ivParameterSpec) throws Exception {
        Cipher cipherDecrypt = Cipher.getInstance(CIPHER_ALGORITHM); // Створення екземпляру шифру з використанням вказаного режиму
        cipherDecrypt.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec); // Ініціалізація шифру для режиму розшифрування
        byte[] decryptedTextBytes = cipherDecrypt.doFinal(ciphertext); // Розшифрування байтового масиву
        return new String(decryptedTextBytes, StandardCharsets.UTF_8); // Перетворення розшифрованого байтового масиву в рядок
    }

    public static void main(String[] args) throws Exception {
        // Перетворення ключа з строки в байтовий масив довжини 16 байт
        byte[] keyBytes = Arrays.copyOf(SECRET_KEY.getBytes(StandardCharsets.UTF_8), 16);
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, ALGORITHM); // Створення секретного ключа

        // Перетворення вектора ініціалізації з строки в байтовий масив довжини 16 байт
        byte[] ivBytes = Arrays.copyOf(IV.getBytes(StandardCharsets.UTF_8), 16);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);

        String plaintext = "Emilia Emilia Emilia Emilia Karlivna Dragomyretska!"; // Початкові дані для шифрування
        System.out.println("Plaintext: " + plaintext); // Виведення початкових даних

        byte[] encryptedText = encrypt(plaintext, secretKey, ivParameterSpec);
        System.out.println("Encrypted Text: " + Base64.getEncoder().encodeToString(encryptedText)); // зашифрованих

        String decryptedText = decrypt(encryptedText, secretKey, ivParameterSpec);
        System.out.println("Decrypted Text: " + decryptedText); // розшифрованих
    }
}