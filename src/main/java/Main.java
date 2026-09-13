import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        // TODO: Uncomment the code below to pass the first stage
        // System.out.print("$ ");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("$ ");

            String command = scanner.nextLine();

            if ("exit".equals(command)) {
                System.exit(0);
            }
            if (command.startsWith("type")) {
                command = command.replaceFirst("type", "").trim();
            }
            if (command.startsWith("echo")) {
                System.out.println(command.replace("echo", "").trim());
            } else if (command.endsWith("echo") || command.endsWith("exit") || command.endsWith("type")) {
                System.out.println(command.trim() + " is a shell builtin");
            } else {
                System.out.println(command + ": not found");
            }
        }

    }
}