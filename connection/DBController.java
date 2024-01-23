package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBController {
	private Connection con;

	public DBController(Connection con) {
		this.con = con;
	}

	public Connection getConnection() {
		return con;
	}

	Statement stmt;
	public DBController() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try {
				this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sang", "root", "sang1234");
				this.stmt = con.createStatement();
				System.out.println("Successfully");
			} catch (Exception e) {
				// TODO: handle exception
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public ResultSet Print() {
		String sql = "SELECT * FROM sang.điểm";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return rs;
	}

	public ResultSet tkfb() {
		String sql = "SELECT * FROM sang.tkfb";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return rs;
	}

}
