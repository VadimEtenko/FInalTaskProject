package project.db;

import project.db.entity.Room;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RoomDao {
    private static final String SQL__FIND_ALL_BOOKED_ROOMS =
            "SELECT hotel_rooms.id, hotel_rooms.number, class_of_room.class,hotel_rooms.number_of_beds, hotel_rooms.cost\n" +
                    "FROM hotel_rooms, class_of_room " +
                    "WHERE hotel_rooms.id IN\n" +
                    "(SELECT room_id FROM booked_rooms)\n" +
                    "AND hotel_rooms.class_id = class_of_room.id;";

    public static final String SQL__FIND_ROOM_BY_ID =
            "SELECT hotel_rooms.id, hotel_rooms.number, class_of_room.class, hotel_rooms.number_of_beds, hotel_rooms.cost\n" +
                    "FROM hotel_rooms, class_of_room\n" +
                    "WHERE hotel_rooms.class_id = class_of_room.id\n" +
                    "AND hotel_rooms.id = ?";

    private static final String SQL__FIND_ALL_FREE_ROOMS =
            "SELECT hotel_rooms.id, hotel_rooms.number, class_of_room.class, hotel_rooms.number_of_beds, hotel_rooms.cost\n" +
                    "FROM hotel_rooms, class_of_room\n" +
                    "WHERE hotel_rooms.id NOT IN\n" +
                        "(SELECT room_id\n" +
                        "FROM booked_rooms\n" +
                        "WHERE status_id != 0 AND " +
                        "(time_out >= ? AND time_in <= ?) OR" +
                        "(time_in >= ? AND time_out <= ?) OR" +
                        "(time_in <= ? AND time_out >= ?) OR" +
                        "(time_in <= ? AND time_out >= ?)) AND " +
                    "hotel_rooms.class_id = class_of_room.id\n";

    private static final String SQL__FIND_ALL_FREE_ROOMS_BY_CRITERIA =
            "SELECT hotel_rooms.id, hotel_rooms.number, class_of_room.class, hotel_rooms.number_of_beds, hotel_rooms.cost\n" +
                    "FROM hotel_rooms, class_of_room\n" +
                    "WHERE (hotel_rooms.id NOT IN\n" +
                    "       (SELECT room_id FROM booked_rooms WHERE status_id != 0)\n" +
                    "    AND hotel_rooms.class_id = class_of_room.id)\n" +
                    "AND hotel_rooms.class_id >= ?\n" +
                    "AND hotel_rooms.number_of_beds >= ?";

    private static final String SQL__FIND_ALL_OFFERED_ROOMS_BY_USER_ID =
            "SELECT hotel_rooms.id, hotel_rooms.number, class_of_room.class, hotel_rooms.number_of_beds, hotel_rooms.cost\n" +
                    "FROM hotel_rooms, offers, class_of_room\n" +
                    "WHERE (hotel_rooms.id IN\n" +
                    "        (SELECT offers.room_id FROM offers)\n" +
                    "    AND hotel_rooms.id = offers.room_id)\n" +
                    "    AND hotel_rooms.class_id = class_of_room.id\n" +
                    "    AND offers.user_id = ?;";


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



    /**
     *
     * @param roomId
     *      room id in database
     * @return
     *      room entity
     */

    public Room findRoomById(long roomId) {
        Room room = null;
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
        return room;
    }

    /**
     * Returns all free rooms.
     *
     * @return List of free room entities.
     */
    public List<Room> findFreeRooms(LocalDate time_in, LocalDate time_out) {
        List<Room> RoomsList = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            con = DBManager.getInstance().getConnection();
            RoomsMapper mapper = new RoomsMapper();

            Date start = Date.valueOf(time_in.toString());
            Date end = Date.valueOf(time_out.toString());
            stmt = con.prepareStatement(SQL__FIND_ALL_FREE_ROOMS);
            int indexValue = 1;
            stmt.setString(indexValue++, sdf.format(start));
            stmt.setString(indexValue++, sdf.format(end));
            stmt.setString(indexValue++, sdf.format(start));
            stmt.setString(indexValue++, sdf.format(end));
            stmt.setString(indexValue++, sdf.format(end));
            stmt.setString(indexValue++, sdf.format(end));
            stmt.setString(indexValue++, sdf.format(start));
            stmt.setString(indexValue, sdf.format(start));
            rs = stmt.executeQuery();
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


    /**
     * @param classId
     *      class of rooms entity id in database
     * @param numberOfBeds
     *      count of beds
     * @return
     *      list of free rooms entities is suitable for enough
     */

    public List<Room> findAllFreeRoomsByCriteria(Long classId, int numberOfBeds) {
        List<Room> requestWish = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            RoomsMapper mapper = new RoomsMapper();
            stmt = con.prepareStatement(SQL__FIND_ALL_FREE_ROOMS_BY_CRITERIA);
            int indexValue = 1;
            stmt.setLong(indexValue++, classId);
            stmt.setInt(indexValue, numberOfBeds);
            rs = stmt.executeQuery();
            while (rs.next())
                requestWish.add(mapper.mapRow(rs));
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollback(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return requestWish;
    }


    /**
     * Find a list of rooms, offered to the user according to his wishes
     *
     * @param userId
     *      user id in database
     * @return
     *      list of rooms entities
     */

    public List<Room> getOfferedRoomsByUserId(long userId) {
        List<Room> RoomsList = new ArrayList<>();
        PreparedStatement prStmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            RoomsMapper mapper = new RoomsMapper();
            prStmt = con.prepareStatement(SQL__FIND_ALL_OFFERED_ROOMS_BY_USER_ID);
            prStmt.setLong(1, userId);
            rs = prStmt.executeQuery();
            while (rs.next())
                RoomsList.add(mapper.mapRow(rs));
            prStmt.close();
            rs.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollback(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
        return RoomsList;
    }

    /**
     * Extracts a category from the result set row.
     */
    private static class RoomsMapper implements EntityMapper<Room> {

        @Override
        public Room mapRow(ResultSet rs) {
            try {
                return new Room.Builder()
                        .withId(rs.getLong(Fields.ENTITY__ID))
                        .withNumber(rs.getInt(Fields.ROOM__NUMBER))
                        .withRoomClass(rs.getString(Fields.ROOM__CLASS))
                        .withNumberOfBeds(rs.getInt(Fields.ROOM__NUMBER_OF_BEDS))
                        .withCost(rs.getDouble(Fields.ROOM__COST))
                        .build();
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }

}
