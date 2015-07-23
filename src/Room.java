

public class Room extends HospitalSQLBase {

    // A.1
    public static String getRoomsOccupied() {
        String query = "select room.number " +
                       "from room, admission " +
                       "where admission.to_room = room.room_id " +
                       "and admission.discharge_date is null";
        return queryToResults(query);
    }

    // A.2
    public static String getRoomsUnoccupied() {
        String query =  "select room.number " +
                        "from room " +
                        "where room.number not in ( " +
                        "select room.number " +
                        "from room, admission " +
                        "where admission.to_room = room.room_id " +
                        "and admission.discharge_date is null)";
        return queryToResults(query);
    }

    // A.3
    public static String getRooms() {
        String query = "select number, COALESCE(first_name, '') as first_name, " +
                       "       COALESCE(last_name, '') as last_name, " +
                       "       COALESCE(date(admission_date), '') as admission_date " +
                       "from room " +
                       "left join (select * from admission where admission.discharge_date is null) as occ_admission " +
                       "    on room.room_id = occ_admission.to_room " +
                       "left join patient " +
                       "    on occ_admission.to_patient = patient.patient_id";
        return queryToResults(query);
    }

    // -=-=-=-=-=- Queries below are in support of the application and are not used by the direct project queries required

    public static String getRoomsWithId() {
        String query = "select room_id, number, COALESCE(first_name, '') as first_name, " +
                "       COALESCE(last_name, '') as last_name, " +
                "       COALESCE(date(admission_date), '') as admission_date " +
                "from room " +
                "left join (select * from admission where admission.discharge_date is null) as occ_admission " +
                "    on room.room_id = occ_admission.to_room " +
                "left join patient " +
                "    on occ_admission.to_patient = patient.patient_id " +
                "order by room_id";
        return queryToResults(query);
    }

    public static String getRoomsUnoccupiedWithId() {
        String query =  "select room.room_id, room.number " +
                "from room " +
                "where room.number not in ( " +
                "select room.number " +
                "from room, admission " +
                "where admission.to_room = room.room_id " +
                "and admission.discharge_date is null)";
        return queryToResults(query);
    }

    public static String addRoom() {
        System.out.println("\nCurrent Rooms:\n" + getRoomsWithId());
        String n = HospitalAdmin.getString("New Room Number: ");
        String query = "insert into room(number) values('" + n + "')";
        return executeUpdate(query);
    }

    public static String updateRoom() {
        System.out.println("\nCurrent Rooms:\n" + getRoomsWithId());
        String id = HospitalAdmin.getString("room_id of room to edit: ");
        String n = HospitalAdmin.getString("New Room Number: ");
        String query = "update room set number='" + n +"' where room_id=" + id;
        return executeUpdate(query);
    }

    public static String deleteRoom() {
        System.out.println("\nNote: This system will only allow you to delete entities that have not been used by other entities");
        System.out.println("\nCurrent Rooms:\n" + getRoomsWithId());
        String id = HospitalAdmin.getString("room_id of room to delete: ");
        String query = "delete room from room where room_id=" + id;
        return executeUpdate(query);
    }
}
