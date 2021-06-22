package project.web;

/**
 * @author V. Etenko
 */
public final class Path {
    // pages
    public static final String PAGE__LOGIN = "/login.jsp";
    public static final String PAGE__REGISTRATION = "/registration.jsp";
    public static final String PAGE__ERROR_PAGE = "/WEB-INF/jsp/error_page.jsp";

    public static final String PAGE__FIND_FREE_ROOM_LIST = "/WEB-INF/jsp/client/find_free_room_list.jsp";
    public static final String PAGE__FREE_ROOM_LIST = "/WEB-INF/jsp/client/free_room_list.jsp";
    public static final String PAGE__USER_REQUESTED_LIST = "/WEB-INF/jsp/client/user_requested_list.jsp";
    public static final String PAGE__USER_NOTIFICATIONS_LIST = "/WEB-INF/jsp/client/notification_list.jsp";
    public static final String PAGE__PLAN_REQUEST = "/WEB-INF/jsp/client/plane_request.jsp";
    public static final String PAGE__VERIFY_OFFERS = "/WEB-INF/jsp/client/offers_list.jsp";


    public static final String PAGE__LIST_REQUESTED = "/WEB-INF/jsp/manager/requested_list.jsp";
    public static final String PAGE__BOOKED_ROOM_LIST = "/WEB-INF/jsp/manager/booked_room_list.jsp";
    public static final String PAGE__UPDATE_BOOKED_ROOM = "/WEB-INF/jsp/manager/edit_booked_room.jsp";
    public static final String PAGE__REQUEST_WISH_LIST = "/WEB-INF/jsp/manager/request_wish_list.jsp";
    public static final String PAGE__PLAN_OFFER_BY_WISH = "/WEB-INF/jsp/manager/plan_offer_list.jsp";


    // commands
    public static final String COMMAND__LOGIN = "/controller?command=login";
    public static final String COMMAND__LIST_REQUESTED = "/controller?command=list-request";
    public static final String COMMAND__LIST_FREE_ROOMS = "/controller?command=list-free-rooms";
    public static final String COMMAND__LIST_BOOKED_ROOMS = "/controller?command=list-booked";
    public static final String COMMAND__USER_NOTIFICATIONS_LIST = "/controller?command=notifications";
    public static final String COMMAND__LIST_REQUEST_WISH = "/controller?command=list-requests-wish";

}