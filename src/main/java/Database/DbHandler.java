package Database;

import java.sql.*;

public class DbHandler {
    private Connection connection;
    private Statement statement;
    private String query;
    private ResultSet result;
    private final String dbURL = "jdbc:mysql://192.168.188.50:3306/screen_time";
    private final String username = "admin";
    private final String password = "ajsdh679*+#+asd2";

    public boolean connectDb() throws SQLException {
        connection = DriverManager.getConnection(dbURL, username, password);

        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

        return true;
    }

    public boolean selectUser(String username, String password) throws SQLException {
        query = "SELECT * FROM screen_time_user WHERE user_name = '" + username + "' AND password='" + password + "';";
        result = statement.executeQuery(query);

        if (getSize(result) <= 0) {
            return false;
        }

        User user = new User(result.getInt(1), result.getString(2), result.getString(3));
        GUI.GUI.user = user;
        System.out.println(user);

        return true;
    }

    public boolean createUser(String username, String password) throws SQLException {
        query = "SELECT * FROM screen_time_user WHERE user_name = '" + username + "';";

        result = statement.executeQuery(query);

        if (getSize(result) > 0) {
            return false;
        }

        query = "INSERT INTO screen_time_user(user_name, password) VALUES ('" + username + "', '" + password + "');";

        statement.execute(query);
        return true;
    }

    public double usedTime(User user) throws SQLException {
        double time = 0;

        query = "SELECT * FROM screen_time_time WHERE user_id = '" + user.getId() + "' AND device = '" + user.device + "';";
        result = statement.executeQuery(query);

        if (getSize(result) > 0) {
            time = result.getDouble(3);
        }

        return time;
    }

    private int getSize(ResultSet resultSet) throws SQLException {
        int size = 0;
        if (resultSet != null)
        {
            while (resultSet.next()) {
                size++;
            }

            resultSet.first();
        }

        return size;
    }

    public void setTime(double t, User user) throws SQLException {
        if (!(user.device == null)) {
            query = "SELECT * FROM screen_time_time WHERE user_id = '" + user.getId() + "' AND device = '" + user.device + "';";
            result = statement.executeQuery(query);

            if (getSize(result) > 0) {
                query = "UPDATE screen_time_time SET time='" + t + "' WHERE user_id='" + user.getId() + "' AND device='" + user.device + "'";
            } else {
                query = "INSERT INTO screen_time_time(user_id, device, time) VALUES ('" + user.getId() + "', '" + user.device + "', '" + t + "');";
            }

            statement.execute(query);
        }
    }
}
