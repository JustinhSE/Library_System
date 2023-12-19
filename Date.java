public class Date {
    private int day, month, year;
    public Date(){
        day = month = year = -1;
    }
    public Date(int day, int month, int year){
        this.day = day;
        this.month = month;
        this.year = year;
    }
    public int getDay(){
        return day;
    }
    public int getMonth(){
        return month;
    }
    public int getYear(){
        return year;
    }
    public void setDay(int day){
        this.day = day;
    }
    public void setMonth(int m){
        month  = m;
    }
    public void setYear(int y){
        year = y;
    }
    public static int compare(int x, int y) {
        if (x == y) {
            return 0;
        } else if (x < y) {
            return -1;
        } else {
            return 1;
        }
    }
    public String toString(){
        if(month == -1){
            return "N/A";
        }
        else {
            return month + "/" + day + "/" + year;
        }
    }

}
