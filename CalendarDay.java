
package calendarday;

import java.util.Arrays;

class YearException extends Exception {
    private int year;
    
    public YearException(int a) {
        super("Year out of range");
        year = a;
    }
    public String toString() {
        return year + " is not a valid year.";
    }
}

class MonthException extends Exception {
    private int month;
    
    public MonthException(int b) {
        super("Month out of range");
        month = b;
    }
    public String toString() {
        return month + " is not a valid month.";
    }
}

class DayException extends Exception {
    private int day;
    
    public DayException(int c) {
        super("Day out of range");
        day = c;
    }
    public String toString() {
        return day + " is not a valid day (for that month/year).";
    }
}

public class CalendarDay implements Comparable {
    private int month;
    private int day;
    private int year;
    
    public CalendarDay(int m, int d, int y) 
        throws YearException, MonthException, DayException {
        
        if( y < 1000 || y > 2500 )
            throw new YearException(y);
        if(m < 1 || m > 12) 
            throw new MonthException(m);
        if(!validDay(m,d,y))
            throw new DayException(d);
        year = y;
        month = m;   
        day = d;
    }
    
    public CalendarDay(String date) 
        throws YearException, MonthException, DayException {
        
        String[] mdy;                           // Use a 3-element String array
        mdy = date.split("/");                  // to split the given date
                                                // into parts
        month = Integer.parseInt(mdy[0]);       // Then convert Strings to ints
        day = Integer.parseInt(mdy[1]);
        year = Integer.parseInt(mdy[2]);
        
        if( year < 1000 || year > 2500 )
            throw new YearException(year);
        if(month < 1 || month > 12) 
            throw new MonthException(month);
        if(!validDay(month,day,year))
            throw new DayException(day);
    }
    
    // Copy constructor
    public CalendarDay(CalendarDay d) {
        this.month = d.month;
        this.day = d.day;
        this.year = d.year; 
    }
    
    public CalendarDay getDate()                // safely return the date
        throws YearException, MonthException, DayException {
        
        CalendarDay temp = new CalendarDay(1,1,1000);
        temp.month = month;
        temp.day = day;
        temp.year = year;
        return temp;
    }
    
    public void setDate(int m, int d, int y) 
        throws YearException, MonthException, DayException {
        
        if( y < 1000 || y > 2500 )
            throw new YearException(y);
        if(m < 1 || m > 12) 
            throw new MonthException(m);
        if(!validDay(m,d,y))
            throw new DayException(d);
        year = y;
        month = m;   
        day = d;
    }
    
    public void setDate(String date) 
        throws YearException, MonthException, DayException {
        
        String[] mdy;                           // same as above
        mdy = date.split("/");
        
        month = Integer.parseInt(mdy[0]);
        day = Integer.parseInt(mdy[1]);
        year = Integer.parseInt(mdy[2]);
        
        if( year < 1000 || year > 2500 )
            throw new YearException(year);
        if(month < 1 || month > 12) 
            throw new MonthException(month);
        if(!validDay(month,day,year))
            throw new DayException(day);
    }
    
    private boolean validDay(int mo, int dy, int yr) {
        // check February
        if(mo == 2) {  
            // check leap year
            if((yr % 4 == 0 && yr % 100 != 0) || yr % 400 == 0) {
                if(dy > 29) return false;
            } else if(dy > 28) return false;
        } 
        // check months with 30 days
        else if(mo == 4 || mo == 6 || mo == 9 || mo == 11) {  
            if(dy > 30) return false;
        }
        // all other months
        else if(dy > 31) return false;
        
        return true;
    }
    
    public boolean equals(CalendarDay d) {
        if(this == d) return true;                          // same reference
        if(d == null) return false;                         // no reference
        if(this.getClass() != d.getClass()) return false;   // different class
        if(this.month != d.month || this.day != d.day || this.year != d.year)
            return false;                                   // different date
        return true;
    }
    
    public int compareTo(Object obj) {
        CalendarDay d = (CalendarDay) obj;
        if(year < d.year) return -1;
        else if(year > d.year) return 1;
        else if(month < d.month) return -1;
        else if(month > d.month) return 1;
        else if(day < d.day) return -1;
        else if(day > d.day) return 1;
        else return 0;
    }
    
    public String toString() {
        return month + "/" + day + "/" + year;
    }
    
    public static void main(String[] args) 
        throws YearException, MonthException, DayException {
        
        CalendarDay def = new CalendarDay(11,11,2222);      // default date
        CalendarDay[] myDates = new CalendarDay[7];
        int month, day, year;
        CalendarDay[] randomDates = new CalendarDay[10]; 
        
        for(int i=0; i< 7; i++) {
            try {
                switch(i) {
                    case 0: myDates[i] = new CalendarDay(14, 35, -100); 
                    case 1: myDates[i] = new CalendarDay("Hi there");
                    case 2: myDates[i] = new CalendarDay(2, 29, 1900);
                    case 3: myDates[i] = new CalendarDay("AB/CD/EF");
                    case 4: myDates[i] = new CalendarDay(2, 29, 2017);
                    case 5: myDates[i] = new CalendarDay(3, 06, 2017);     // OK
                            break;
                    case 6: myDates[i] = new CalendarDay("7/8/99");
                }
            } catch(YearException | MonthException | DayException exc) {
                System.out.println(exc);
                myDates[i] = new CalendarDay(def);       // use copy constructor
            } catch(NumberFormatException exc) {
                System.out.println("Error translating string to date.");
                myDates[i] = new CalendarDay(def);
            }
        }
        System.out.println("\nDate 1 was: " + myDates[0].getDate());  // getDate()
        myDates[0].setDate(4, 18, 2004);                              // setDate()
        System.out.println("Date 1 is now: " + myDates[0].getDate());
        System.out.println("Date 6 was: " + myDates[5].getDate());
        myDates[5].setDate("4/18/2004");
        System.out.println("Date 6 is now: " + myDates[5].getDate());
        
        if(myDates[0].equals(myDates[5])) 
            System.out.println("Date 1 and Date 6 are the same.");     // equals()
        
        System.out.println("\nNow a collection of 10 random dates "
                + "will be created.");
        int ct = 0;
        while(ct < 10) {
            month = (int) (Math.random() * 12 + 1);
            day = (int) (Math.random() * 31 + 1);
            year = (int) (Math.random() * 1501 + 1000);
            try {
                randomDates[ct] = new CalendarDay(month,day,year);
                System.out.println(randomDates[ct]);
                ct++;
                Thread.sleep(500);
            } catch(YearException | MonthException | DayException exc) {
                System.out.printf(" %d/%d/%d is invalid\n", month, day, year);
            } catch(InterruptedException exc) {
                System.out.println("Interrupted");
            }
        }
        Arrays.sort(randomDates);
        System.out.println("\nThe sorted dates are:");
        for(CalendarDay d : randomDates) System.out.println(d);
    }
}


/*run:
-100 is not a valid year.
Error translating string to date.
29 is not a valid day (for that month/year).
Error translating string to date.
29 is not a valid day (for that month/year).
99 is not a valid year.

Date 1 was: 11/11/2222
Date 1 is now: 4/18/2004
Date 6 was: 3/6/2017
Date 6 is now: 4/18/2004
Date 1 and Date 6 are the same.

Now a collection of 10 random dates will be created.
12/10/2161
5/11/1954
12/17/1882
4/20/2005
11/10/1837
9/26/1921
12/22/1882
2/2/1285
5/4/2442
12/29/1288

The sorted dates are:
2/2/1285
12/29/1288
11/10/1837
12/17/1882
12/22/1882
9/26/1921
5/11/1954
4/20/2005
12/10/2161
5/4/2442
*/