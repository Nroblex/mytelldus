package DBmanager;

/**
 * Created by ueh093 on 11/20/15.
 */
public enum WeekDays {

    måndag(1), tisdag(2), onsdag(3), torsdag(4), fredag(5), lördag(6), söndag(7);
    private int weekday;

    WeekDays(int i) {
        weekday=i;
    }
    int getWeekDay(){
        return weekday;
    }
}
