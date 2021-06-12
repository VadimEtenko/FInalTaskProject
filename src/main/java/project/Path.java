package project;

/**
 * @author V. Etenko
 */
public final class Path {
    // pages
    public static final String PAGE__LOGIN = "/login.jsp";
    public static final String PAGE__REGISTRATION = "/registration.jsp";
    public static final String PAGE__ERROR_PAGE = "/WEB-INF/jsp/error_page.jsp";
    public static final String PAGE__FREE_ROOM_LIST = "/WEB-INF/jsp/client/free_room_list.jsp";
    public static final String PAGE__LIST_REQUESTED = "/WEB-INF/jsp/manager/requested_list.jsp";
    public static final String PAGE__BOOKED_ROOM_LIST = "/WEB-INF/jsp/manager/booked_room_list.jsp";

    // commands
    public static final String COMMAND__LOGIN = "/controller?command=login";
    public static final String COMMAND__LIST_REQUESTED = "/controller?command=listRequest";
    public static final String COMMAND__LIST_FREE_ROOMS = "/controller?command=listFreeRooms";



}