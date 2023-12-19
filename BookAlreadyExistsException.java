public class BookAlreadyExistsException extends Exception {
    public BookAlreadyExistsException(){
        super("This book already exists.");
    }
}
