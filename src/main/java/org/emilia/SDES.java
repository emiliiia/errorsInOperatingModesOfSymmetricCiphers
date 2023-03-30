package org.emilia;
/*
  @author emilia
  @project crypto_1_caesar_cipher
  @class SDES
  @version 1.0.0
  @since 24.03.2023 - 20:39
*/

import java.io.DataInputStream;
import java.io.*;

/*
Шифрування S-DES включає чотири функції:
1. Початкова перестановка (IP)
2. Комплексна функція (f(k)) – комбінація функцій перестановки та заміни:
   *Розширена перестановка (EP)
   *S-блоки (S0 і S1)
   *Перестановка P4
3. Перемикач (SW)
4. Інверсія початкової перестановки (IP(^-1))
 */

public class SDES {
    // int key[]= {0,0,1,0,0,1,0,1,1,1};

    //масив, що містить ключ, який буде використовуватись для шифрування
    int key[] = {
            1, 0, 1, 0, 0, 0, 0, 0, 1, 0
    };

    //масиви, що містять перестановки для генерації ключів;
    int P10[] = { 3, 5, 2, 7, 4, 10, 1, 9, 8, 6 };
    int P8[] = { 6, 3, 7, 4, 8, 5, 10, 9 };

    //масиви, що містять згенеровані ключі;
    int key1[] = new int[8];
    int key2[] = new int[8];

    //масиви, що містять перестановки для шифрування;
    int[] IP = { 2, 6, 3, 1, 4, 8, 5, 7 };
    int[] EP = { 4, 1, 2, 3, 2, 3, 4, 1 };
    int[] P4 = { 2, 4, 3, 1 };
    int[] IP_inv = { 4, 1, 3, 5, 7, 2, 8, 6 };


    // матриці заміни
    int[][] S0 = {{ 1, 0, 3, 2 },
            { 3, 2, 1, 0 },
            { 0, 2, 1, 3 },
            { 3, 1, 3, 2 } };
    int[][] S1 = {{ 0, 1, 2, 3 },
            { 2, 0, 1, 3 },
            { 3, 0, 1, 0 },
            { 2, 1, 0, 3 } };


    // генерує ключ (key1 та key2) з використанням P10 і P8 зі зрушеннями вліво (1 і 2)
    void key_generation()
    {
        int key_[] = new int[10];

        //перестановка бітів у вихідному ключі за допомогою таблиці P10
        for (int i = 0; i < 10; i++) {
            key_[i] = key[P10[i] - 1];
        }

        int Ls[] = new int[5];
        int Rs[] = new int[5];

        //новий масив key_ з 10 бітами,розбивається на Ls і Rs, кожна з 5 бітів
        for (int i = 0; i < 5; i++) {
            Ls[i] = key_[i];
            Rs[i] = key_[i + 5];
        }

        //циклічний зсув лівої і правої половини на 1 біт (для створення key1)
        int[] Ls_1 = shift(Ls, 1);
        int[] Rs_1 = shift(Rs, 1);

        //Ls і Rs об'єднуються у масив key_
        for (int i = 0; i < 5; i++) {
            key_[i] = Ls_1[i];
            key_[i + 5] = Rs_1[i];
        }

        //перестановка бітів за допомогою таблиці P8
        for (int i = 0; i < 8; i++) {
            key1[i] = key_[P8[i] - 1];
        }

        //циклічний зсув лівої і правої половини на 2 біти (для створення key2)
        int[] Ls_2 = shift(Ls, 2);
        int[] Rs_2 = shift(Rs, 2);

        //Ls і Rs об'єднуються у масив key_
        for (int i = 0; i < 5; i++) {
            key_[i] = Ls_2[i];
            key_[i + 5] = Rs_2[i];
        }

        //перестановка бітів за допомогою таблиці P8
        for (int i = 0; i < 8; i++) {
            key2[i] = key_[P8[i] - 1];
        }


        //ВИВІД
        System.out.println("Your Key-1 :");

        for (int i = 0; i < 8; i++)
            System.out.print(key1[i] + " ");

        System.out.println();
        System.out.println("Your Key-2 :");

        for (int i = 0; i < 8; i++)
            System.out.print(key2[i] + " ");
    }


    // здійснює циклічний зсув масиву ar на n позицій вліво
    int[] shift(int[] ar, int n)
    {
        while (n > 0) {
            int temp = ar[0];
            for (int i = 0; i < ar.length - 1; i++) {
                ar[i] = ar[i + 1];
            }
            ar[ar.length - 1] = temp;
            n--;
        }
        return ar;
    }

    // це основна функція шифрування, яка приймає звичайний текст
    //введення використовує інші функції та повертає масив
    //зашифрований текст

    int[] encryption(int[] plaintext)
    {
        int[] arr = new int[8];

        //перестановка за таблицею IP
        //кожен біт вхідного масиву plaintext переставляється на позицію,
        //яку вказує таблиця IP, і результат записується в масив arr
        for (int i = 0; i < 8; i++) {
            arr[i] = plaintext[IP[i] - 1];
        }

        int[] arr1 = function_(arr, key1);

        //половина масиву arr1 переставляється місцями з іншою половиною
        int[] after_swap = swap(arr1, arr1.length / 2);

        int[] arr2 = function_(after_swap, key2);

        int[] ciphertext = new int[8];

        // перестановка за таблицею IP^(-1)
        for (int i = 0; i < 8; i++) {
            ciphertext[i] = arr2[IP_inv[i] - 1];
        }

        return ciphertext;
    }


    //приймає ціле число та повертає його двійкове представлення у вигляді рядка
    String binary_(int val)
    {
        if (val == 0)
            return "00";
        else if (val == 1)
            return "01";
        else if (val == 2)
            return "10";
        else
            return "11";
    }


    // ця функція виконує такі основні дії, як розширення
    // потім xor з потрібним ключем, потім S0 і S1
    // заміна P4 перестановка і знову xor ми використали
    //ця функція 2 рази (клавіша-1 і клавіша-2) протягом
    //шифрування та 2 рази (ключ-2 і ключ-1) протягом
    //розшифрування

    int[] function_(int[] ar, int[] key_)
    {
         //ar - 8-елементний масив з вхідним блоком
         //key_ - 8-елементний масив з ключем шифрування

        int[] l = new int[4];
        int[] r = new int[4];

        //масив ar розбивається на дві частини
        for (int i = 0; i < 4; i++) {
            l[i] = ar[i];
            r[i] = ar[i + 4];
        }


        //містить елементи правої частини r, переставлені відповідно до таблиці замін EP
        int[] ep = new int[8];

        for (int i = 0; i < 8; i++) {
            ep[i] = r[EP[i] - 1];
        }

        //для кожного елементу ar виконується XOR з відповідним елементом масиву key_
        //і елементом масиву ep з тим самим індексом.
        for (int i = 0; i < 8; i++) {
            ar[i] = key_[i] ^ ep[i];
        }

        int[] l_1 = new int[4];
        int[] r_1 = new int[4];


        //результат розбивається на дві частини l_1 і r_1
        for (int i = 0; i < 4; i++) {
            l_1[i] = ar[i];
            r_1[i] = ar[i + 4];
        }

        //обчислення S-блоків
        int row, col, val;

        //S-блок приймає на вхід 6-бітний вектор, отриманий з лівої і правої частин блоку l_1 і r_1 відповідно
        //через цей вектор в S-блоках знаходяться значення і перетворюються в дві 2-бітні стрічки str_l і str_r
        row = Integer.parseInt("" + l_1[0] + l_1[3], 2);
        col = Integer.parseInt("" + l_1[1] + l_1[2], 2);
        val = S0[row][col];
        String str_l = binary_(val);

        row = Integer.parseInt("" + r_1[0] + r_1[3], 2);
        col = Integer.parseInt("" + r_1[1] + r_1[2], 2);
        val = S1[row][col];
        String str_r = binary_(val);

        //str_l і str_r розбиваються на окремі символи
        //кожен конвертується в числовий еквівалент r_[i]
        int[] r_ = new int[4];
        for (int i = 0; i < 2; i++) {
            char c1 = str_l.charAt(i);
            char c2 = str_r.charAt(i);
            r_[i] = Character.getNumericValue(c1);
            r_[i + 2] = Character.getNumericValue(c2);
        }


        //значення r_ з'єднюються у масив r_p4 за допомогою таблиці замін P4
        int[] r_p4 = new int[4];
        for (int i = 0; i < 4; i++) {
            r_p4[i] = r_[P4[i] - 1];
        }

        //ліва частина l XOR-ується з масивом r_p4
        for (int i = 0; i < 4; i++) {
            l[i] = l[i] ^ r_p4[i];
        }

        //результат збирається з лівої і правої частин масивів l і r
        int[] output = new int[8];
        for (int i = 0; i < 4; i++) {
            output[i] = l[i];
            output[i + 4] = r[i];
        }
        return output;
    }

    //виконує обмін двох частин масиву array довжиною n
    int[] swap(int[] array, int n)
    {
        int[] l = new int[n];
        int[] r = new int[n];

        for (int i = 0; i < n; i++) {
            l[i] = array[i];
            r[i] = array[i + n];
        }

        int[] output = new int[2 * n];
        for (int i = 0; i < n; i++) {
            output[i] = r[i];
            output[i + n] = l[i];
        }

        return output;
    }

        int[] decryption(int[] ar)
    {
        int[] arr = new int[8];

        for (int i = 0; i < 8; i++) {
            arr[i] = ar[IP[i] - 1];
        }

        int[] arr1 = function_(arr, key2);

        int[] after_swap = swap(arr1, arr1.length / 2);

        int[] arr2 = function_(after_swap, key1);

        int[] decrypted = new int[8];

        for (int i = 0; i < 8; i++) {
            decrypted[i] = arr2[IP_inv[i] - 1];
        }

        return decrypted;
    }

    public static void main(String[] args)
    {
        SDES obj = new SDES();

        obj.key_generation();

        //int []plaintext= {1,0,1,0,0,1,0,1};
        int[] plainText = {
                1, 0, 0, 1, 0, 1, 1, 1
        };

        System.out.println();
        System.out.println("Plain text:");
        for (int i = 0; i < 8; i++)
            System.out.print(plainText[i] + " ");

        int[] cipherText = obj.encryption(plainText);

        System.out.println();
        System.out.println("Encrypted text:");

        for (int i = 0; i < 8; i++)
            System.out.print(cipherText[i] + " ");

        int[] decrypted = obj.decryption(cipherText);

        System.out.println();
        System.out.println("Decrypted text:");
        for (int i = 0; i < 8; i++)
            System.out.print(decrypted[i] + " ");
    }
}

