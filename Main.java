import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        boolean exit = false;

        System.out.println("Welcome to ToDo List Manager");

        // using do-while loop to make the program continuously usable until exit.
        do {
            System.out.println("""
                \nSelect an option to continue, \
                \n1. Make new list\
                \n2. Work with existing lists
                3. Exit""");

            int choose;

            //running while loop to handle wrong input case
            while (true){
                try {
                    System.out.print("\nEnter desired option: ");
                    choose = sc.nextInt();
                    sc.nextLine();

                    if (choose>3 || choose == 0){
                        System.out.println("Enter from available choices.");
                    }
                    break;         //exit loop if input valid

                } catch (InputMismatchException e){
                    System.out.println("Please enter only numerical value.");
                    sc.next();     //consuming invalid input to avoid infinite loop
                }
            }

            if (choose == 1){
                methods.createNewFile();
            }
            else if (choose == 2) {
                methods.showListsAndActions();
                Thread.sleep(1000);

            }
            else if (choose == 3) {
                System.out.println("Thanks for using our app\nHave a great day");
                exit = true;
            }

        } while (!exit);
    }
}