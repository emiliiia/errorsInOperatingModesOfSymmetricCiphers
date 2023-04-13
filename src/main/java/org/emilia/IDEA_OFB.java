package org.emilia;
/*
  @author emilia
  @project 11Lab
  @class IDEA_FB
  @version 1.0.0
  @since 11.04.2023 - 18:45
*/

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Security;

public class IDEA_OFB {
    public static void main(String[] args) throws Exception {
        // Додати провайдер Bouncy Castle
        Security.addProvider(new BouncyCastleProvider());

        // Вхідний текст для шифрування
        String plaintext = "Emilia Emilia Emilia Emilia Karlivna Dragomyretska!";
        System.out.println("PlainText: " + plaintext);

        // Ключ для шифрування
        byte[] keyBytes = Hex.decode("1122334455667788"); // 64-бітний ключ (8 байт)
        SecretKeySpec key = new SecretKeySpec(keyBytes, "IDEA");

        // Вектор ініціалізації (IV) для режиму CBC
        byte[] ivBytes = Hex.decode("0011223344556677"); // 64-бітний вектор (8 байт)
        IvParameterSpec iv = new IvParameterSpec(ivBytes);

        // Шифрування
        Cipher cipher = Cipher.getInstance("IDEA/OFB/PKCS5Padding", "BC"); // Використовуємо Bouncy Castle провайдер
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] ciphertext = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
        System.out.println("Encrypted Text: " + Hex.toHexString(ciphertext));

        // Дешифрування
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] decryptedTextBytes = cipher.doFinal(ciphertext);
        String decryptedText = new String(decryptedTextBytes, StandardCharsets.UTF_8);
        System.out.println("Decrypted Text: " + decryptedText);

        String replacementText = "Karlivna Dragomyretska";

/*        //Заміна в 0-му блоку в режимі CBC
        System.out.println("Replacement in the 0st block");
        byte[] replacementBytes0 = replacementText.getBytes(StandardCharsets.UTF_8);
        System.arraycopy(replacementBytes0, 0, ciphertext, 0, 16);

        // Дешифрування
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] decryptedTextBytes0 = cipher.doFinal(ciphertext);
        String decryptedText0 = new String(decryptedTextBytes0, StandardCharsets.UTF_8);
        System.out.println("Decrypted Text: " + decryptedText0);*/


/*        //Заміна в 1-му блоку в режимі CBC
        System.out.println("Replacement in the 1st block");
        byte[] replacementBytes1 = replacementText.getBytes(StandardCharsets.UTF_8);
        System.arraycopy(replacementBytes1, 0, ciphertext, 16, 16);

        // Дешифрування
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] decryptedTextBytes1 = cipher.doFinal(ciphertext);
        String decryptedText1 = new String(decryptedTextBytes1, StandardCharsets.UTF_8);
        System.out.println("Decrypted Text: " + decryptedText1);*/

/*        //Заміна в передостаннього блоку в режимі CBC
        System.out.println("Replacement in the last but one block");
        byte[] replacementBytes1 = replacementText.getBytes(StandardCharsets.UTF_8);
        System.arraycopy(replacementBytes1, 0, ciphertext, ciphertext.length-32, 16);

        // Дешифрування
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] decryptedTextBytes1 = cipher.doFinal(ciphertext);
        String decryptedText1 = new String(decryptedTextBytes1, StandardCharsets.UTF_8);
        System.out.println("Decrypted Text: " + decryptedText1);*/


        //Заміна в останнього блоку в режимі CBC
        System.out.println("Replacement in the last block");
        byte[] replacementBytes1 = replacementText.getBytes(StandardCharsets.UTF_8);
        System.arraycopy(replacementBytes1, 0, ciphertext, ciphertext.length-17, 1);

        // Дешифрування
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] decryptedTextBytes1 = cipher.doFinal(ciphertext);
        String decryptedText1 = new String(decryptedTextBytes1, StandardCharsets.UTF_8);
        System.out.println("Decrypted Text: " + decryptedText1);
    }
}