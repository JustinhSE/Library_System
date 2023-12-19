public class ReturnLog {
    private long ISBN, userID;
    private Date returnDate;
    private ReturnLog nextLog;
    public ReturnLog(){
        ISBN = userID = -1L;
        returnDate = null;
        nextLog = null;
    }
    public ReturnLog(long ISBN, long userID, Date returnDate, ReturnLog nextLog){
        this.ISBN = ISBN;
        this.userID = userID;
        this.returnDate = returnDate;
        this.nextLog = nextLog;
    }
    public String spacing(long l){
        String temp = "        ISBN        ";
        String str = "";
        for(int i  =0; i < (temp.length() - String.valueOf(l).length()) /2; i++){
            str = str.concat(" ");
        }
        str = str.concat(String.valueOf(l));
        for(int i  =0; i < (temp.length() - String.valueOf(l).length()) /2; i++){
            str = str.concat(" ");
        }
        if((temp.length() - String.valueOf(l).length()) %2 == 1){
            str = str.concat(" ");
        }
        return str;
    }
    public String spacing(String l){
        String temp = "        ISBN        ";
        String str = "";
        int i1 = (temp.length() - String.valueOf(l).length()) / 2;
        for(int i = 0; i < i1; i++){
            str = str.concat(" ");
        }
        str = str.concat(String.valueOf(l));
        for(int i = 0; i < i1; i++){
            str = str.concat(" ");
        }
        if((temp.length() - String.valueOf(l).length()) %2 == 1){
            str = str.concat(" ");
        }
        return str;
    }
    public String toString(){
        String str = " ";
        while(str.length() + String.valueOf(ISBN).length() < 13){
            str = str.concat("0");
        }
        str = str.concat(String.valueOf(ISBN) + " ");

        return "|   " + str + "   |" + spacing(userID) + "|" + spacing(returnDate.toString()) +"|";
    }
    public void setISBN(long l){
        ISBN = l;
    }
    public void setUserID(long i){
        userID = i;
    }
    public void setReturnDate(Date d){
        returnDate = d;
    }
    public void setNextLog(ReturnLog r){
        nextLog = r;
    }
    public long getISBN(){
        return ISBN;
    }
    public long getUserID(){
        return userID;
    }
    public Date getReturnDate(){
        return returnDate;
    }
    public ReturnLog getNextLog(){
        return nextLog;
    }

}
