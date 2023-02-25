package org.emilia;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
/*
  @author emilia
  @project crypto_1_caesar_cipher
  @class AffineCaesarCipher
  @version 1.0.0
  @since 18.02.2023 - 0:11
*/
import java.io.*;

public class AffineCaesarCipher {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the value of a: ");
        int a = Integer.parseInt(br.readLine());
        System.out.println("Enter the value of b: ");
        int b = Integer.parseInt(br.readLine());

        File inputFile = new File("C:/cryptLab/input.txt");
        BufferedReader fileReader = new BufferedReader(new FileReader(inputFile));
        StringBuilder inputText = new StringBuilder();
        String line;
        while ((line = fileReader.readLine()) != null) {
            inputText.append(line);
        }
        fileReader.close();

        StringBuilder CTxt = new StringBuilder();
        for (int i = 0; i < inputText.length(); i++)
        {
            if (inputText.charAt(i) != ' '){
                CTxt.append((char) ((((a * inputText.charAt(i)) + b) % 26) + 65));
            }
            else{
                CTxt.append(' ');
            }
        }

        File outputFile = new File("C:/cryptLab/output.txt");
        FileWriter fileWriter = new FileWriter(outputFile);
        fileWriter.write( a + "\n" + b + "\n");
        fileWriter.write(CTxt.toString());
        fileWriter.close();

        decryption();
    }


    static void decryption(){
        int a = 0, b = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("C:/cryptLab/output.txt"));
            String lineA = reader.readLine();
            a = Integer.parseInt(lineA);
            System.out.println(a);
            String lineB = reader.readLine();
            b = Integer.parseInt(lineB);
            System.out.println(b);
            reader.close();
        } catch (IOException e) {
            System.out.println("ERROR");
        }

        // Читаємо з файлу отриману шифрограму
        String cipherText = "";
        try {

            BufferedReader reader = new BufferedReader(new FileReader("C:/cryptLab/output.txt"));
            reader.readLine();
            reader.readLine();
            cipherText = reader.readLine();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        StringBuilder plainText = new StringBuilder();
        int a_inv = 0;
        int flag;
        for (int i = 0; i < 26; i++)
        {
            flag = (a * i) % 26;
            if (flag == 1)
            {
                a_inv = i;
                System.out.println(i);
            }
        }
        for (int i = 0; i < cipherText.length(); i++)
        {
            if (cipherText.charAt(i) != ' '){
                plainText.append((char) (((a_inv * ((cipherText.charAt(i) - b)) % 26)) + 65));
            }
            else{
                plainText.append(' ');
            }

        }

        System.out.println("Decrypted text: " + plainText);
    }
}