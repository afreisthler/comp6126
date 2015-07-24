import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class HospitalSQLBase {

    protected static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            String db_uri = "jdbc:mysql://localhost:3306/";
            String db_name = "project";
            String db_user = "magnolia";
            String db_password = "magnolia";
            try {
                Properties prop = new Properties();
                InputStream input = null;
                input = new FileInputStream("config.properties");
                prop.load(input);
                db_uri = prop.getProperty("db_uri", "jdbc:mysql://localhost:3306/");
                db_name = prop.getProperty("db_name", "project");
                db_user = prop.getProperty("db_user", "magnolia");
                db_password = prop.getProperty("db_password", "magnolia");
            }  catch (FileNotFoundException e) {
            }
            return DriverManager.getConnection(db_uri + db_name, db_user, db_password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected static String queryToResults(String query) {
        String string = "";
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            string = printTable(resultSet);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return string;
    }

    protected static String executeUpdate(String query) {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            return "Failure!\n" + e.toString() + "\n";
        }
        return "Success!\n";
    }


    // Taken from notes.  Thanks!!
    protected static String printTable(ResultSet result) throws SQLException {

        ResultSetMetaData md = result.getMetaData();
        int numcols = md.getColumnCount();

        // set the maximum column width.
        // note that md.getColumnDisplaySize() might return not-useful values,
        // so we'll set the width at 10.
        int width = 20;
        String colformat = "%-" + width + "s";

        // use a StringBuilder to create the display output
        StringBuilder sb = new StringBuilder();

        // get the column labels to use for a header
        for (int i = 1; i <= numcols; i++) {
            String label = md.getColumnLabel(i);
            sb.append(String.format(colformat, label));
        }
        sb.append("\n");

        // get each row in the result
        while (result.next()) {
            for (int i = 1; i <= numcols; i++) {
                String colval = result.getString(i);
                sb.append(String.format(colformat, colval));
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
