public interface Reservation_Interface {

    boolean checkTime(String reservedTime);

    boolean checkArrivalTime(String arrivalTime);

    String getName();

    int getNumOfGuests();

    void print();
}
