package app.model;

import static org.assertj.core.api.Assertions.*;

import java.sql.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class GetPurchaseListLogicTest {

	@Test
	public void GetPurchaseLogicTest() throws Exception {
		//ユーザーID(userA)の購入情報をリストとして取得できているかどうか
		String sdate1 = "2020-01-01";
		Date date1 = Date.valueOf(sdate1);
		String sdate2 = "2020-01-02";
		Date date2 = Date.valueOf(sdate2);
		String sdate3 = "2020-01-03";
		Date date3 = Date.valueOf(sdate3);

		String id = "userA";

		GetPurchaseListLogic getPurchaseListLogic = new GetPurchaseListLogic();
		List<Purchase> purchaseList = getPurchaseListLogic.execute(id);

		assertThat(purchaseList).extracting("id")
				.containsExactly("userA", "userA", "userA");
		assertThat(purchaseList).extracting("purchase_date")
				.containsExactly(date1, date2, date3);
		assertThat(purchaseList).extracting("suu")
				.containsExactly(5, 5, 5);

	}

	@Test
	public void GetChangePurchaseLogicTest() throws Exception {
		//ユーザーID(userD)の購入情報を選択した基準月をもとにリストとして取得できているかどうか
		String sdate1 = "2020-01-07";
		Date date1 = Date.valueOf(sdate1);
		String sdate2 = "2020-01-08";
		Date date2 = Date.valueOf(sdate2);
		String sdate3 = "2020-01-09";
		Date date3 = Date.valueOf(sdate3);

		String id = "userD";
		String date = "2020-01";

		GetPurchaseListLogic getPurchaseListLogic = new GetPurchaseListLogic();
		List<Purchase> purchaseList = getPurchaseListLogic.execute(id, date);

		assertThat(purchaseList).extracting("id")
				.containsExactly("userD", "userD", "userD");
		assertThat(purchaseList).extracting("purchase_date")
				.containsExactly(date1, date2, date3);
		assertThat(purchaseList).extracting("suu")
				.containsExactly(5, 5, 5);

	}

}
