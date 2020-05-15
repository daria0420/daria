package com;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @Author Daria
 * @Description
 * @Date 2020/4/29 -- 19:04
 */
public class FengKuangYouXi {
    public static void main2(String[] args) {
        double x1 = 55.9, x2 = 94.2, x3 = 12.5,
                y1 = 68.4, y2 = 24.7, y3 = 53.0,
                z1 = 80.8, z2 = 22.5, z3 = 59.9;
//        isTriangle()
        System.out.println(true);
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] s = scanner.nextLine().split(";");
        String[] split = s[0].split(",");
        int[] array = new int[split.length];
        for (int i = 0; i < split.length; ++i) {
            array[i] = Integer.parseInt(split[i]);
        }
        int m = Integer.parseInt(s[1]);
        System.out.println(getNum(array, m));
    }
    static int count = 0;

    public static int getNum(int[] array, int m) {
        int[][] dp = new int[array.length+1][array.length+1];
        for (int i = 0; i <= array.length; ++i) {
            dp[i][0] = 1;
        }
        for (int i = 1; i <= array.length; ++i) {
            for (int sum = 1; sum <= m; ++sum) {
                for (int j = 0; j <= sum/array[i-1]; ++j) {
                    dp[i][sum] += dp[i-1][sum-j*array[i-1]];
                }
            }
        }
        for (int i = 0; i <dp.length; ++i) {
            for (int j = 0; j < dp[i].length;++j) {
                System.out.print(dp[i][j]);
            }
            System.out.println();
        }
        return dp[array.length][m];
    }
    public static void main1(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();

        System.out.println(getLength(s));
    }
    public static int getLength(String s) {
        if (s.length() < 2) return 0;
        int i = 0, j = s.length() - 1;
        for (; i < s.length(); ++i) {
            if (s.charAt(i) == '2') break;
        }
        for (; j > 0; --j) {
            if (s.charAt(j) == '5') break;
        }

        return j-i+1;
    }
}
