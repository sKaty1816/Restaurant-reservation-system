import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

public class Start {


    public static void run() {

        Restaurant restaurant = new Restaurant();
        Path filePath = Paths.get("D:\\Input.txt");
        try {
            List<String> lines = Files.readAllLines(filePath);
            TreeMap<Integer, Table> tables = new TreeMap<>();
            for (String str : lines) {
                String[] line = str.split(" ");
                if (line[1].matches("[0-9]+") &&
                        line[2].matches("[0-9]+") &&
                        (line[3].equals("smoker") || line[3].equals("non-smoker"))) {
                    int tableID = Integer.parseInt(line[1]);
                    int tableCapacity = Integer.parseInt(line[2]);
                    if (line[0].equals("Table")) {
                        Table table = new Table(tableID, tableCapacity, line[3]);
                        table.setType("regular");
                        tables.put(table.getId(), table);
                    } else if (line[0].equals("VIP_Table")) {
                        Table table = new VIP_Table(tableID, tableCapacity, line[3], line[4]);
                        table.setType("VIP");
                        tables.put(table.getId(), table);
                    }

                } else {
                    System.out.println("Incorrect table input!");
                    break;
                }
            }
            restaurant.addTables(tables);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner scan = new Scanner(System.in);
        String command = scan.nextLine();
        while (!command.equals("end")) {
            String[] tokens = command.split(" ");
            if (tokens[0].equals("reserve")
                    && (tokens.length == 6 || tokens.length == 7)
                    && Start.verifyTime(tokens[5])) {
                String type = tokens[1];
                String name = tokens[2];
                int numOfPeople = Integer.parseInt(tokens[3]);
                String smoke = tokens[4];
                String time = tokens[5];
                if (tokens.length == 6)
                    restaurant.reserve(type, name, numOfPeople, smoke, time);
                else if (tokens.length == 7) {
                    String view = tokens[6];
                    restaurant.reserveVIP(type, name, numOfPeople, smoke, time, view);
                }

            } else if (tokens.length == 4 && tokens[0].equals("arrive") && Start.verifyTime(tokens[3])) {
                int numOfPeople = Integer.parseInt(tokens[2]);
                restaurant.guestArrives(tokens[1], numOfPeople, tokens[3]);
            } else if (tokens.length == 2 && tokens[0].equals("leave")) {
                restaurant.leave(tokens[1]);
            } else {
                System.out.println("Incorrect input!");
            }

            command = scan.nextLine();

        }
        restaurant.leaveAll();
        restaurant.printTables();

    }

    static boolean verifyTime(String time) {
        String[] time1 = time.split(":");
        int hour = Integer.parseInt(time1[0]);
        int minutes = Integer.parseInt(time1[1]);
        if (hour < 0 || hour > 23)
            return false;
        if (minutes < 0 || minutes > 59)
            return false;
        return true;
    }

}
