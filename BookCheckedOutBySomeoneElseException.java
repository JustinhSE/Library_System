public class BookCheckedOutBySomeoneElseException extends Exception {
    public BookCheckedOutBySomeoneElseException(){
        super("Someone else has this book.");
    }
}
