import java.util.ArrayList;

public class PopulateTables {

    public static int room_counter = 0;
    public static void main (String[]args){
        ArrayList<Room> rooms = generateRooms(10, 50, 40);
    }

    public static ArrayList<Room> generateRooms(int num_suites, int num_reg, int num_basic){
        ArrayList<Room> rooms = new ArrayList<>();

        for (int i=0; i<num_basic; i++){
            int price = 70;
            Room rm = new Room(room_counter, room_counter, "basic", price);
            rooms.add(rm);
            room_counter++;
        }

        for (int i=0; i<num_reg; i++){
            int price = 120;
            Room rm = new Room(room_counter, room_counter, "regular", price);
            rooms.add(rm);
            room_counter++;
        }

        return rooms;
    }
}
