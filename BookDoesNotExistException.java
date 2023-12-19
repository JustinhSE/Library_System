public class BookDoesNotExistException extends Exception {
    public BookDoesNotExistException(){
        super("This book does not exist.");
    }
}
