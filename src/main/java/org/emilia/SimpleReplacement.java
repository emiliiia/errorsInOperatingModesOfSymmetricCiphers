package org.emilia;
/*
  @author emilia
  @project crypto_1_caesar_cipher
  @class SimpleReplacement
  @version 1.0.0
  @since 22.03.2023 - 19:59
*/

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SimpleReplacement {

    // Виконуємо шифрування
    public static StringBuilder encryptSimpleReplacement(StringBuilder plaintext, String key) {
        //генерується таблиця замін
        Map<Character, Character> substitutionTable = generateSubstitutionTable(key);

        //Якщо поточний символ належить до алфавіту і міститься в таблиці замін, то його замінюємо на відповідний символ
        //з таблиці замін, якщо символ не знаходиться в таблиці замін, його залишаємо без змін.
        StringBuilder ciphertext = new StringBuilder();
        for (int i = 0; i < plaintext.length(); i++) {
            char c = plaintext.charAt(i);
            if (substitutionTable.containsKey(c)) {
                c = substitutionTable.get(c);
            }
            ciphertext.append(c);
        }

        return ciphertext;
    }

    // Виконуємо дешифрування
    public static StringBuilder decryptSimpleReplacement(StringBuilder ciphertext, String key) {
        Map<Character, Character> substitutionTable = generateSubstitutionTable(key);

        StringBuilder plaintext = new StringBuilder();
        for (int i = 0; i < ciphertext.length(); i++) {
            char c = ciphertext.charAt(i);
            for (Map.Entry<Character, Character> entry : substitutionTable.entrySet()) {
                if (entry.getValue().equals(c)) {
                    c = entry.getKey();
                    break;
                }
            }
            plaintext.append(c);
        }

        return plaintext;
    }

    // Генеруємо таблицю замін
    public static Map<Character, Character> generateSubstitutionTable(String key) {
        //приймає ключ як аргумент і повертає мапу, в якій ключі - літери алфавіту, а значення - відповідні літери ключа
        Map<Character, Character> substitutionTable = new HashMap<>();
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < alphabet.length(); i++) {
            substitutionTable.put(alphabet.charAt(i), key.charAt(i));
        }
        return substitutionTable;
    }

    // Виконуємо шифрування
    public static StringBuilder encryptData(StringBuilder plaintext, int shiftAmount, String direction) {
        StringBuilder ciphertext = new StringBuilder();
        for (int i = 0; i < plaintext.length(); i++) {
            char c = plaintext.charAt(i);
            if (Character.isLetter(c)) {
                // кількість позицій, на яку потрібно зсунути символ у алфавіті.
                int offset = shiftAmount % 26;
                if (direction.equals("l")) {
                    offset = 26 - offset;
                }
                if (Character.isUpperCase(c)) {
                    c = (char) ((c - 'A' + offset) % 26 + 'A');
                } else {
                    c = (char) ((c - 'a' + offset) % 26 + 'a');
                }
            }
            ciphertext.append(c);
        }

        return ciphertext;
    }


    public static StringBuilder decryptData(StringBuilder ciphertext, int shiftAmount, String direction) {
        StringBuilder plaintext = new StringBuilder();
        for (int i = 0; i < ciphertext.length(); i++) {
            char c = ciphertext.charAt(i);
            if (Character.isLetter(c)) {
                int offset = shiftAmount % 26;
                if (direction.equals("l")) {
                    offset = 26 - offset;
                }
                if (Character.isUpperCase(c)) {
                    c = (char) ((c - 'A' - offset + 26) % 26 + 'A');
                } else {
                    c = (char) ((c - 'a' - offset + 26) % 26 + 'a');
                }
            }
            plaintext.append(c);
        }

        return plaintext;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Ceaser PART:");
        // Запитуємо шлях до вхідного файлу
        String inputPath = "C:/cryptLab/input.txt";

        // Запитуємо величину зсуву
        System.out.print("Enter the shift amount: ");
        int shiftAmount = input.nextInt();
        input.nextLine(); // Поглинаємо зайвий символ \n з буферу

        // Запитуємо напрям зсуву
        System.out.print("Enter the shift direction (l - left, r - right): ");
        String direction = input.nextLine();

        // Читаємо вхідний файл
        File inputFile = new File(inputPath);
        StringBuilder plaintext = new StringBuilder();
        try {
            Scanner fileScanner = new Scanner(inputFile);
            while (fileScanner.hasNextLine()) {
                plaintext.append(fileScanner.nextLine());
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            System.exit(1);
        }


        // Виконуємо шифрування методом Цезаря
        StringBuilder ciphertext = encryptData(plaintext, shiftAmount, direction);

        System.out.println("Input: " + plaintext);
        System.out.println("Encrypted input: " + ciphertext);


        System.out.println("Simple Replacement PART:");
        // Запитуємо ключ шифрування
        System.out.print("Enter the encryption key for simple replacement: ");
        String encryptionKey = input.nextLine();
        // Виконуємо шифрування
        StringBuilder cipherSimpleReplacement = encryptSimpleReplacement(ciphertext, encryptionKey);
        System.out.println("Encrypted input by replacement: " + cipherSimpleReplacement);

        StringBuilder decipherSimpleReplacement = decryptSimpleReplacement(cipherSimpleReplacement, encryptionKey);
        System.out.println("Decrypted input by replacement: " + decipherSimpleReplacement);
        // Записуємо результат у вихідний файл
        String outputPath = "C:/cryptLab/output.txt";
        try {
            FileWriter fileWriter = new FileWriter(outputPath);
            fileWriter.write(cipherSimpleReplacement.toString());
            fileWriter.close();
            System.out.println("Encryption completed successfully. The result is saved in a file " + outputPath);
        } catch (IOException e) {
            System.out.println("Error writing to file.");
            System.exit(1);
        }

        // Виконуємо дешифрування
        StringBuilder deciphertext = decryptData(decipherSimpleReplacement, shiftAmount, direction);
        System.out.println("OutPut by Ceaser: " + deciphertext);

        input.close();
    }
}


//zyxwvutsrqponmlkjihgfedcba
//Enter the value by which each character in the plaintext message gets shifted