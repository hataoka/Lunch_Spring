package app.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.model.DB_Data;
import app.model.Employee;
import app.model.Purchase;
import app.model.PurchaseSum;

@Service
public class PurchaseDAO {

	DB_Data d_data = new DB_Data();

	@Autowired
	Employee emp;

	/**
	 * 一般ユーザーの全データの並び替えたものをリストに入れてreturn
	 * @param 今ログインしている一般ユーザーのID
	 * @return selectで並び替えたList
	 */
	public List<Purchase> findById(String id) {
		List<Purchase> purchaseList = new ArrayList<Purchase>();

		// データベース接続
		final String JDBC_URL = emp.getUrl();
		final String DB_USER = emp.getUsername();
		final String DB_PASS = emp.getPassword();
		try {
			Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);

			// SELECT文の準備
			String sql = "SELECT purchase_date,id,suu FROM PURCHASE WHERE id = ? ORDER BY purchase_date ASC";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setString(1, id);

			// SELECTを実行
			ResultSet rs = pStmt.executeQuery();

			// SELECT文の結果をArrayListに格納
			while (rs.next()) {
				id = rs.getString("id");
				Date purchase_date = rs.getDate("purchase_date");
				int suu = rs.getInt("suu");
				Purchase purchase = new Purchase(id, purchase_date, suu);
				purchaseList.add(purchase);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return purchaseList;
	}

	public List<Purchase> findByDate(String id, String date) {
		List<Purchase> purchaseList = new ArrayList<Purchase>();

		// データベース接続
		final String JDBC_URL = emp.getUrl();
		final String DB_USER = emp.getUsername();
		final String DB_PASS = emp.getPassword();
		try {
			Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);

			int year = Integer.parseInt(date.substring(0, 4));
			date = date.substring(5);
			int month = Integer.parseInt(date);

			// SELECT文の準備
			String sql = "SELECT purchase_date,id,suu "
					+ "FROM PURCHASE "
					+ "WHERE YEAR(purchase_date) = ? AND "
					+ "MONTH(purchase_date) = ? AND "
					+ "id = ? ORDER BY purchase_date ASC";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setInt(1, year);
			pStmt.setInt(2, month);
			pStmt.setString(3, id);

			// SELECTを実行
			ResultSet rs = pStmt.executeQuery();

			// SELECT文の結果をArrayListに格納
			while (rs.next()) {
				id = rs.getString("id");
				Date purchase_date = rs.getDate("purchase_date");
				int suu = rs.getInt("suu");
				Purchase purchase = new Purchase(id, purchase_date, suu);
				purchaseList.add(purchase);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return purchaseList;
	}

	/**
	 * @param purchase 指定した日付、注文数、注文したユーザー
	 * @return 正常にデータの挿入ができたかどうか
	 */
	public boolean add(Purchase purchase) {
		// データベース接続
		final String JDBC_URL = emp.getUrl();
		final String DB_USER = emp.getUsername();
		final String DB_PASS = emp.getPassword();
		try {
			Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);

			// INSERT文の準備
			String sql = "INSERT INTO PURCHASE(PURCHASE_DATE,ID,SUU ) VALUES(?, ?, ?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			// INSERT文中の「?」に使用する値を設定しSQLを完成
			pStmt.setDate(1, purchase.getPurchase_date());
			pStmt.setString(2, purchase.getId());
			pStmt.setInt(3, purchase.getSuu());
			// INSERT文を実行
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

	/**
	 * @return 各IDごとの合計データをListに入れたもの
	 */
	public List<PurchaseSum> getSumGroupByUser() {
		List<PurchaseSum> purchaseSumList = new ArrayList<PurchaseSum>();
		// データベース接続
		final String JDBC_URL = emp.getUrl();
		final String DB_USER = emp.getUsername();
		final String DB_PASS = emp.getPassword();
		try {
			Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);

			// SELECT文の準備
			String sql = "SELECT id,SUM(suu) as suu "
					+ " FROM PURCHASE "
					+ " GROUP BY id";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SELECTを実行
			ResultSet rs = pStmt.executeQuery();
			// SELECT文の結果をArrayListに格納
			while (rs.next()) {
				String id = rs.getString("id");
				int suu = rs.getInt("suu");
				PurchaseSum purchase_sum = new PurchaseSum(id, suu);
				purchaseSumList.add(purchase_sum);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return purchaseSumList;
	}
}