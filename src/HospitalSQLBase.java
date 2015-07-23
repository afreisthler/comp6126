import java.sql.*;

public class HospitalSQLBase {

    protected static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "magnolia", "magnolia");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
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
