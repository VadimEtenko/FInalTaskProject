package project.db;

import org.apache.log4j.Logger;
import project.db.entity.User;
import project.web.command.LoginCommand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    private static final Logger log = Logger.getLogger(UserDao.class);

    private static final String SQL__FIND_USER_BY_ID =
            "SELECT * FROM users WHERE id=?";

    private static final String SQL__FIND_USER_BY_LOGIN =
            "SELECT * FROM users WHERE login=?";

    private static final String SQL__FIND_USER_BY_EMAIL =
            "SELECT * FROM users WHERE email=?";

    private static final String SQL_UPDATE_USER =
            "UPDATE users SET password=?, name=?, surname=?, login=?, email=?, local=?" +
                    "	WHERE id=?";

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
            prStmt.setString(indexParam, user.getLocal());
            prStmt.executeUpdate();
            prStmt.close();
        } catch (SQLException ex) {
            assert con != null;
            DBManager.getInstance().rollback(con);
            ex.printStackTrace();
        } finally {
            assert con != null;
            DBManager.getInstance().commitAndClose(con);
        }
    }


    /**
     * Update user.
     *
     * @param user user to update.
     * @throws SQLException
     */
    public void updateUser(Connection con, User user) throws SQLException {
        PreparedStatement prStmt = con.prepareStatement(SQL_UPDATE_USER);
        int indexParam = 1;
        prStmt.setString(indexParam++, user.getPassword());
        prStmt.setString(indexParam++, user.getName());
        prStmt.setString(indexParam++, user.getSurname());
        prStmt.setString(indexParam++, user.getLogin());
        prStmt.setString(indexParam++, user.getEmail());
        prStmt.setString(indexParam++, user.getLocal());
        prStmt.setLong(indexParam, user.getId());
        prStmt.executeUpdate();
        prStmt.close();
    }


    /**
     * Extracts a user from the result set row.
     */
    private static class UserMapper implements EntityMapper<User> {

        @Override
        public User mapRow(ResultSet rs) {
            try {
                User user = new User();
                user.setId(rs.getLong(Fields.ENTITY__ID));
                user.setName(rs.getString(Fields.USER__NAME));
                user.setSurname(rs.getString(Fields.USER__SURNAME));
                user.setLogin(rs.getString(Fields.USER__LOGIN));
                user.setPassword(rs.getString(Fields.USER__PASSWORD));
                user.setEmail(rs.getString(Fields.USER__EMAIL));
                user.setRoleId(rs.getInt(Fields.USER__ROLE_ID));
                user.setLocal(rs.getString(Fields.USER__LOCAL));
                return user;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}

