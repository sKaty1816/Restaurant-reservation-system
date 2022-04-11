public class Reservation implements Reservation_Interface {
    private String name;
    private int numOfGuests;
    private int hour;
    private int minutes;


    Reservation(String name, int numOfGuests, String time) {
        this.name = name;
        this.numOfGuests = numOfGuests;
        String[] time1 = time.split(":");
        this.hour = Integer.parseInt(time1[0]);
        this.minutes = Integer.parseInt(time1[1]);
    }

    public boolean checkTime(String reservedTime) {
        String[] time = reservedTime.split(":");
        int hour = Integer.parseInt(time[0]);
        int minutes = Integer.parseInt(time[1]);
        int timeInMinutes = hour * 60 + minutes;
        int thisTimeInMinutes = this.hour * 60 + this.minutes;
        if (Math.abs(timeInMinutes - thisTimeInMinutes) < 30)
            return false;
        return true;
    }

    public boolean checkArrivalTime(String arrivalTime) {
        String[] time = arrivalTime.split(":");
        int hour = Integer.parseInt(time[0]);
        int minutes = Integer.parseInt(time[1]);
        int timeInMinutes = hour * 60 + minutes;
        int thisTimeInMinutes = this.hour * 60 + this.minutes;
        if (timeInMinutes > thisTimeInMinutes && timeInMinutes - thisTimeInMinutes > 15) {
            System.out.printf("Sorry %s, you are late for your reservation.%n", this.name);
            return false;
        } else if (timeInMinutes < thisTimeInMinutes && thisTimeInMinutes - timeInMinutes > 15) {
            System.out.printf("%s, you are too early for your reservation.%n", this.name);
            return false;
        }
        return true;
    }

    public String getName() {
        return name;
    }

    public int getNumOfGuests() {
        return numOfGuests;
    }

    public void print() {
        System.out.print("----Name: " + this.name + " Guests: " + this.numOfGuests + " Time: ");
        if (hour < 10)
            System.out.print("0" + hour + ":");
        else
            System.out.print(hour + ":");
        if (minutes < 10)
            System.out.println("0" + minutes);
        else
            System.out.println(minutes);
    }
}
