public class BookAlreadyCheckedOutException extends Exception {
    public BookAlreadyCheckedOutException(){
        super("Book unavaliable.");
    }
}
