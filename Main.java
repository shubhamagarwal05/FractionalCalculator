package com.personal.trypad;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {
        Fractional fractional = new Fractional();

        // Commenting out the testcases that I ran and tested with.
        // testCases(fractional);

        System.out.println("Add operations on fractions as the input or enter 'q' to exit");
        while (true) {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(System.in));
            System.out.print("? ");
            String input = reader.readLine();

            if (input.trim().equals("q"))
                break;

            String result = fractional.process(input);
            System.out.println("= " + result);
            System.out.println();
        }
    }

    // Call this method from main to run the testcases I tested with.
    private static void testCases(Fractional fractional) {
        String result = "";

        result = fractional.process("2_3/8 + 9/8 ");
        System.out.println(result.equals("3_1/2"));

        result = fractional.process("1 - 1/4 ");
        System.out.println(result.equals("3/4"));

        result = fractional.process("  1/2 * 3_3/4");
        System.out.println(result.equals("1_7/8"));

        result = fractional.process("1/2 /    3/4 ");
        System.out.println(result.equals("2/3"));

        result = fractional.process("  5 * 2");
        System.out.println(result.equals("10"));

        result = fractional.process("  -5 * 2");
        System.out.println(result.equals("-10"));

        result = fractional.process("  -1/2 + 1");
        System.out.println(result.equals("1/2"));

        result = fractional.process("1 - 1_1/2");
        System.out.println(result.equals("-1/2"));

        result = fractional.process("-1_1/2 + 1");
        System.out.println(result.equals("-1/2"));

        result = fractional.process("0 - 1_1/2");
        System.out.println(result.equals("-1_1/2"));

        result = fractional.process("-3/4 - -8/9");
        System.out.println(result.equals("5/36"));

        result = fractional.process("-3/4 * -8/9");
        System.out.println(result.equals("2/3"));

        result = fractional.process("  1/2 * 3_3_/4");
        System.out.println(result.contains("ValidationException: Invalid Value: 3_3_/4"));

        result = fractional.process("1 - 1/4 + 2/3");
        System.out.println(result.contains("ValidationException: Invalid Input: '1 - 1/4 + 2/3'"));

        result = fractional.process("1/2-1/4");
        System.out.println(result.contains("ValidationException: Invalid Input: '1/2-1/4'"));

        result = fractional.process("1 # 1/4 ");
        System.out.println(result.contains("UnsupportedOperationException: The provided operator '#' is not supported"));
    }
}
