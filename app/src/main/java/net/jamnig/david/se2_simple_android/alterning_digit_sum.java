package net.jamnig.david.se2_simple_android;

public class alterning_digit_sum {
    public static void main(String[] args) {
        printAlterningDigitSum("12345678");
    }


    static void printAlterningDigitSum(String number) {
        int[] numericalValues = getNumber(number);
        int digitSum = numericalValues[0];

        for (int i = 1; i < numericalValues.length; i++) {
            if (numericalValues[i] % 2 == 0) {
                digitSum += numericalValues[i];
            } else {
                digitSum -= numericalValues[i];
            }
        }

        System.out.println("Alterning Digitsum: " + digitSum);
        System.out.println("The value is " + (digitSum % 2 == 0 ? "even" : "odd"));

    }

    static int[] getNumber(String number) {
        int[] numericalValues = new int[number.length()];
        for (int i = 0; i < number.length(); i++) {
            numericalValues[i] = Character.getNumericValue(number.charAt(i));
        }
        return numericalValues;
    }
}
