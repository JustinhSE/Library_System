import java.lang.annotation.Native;
import java.util.*;
public class LibraryManager {
    public static void main(String[] args) throws InvalidISBNException, InvalidUserIDException, BookDoesNotExistException, BookAlreadyCheckedOutException, InvalidSortCriteriaException, BookAlreadyExistsException, BookCheckedOutBySomeoneElseException, InvalidReturnDateException, BookNotCheckedOutException {
        BookRepository bookRepository = new BookRepository();
        ReturnStack returnStack = new ReturnStack();
        System.out.println("Starting... ");
        Scanner input = new Scanner(System.in);
        String option = "";
        while(!(option.equals("q"))) {
            menu();
            System.out.println("Enter a choice: ");
            option = input.nextLine().toLowerCase();
            //edge case
            if(option.equals("")){
                option = input.nextLine().toLowerCase();
            }
            if(option.equals("b")){
                System.out.println("Enter a choice: ");
                option = input.nextLine().toLowerCase();
                if(option.equals("c")){
                    System.out.println("Enter the userID #: ");
                    long ID = input.nextLong();
                    System.out.println("Enter the ISBN #: ");
                    long ISBN = input.nextLong();
                    System.out.println("Enter the Due Date(MM/DAY/YEAR): ");
                    String dis = input.nextLine();
                    String temp = input.nextLine();
                    String[] t = temp.splitWithDelimiters("/", temp.length());
                    Date newDate = new Date();
                    newDate.setMonth(Integer.parseInt(t[0]));
                    newDate.setDay(Integer.parseInt(t[2]));
                    newDate.setYear(Integer.parseInt(t[4]));
                    bookRepository.checkOutBook(ISBN, ID,newDate );
                    System.out.println("Loading...");
                    System.out.println(bookRepository.findTitle(ISBN) + " has been checked out by user " + ID + " and must be returned by " + newDate.toString());
                }
                if(option.equals("n")){
                    System.out.println("Enter the ISBN #: ");
                    long ISBN = input.nextLong();
                    System.out.println("Enter title: ");
                    String disgard = input.nextLine();
                    String name = input.nextLine();
                    System.out.println("Enter author: ");
                    String author = input.nextLine();
                    System.out.println("Enter genre: ");
                    String genre = input.nextLine();
                    System.out.println("Enter Condition: ");
                    Condition condition = Condition.valueOf(input.nextLine().toUpperCase());
                    System.out.println("Enter the Year Published: ");
                    int year = input.nextInt();
                    System.out.println("Loading...");
                    bookRepository.addBook( name,  author,  genre, condition, ISBN, -1L,  year,  false );

                }
                if(option.equals("r")){
                    System.out.println("Enter the ISBN #: ");
                    long ISBN = input.nextLong();
                    bookRepository.removeBook(ISBN);

                }
                if(option.equals("p")){
                    System.out.println("Please select a shelf: ");
                    int print = input.nextInt();
                    bookRepository.printTable(print-1);
                }
                if(option.equals("s")){
                    int x = 0;
                    System.out.println("Enter a choice: ");
                    option = input.nextLine().toLowerCase();
                    if(option.equals("i")){
                        System.out.println("What shelf would you like us to sort?(#): ");
                        x = input.nextInt();
                        System.out.println(x);
                        bookRepository.sortShelf(x-1, option);
                    }
                    else if(option.equals("n")){
                        System.out.println("What shelf would you like us to sort?(#): ");
                        x = input.nextInt();
                        bookRepository.sortShelf(x-1, option);


                    }
                    else if(option.equals("a")){
                        System.out.println("What shelf would you like us to sort?(#): ");
                        x = input.nextInt();
                        bookRepository.sortShelf(x-1, option);


                    }
                    else if(option.equals("g")){
                        System.out.println("What shelf would you like us to sort?(#): ");
                        x = input.nextInt();
                        bookRepository.sortShelf(x-1, option);


                    }else if(option.equals("y")){
                        System.out.println("What shelf would you like us to sort?(#): ");
                        x = input.nextInt();
                        bookRepository.sortShelf(x-1, option);


                    }else if(option.equals("c")){
                        System.out.println("What shelf would you like us to sort?(#): ");
                        x = input.nextInt();
                        bookRepository.sortShelf(x-1, option);
                    }
                    System.out.println("Shelf " + x + " has been sorted by " + bookRepository.getShelf(x-1).getShelfSortCriteria());
                }

            }
            if(option.equals("r")){
                System.out.println("Enter a choice: ");
                option = input.nextLine().toLowerCase();
                if(option.equals("r")){
                    System.out.println("Enter the ISBN #: ");
                    long ISBN = input.nextLong();
                    System.out.println("Enter the userID #: ");
                    long ID = input.nextLong();
                    System.out.println("Enter the Return Date (MM/DAY/YEAR): ");
                    String dis = input.nextLine();
                    String temp = input.nextLine();
                    String[] t = temp.splitWithDelimiters("/", temp.length());
                    Date newDate = new Date();
                    newDate.setMonth(Integer.parseInt(t[0]));
                    newDate.setDay(Integer.parseInt(t[2]));
                    newDate.setYear(Integer.parseInt(t[4]));
                    System.out.println("Loading...");
                    returnStack.pushLog(ISBN, ID, newDate, bookRepository);

                }
                if(option.equals("l")){
                    System.out.println(bookRepository.findTitle(returnStack.peek().getISBN()) + " is the next book to be checked in.");

                }
                if(option.equals("c")){
                    bookRepository.checkInBook(returnStack.peek().getISBN());
                    System.out.println(bookRepository.findTitle(returnStack.peek().getISBN()) + " has been checked in!");
                    returnStack.popLog();


                }
                if(option.equals("p")){
                    returnStack.printTable(bookRepository);
                }

            }
            if(option.equals("q")){
                return;
            }
        }
    }
    public static void menu(){
        System.out.println("(B) – Manage Book Repository\n" +
                "   (C) – Checkout Book\n" +
                "   (N) – Add New Book\n" +
                "   (R) – Remove Book\n" +
                "   (P) – Print Repository\n" +
                "   (S) – Sort Shelf\n" +
                "       (I) – ISBN Number\n" +
                "       (N) – Name\n" +
                "       (A) – Author\n" +
                "       (G) – Genre\n" +
                "       (Y) – Year\n" +
                "       (C) – Condition\n" +
                "(R) – Manage Return Stack\n" +
                "   (R) – Return Book\n" +
                "   (L) – See Last Return\n" +
                "   (C) – Check In Last Return\n" +
                "   (P) – Print Return Stack\n" +
                "(Q) – Quit");
    }
}
