package ex4_gui;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Sql {

	public static void getsql () {

		String jdbcUrl="jdbc:mysql://ariel-oop.xyz:3306/oop"; //?useUnicode=yes&characterEncoding=UTF-8&useSSL=false";
		String jdbcUser="student";
		String jdbcPassword="student";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = 
					DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);


			Statement statement = connection.createStatement();

			//select data
			String allCustomersQuery = "SELECT * FROM logs where FirstID = 205478431 and SecondID = 311483432;";
			ResultSet resultSet = statement.executeQuery(allCustomersQuery);
			resultSet.last();
			double score = resultSet.getDouble("Point");
			double gameid = resultSet.getDouble("SomeDouble");
			allCustomersQuery = "SELECT * FROM logs where FirstID = 205478431 and SecondID = 311483432 and SomeDouble =" + gameid + "ORDER BY point DESC;";
			resultSet = statement.executeQuery(allCustomersQuery);
			int i=1;
			while (resultSet.next()) {
				if (resultSet.getDouble("Point") > score)  {
					i++;
				}
				else {
					break;
				}
			}
			System.out.println("in this game your in " + i + " place, comapre to other game you played in this map");
			allCustomersQuery = "SELECT * FROM logs where SomeDouble =" + gameid + "ORDER BY point DESC;";
			resultSet = statement.executeQuery(allCustomersQuery);
			i = 1;
			while (resultSet.next()) {
				if (resultSet.getDouble("Point") > score)  {
					i++;
				}
				else {
					break;
				}
			}
			System.out.println("in this game your in " + i + " place, comapre to other game there all your class played in this map");
			resultSet.close();		
			statement.close();		
			connection.close();		
		}
		catch (SQLException sqle) {
			System.out.println("SQLException: " + sqle.getMessage());
			System.out.println("Vendor Error: " + sqle.getErrorCode());
		}

		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}




}



