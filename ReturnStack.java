import java.util.*;

public class ReturnStack {
    ReturnLog topLog;

    public ReturnStack() {
        topLog = null;
    }

    public ReturnLog peek() {
        return topLog;
    }

    public int length() {
        ReturnLog cursor = topLog;
        int i = 0;
        while (cursor != null) {
            i++;
            cursor = cursor.getNextLog();
        }
        return i;
    }

    public void returnEverything(BookRepository bookRepository) {
        while (topLog != null) {
            bookRepository.checkInBook(peek().getISBN());
            popLog();
        }
    }

    public boolean pushLog(long returnISBN, long returnUserID, Date returnDate, BookRepository bookRepoRef) throws InvalidISBNException, InvalidReturnDateException, BookNotCheckedOutException, BookCheckedOutBySomeoneElseException, InvalidUserIDException {
        boolean good = false;
        try {
            if (String.valueOf(returnISBN).length() > 13) {
                throw new InvalidISBNException();
            }
            if (returnDate.getDay() < 1 || returnDate.getMonth() < 1 || returnDate.getDay() > 31 || returnDate.getMonth() > 12) {

                throw new InvalidReturnDateException();
            }
            if (String.valueOf(returnISBN).length() > 10) {
                throw new InvalidUserIDException();
            }
            Book cursor = bookRepoRef.getHeadBook(0);
            for (int i = 0; i < bookRepoRef.getShelves().length; i++) {
                cursor = bookRepoRef.getHeadBook(i);
                for (int j = 0; j < bookRepoRef.getShelf(i).length(); j++) {
                    if (cursor.getISBN() == returnISBN) {
                        if (!cursor.getCheckedOut()) {
                            good = false;
                            throw new BookNotCheckedOutException();
                        } else if (cursor.getDueDate().getYear() < returnDate.getYear() || cursor.getDueDate().getMonth() < returnDate.getMonth() || (cursor.getDueDate().getDay() < returnDate.getDay() && cursor.getDueDate().getMonth() == returnDate.getMonth())) {
                            good = true;
                            System.out.println(bookRepoRef.findTitle(returnISBN) + " has been returned LATE! Checking everything in...");
                            returnEverything(bookRepoRef);
                        } else if (returnUserID != cursor.getCheckOutUserID()) {
                            good = false;
                            throw new BookCheckedOutBySomeoneElseException();
                        } else {
                            ReturnLog newLog = new ReturnLog(returnISBN, returnUserID, returnDate, null);
                            newLog.setNextLog(topLog);
                            topLog = newLog;
                            good = true;
                            System.out.println(bookRepoRef.findTitle(returnISBN) + " has been returned on time!");
                            return good;

                        }


                    }
                    cursor = cursor.getNextBook();
                }

            }
        } catch (InvalidUserIDException | InvalidISBNException | InvalidReturnDateException |
                 BookCheckedOutBySomeoneElseException ex) {
            System.out.println(ex.getMessage());
        }
        return good;


        //do other exceptions
        //how to use bookRef?

    }

    public ReturnLog popLog() throws EmptyStackException {
        try {
            if (topLog == null) {
                throw new EmptyStackException();
            } else {

                ReturnLog cursor = topLog;
                topLog = topLog.getNextLog();
                return cursor;
            }
        } catch (EmptyStackException ex) {
            System.out.println(ex.getMessage());
        }
        return null;

    }

    public void printTable(BookRepository b) throws InvalidISBNException, BookCheckedOutBySomeoneElseException, InvalidReturnDateException, BookNotCheckedOutException, InvalidUserIDException {
        System.out.println("|        ISBN        " + "|" + "      USERID        " + "|" + "     Return Date    " + "|");
        for (int j = 0; j < 64; j++)
            System.out.print("=");
        System.out.println();
        if (topLog == null) {
            return;
        } else {
            while (topLog != null) {
                System.out.println(popLog().toString());
            }

        }


    }


}