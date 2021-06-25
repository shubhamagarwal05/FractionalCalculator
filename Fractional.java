package com.personal.trypad;

import javax.xml.bind.ValidationException;

public class Fractional {
    private static final String UNDERSCORE = "_";
    private static final String DIVIDE = "/";
    private static final String MULTIPLY = "*";
    private static final String PLUS = "+";
    private static final String MINUS = "-";

    private class FractionNumber {
        int numerator;
        int denominator;

        public FractionNumber(int numerator, int denominator) {
            this.numerator = numerator;
            this.denominator = denominator;
            reduce();
        }

        @Override
        public String toString() {
            if (this.denominator == 1) {
                return String.valueOf(this.numerator);
            }

            StringBuilder builder = new StringBuilder();
            if (numerator < 0) {
                builder.append(MINUS);
                numerator = -1 * this.numerator;
            }

            if (this.numerator < this.denominator) {
                builder.append(this.numerator).append(DIVIDE).append(this.denominator);
            } else {
                int main = this.numerator / this.denominator;
                int residue = this.numerator % this.denominator;
                builder.append(main).append(UNDERSCORE).append(residue).append(DIVIDE).append(this.denominator);
            }

            return builder.toString();
        }

        public FractionNumber add(FractionNumber second) {
            int numerator = (this.numerator * second.denominator) + (second.numerator * this.denominator);
            int denominator = this.denominator * second.denominator;
            return new FractionNumber(numerator, denominator);
        }

        public FractionNumber subtract(FractionNumber second) {
            int numerator = (this.numerator * second.denominator) - (second.numerator * this.denominator);
            int denominator = this.denominator * second.denominator;
            return new FractionNumber(numerator, denominator);
        }

        public FractionNumber multiply(FractionNumber second) {
            int numerator = this.numerator * second.numerator;
            int denominator = this.denominator * second.denominator;
            return new FractionNumber(numerator, denominator);
        }

        public FractionNumber divide(FractionNumber second) {
            int numerator = this.numerator * second.denominator;
            int denominator = this.denominator * second.numerator;
            return new FractionNumber(numerator, denominator);
        }

        private void reduce() {
            int gcd = Math.abs(gcd(this.numerator, this.denominator));

            this.numerator = this.numerator / gcd;
            this.denominator = this.denominator / gcd;
        }

        private int gcd(int numerator, int denominator) {
            if (denominator == 0)
                return numerator;

            return gcd(denominator, numerator % denominator);
        }
    }

    public String process(String input) {
        try {
            String[] data = input.trim().split("\\s+");

            if (data.length != 3)
                throw new ValidationException("Invalid Input: '" + input + "' It should just contain an operator between 2 operands separated by spaces.");

            FractionNumber firstNum = getFractionNumber(data[0]);
            String operator = data[1];
            FractionNumber secondNum = getFractionNumber(data[2]);

            FractionNumber result = performOperation(operator, firstNum, secondNum);
            return result.toString();
        } catch (Exception ex) {
            return (ex.toString());
        }
    }

    private FractionNumber performOperation(String operation, FractionNumber first, FractionNumber second) {
        switch (operation) {
            case PLUS:
                return first.add(second);

            case MINUS:
                return first.subtract(second);

            case MULTIPLY:
                return first.multiply(second);

            case DIVIDE:
                return first.divide(second);

            default:
                throw new UnsupportedOperationException("The provided operator '" + operation + "' is not supported.");
        }
    }

    private FractionNumber getFractionNumber(String number) throws ValidationException {
        boolean isMinus = false;
        String sanitizedNumber = number;
        int numerator = 0, denominator = 0;

        if (number.startsWith("-")) {
            isMinus = true;
            sanitizedNumber = number.substring(1);
        }

        if (sanitizedNumber.isEmpty()) {
            throw new ValidationException("Invalid Value: " + number);
        }

        if (sanitizedNumber.contains(UNDERSCORE)) {
            // Fraction like 3_1/2
            String[] numberSplit = sanitizedNumber.split(UNDERSCORE);
            if (numberSplit.length != 2)
                throw new ValidationException("Invalid Value: " + number);

            int whole = Integer.parseInt(numberSplit[0]);
            String[] fractionSplit = numberSplit[1].split(DIVIDE);
            if (fractionSplit.length != 2)
                throw new ValidationException("Invalid Value: " + number);

            numerator = Integer.parseInt(fractionSplit[0]);
            denominator = Integer.parseInt(fractionSplit[1]);
            numerator = (denominator * whole) + numerator;

        } else if (sanitizedNumber.contains(DIVIDE)) {
            // Fraction like 1/2
            String[] fractionSplit = sanitizedNumber.split(DIVIDE);
            if (fractionSplit.length != 2)
                throw new ValidationException("Invalid Value: " + number);

            numerator = Integer.parseInt(fractionSplit[0]);
            denominator = Integer.parseInt(fractionSplit[1]);
        } else {
            // Whole Number
            numerator = Integer.parseInt(sanitizedNumber);
            denominator = 1;
        }

        if (isMinus) {
            numerator = numerator * -1;
        }

        return new FractionNumber(numerator, denominator);
    }
}
