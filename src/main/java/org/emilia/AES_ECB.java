package org.emilia;
/*
  @author emilia
  @project crypto_1_caesar_cipher
  @class AES_ECB
  @version 1.0.0
  @since 11.04.2023 - 2:01
*/

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class AES_ECB {
    private static final String ALGORITHM = "AES";
    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding"; // Режим шифрування ECB з використанням PKCS5Padding
    private static final String SECRET_KEY = "0123456789abcdef"; // 128-бітовий ключ шифрування
    private static final String REPLACEMENT = "Karlivna Dragomyretska";

    public static String encrypt(String plaintext) throws Exception {
        SecretKey secretKey = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM); // Створення секретного ключа з використанням AES
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM); // Ініціалізація Cipher з використанням AES/ECB/PKCS5Padding
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);  // Ініціалізація шифрування з використанням секретного ключа
        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());  // Шифрування даних

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
        System.arraycopy(replacementBytes1, 0, encryptedBytes, encryptedBytes.length - 25, 1);
*/


        return Base64.getEncoder().encodeToString(encryptedBytes);  // Повернення зашифрованих даних у вигляді Base64 рядка
    }

    public static String decrypt(String ciphertext) throws Exception {
        SecretKey secretKey = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM); // Створення секретного ключа з використанням AES
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM); // Ініціалізація Cipher з використанням AES/ECB/PKCS5Padding
        cipher.init(Cipher.DECRYPT_MODE, secretKey);  // Ініціалізація розшифрування з використанням секретного ключа
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(ciphertext));  // Розшифрування даних
        return new String(decryptedBytes);  // Повернення розшифрованих даних у вигляді рядка
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
