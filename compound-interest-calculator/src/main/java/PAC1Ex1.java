import java.util.Scanner;
import java.util.Locale;

public class PAC1Ex1 {

    public static boolean isDataValid(double initialCapital, double annualInterestRate, int periodsPerYear, double timeInYears) {
        boolean isValid = true;

        if (initialCapital <= 0) {
            System.out.println("Initial capital must be greater than zero.");
            isValid = false;
        }

        if (annualInterestRate < 0) {
            System.out.println("Annual interest rate must be greater or equal to zero.");
            isValid = false;
        }

        if (periodsPerYear <= 0) {
            System.out.println("Periods per year must be greater than zero.");
            isValid = false;
        }

        if (timeInYears < 0) {
            System.out.println("Time in years must be greater or equal to zero.");
            isValid = false;
        }

        if (!isValid) {
            System.out.println("Please re-enter the data.");
        }

        return isValid;
    }

    public static double calculateCompoundInterest(double initialCapital, double annualInterestRate, int periodsPerYear, double timeInYears) {
        double interestPerPeriod = annualInterestRate / periodsPerYear;
        double totalPeriods = periodsPerYear * timeInYears;

        return initialCapital * Math.pow(1 + interestPerPeriod, totalPeriods);
    }

    // Método auxiliar para validar y leer números decimales
    public static double getValidatedDouble(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextDouble()) {
                double value = scanner.nextDouble();
                scanner.nextLine(); // Limpia el buffer
                return value;
            } else {
                System.out.println("Invalid input. Please enter a numerical value.");
                scanner.nextLine(); // Descarta la entrada incorrecta
            }
        }
    }

    // Método auxiliar para validar y leer números enteros
    public static int getValidatedInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();
                scanner.nextLine(); // Limpia el buffer
                return value;
            } else {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.nextLine(); // Descarta la entrada incorrecta
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US); // Fuerza el uso del punto como separador decimal

        double initialCapital, annualInterestRate, timeInYears;
        int periodsPerYear;

        do {
            initialCapital = getValidatedDouble(scanner, "Enter the initial capital (greater than zero): ");
            annualInterestRate = getValidatedDouble(scanner, "Enter the annual interest rate (greater or equal to zero): ");
            periodsPerYear = getValidatedInt(scanner, "Enter the number of periods per year (greater than zero): ");
            timeInYears = getValidatedDouble(scanner, "Enter the time in years (greater or equal to zero): ");
        } while (!isDataValid(initialCapital, annualInterestRate, periodsPerYear, timeInYears));

        double finalCapital = calculateCompoundInterest(initialCapital, annualInterestRate, periodsPerYear, timeInYears);
        System.out.printf(Locale.US, "Final capital after %.2f years: %.2f\n", timeInYears, finalCapital);

        scanner.close();
    }
}