/* Терновская Е.В.
 * 1к 15.1гр Таск 6 Вариант 1
 * ln(1-x) = -x/1 - x^2/2 - x^3/3 - ..., R = 1
 *
 * При некоторых заданных x (допустимые значения x – интервал (-R, R)), n и e, определяемых вводом, вычислить:
 * 1) сумму n слагаемых заданного вида;
 * 2) сумму тех слагаемых, которые по абсолютной величине больше e;
 * 3) сумму тех слагаемых, которые по абсолютной величине больше e/10;
 * 4) значение функции с помощью методов Math.*/

import java.util.InputMismatchException;
import java.util.Scanner;

public class PartialSumOfSeries {
    public static double checkX() {
        Scanner scanner = new Scanner((System.in));
        double input = 0.0;

        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.println("\nPlease, input the value for which to calculate the function (-1 < x < 1): ");
                input = scanner.nextDouble();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Incorrect input (invalid type)!");
                scanner.nextLine();
                continue;
            }
            if (-1.0 >= input || input >= 1.0) {
                System.out.println("Incorrect input (-1 < x < 1)!");
                validInput = false;
            }
        }
        return input;
    }

    public static int checkNumber() {
        Scanner scanner = new Scanner((System.in));
        int input = 0;

        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.println("\nPlease, input the number of terms for which to calculate the sum of the series" +
                        " (number n > 0): ");
                input = scanner.nextInt();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Incorrect input (invalid type)!");
                scanner.nextLine();
                continue;
            }
            if (input <= 0) {
                System.out.println("Incorrect input (n is natural number)!");
                validInput = false;
            }
        }
        return input;
    }

    public static double checkEpsilon() {
        Scanner scanner = new Scanner((System.in));
        double input = 0.0;

        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.println("\nPlease, input the accuracy with which to calculate the sum of the series " +
                        "(epsilon 0 < e < 1): ");
                input = scanner.nextDouble();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Incorrect input (invalid type)!");
                scanner.nextLine();
                continue;
            }
            if (0.0 >= input || input >= 1.0) {
                System.out.println("Incorrect input (0 < e < 1)!");
                validInput = false;
            }
        }
        return input;
    }

    public static double[] calculatePartialSum(double x, int number, double epsilon) {
        double lastSummandN = -x;
        double lastSummandE = -x;
        double[] sum = {lastSummandN, lastSummandE, lastSummandE};

        if (x == 0) return sum;

        int i = 2;
        int n = 2;

        /* Принцип такой:
        * в 1й if заходим только пока не почитаем до number слагаемых
        * во 2й if - только пока не посчитаем до точности epsilon
        * и в 3й if - пока не посчитаем до точности epsilon/10 */

        while (i < number || Math.abs(lastSummandE) >= epsilon / 10) {
            // Подсчёт результата для кол-ва слагаемых number
            if (i < number) {
                lastSummandN *= x * (i - 1) / i;
                sum[0] += lastSummandN;
                i++;
            }

            // Подсчёт результатов с точностью epsilon
            if (Math.abs(lastSummandE) >= epsilon) {
                lastSummandE *= x * (n - 1) / n;
                sum[1] += lastSummandE;
                n++;
                sum[2] = sum[1]; // Последний результат будет использован для точности epsilon/10
            }

            // Подсчёт результатов с точностью epsilon/10 (начиная с результата с точностью epsilon)
            if (Math.abs(lastSummandE) >= epsilon / 10 && Math.abs(lastSummandE) < epsilon) {
                lastSummandE *= x * (n - 1) / n;
                sum[2] += lastSummandE;
                n++;
            }
        }

        return sum;
    }

    public static void examples() {
        double[] examplesX = new double[]{0.5, 0.2, 0.9, 0.01, -0.3, 0};
        int[] examplesN = new int[]{10, 15, 20, 100, 50, 2};
        double[] examplesE = new double[]{0.00001, 0.000001, 0.0001, 0.01, 0.1, 0.01};

        for (int i = 0; i < examplesN.length; i++) {
            double[] result = calculatePartialSum(examplesX[i], examplesN[i], examplesE[i]);

            System.out.println("\nFor x = " + examplesX[i] + ", " +
                    "number = " + examplesN[i] + ", " +
                    "epsilon = " + examplesE[i] + ": ");

            System.out.println("1) Partial sum of n terms: " + result[0]);
            System.out.println("2) Partial sum of a series with a accuracy epsilon: " + result[1]);
            System.out.println("3) Partial sum of a series with a accuracy epsilon/10: " + result[2]);
            System.out.println("4) The exact value of the function ln(1-x): " + Math.log(1 - examplesX[i]));
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to program Finding partial sum of the series ln(1-x)!");

        boolean flag = true;
        while (flag) {
            System.out.println("Show examples? y/n");
            char answer = scanner.next().charAt(0);
            switch (answer) {
                case 'y' -> {
                    examples();
                    flag = false;
                }
                case 'n' -> flag = false;
                default -> System.out.println("Incorrect input!");
            }
        }

        double x = checkX();
        int number = checkNumber();
        double epsilon = checkEpsilon();

        double[] result = calculatePartialSum(x, number, epsilon);

        System.out.println("1) Partial sum of n terms: " + result[0]);
        System.out.println("2) Partial sum of a series with a accuracy epsilon: " + result[1]);
        System.out.println("3) Partial sum of a series with a accuracy epsilon/10: " + result[2]);
        System.out.println("4) The exact value of the function ln(1-x): " + Math.log(1 - x));
    }
}