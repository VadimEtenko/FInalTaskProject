package project.db;

import project.db.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    private static final String SQL__FIND_USER_BY_ID =
            "SELECT * FROM users WHERE id=?";

    private static final String SQL__FIND_USER_BY_LOGIN =
            "SELECT * FROM users WHERE login=?";

    private static final String SQL__FIND_USER_BY_EMAIL =
            "SELECT * FROM users WHERE email=?";

    private static final String SQL__DELETE_USER_BY_LOGIN =
            "DELETE FROM users WHERE login = ?";

    private static final String SQL__FIND_USER_BY_REQUESTED_ROOM_ID =
            "SELECT users.*\n" +
                    "FROM users,\n" +
                    "     requested_rooms\n" +
                    "WHERE users.id = requested_rooms.user_id\n" +
                    "  AND requested_rooms.room_id = ?";

    private static final String SQL__FIND_USER_BY_REQUESTED_ID =
            "SELECT users.*\n" +
                    "FROM users,\n" +
                    "     requested_rooms\n" +
                    "WHERE users.id = requested_rooms.user_id\n" +
                    "  AND requested_rooms.id = ?";

    private static final String SQL__CREATE_NEW_USER =
            "INSERT INTO users(name, surname, login, password, email, role_id, local)" +
                    "VALUE (?,?,?,?,?,0,?)";


    /**
     * Returns a user with the given identifier.
     *
     * @param id User identifier.
     * @return User entity.
     */
    public User findUser(Long id) {
        User user = null;
        PreparedStatement prStmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            UserMapper mapper = new UserMapper();
            prStmt = con.prepareStatement(SQL__FIND_USER_BY_ID);
            prStmt.setLong(1, id);
            rs = prStmt.executeQuery();
            if (rs.next())
                user = mapper.mapRow(rs);
            rs.close();
            prStmt.close();
        } catch (SQLException ex) {
            assert con != null;
            DBManager.getInstance().rollback(con);
            ex.printStackTrace();
        } finally {
            assert con != null;
            DBManager.getInstance().commitAndClose(con);
        }
        return user;
    }

    /**
     * Returns a user with the given login.
     *
     * @param login User login.
     * @return User entity.
     */
    public User findUserByLogin(String login) {
        User user = null;
        PreparedStatement prStmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            UserMapper mapper = new UserMapper();
            prStmt = con.prepareStatement(SQL__FIND_USER_BY_LOGIN);
            prStmt.setString(1, login);
            rs = prStmt.executeQuery();
            if (rs.next())
                user = mapper.mapRow(rs);
            rs.close();
            prStmt.close();
        } catch (SQLException ex) {
            assert con != null;
            DBManager.getInstance().rollback(con);
            ex.printStackTrace();
        } finally {
            assert con != null;
            DBManager.getInstance().commitAndClose(con);
        }
        return user;
    }


    /**
     * @param email String email of user
     * @return user entity registered on this email
     */

    public User findUserByEmail(String email) {
        User user = null;
        PreparedStatement prStmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            UserMapper mapper = new UserMapper();
            prStmt = con.prepareStatement(SQL__FIND_USER_BY_EMAIL);
            prStmt.setString(1, email);
            rs = prStmt.executeQuery();
            if (rs.next())
                user = mapper.mapRow(rs);
            rs.close();
            prStmt.close();
        } catch (SQLException ex) {
            assert con != null;
            DBManager.getInstance().rollback(con);
            ex.printStackTrace();
        } finally {
            assert con != null;
            DBManager.getInstance().commitAndClose(con);
        }
        return user;
    }


    /**
     * @param requestedId request record id in database
     * @return user entity
     */

    public User findUserByRequestedId(long requestedId) {
        User user = null;
        PreparedStatement prStmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            UserMapper mapper = new UserMapper();
            prStmt = con.prepareStatement(SQL__FIND_USER_BY_REQUESTED_ID);
            prStmt.setLong(1, requestedId);
            rs = prStmt.executeQuery();
            if (rs.next())
                user = mapper.mapRow(rs);
            rs.close();
            prStmt.close();
        } catch (SQLException ex) {
            assert con != null;
            DBManager.getInstance().rollback(con);
            ex.printStackTrace();
        } finally {
            assert con != null;
            DBManager.getInstance().commitAndClose(con);
        }
        return user;
    }


    /**
     * @param requestedRoomId requested room record in database
     * @return list of user entities
     */

    public List<User> findUsersByRequestedRoomId(long requestedRoomId) {
        List<User> usersList = new ArrayList<>();
        PreparedStatement prStmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            UserMapper mapper = new UserMapper();
            prStmt = con.prepareStatement(SQL__FIND_USER_BY_REQUESTED_ROOM_ID);
            prStmt.setLong(1, requestedRoomId);
            rs = prStmt.executeQuery();
            while (rs.next())
                usersList.add(mapper.mapRow(rs));
            rs.close();
            prStmt.close();
        } catch (SQLException ex) {
            assert con != null;
            DBManager.getInstance().rollback(con);
            ex.printStackTrace();
        } finally {
            assert con != null;
            DBManager.getInstance().commitAndClose(con);
        }
        return usersList;
    }

    /**
     * insert new user record in database
     *
     * @param user user entities
     */

    public void createNewUser(User user) {
        PreparedStatement prStmt;
        ResultSet rs;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            prStmt = con.prepareStatement(SQL__CREATE_NEW_USER);
            int indexParam = 1;
            prStmt.setString(indexParam++, user.getName());
            prStmt.setString(indexParam++, user.getSurname());
            prStmt.setString(indexParam++, user.getLogin());
            prStmt.setString(indexParam++, user.getPassword());
            prStmt.setString(indexParam++, user.getEmail());
            prStmt.setString(indexParam, user.getLocale());
            prStmt.executeUpdate();
            prStmt.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollback(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
    }

    public void deleteUserByLogin(String login) {
        PreparedStatement prStmt;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            prStmt = con.prepareStatement(SQL__DELETE_USER_BY_LOGIN);
            prStmt.setString(1, login);
            prStmt.executeUpdate();
            prStmt.close();
        } catch (SQLException ex) {
            DBManager.getInstance().rollback(con);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().commitAndClose(con);
        }
    }


    /**
     * Extracts a user from the result set row.
     */
    private static class UserMapper implements EntityMapper<User> {

        @Override
        public User mapRow(ResultSet rs) {
            try {
                return new User.Builder()
                        .withId(rs.getLong(Fields.ENTITY__ID))
                        .withName(rs.getString(Fields.USER__NAME))
                        .withSurname(rs.getString(Fields.USER__SURNAME))
                        .withLogin(rs.getString(Fields.USER__LOGIN))
                        .withPassword(rs.getString(Fields.USER__PASSWORD))
                        .withEmail(rs.getString(Fields.USER__EMAIL))
                        .withRoleId(rs.getInt(Fields.USER__ROLE_ID))
                        .withLocale(rs.getString(Fields.USER__LOCAL))
                        .build();
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}

