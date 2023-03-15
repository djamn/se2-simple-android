package net.jamnig.david.se2_simple_android;

public class findPrimes {
    public static void main(String[] args) {
        printPrime("123456789");
    }

    static void printPrime(String number) {
        int[] numericalValues = getNumber(number);
        StringBuilder builder = new StringBuilder();
        boolean value;

        for (int numericalValue : numericalValues) {
            value = checkPrime(numericalValue);
            if (value) {
                builder.append(numericalValue).append(", ");
            }
        }
        System.out.println("Primes: " + builder.toString());
    }

    static boolean checkPrime(int number) {
        for (int i = 2; i < number; i++) {
            if (number % i == 0) return false;
        }
        return true;
    }

    static int[] getNumber(String number) {
        int[] numericalValues = new int[number.length()];
        for (int i = 0; i < number.length(); i++) {
            numericalValues[i] = Character.getNumericValue(number.charAt(i));
        }
        return numericalValues;
    }
}
