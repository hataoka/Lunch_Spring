package app.dao;

import static org.assertj.core.api.Assertions.assertThat;
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
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import app.model.DB_Data;
import app.model.Purchase;
import app.model.PurchaseSum;
import app.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PurchaseDAOTest {
	@Autowired
	UserDAO target;

	@MockBean
	User user;

	DB_Data d_data = new DB_Data();

	@Test
	public void findByIdTest() throws Exception {
		//ユーザーID(userA)を渡した際に、正しくリストが取得できているか
		String sdate1 = "2020-01-01";
		Date date1 = Date.valueOf(sdate1);
		String sdate2 = "2020-01-02";
		Date date2 = Date.valueOf(sdate2);
		String sdate3 = "2020-01-03";
		Date date3 = Date.valueOf(sdate3);

		String id = "userA";
		PurchaseDAO dao = new PurchaseDAO();
		List<Purchase> purchaseList = dao.findById(id);

		assertThat(purchaseList).extracting("id")
				.containsExactly("userA", "userA", "userA");
		assertThat(purchaseList).extracting("purchase_date")
				.containsExactly(date1, date2, date3);
		assertThat(purchaseList).extracting("suu")
				.containsExactly(5, 5, 5);
	}

	@Test
	public void findByDateTest() throws Exception {
		//ユーザーID(userD)と基準となる日付(2020-01-01)を渡した際に、正しくリストが取得できているか
		String sdate1 = "2020-01-07";
		Date date1 = Date.valueOf(sdate1);
		String sdate2 = "2020-01-08";
		Date date2 = Date.valueOf(sdate2);
		String sdate3 = "2020-01-09";
		Date date3 = Date.valueOf(sdate3);

		String id = "userD";
		String date = "2020-01";
		PurchaseDAO dao = new PurchaseDAO();
		List<Purchase> purchaseList = dao.findByDate(id, date);

		assertThat(purchaseList).extracting("id")
				.containsExactly("userD", "userD", "userD");
		assertThat(purchaseList).extracting("purchase_date")
				.containsExactly(date1, date2, date3);
		assertThat(purchaseList).extracting("suu")
				.containsExactly(5, 5, 5);
	}

	@Test
	public void addTest() throws Exception {
		//ユーザーID(t_user)でデータが登録されているかのテスト
		deleteData();
		String id = "t_user";
		String date = "2020-01-11";
		Date date_test = Date.valueOf(date);
		int suu = 10;

		Purchase purchase = new Purchase(id, date_test, suu);
		PurchaseDAO dao = new PurchaseDAO();
		boolean PurchaseAdd = dao.add(purchase);

		assertTrue(PurchaseAdd);
		assertTrue(checkData());
		if (PurchaseAdd = true) {
			deleteData();
		}
	}

	@Test
	public void getSumGroupByUserTest() throws Exception {
		//管理者ユーザーでログインした時にそれぞれのユーザーの購入数を出力できているか
		PurchaseDAO dao = new PurchaseDAO();
		List<PurchaseSum> purchaseSumList = dao.getSumGroupByUser();

		assertThat(purchaseSumList).extracting("id")
				.containsExactly("userA", "userB", "userC", "userD");
		assertThat(purchaseSumList).extracting("suu")
				.containsExactly(15, 10, 5, 20);
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
