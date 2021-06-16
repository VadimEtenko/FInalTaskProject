package project.db;

/**
 * Holder for fields names of DB tables and beans.
 *
 * @author V. Etenko
 *
 */

public final class Fields {
    // entities
    public static final String ENTITY__ID = "id";

    public static final String USER__NAME = "name";
    public static final String USER__SURNAME = "surname";
    public static final String USER__LOGIN = "login";
    public static final String USER__PASSWORD = "password";
    public static final String USER__EMAIL = "email";
    public static final String USER__LOCAL = "local";
    public static final String USER__ROLE_ID = "role_id";


    public static final String ROOM__NUMBER = "number";
    public static final String ROOM__CLASS= "class";
    public static final String ROOM__NUMBER_OF_BEDS= "number_of_beds";
    public static final String ROOM__COST= "cost";

    public static final String  BOOKED_ROOM_STATUS = "status_id";
    public static final int  BOOKED_ROOM_STATUS__FREE = 0;
    public static final int  BOOKED_ROOM_STATUS__BOOKED = 1;
    public static final int  BOOKED_ROOM_STATUS__OCCUPIED = 2;
    public static final int  BOOKED_ROOM_STATUS__NOT_AVAILABLE = 3;

    public static final String NOTIFICATION__USER_ID = "user_id";
    public static final String NOTIFICATION__TEXT = "text";

    public static final String BOOKING_REQUEST__BOOKED_ID = "id";
    public static final String BOOKING_REQUEST__USER_ID_WHO_BOOKED = "user_id";
    public static final String BOOKING_REQUEST__RESERVED_ROOM_ID = "room_id";
    public static final String BOOKING_REQUEST__USER_LOGIN_WHO_BOOKED = "login";
    public static final String BOOKING_REQUEST__RESERVED_ROOM_NUMBER = "number";

    public static final String REQUEST_WISHED__USER_ID_WHO_WISHED = "user_id";

    public static final String REQUEST_WISHED__WISHED_CLASS_ID = "class_id";
    public static final String REQUEST_WISHED__WISHED_NUMBER_OF_BEDS = "number_of_beds";
    public static final String REQUEST_WISHED__WISHED_TIME_IN = "time_in";
    public static final String REQUEST_WISHED__WISHED_TIME_OUT = "time_out";


}
