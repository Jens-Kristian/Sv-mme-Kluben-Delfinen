package Delfine;
import java.util.Scanner;

public class Login {
    public void authenticateUser() {
        Menu menu = new Menu();
        int maxAttempts = 3;
        String correctCode = "password";
        String correctSecurityCode = "123";

        Scanner scanner = new Scanner(System.in);

        for (int attempts = 1; attempts <= maxAttempts; attempts++) {
            System.out.print("Log in: ");
            String userCode = scanner.nextLine();

            if (userCode.equals(correctCode)) {
                System.out.println("User authenticated. Proceed to main menu.");
                menu.run();
            } else if (attempts < maxAttempts) {
                System.out.println("Login failed. Try again.");
            } else {
                // Hvis det er tredje forsøg, kræv en sikkerhedskode
                System.out.print("For your own safety, enter validation code: ");
                String securityCode = scanner.nextLine();

                if (securityCode.equals(correctSecurityCode)) {
                    System.out.println("Welcome!");
                    menu.run();
                } else {
                    System.out.println("Login failed.");
                }
            }
        }
        System.exit(1);
    }
}
