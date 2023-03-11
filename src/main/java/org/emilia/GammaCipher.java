package org.emilia;
/*
  @author emilia
  @project crypto_1_caesar_cipher
  @class GammaCipher
  @version 1.0.0
  @since 25.02.2023 - 19:06
*/

import java.io.*;
import java.util.*;

public class GammaCipher {
    static int[] decryptData(int[] message, int[] gamma)
    {
        int[] decryptedMessage = new int[message.length];
        for (int i = 0; i < message.length; i++) {
            int encryptedValue = message[i];
            int decryptedValue = (encryptedValue - gamma[i]);
            decryptedMessage[i] = decryptedValue;
        }

        return decryptedMessage;
    }

    static int[] encryptData(int[] message, int[] Н) {
        // Encrypt the message using the gamma sequence
        int[] encryptedMessage = new int[message.length];
        for (int i = 0; i < message.length; i++) {
            int encryptedValue = (message[i] + Н[i] % 10);
            encryptedMessage[i] = encryptedValue;
        }

        System.out.println("Encrypted message: ");
        for (int i = 0; i < encryptedMessage.length; i++) {
            System.out.print(encryptedMessage[i] + " ");
        }
        System.out.println(" ");
        return encryptedMessage;
    }

    public static void main (String[]args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter secret key (3 digits): ");
        String key = scanner.nextLine();

        // перевірка
        if (key.length() != 3) {
            System.out.println("Invalid key length. The key must be 3 digits.");
            return;
        }

        // перетворюю ключ на масив з 3 чисел
        int[] keyArray = new int[3];
        for (int i = 0; i < 3; i++) {
            keyArray[i] = Character.getNumericValue(key.charAt(i));
        }

        File inputFile = new File("C:/cryptLab/input.txt");
        BufferedReader fileReader = new BufferedReader(new FileReader(inputFile));
        String word = " ";
        String line;
        while ((line = fileReader.readLine()) != null) {
            word += line;
        }
        fileReader.close();
        word.replace(" ", "z");
        char[] charArray = word.toCharArray();


        /********************************************/
        int[] intArray = new int[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            int asciiCode = (int) charArray[i];
            intArray[i] = (char) asciiCode;
        }

        // генерую гаму
        int[] gamma = new int[intArray.length];
        for (int i = 0; i < intArray.length; i++) {
            int gammaValue = keyArray[i % 3] + intArray[i];
            gamma[i] = gammaValue % 10;
        }

        System.out.println("Gamma: ");
        for (int i = 0; i < gamma.length; i++) {
            System.out.print(gamma[i]);
        }
        System.out.println(" ");

        int[] encryptDigits = encryptData(intArray, gamma);

        int[] decryptDigits = decryptData(encryptDigits, gamma);


        char[] result = new char[decryptDigits.length];

        for (int i = 0; i < decryptDigits.length; i++) {
            int asciiCode = decryptDigits[i];
            char c = (char) asciiCode;
            result[i] = c;
        }


        System.out.println("Decrypted message: ");
        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i]);
        }
        System.out.println(" ");

        String r = new String(result);

        File outputFile = new File("C:/cryptLab/output.txt");
        FileWriter fileWriter = new FileWriter(outputFile);
        fileWriter.write(r.toString());
        fileWriter.close();

    }


}


/*
1)заміщую в вхідному повідомленні пробіли на z
2)перетворюю пов. на масив чисел
3)генеруб гаму і шифрую повідомлення за формулою
4)за цією ж схемою але навпаки декриптую
 */
//FrEqUeNcY DeCrYpTeD
//Global English Alliance
//Enter the value by which each character in the plaintext message gets shifted
//7/11//Now lets check on Android Studio whether it auto detects where Git was downloaded So to do this we are going to click on File and then on Settings




