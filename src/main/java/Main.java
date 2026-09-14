import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        try {

            while (true) {
                System.out.print("$ ");

                String input = scanner.nextLine();

                if ("exit".equals(input)) {
                    break;
                }

                if (input.startsWith("echo ")) {
                    String message = input.substring(5);
                    System.out.println(message);

                } else if (input.startsWith("type ")) {
                    String command = input.substring(5).trim();

                    if (isBuiltin(command)) {
                        System.out.println(command + " is a shell builtin");

                    } else {
                        String result = searchForFile(command);

                        if (result != null) {
                            System.out.println(command + " is " + result);
                        } else {
                            System.out.println(command + ": not found");
                        }
                    }

                } else {
                    executeCommand(input);
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static boolean isBuiltin(String command) {
        return command.equals("echo")
                || command.equals("exit")
                || command.equals("type");
    }

    public static String searchForFile(String command) {

        String pathVariable = System.getenv("PATH");

        if (pathVariable == null) {
            return null;
        }

        for (String directory : pathVariable.split(File.pathSeparator)) {

            Path commandPath = Paths.get(directory, command);

            if (Files.exists(commandPath) && Files.isExecutable(commandPath)) {
                return commandPath.toString();
            }
        }

        return null;
    }

    private static void executeCommand(String input) throws Exception {
        String[] parts = input.split(" ");

        String command = parts[0];

        String executable = searchForFile(command);

        if (executable == null) {
            System.out.println(command + ": command not found");
            return;
        }

        ProcessBuilder processBuilder = new ProcessBuilder(parts);
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();

        Scanner output = new Scanner(process.getInputStream());

        while (output.hasNextLine()) {
            System.out.println(output.nextLine());
        }

        process.waitFor();
    }
}