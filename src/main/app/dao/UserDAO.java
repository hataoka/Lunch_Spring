package app.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.model.DB_Data;
import app.model.Employee;
import app.model.Idcheck;
import app.model.Login;
import app.model.User;
import app.security.MLoginUser;

@Component
public class UserDAO {

	@Autowired
	Employee emp;

	DB_Data d_data = new DB_Data();

	//処理の確認
//	public void dbproperty() {
//		String db_property[] = new String[3];
//		try {
//			InputStream is = new ClassPathResource(System.getenv("DBDATA_FILESELECT")).getInputStream();
//			BufferedReader br = new BufferedReader(new InputStreamReader(is));
//			int i = 0;
//
//			// 1行ずつファイルを読み込む
//			while (i < 2) {
//				db_property[i] = br.readLine();
//				i++;
//			}
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			d_data.setUrl(db_property[0]);
//			d_data.setUsername(db_property[1]);
//			d_data.setPass(db_property[2]);
//		}
//	}



	public MLoginUser findByIdcheck(Idcheck Idcheck) {
		MLoginUser user = null;

		//データベースへ接続
		final String JDBC_URL = emp.getUrl();
		final String DB_USER = emp.getUsername();
		final String DB_PASS = emp.getPassword();

//		dbproperty();
//		final String JDBC_URL = Employee.db_url;
//		final String DB_USER = Employee.db_username;
//		final String DB_PASS = Employee.db_password;
		try {
			Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);

			//SELECT文を準備
			String sql = "SELECT id, name, pass, kubun FROM USER WHERE id = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, Idcheck.getId());

			//SELECT文を実行し、結果表を取得
			ResultSet rs = pStmt.executeQuery();

			//一致したユーザーが存在した場合、そのユーザーを表すAccountインスタンスを生成
			if (rs.next()) {
				//結果票からデータを取得
				String id = rs.getString("ID");
				String pass = rs.getString("PASS");
				int kubun = rs.getInt("kubun");

				user = new MLoginUser(id, pass, kubun);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		//見つかったユーザーまたはnullを返す
		return user;
	}

	public User findByLogin(Login login) {

		User user = null;

		//データベースへ接続
		final String JDBC_URL = emp.getUrl();
		final String DB_USER = emp.getUsername();
		final String DB_PASS = emp.getPassword();
		try {
			Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);

			//SELECT文を準備
			String sql = "SELECT id, name, kubun FROM USER WHERE id = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, login.getId());

			//SELECT文を実行し、結果表を取得
			ResultSet rs = pStmt.executeQuery();

			//一致したユーザーが存在した場合、そのユーザーを表すAccountインスタンスを生成
			if (rs.next()) {
				//結果票からデータを取得
				String id = rs.getString("ID");
				String name = rs.getString("NAME");
				int kubun = rs.getInt("KUBUN");
				user = new User(id, name, kubun);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		//見つかったユーザーまたはnullを返す
		return user;
	}

	public String getName(String id) {
		String name = null;
		// データベース接続
		final String JDBC_URL = emp.getUrl();
		final String DB_USER = emp.getUsername();
		final String DB_PASS = emp.getPassword();
		try {
			Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);

			// SELECT文の準備
			String sql = "SELECT name FROM USER WHERE id = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setString(1, id);

			// SELECTを実行
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				name = rs.getString("name");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return name;
	}
}
