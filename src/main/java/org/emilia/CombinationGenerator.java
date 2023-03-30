package org.emilia;
/*
  @author emilia
  @project crypto_1_caesar_cipher
  @class CombinationGeneration
  @version 1.0.0
  @since 27.03.2023 - 2:55
*/

import java.util.Arrays;

public class CombinationGenerator {
    int e = 1;
    private int[] combination;
    private int n;
    long startTime = System.currentTimeMillis();
    int[] key = {
            1, 0, 1, 0, 0, 0, 0, 0, 1, 0
    };

    public CombinationGenerator(int n) {
        this.n = n;
        this.combination = new int[n];
        for (int i = 0; i < n; i++) {
            this.combination[i] = 0;
        }
    }

    public void generate() {
        generateHelper(0);
    }

    private void generateHelper(int index) {
        if (index == n) {
            printCombination();
        } else {
            for (int i = 0; i < 2; i++) {
                combination[index] = i;
                generateHelper(index + 1);
            }
        }
    }

    private void printCombination() {
        if(Arrays.equals(combination, key)){
            System.out.println("STOP");
            System.out.print(e + ") ");
            for (int i = 0; i < n; i++) {
                System.out.print(combination[i] + " ");
            }
            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            System.out.println();
            System.out.println("Execution time: " + totalTime + " ms");
            System.exit(0);
        }
        else{
            System.out.print(e + ") ");
            for (int i = 0; i < n; i++) {
                System.out.print(combination[i] + " ");
            }
            e++;
            System.out.println();
        }

    }

    public static void main(String[] args) {
        CombinationGenerator generator = new CombinationGenerator(10);
        generator.generate();

    }
}
