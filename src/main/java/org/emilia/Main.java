package org.emilia;

import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;
import java.io.*;

public class Main {
    public static class CaesarCipher {
        public CaesarCipher() {
        }

        public static String encryptData(String str, int key) {
            StringBuilder encryptStr = new StringBuilder();

            //початок по символьно
            for (int i = 0; i < str.length(); i++) {
                char ch = str.charAt(i);
                if (Character.isLetter(ch)) {
                    if (Character.isLowerCase(ch)) {
                        char c = (char) (ch + key);
                        if (c > 'z') {
                            encryptStr.append((char) (ch - (26 - key)));
                        } else {
                            encryptStr.append(c);
                        }
                    } else if (Character.isUpperCase(ch)) {
                        char c = (char) (ch + key);
                        if (c > 'Z') {
                            encryptStr.append((char) (ch - (26 - key)));
                        } else {
                            encryptStr.append(c);
                        }
                    }
                } else {
                    encryptStr.append(ch);
                }
            }
            return encryptStr.toString();
        }

        public static String decryptData(String inputStr, int key)
        {
            StringBuilder decryptStr = new StringBuilder();
            // використання циклу for для обходу кожного символу вхідного рядка
            for (int i = 0; i < inputStr.length(); i++)
            {
                char ch = inputStr.charAt(i);
                if(Character.isLetter(ch)){
                    if(Character.isLowerCase(ch)){
                        char c = (char)(ch - key);
                        if(c < 'a'){
                            decryptStr.append((char) (ch + (26 - key)));
                        }
                        else {
                            decryptStr.append(c);
                        }
                    }
                    else if(Character.isUpperCase(ch)){
                        char c = (char)(ch - key);
                        if(c < 'A'){
                            decryptStr.append((char) (ch + (26 - key)));
                        }
                        else {
                            decryptStr.append(c);
                        }
                    }
                }
                else {
                    decryptStr.append(ch);
                }
            }
            // повернення розшифрованого рядка
            return decryptStr.toString();
        }

        static void printString(String S, int N) {

            //це потрібно щоб повернути регістр вихідному результату
            String s = S;
            if (S.length() != 0){
                S = S.toUpperCase(Locale.ROOT);
            }

            // Зберігає останні 5 можливих розшифрованих варіантів
            String []plaintext = new String[5];

            // Збереження частоти кожної літери в зашифрованому тексті
            int[] freq = new int[26];

            // Зберігає частоту кожної літери у зашифрованому тексті в порядку спадання
            int[] freqSorted = new int[26];

            // Зберігайте, який алфавіт уже використовується
            int[] Used = new int[26];

            // Проходить рядок S й вираховує freq
            for (int i = 0; i < N; i++) {
                if (S.charAt(i) != ' ') {
                    freq[S.charAt(i) - 'A']++;
                }
            }

            // Копіює частотний масив
            System.arraycopy(freq, 0, freqSorted, 0, 26);

            //Зберігає рядок, англійських літер у зменшуваній частоті англійської мови
            String T = "ETAOINSHRDLCUMWFGYPBVKJXQZ";

            // Відсортовую масив у порядку спадання і розвертає його
            Arrays.sort(freqSorted);
            reverse(freqSorted);

            // Ітерація в діапазоні [0, 5]
            for (int i = 0; i < 5; i++) {

                int ch = -1;

                // перевіряємо на якому індексі в масиві freq зійшлось значення з числом в масиві freqSorted
                for (int j = 0; j < 26; j++) {
                    if (freqSorted[i] == freq[j] && Used[j] == 0) {
                        Used[j] = 1;
                        ch = j;
                        break;
                    }
                }
                if (ch == -1)
                    break;

                // дізнємся позицію літери з алфавіту-частот
                int x = T.charAt(i) - 'A';

                // Ймовірний зсув
                x = x - ch;

                // тут зберігається розшиврований варіант
                StringBuilder curr = new StringBuilder();

                // Генерування ймовірного рядка i-го розшифрованого тексту, використовуючи розрахований вище зсув
                for (int k = 0; k < N; k++) {
                    // Вставляння пробілів
                    if (S.charAt(k) == ' ') {
                        curr.append(' ');
                        continue;
                    }

                    // Переміщення k-тої літери шифру на x
                    int y = (S.charAt(k) - 'A') + x;

                    if (y < 0)
                        y += 26;
                    if (y > 25)
                        y -= 26;

                    // Додавання k-ої зміщеної букви до тимчасового рядка з наданням потрібного регістру
                    if(Character.isLetter(s.charAt(k))) {
                        if (Character.isLowerCase(s.charAt(k))) {
                                curr.append((char) Character.toLowerCase('A' + y));
                            }
                        if (Character.isUpperCase(s.charAt(k))) {
                                curr.append((char) Character.toUpperCase('A' + y));
                        }
                    }
                }

                plaintext[i] = curr.toString();
            }

            // Надрукування розрахованих 5-ти можливих розшифрованих текстів
            System.out.print(plaintext[0] +"\n");
            /*
            for (int i = 0; i < 5; i++) {
                System.out.print(plaintext[i] +"\n");
            }*/

        }
        static void reverse(int[] a) {
            int i, n = a.length, t;
            for (i = 0; i < n / 2; i++) {
                t = a[i];
                a[i] = a[n - i - 1];
                a[n - i - 1] = t;
            }
        }

        public static void main (String[]args) throws IOException {
            FileWriter nFile = new FileWriter("C:/cryptLab/outputData.txt");

            FileReader fr= new FileReader("C:/cryptLab/inputData.txt");
            Scanner sc = new Scanner(fr);

            StringBuilder inputStr = new StringBuilder();
            while (sc.hasNextLine()) {
                inputStr.append(sc.nextLine());
            }

            fr.close();

            Scanner shift = new Scanner(System.in);
            System.out.println("Enter the value by which each character in the plaintext message gets shifted: ");
            int shiftKey = Integer.parseInt(shift.nextLine());

            String s = encryptData(inputStr.toString(), shiftKey);
            System.out.println("Encrypted Data ===> " + s);

            nFile.write(s);
            nFile.close();

            System.out.println("Decrypted Data ===> " + decryptData(s, shiftKey));

            System.out.println("Frequency Decrypted Data ===> ");

            printString(s, s.length());


            sc.close();
        }
    }
}

//FrEqUeNcY DeCrYpTeD
//Global English Alliance
//Enter the value by which each character in the plaintext message gets shifted
//7/11//Now lets check on Android Studio whether it auto detects where Git was downloaded So to do this we are going to click on File and then on Settings


