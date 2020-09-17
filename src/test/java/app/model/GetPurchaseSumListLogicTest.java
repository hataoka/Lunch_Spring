package app.model;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class GetPurchaseSumListLogicTest {

	@Test
	public void GetPurchaseSumLogicTest()  throws Exception {
		//管理者ユーザーでログインした時にそれぞれのユーザーの購入数を出力できているか
		GetPurchaseSumListLogic getPurchaseSumListLogic = new GetPurchaseSumListLogic();
		List<PurchaseSum> purchaseSumList = getPurchaseSumListLogic.execute();

		assertThat(purchaseSumList).extracting("id")
				.containsExactly("userA", "userB", "userC", "userD");
		assertThat(purchaseSumList).extracting("suu")
				.containsExactly(15, 10, 5, 20);
	}

}
