package project.db;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DB manager. Works with MySQL .
 * Only the required DAO methods are defined!
 */

public class DBManager {
    private static final Logger log = Logger.getLogger(DBManager.class);

    /**
     * Singleton
    */

    private static DBManager instance;

    private DBManager() {
    }

    public static synchronized DBManager getInstance() {
        if (instance == null)
            instance = new DBManager();
        return instance;
    }


    /**
     * Returns a DB connection getting by DriverManager.
     *
     * @return A DB connection.
     */
    public Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/hotel?useSSL=false&characterEncoding=UTF-8";
        String user = "root";
        String password = "password";
        Connection connection;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        }catch(ClassNotFoundException ex){
            log.error("Can't connect to database error: " + ex);
        }
        connection = DriverManager.getConnection(url, user, password);
        connection.setAutoCommit(false);

        return connection;
    }


    /**
     * Commits and close the given connection.
     *
     * @param con
     *            Connection to be committed and closed.
     */
    public void commitAndClose(Connection con) {
        try {
            if (con != null) {
                con.commit();
                con.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            log.error("Can't commit and close connection error: " + ex);
        }
    }

    /**
     * Rollbacks and close the given connection.
     *
     * @param con
     *            Connection to be rollbacked and closed.
     */
    public void rollback(Connection con) {
        try {
            if (con != null) {
                con.rollback();
                con.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            log.error("Can't rollback transaction error: " + ex);
        }
    }

}
