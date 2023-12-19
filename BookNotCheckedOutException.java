public class BookNotCheckedOutException extends Exception {
    public BookNotCheckedOutException(){
        super("ISBN was not checked out.");
    }
}
