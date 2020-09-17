package app.model;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import app.dao.UserDAO;
import app.security.MLoginUser;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class IdcheckLogicTest {

	@Autowired
	UserDAO target;

	@MockBean
	User user;

	@Test
	public void IDCheckLogicTest()  throws Exception{
		//ユーザーID(test)を渡した際に、正しいIDと区分を取得できているかどうか
		MLoginUser Muser = null;

			Idcheck login = new Idcheck("test");
			IdcheckLogic bo = new IdcheckLogic();
			Muser = bo.execute(login);

			assertThat(Muser.getLoginUserId(), is("test"));
			assertThat(Muser.getKubun(), is(10));
	}

}
