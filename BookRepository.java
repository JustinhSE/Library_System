

public class BookRepository {
    private Shelf[] shelves;
//    private enum Condition{
//        //bookCondition
//        NEW,
//        GOOD,
//        BAD,
//        REPLACE;
//    }

    public BookRepository(){
        shelves = new Shelf[10];
    }
    public Book getHeadBook(int i){
        return shelves[i].getHeadBook();
    }
    public Shelf getShelf(int i){
        return shelves[i];
    }
    public Shelf[] getShelves(){
        return shelves;
    }
    public String findTitle(long ISBN) {
        Book cursor;
        for (int i = 0; i < shelves.length; i++) {
            if(shelves[i] == null){
                continue;
            }
            cursor = shelves[i].getHeadBook();
            for (int j = 0; j < shelves[i].length(); j++) {
                if (cursor == null) {
                    break;
                } else {
                    if (cursor.getISBN() == ISBN) {
                        return cursor.getName();
                    }
                    cursor = cursor.getNextBook();
                }
            }


        }
        return null;
    }

    public void checkInBook(long checkedInISBN/*, long checkInUserID*/){
        //what is checkInUserID do? - return log, book goes to return stack, in stack go to shelf
        Book cursor = null;
        for(int i = 0; i < shelves.length; i++){
            if(shelves[i] == null){
                continue;
            }
            cursor = shelves[i].getHeadBook();
            for(int j = 0; j < shelves[i].length();j++ ){
                if(cursor == null) {
                    return;
                }
                else{
                    if(cursor.getISBN() == checkedInISBN){
                        cursor.setCheckedOut(false);


                        return;
                    }
                    cursor = cursor.getNextBook();
                }
            }

        }



    }
    public void checkOutBook(long checkedOutISBN, long checkOutUserID, Date dueDate) throws InvalidISBNException, InvalidUserIDException, BookAlreadyCheckedOutException, BookDoesNotExistException {
        Book cursor;
        try {
            if ((Long.toString(checkedOutISBN)).length() > 13) {
                throw new InvalidISBNException();
            }
            if (Long.toString(checkOutUserID).length() > 10) {
                throw new InvalidUserIDException();
            }
            int pointer = insert(checkedOutISBN);
            if (String.valueOf(checkedOutISBN).length() < 13) {
                pointer = 0;
            }
            //removed from repository and later we don't have user records, removed and only can get it once it comes back
            if (shelves[pointer] == null) {
                return;
            }
            cursor = shelves[pointer].getHeadBook();
            for (int j = 0; j < shelves[pointer].length(); j++) {
                if (cursor == null) {
                    throw new BookDoesNotExistException();
                } else {
                    //fix
                    if (cursor.getISBN() == checkedOutISBN) {
                        if (cursor.getCheckedOut()) {
                            throw new BookAlreadyCheckedOutException();
                        }
                        cursor.setCheckedOut(true);
                        cursor.setCheckOutUserID(checkOutUserID);
                        cursor.setDueDate(dueDate);
                        return;
                    }
                    cursor = cursor.getNextBook();
                }
            }
        }
        catch(BookDoesNotExistException | InvalidUserIDException | InvalidISBNException | BookAlreadyCheckedOutException ex){
            System.out.println(ex.getMessage());

                }


    }
    public String edit(String name){
        String measurement = "  Sorting Criteria  ";
        String temp = "";
        int len = name.length();
        int length = measurement.length();
        if(name.length() % 2 == 1){
            temp = temp.concat(" ");
        }

        for(int i = 0; i < ((length - len)/2);i++){
            temp = temp.concat(" ");
        }
        temp = temp.concat(name);
        for(int i = 0; i < ((length - len)/2);i++){
            temp = temp.concat(" ");
        }
        return temp;
    }
    public void printTable(int index){
        System.out.println("|"+edit("SC:" + shelves[index].getShelfSortCriteria().toString())+"|        Name        " + "|" + " Checked Out " + "|" + "      Due Date      |" + "    Checkout UserID  ");
        for (int j = 0; j < 102; j++)
            System.out.print("=");
        System.out.println();
        System.out.println(shelves[index].toString());
    }

    public int insert(long l){
        String str = String.valueOf(l);
        String s = str.substring(0,1);
        return Integer.parseInt(s);
    }
    //    public Book(String name, String author, String genre, Condition given, long ISBN, long checkOutUserID, int yearPublished, Date checkOutDate, Book nextBook, boolean checkedOut ){
    public void addBook(String name, String author, String genre, Condition given, long ISBN, long checkOutUserID, int yearPublished, boolean checkedOut ) throws InvalidISBNException, BookAlreadyExistsException, InvalidSortCriteriaException {
        Shelf shelf;
        Book newBook = new Book(name, author, genre, given, ISBN, checkOutUserID, yearPublished, checkedOut);

        try {
            int pointer = insert(ISBN);
            if(String.valueOf(ISBN).length() < 13){
                pointer = 0;
            }
            if(String.valueOf(ISBN).length() > 13){
                throw new InvalidISBNException();
            }
            if (shelves[pointer] == null) {
                shelves[pointer] = new Shelf();
                shelves[pointer].addBook(newBook);
                System.out.println(name + " has been successfully added to the book repository!");
                return;
            }
            Shelf temp = shelves[pointer];
            Book cursor = temp.getHeadBook();

            while (cursor != null) {
                if (ISBN == cursor.getISBN()) {
                    throw new BookAlreadyExistsException();

                }
                cursor = cursor.getNextBook();
            }
            temp.addBook(newBook);
            System.out.println(name + " has been successfully added to the book repository!");
        }
        catch(BookAlreadyExistsException | InvalidISBNException ex){
            System.out.println(ex.getMessage());
        }

    }
    public void removeBook(long removeISBN) throws InvalidISBNException, BookDoesNotExistException {
        boolean b1= true;
        try{
            if((Long.toString(removeISBN)).length() > 13) {
                b1 = false;
                throw new InvalidISBNException();
            }
        }
        catch(InvalidISBNException ex) {
            System.out.println("Invalid ISBN");
        }
        if(b1) {
            Book cursor;
            int pointer = insert(removeISBN);
            if (String.valueOf(removeISBN).length() < 13) {
                pointer = 0;
            }
            if (shelves[pointer] == null) {
                System.out.println("Not found");
                return;
            } else {
                System.out.println(findTitle(removeISBN) + " has been successfully removed from the book repository!");
                shelves[pointer].removeBook(removeISBN);
                return;
            }
        }



    }

    public void sortShelf(int shelfInd, String sortCriteria) throws InvalidSortCriteriaException{
        sortCriteria = sortCriteria.toLowerCase();

            if (sortCriteria.equals("i")) {
                shelves[shelfInd].sort(SortCriteria.ISBN);
            }
            else if (sortCriteria.equals("n")) {

                shelves[shelfInd].sort(SortCriteria.NAME);
            }
            else if (sortCriteria.equals("a")) {
                shelves[shelfInd].sort(SortCriteria.AUTHOR);

            }
            else if (sortCriteria.equals("g")) {
                shelves[shelfInd].sort(SortCriteria.GENRE);

            }
            else if (sortCriteria.equals("y")) {
                shelves[shelfInd].sort(SortCriteria.YEAR);
            }
            else if (sortCriteria.equals("c")) {
                shelves[shelfInd].sort(SortCriteria.CONDITION);

            }
            else {
                try {
                    throw new InvalidSortCriteriaException();
                }
                catch (InvalidSortCriteriaException ex){
                    System.out.println(ex.getMessage());
                }
            }
        }

//ISBN Number
//(N) – Name
//(A) – Author
//(G) – Genre
//(Y) – Year
//(C) – Condition

    }




