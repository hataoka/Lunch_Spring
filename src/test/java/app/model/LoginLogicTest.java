package app.model;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class LoginLogicTest {

	@Test
	public void userLoginLogicTest() throws Exception {
		//ログインしたユーザーの情報の照合と取得

		Login login = new Login("test");
		LoginLogic bo = new LoginLogic();
		User User = bo.execute(login);

		assertThat(User.getName(), is("test"));
		assertThat(User.getKubun(), is(10));
	}

}
