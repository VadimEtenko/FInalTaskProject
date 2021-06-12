package project.db;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DB manager. Works with MySQL .
 * Only the required DAO methods are defined!
 *
 *
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
     * @return A DB connection.
     */
    public Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hotel?useSSL=false","root","password");
        conn.setAutoCommit(false);
        return conn;
    }



    /**
     * Commits and close the given connection.
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
        }
    }

}
