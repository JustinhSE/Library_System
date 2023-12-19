public class Book {
    private String name, author, genre;

    private Condition bookCondition;
    private long ISBN, checkOutUserID;
    private int yearPublished;
    private Date checkOutDate, dueDate;
    private Book nextBook;
    private boolean checkedOut;



    public Book(){
        name = author= genre = null;
        ISBN = checkOutUserID = -1L;
        yearPublished = -1;
        checkOutDate = null;
        nextBook = null;
        checkedOut = false;
        bookCondition = Condition.NEW;
    }
    public Book(String name, String author, String genre, Condition given, long ISBN, long checkOutUserID, int yearPublished, boolean checkedOut ){
        this.name = name;
        this.author= author;
        this.genre = genre;
        this.bookCondition = given;
        this.ISBN = ISBN;
        this.checkOutUserID = checkOutUserID;
        this.yearPublished = yearPublished;
        this.checkOutDate = checkOutDate;
        this.nextBook = nextBook;
        this.checkedOut= checkedOut;

    }
    public String spacing(String name){
        String temp = "";
        //    Jocelyn
        int len = name.length();
        if(len > 20){
            while(len > 20){
                temp = temp.concat("|" + name.substring(0,20) + "|             |                 |" + "\n");
                name = name.substring(20);
                len = name.length();
            }

        }
        if(len < 20){
            temp = temp.concat("|");
            if(name.length() % 2 == 1){
                temp = temp.concat(" ");
            }

            for(int i = 0; i < ((20 - len)/2);i++){
                temp = temp.concat(" ");
            }
            temp = temp.concat(name);
            for(int i = 0; i < ((20 - len)/2);i++){
                temp = temp.concat(" ");
            }
            temp = temp.concat("|");
        /*
        |David and Goli|
        | ath in the B |
        |  attleField  |
         */

        }
        return temp;
    }

    public String toString(){
        String l = "";
        String bool = "Y";
        String s = null;
        String str = "";

        if(checkedOut){
            bool = "Y";
             s = spacing(dueDate.toString());
            str = str.concat(spacing(String.valueOf(getCheckOutUserID())));
            str = str.concat("   ");

        }
        if(!checkedOut){
            bool = "N";
            s = str = spacing("N/A");
            str = str.substring(1,str.length()-1);

        }

        l = l.concat(spacing(name) + "     " + bool + "       " +  s + str );
        return l;
    }
    public Condition getCondition(){
        return bookCondition;

    }
    public void setBookCondition(Condition d){
        bookCondition = d;
    }
    public int getYearPub(){
        return yearPublished;
    }
    public String getAuthor(){
        return author;
    }
    public void setAuthor(String s){
        author = s;
    }
    public String getGenre(){
        return genre;
    }
    public void setDueDate(Date s){
        dueDate = s;
    }
    public Date getDueDate(){
        return dueDate;
    }
    public void setCheckOutDate(Date d){
        checkOutDate = d;
    }
    public Date getCheckOutDate(){
        return checkOutDate;
    }


    public void setCheckOutUserID(long l){
        checkOutUserID = l;
    }
    public long getCheckOutUserID(){
        return checkOutUserID;
    }
//    public void setDate(Date d){
//        checkOutDate = d;
//    }
//    public Date getDate(){return checkOutDate;}
    public void setCheckedOut(boolean b){
        checkedOut = b;
    }
    public boolean getCheckedOut(){
        return checkedOut;
    }


    public void setNextBook(Book b){
        nextBook = b;
    }
    public Book getNextBook(){
        return nextBook;
    }
    public String getName(){
        return name;
    }
    public long getISBN(){
        return ISBN;
    }
}
