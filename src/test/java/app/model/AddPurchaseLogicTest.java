package app.model;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import app.dao.UserDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AddPurchaseLogicTest {

	@Autowired
	UserDAO target;

	@MockBean
	User user;

	DB_Data d_data = new DB_Data();

	@Test
	public void AppPurchaseLogictest() throws Exception {
		deleteData();
		String id = "t_user";
		String date_test = "2020-01-11";
		Date date = Date.valueOf(date_test);
		int suu = 10;

		Purchase purchase = new Purchase(id, date, suu);
		AddPurchaseLogic bo = new AddPurchaseLogic();
		boolean PurchaseLogicTest = bo.execute(purchase);

		assertTrue(PurchaseLogicTest);
		assertTrue(checkData());
		if (PurchaseLogicTest = true) {
			deleteData();
		}
	}


	//db接続用メソッド
	public void test_dbproperty() {
		String db_property[] = new String[3];
		try {
			InputStream is = new ClassPathResource(System.getenv("DBDATA_FILESELECT")).getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			int i = 0;

			// 1行ずつファイルを読み込む
			while (i < 2) {
				db_property[i] = br.readLine();
				i++;
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			d_data.setUrl(db_property[0]);
			d_data.setUsername(db_property[1]);
			d_data.setPass(db_property[2]);
		}
	}

	//dbテスト用仮データの削除
	public boolean deleteData() {
		// データベース接続
		test_dbproperty();
		final String JDBC_URL = d_data.getUrl();
		final String DB_USER = d_data.getUsername();
		final String DB_PASS = d_data.getPass();
		try {
			Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);

			// DELETE文の準備
			String sql = "DELETE FROM PURCHASE WHERE ID='t_user'";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// DELETE文を実行
			int result = pStmt.executeUpdate();

			if (result != 1) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	//dbテスト用仮データの確認
	public boolean checkData() {
		// データベース接続
		test_dbproperty();
		final String JDBC_URL = d_data.getUrl();
		final String DB_USER = d_data.getUsername();
		final String DB_PASS = d_data.getPass();
		try {
			Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);

			// SELECT文の準備
			String sql = "SELECT * FROM PURCHASE WHERE id = 't_user'";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// DELETE文を実行
			pStmt.execute();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}

