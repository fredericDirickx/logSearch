import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        LogSearcher logSearcher = new LogSearcher(
                "C:\\Users\\intec\\logdetails.txt",
                "C:\\Users\\intec\\");

        Scanner scanner = new Scanner(System.in);
        System.out.println("what the f*** do you need????");
        System.out.println("split with \",\" for mutiple searches");
        String needles = scanner.next();

        logSearcher.findNeedles(logSearcher.splitStringToArray(needles));


    }
}
