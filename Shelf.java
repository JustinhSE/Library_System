public class Shelf {
    private Book headBook, tailBook;
    private int length;

    private SortCriteria shelfSortCriteria;
    public Book getHeadBook(){
        return headBook;
    }
    public Book getTailBook(){
        return tailBook;
    }
    public Shelf(){
        headBook = tailBook = null;
        length = 0;
        shelfSortCriteria = SortCriteria.ISBN;
    }
    public SortCriteria getShelfSortCriteria(){
        return shelfSortCriteria;
    }

    public void addBook(Book addedBook) throws BookAlreadyExistsException{
        if(headBook == null){
            addedBook.setNextBook(headBook);
            headBook = tailBook = addedBook;
            length++;
            return;
        }
        else{
            Book cursor = headBook;
            try {
                while (cursor != null) {
                    if (cursor.getISBN() == addedBook.getISBN()) {
                        throw new BookAlreadyExistsException();
                    }
                    cursor = cursor.getNextBook();
                }
                addedBook.setNextBook(headBook);
                headBook = addedBook;
                length++;
                sort(SortCriteria.ISBN);
            }
            catch(BookAlreadyExistsException ex){
                System.out.println(ex.getMessage());
            }

        }
    }
    public int getLength(){
        return length;
    }
    public void removeBook(long removedISBN) throws InvalidISBNException, BookDoesNotExistException{
        boolean demo = true;
        try {
            if (String.valueOf(removedISBN).length() > 13) {
                System.out.println(String.valueOf(removedISBN));
                demo = false;
                throw new InvalidISBNException();
            }
        } catch(InvalidISBNException ex){
            System.out.println(ex.getMessage());
        }
        if(demo){
            Book ptr = headBook;
            if (ptr == null) {
                return;
            }
            if (ptr.getISBN() == removedISBN && ptr.getNextBook() == null) {
                tailBook = headBook = null;
                length--;
                return;
            }
            if (ptr.getISBN() == removedISBN && ptr.getNextBook() != null) {
                headBook = headBook.getNextBook();
                length--;
                return;
            }
            Book t1 = headBook ;
            try {
                for (int i = 0; i < length; i++) {
                    if(t1 == null){
                        break;
                    }
                    if (t1.getNextBook().getISBN() == removedISBN) {
                        if (t1.getNextBook().getNextBook() == null) {
                            t1.setNextBook(null);
                            demo = false;
                            break;
                        } else {
                            t1.setNextBook(t1.getNextBook().getNextBook());
                            length--;
                            demo = false;
                            return;
                        }
                    }
                    t1 = t1.getNextBook();
                }
                if(tailBook.getISBN() != removedISBN && demo){
                    throw new BookDoesNotExistException();
                }
                else{
                    return;
                }
            }
            catch(BookDoesNotExistException ex){
                System.out.println(ex.getMessage());
            }
        }
    }


    //trivial method
    public int length(){
        Book cursor = headBook;
        int count = 0;
        while(cursor != null){
            cursor = cursor.getNextBook();
            count++;
        }
        length = count;
        return count;
    }
    public String toString(){
        String result = "";
        if(headBook == null){
            return "";
        }
        else{
            Book cursor;
            cursor = headBook;
            for(int j = 0; j < length; j++){
                if(cursor == null){
                    break;
                }
                if(shelfSortCriteria == SortCriteria.ISBN){
                    String str = " ";
                    while(str.length() + String.valueOf(cursor.getISBN()).length() < 13){
                        str = str.concat("0");
                    }
                    str = str.concat(String.valueOf(cursor.getISBN()) + " ");
                    result = result.concat(cursor.spacing(String.valueOf(str)));
                }
                else if(shelfSortCriteria == SortCriteria.GENRE){
                    result = result.concat(cursor.spacing(String.valueOf(cursor.getGenre())));
                }
                else if(shelfSortCriteria == SortCriteria.YEAR){
                    result = result.concat(cursor.spacing(String.valueOf(cursor.getYearPub())));
                }
                else if(shelfSortCriteria == SortCriteria.NAME){
                        result = result.concat(cursor.spacing(String.valueOf(cursor.getName())));
                }
                else if(shelfSortCriteria == SortCriteria.CONDITION){
                    result = result.concat(cursor.spacing(cursor.getCondition().toString()));

                }
                else if(shelfSortCriteria == SortCriteria.AUTHOR){
                    result = result.concat(cursor.spacing(cursor.getAuthor()));
                }
                if(!result.isEmpty())
                    result = result.substring(0,result.length()-1);
                result = result.concat(cursor.toString() + "\n");
                if(cursor.getNextBook() == null){
                    return result;
                }
                else{
                    cursor = cursor.getNextBook();
                }
            }
            return result;
        }

    }
    public void setHeadBook(Book b){
        headBook = b;
    }
    public void setTailBook(Book t){
        tailBook = t;
    }

    public void sort(SortCriteria sortCriteria){
        System.out.println(sortCriteria);
        if(headBook == null || length == 1){
            shelfSortCriteria = sortCriteria;
            return;
        }
        else if (sortCriteria == SortCriteria.NAME){
            shelfSortCriteria = SortCriteria.NAME;
            Book cursor = headBook;
            Book[] b = new Book[length];
            //adding all elements to array
            for(int a = 0; a < length; a++){
                b[a] = cursor;
                cursor = cursor.getNextBook();
            }
            //sorting array using compareTo
            for(int i = 0; i < length-1;i++ ){
                for(int j = i+1 ; j < length; j++){
                    if(b[i].getName().compareToIgnoreCase(b[j].getName()) > 0){
                        Book temp1 = b[j];
                        b[j] = b[i];
                        b[i] = temp1;
                    }

                }
            }
            //resorting linkedlist based on array
            headBook = b[0];
            cursor = headBook;
            for(int c = 1 ; c < length && cursor != null; c++){

                cursor.setNextBook(b[c]);
                cursor = cursor.getNextBook();
            }
            tailBook = b[length-1];
            return;
        }
        else if(sortCriteria == SortCriteria.AUTHOR){
            shelfSortCriteria = SortCriteria.AUTHOR;
            Book cursor = headBook;
            Book[] b = new Book[length];
            //adding all elements to array
            for(int a = 0; a < length; a++){
                b[a] = cursor;
                cursor = cursor.getNextBook();
            }
            //sorting array using compareTo
            for(int i = 0; i < length-1;i++ ){
                for(int j = i+1 ; j < length; j++){
                    if(b[i].getAuthor().compareToIgnoreCase(b[j].getAuthor()) > 0){
                        Book temp1 = b[j];
                        b[j] = b[i];
                        b[i] = temp1;
                    }
                }
            }
            //resorting linkedlist based on array
            headBook = b[0];
            cursor = headBook;
            for(int c = 1 ; c < length && cursor != null; c++){
                cursor.setNextBook(b[c]);
                cursor = cursor.getNextBook();
            }
            tailBook = b[length-1];
            return;

        }
        else if(sortCriteria == SortCriteria.CONDITION){
            shelfSortCriteria = SortCriteria.CONDITION;
            Book cursor = headBook;
            Book[] b = new Book[length];
            //adding all elements to array
            for(int a = 0; a < length; a++){
                b[a] = cursor;
                cursor = cursor.getNextBook();
            }
            //sorting array using compareTo
            for(int i = 0; i < length-1;i++ ){
                for(int j = i+1 ; j < length; j++){
                    if(String.valueOf(b[i].getCondition()).compareTo(String.valueOf(b[j].getCondition())) > 0){
                        Book temp1 = b[j];
                        b[j] = b[i];
                        b[i] = temp1;
                    }
                }
            }
            //bad good new
            //sorting makes it in order: bad good new replace so using array cop to make it replace bad good new
            int found = 0;
            int found2 = 0;
            int count = 0;
            int count2 = 0;
            for(int t = 0; t < b.length; t++){
                if(b[t].getCondition() == Condition.REPLACE){
                    if(count == 0){
                        found = t;
                        count++;
                    }
                }
                else{
                    count2++;
                }
            }
            if(found != 0 ){
                Book[] temp2 = new Book[b.length];
                System.arraycopy(b,0,temp2,b.length-found, found );
                System.arraycopy(b, found, temp2,0, b.length - found);
                b = temp2;
            }

            //resorting linkedlist based on array
            headBook = b[0];
            cursor = headBook;
            for(int c = 1 ; c < length && cursor != null; c++){
                cursor.setNextBook(b[c]);
                cursor = cursor.getNextBook();
            }
            tailBook = b[length-1];
            return;
        }
        else if(sortCriteria == SortCriteria.ISBN){
            shelfSortCriteria = SortCriteria.ISBN;
            Book cursor = headBook;
            Book[] b = new Book[length()];
            //adding all elements to array
            for(int a = 0; a < length(); a++){
                b[a] = cursor;
                cursor = cursor.getNextBook();
            }

            //sorting array using compareTo
            for(int i = 0; i < length()-1;i++ ){
                for(int j = i ; j < length(); j++){
                    if(b[i].getISBN() > (b[j].getISBN())){
                        Book temp1 = b[j];
                        b[j] = b[i];
                        b[i] = temp1;
                    }
                }
            }
            //resorting linkedlist based on array
            headBook = b[0];
            cursor = headBook;
            for(int c = 1 ; c < length && cursor != null; c++){

                cursor.setNextBook(b[c]);
                cursor = cursor.getNextBook();
            }
            tailBook = b[length-1];
            return;

        }
        else if(sortCriteria == SortCriteria.YEAR){
            shelfSortCriteria = SortCriteria.YEAR;
            Book cursor = headBook;
            Book[] b = new Book[length()];
            //adding all elements to array
            for(int a = 0; a < length(); a++){
                b[a] = cursor;
                cursor = cursor.getNextBook();
            }

            //sorting array using compareTo
            for(int i = 0; i < length()-1;i++ ){
                for(int j = i ; j < length(); j++){
                    if(b[i].getYearPub() > (b[j].getYearPub())){
                        Book temp1 = b[j];
                        b[j] = b[i];
                        b[i] = temp1;
                    }
                }
            }
            //resorting linkedlist based on array
            headBook = b[0];
            cursor = headBook;
            for(int c = 1 ; c < length && cursor != null; c++){
                cursor.setNextBook(b[c]);
                cursor = cursor.getNextBook();
            }
            tailBook = b[length-1];
            return;

        }
        else if(sortCriteria == SortCriteria.GENRE){

            shelfSortCriteria = SortCriteria.GENRE;
            Book cursor = headBook;
            Book[] b = new Book[length];
            //adding all elements to array
            for(int a = 0; a < length; a++){
                b[a] = cursor;
                cursor = cursor.getNextBook();
            }


            //sorting array using compareTo
            for(int i = 0; i < length-1;i++ ){
                for(int j = i+1 ; j < length; j++){
                    if(b[i].getGenre().compareToIgnoreCase(b[j].getGenre()) > 0){
                        Book temp1 = b[j];
                        b[j] = b[i];
                        b[i] = temp1;
                    }
                }
            }

            //resorting linkedlist based on array
            headBook = b[0];
            cursor = headBook;
            for(int c = 1 ; c < length && cursor != null; c++){

                cursor.setNextBook(b[c]);
                cursor = cursor.getNextBook();
            }
            tailBook = b[length-1];
            return;


        }
        else{
            System.out.println("Unable to sort. ");

        }

    }




}
