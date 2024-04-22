import java.util.Scanner;

public class Main {
    private static String addCommand = "add Василий Петров " +
            "vasily.petrov@gmail.com +79215637722";
    private static String commandExamples = "\t" + addCommand + "\n" +
            "\tlist\n\tcount\n\tremove Василий Петров";
    private static String commandError = "Wrong command! Available command examples: \n" +
            commandExamples;
    private static String helpText = "Command examples:\n" + commandExamples;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CustomerStorage executor = new CustomerStorage();
        for (; ; ) // можно использовать while(условие) - https://javarush.com/groups/posts/1876-operator-while
        {
            String command = scanner.nextLine();
            String[] tokens = command.split("\\s+", 2);
            String token = tokens[0]; // можно вынести в переменную и потом везде с ней работать и не делать каждый раз tokens[0]

            if (token.equals("add")) {
                try {
                    executor.addCustomer(tokens[1]);
                } catch (IllegalArgumentException ex) {
                    System.out.println(ex.getMessage());
                }
            } else if (tokens[0].equals("list")) {
                executor.listCustomers();
            } else if (tokens[0].equals("remove")) {
                executor.removeCustomer(tokens[1]);
            } else if (tokens[0].equals("count")) {
                System.out.println("There are " + executor.getCount() + " customers");
            } else if (tokens[0].equals("help")) {
                System.out.println(helpText);
            } else {
                System.out.println(commandError);
            }


        }
    }
}
