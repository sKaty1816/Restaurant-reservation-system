import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Restaurant implements Restaurant_Interface {
    private LinkedHashMap<Table, ArrayList<Reservation>> reservations;
    private String currentTime;
    private double total;

    Restaurant() {
        this.reservations = new LinkedHashMap<>();
        total = 0;
        this.currentTime = "08:00";
    }

    public String getCurrentTime() {
        return this.currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public void addTables(TreeMap<Integer, Table> tables) {
        for (var t : tables.entrySet()) {
            this.reservations.put(t.getValue(), new ArrayList<>());
        }
    }

    public void reserveVIP(String type, String name, int numOfPeople, String smoke, String time, String view) {
        if (!isItWorkingTime(time))
            return;
        boolean isItPossible = true;
        for (var table : reservations.entrySet()) {
            Table t = table.getKey();
            if (((smoke.equals("smoker") && t.isSmoker())
                    || (smoke.equals("non-smoker") && !t.isSmoker()))
                    && t.getMaxCapacity() >= numOfPeople
                    && t.getType().equals(type)) {
                for (var reserve : table.getValue()) {
                    if (!reserve.checkTime(time)) {
                        isItPossible = false;
                        break;
                    }
                }
                if (isItPossible) {
                    if (((VIP_Table) t).getView() && view.equals("view")
                            || !((VIP_Table) t).getView() && view.equals("no-view")) {
                        System.out.println("Reservation made.");
                        Reservation newReservation = new Reservation(name, numOfPeople, time);
                        table.getValue().add(newReservation);
                        break;
                    }
                }
            }
            if (!isItPossible)
                System.out.println("We cannot make a reservation at that time!");
        }
    }

    public void reserve(String type, String name, int numOfPeople, String smoke, String time) {
        if (!isItWorkingTime(time))
            return;
        boolean isItPossible = true;
        for (var table : reservations.entrySet()) {
            Table t = table.getKey();
            if (((smoke.equals("smoker") && t.isSmoker())
                    || (smoke.equals("non-smoker") && !t.isSmoker()))
                    && t.getMaxCapacity() >= numOfPeople
                    && t.getType().equals(type)) {
                for (var reserve : table.getValue()) {
                    //checkTime() - checks if desired time is too close to another already made reservation
                    if (!reserve.checkTime(time)) {
                        isItPossible = false;
                        break;
                    }
                }
                if (isItPossible) {
                    Reservation newReservation = new Reservation(name, numOfPeople, time);
                    table.getValue().add(newReservation);
                    System.out.println("Reservation made.");
                    return;
                }
            }
        }
        if (!isItPossible)
            System.out.println("We cannot make a reservation at that time!");
    }

    public void guestArrives(String name, int numOfGuests, String time) {

        for (var table : reservations.entrySet()) {
            for (var reserve : table.getValue()) {
                if (reserve.getName().equals(name) && numOfGuests <= reserve.getNumOfGuests()) {
                    if (time.compareTo(this.currentTime) >= 0)
                        this.currentTime = time;
                    else {
                        System.out.println("Sorry you are late! Your reservation remains for tomorrow.");
                        return;
                    }
                    if (!reserve.checkArrivalTime(time)) {
                        table.getValue().remove(reserve);
                        return;
                    } else {
                        if (!table.getKey().isFree()) {
                            String leavingGuest = table.getKey().getNameOfCurrentGuest();
                            leave(leavingGuest);
                        }
                        table.getKey().setPeopleSeated(numOfGuests);
                        table.getKey().setMaxCapacity(table.getKey().getMaxCapacity() - numOfGuests);
                        table.getKey().setNameOfCurrentGuest(name);
                        System.out.printf("%s has arrived.%n", name);
                        return;
                    }
                } else if (numOfGuests > reserve.getNumOfGuests()) {
                    System.out.printf("Sorry %s but your reservation was for no more than %d people.%n", name, reserve.getNumOfGuests());
                    return;
                }
            }
        }
        System.out.printf("We're sorry %s, but you don't have a reservation.%n", name);
    }

    public void leave(String name) {
        //you can't leave if you are not here
        boolean found = false;
        for (var table : reservations.entrySet()) {
            for (var reserve : table.getValue()) {
                if (reserve.getName().equals(name) && !table.getKey().isFree()) {
                    //generate how much the guests have to pay
                    found = true;
                    double min = (double) table.getKey().getPeopleSeated();
                    double max = 2000;
                    double random = ThreadLocalRandom.current().nextDouble(min, max);

                    //nice view costs +10% more
                    if (table.getKey().getType().equals("VIP") && ((VIP_Table) table.getKey()).getView())
                        random = random + random * 0.1;

                    this.total += random;
                    table.getValue().remove(reserve);
                    table.getKey().setMaxCapacity(table.getKey().getMaxCapacity() + table.getKey().getPeopleSeated());
                    table.getKey().setPeopleSeated(0);
                    table.getKey().setNameOfCurrentGuest("");

                    System.out.println(name + " left and paid " + String.format("%.2f", random) + "$");
                    return;
                }
            }
        }
        if(!found)
            System.out.printf("No guest with the name %s", name);
    }

    public void leaveAll() {
        for (var table : reservations.entrySet()) {
            for (var reserve : table.getValue()) {
                if (!table.getKey().isFree()) {
                    double min = (double) table.getKey().getPeopleSeated();
                    double max = 2000;
                    double random = ThreadLocalRandom.current().nextDouble(min, max);

                    //nice view costs +10% more
                    if (table.getKey().getType().equals("VIP") && ((VIP_Table) table.getKey()).getView())
                        random = random + random * 0.1;

                    this.total += random;
                    String name = table.getKey().getNameOfCurrentGuest();
                    table.getValue().remove(reserve);
                    table.getKey().setMaxCapacity(table.getKey().getMaxCapacity() + table.getKey().getPeopleSeated());
                    table.getKey().setPeopleSeated(0);
                    table.getKey().setNameOfCurrentGuest("");

                    System.out.println(name + " left and paid " + String.format("%.2f", random) + "$");
                    break;
                }
            }
        }
    }

    public boolean isItWorkingTime(String reservedTime) {
        String[] time = reservedTime.split(":");
        int hour = Integer.parseInt(time[0]);
        int minutes = Integer.parseInt(time[1]);
        if (hour < 8) {
            System.out.println("Sorry, the restaurant is not open at that time!");
            return false;
        }
        //The restaurant closes at 22:00 so the last reservation can be at 21:30
        if (hour > 21 && minutes > 30) {
            System.out.println("Sorry, the restaurant closes at 22:00");
            return false;
        }
        return true;
    }

    public void printTables() {
        for (var t : reservations.entrySet()) {
            t.getKey().print();
            System.out.println();
            for (var r : t.getValue()) {
                r.print();
            }
        }

        System.out.println("Total: " + String.format("%.2f", this.total) + "$");
    }

}

