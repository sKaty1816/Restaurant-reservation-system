import java.util.ArrayList;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

public interface Restaurant_Interface {
    String getCurrentTime();

    void setCurrentTime(String currentTime);

    void addTables(TreeMap<Integer, Table> tables);

    void reserveVIP(String type, String name, int numOfPeople, String smoke, String time, String view);

    void reserve(String type, String name, int numOfPeople, String smoke, String time);

    void guestArrives(String name, int numOfGuests, String time);

    void leave(String name);

    void leaveAll();

    boolean isItWorkingTime(String reservedTime);

    void printTables();

}
