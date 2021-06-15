package project.db;

import project.db.entity.Room;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDao {
    private static final String SQL__FIND_ALL_BOOKED_ROOMS =
            "SELECT hotel_rooms.id, hotel_rooms.number, class_of_room.class,hotel_rooms.number_of_beds, hotel_rooms.cost\n" +
                    "FROM hotel_rooms, class_of_room WHERE hotel_rooms.id IN\n" +
                    "(SELECT room_id FROM booked_rooms)\n" +
                    "AND\n" +
                    "hotel_rooms.class_id = class_of_room.id;";

    public static final String SQL__FIND_ROOM_BY_ID =
            "SELECT hotel_rooms.id, hotel_rooms.number, class_of_room.class, hotel_rooms.number_of_beds, hotel_rooms.cost\n" +
                    "FROM hotel_rooms, class_of_room\n" +
                    "WHERE hotel_rooms.class_id = class_of_room.id\n" +
                    "AND hotel_rooms.id = ?";

    private static final String SQL__FIND_ALL_FREE_ROOMS =
            "SELECT hotel_rooms.id, hotel_rooms.number, class_of_room.class,hotel_rooms.number_of_beds, hotel_rooms.cost\n" +
                    "FROM hotel_rooms, class_of_room WHERE hotel_rooms.id NOT IN\n" +
                    "(SELECT room_id FROM booked_rooms WHERE status_id != 0)\n" +
                    "AND\n" +
                    "hotel_rooms.class_id = class_of_room.id;";

    private static final String SQL__CREATE_NEW_BOOKING_RECORDS =
            "INSERT INTO booked_rooms(room_id, user_id, status_id)\n" +
                    "VALUE (?,?,?);";



    /**
     * Returns all booked rooms.
     *
     * @return List of booked rooms entities.
     */
    public List<Room> findAllBookedRooms() {
        List<Room> allBookedRoomsList = new ArrayList<Room>();
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            RoomsMapper mapper = new RoomsMapper();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL__FIND_ALL_BOOKED_ROOMS);
            while (rs.next())
                allBookedRoomsList.add(mapper.mapRow(rs));
            stmt.close();
            rs.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollback(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return allBookedRoomsList;
    }

    public int findBookedRoomNumberById(long roomId) {
        Room room = new Room();
        PreparedStatement prStmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            RoomsMapper mapper = new RoomsMapper();
            prStmt = con.prepareStatement(SQL__FIND_ROOM_BY_ID);
            prStmt.setLong(1, roomId);
            rs = prStmt.executeQuery();
            while (rs.next())
                room = mapper.mapRow(rs);
            prStmt.close();
            rs.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollback(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return room.getNumber();
    }

    /**
     * Returns all free rooms.
     *
     * @return List of free room entities.
     */
    public List<Room> findFreeRooms() {
        List<Room> RoomsList = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            RoomsMapper mapper = new RoomsMapper();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL__FIND_ALL_FREE_ROOMS);
            while (rs.next())
                RoomsList.add(mapper.mapRow(rs));
            stmt.close();
            rs.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollback(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return RoomsList;
    }

    public void createBookedRoom(long roomId, long userId){
        PreparedStatement preStmt = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            preStmt = con.prepareStatement(SQL__CREATE_NEW_BOOKING_RECORDS);
            int indexValue = 1;
            preStmt.setLong(indexValue++, roomId);
            preStmt.setLong(indexValue++, userId);
            preStmt.setInt(indexValue, 1);
            preStmt.executeUpdate();
            preStmt.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollback(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
    }

    public void updateBookedRoom(long bookedRoomId,String status){

    }

    /**
     * Extracts a category from the result set row.
     */
    private static class RoomsMapper implements EntityMapper<Room> {

        @Override
        public Room mapRow(ResultSet rs) {
            try {
                Room room = new Room();
                room.setId(rs.getLong(Fields.ENTITY__ID));
                room.setNumber(rs.getInt(Fields.ROOM__NUMBER));
                room.setRoomClass(rs.getString(Fields.ROOM__CLASS));
                room.setNumberOfBeds(rs.getInt(Fields.ROOM__NUMBER_OF_BEDS));
                room.setCost(rs.getDouble(Fields.ROOM__COST));
                return room;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }

}
