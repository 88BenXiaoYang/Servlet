package ad;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class DBUtil {
	//table
	public static final String TABLE_AD = "ad_list";
			
	//connect to MySql database
	public static Connection getConnect() {
		String url = "jdbc:mysql://localhost:3306/"; //服务端MySQL中的表
		Connection connecter = null;
		try {
			Class.forName("com.mysql.jdbc.Driver"); //Java反射，固定写法
			connecter = (Connection) DriverManager.getConnection(url, "root", ""); //服务端MySQL密码
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		}
		return connecter;
	}
}
