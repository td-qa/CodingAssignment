package codingassignment.tests.util;

public class NameCli {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: NameCli <name...>");
            return;
        }
        String name = String.join(" ", args);
        System.out.println(NameFormatter.convertNameToInitials(name));
    }
}
