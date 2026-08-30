package edu.uoc.pac2;

public class RomanConverter {

    private static final int[] VALUES = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    private static final String[] SYMBOLS = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

    public static int romanCharToInt(char c) {

        switch (c) {
            case 'I': return 1;
            case 'V': return 5;
            case 'X': return 10;
            case 'L': return 50;
            case 'C': return 100;
            case 'D': return 500;
            case 'M': return 1000;
            default:  return -1;
        }
    }

    public static int romanToDecimal(String roman) {
        if (roman == null || roman.isEmpty()) {
            throw new IllegalArgumentException("Invalid Roman numeral format.");
        }

        int total = 0;
        int last = 0;

        for (int i = roman.length() - 1; i >= 0; i--) {
            char ch = roman.charAt(i);
            int val = romanCharToInt(ch);
            if (val == -1) {
                throw new IllegalArgumentException("Invalid Roman numeral format.");
            }

            if (val < last) {
                total -= val;
            } else {
                total += val;
                last = val;
            }
        }
        return total;
    }

    public static String decimalToRoman(int number) {
        if (number <= 0 || number > 3999) {
            throw new IllegalArgumentException("Number must be between 1 and 3999.");
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < VALUES.length; i++) {
            while (number >= VALUES[i]) {
                sb.append(SYMBOLS[i]);
                number -= VALUES[i];
            }
        }

        return sb.toString();
    }

}
