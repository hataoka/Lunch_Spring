package app.dao;


import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import app.model.Idcheck;
import app.model.Login;
import app.model.LoginLogic;
import app.model.User;
import app.security.MLoginUser;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserDAOTest {

	@Autowired
	UserDAO target;

	@MockBean
	User user;


	@Test
	public void findByIdCheckTest() throws Exception {
		//ユーザーID(test)を渡した際に、正しいIDと区分を取得できているかどうか
		Idcheck login = new Idcheck("test");
		UserDAO dao = new UserDAO();
		MLoginUser User = dao.findByIdcheck(login);

		assertThat(User.getLoginUserId(), is("test"));
		assertThat(User.getKubun(), is(10));
	}

	@Test
	public void findByIdCheckNullTest() throws Exception {
		//存在しないユーザーID(false)を渡した際にtry-catchの部分でException処理できているか
		Idcheck login = new Idcheck("false");
		UserDAO dao = new UserDAO();
		MLoginUser User = dao.findByIdcheck(login);

		assertThat(User, is(nullValue()));
		}

	@Test
	public void findByLoginTest() throws Exception {
		//ユーザーID(test)を渡した際に、正しいIDと区分を取得できているかどうか
		Login login = new Login("test");
		LoginLogic bo = new LoginLogic();
		User User = bo.execute(login);

		assertThat(User.getName(), is("test"));
		assertThat(User.getKubun(), is(10));
	}

	@Test
	public void findByLoginNullTest() throws Exception {
		//存在しないユーザーID(false)を渡した際にtry-catchの部分でException処理できているか
		Login login = new Login("false");
		LoginLogic bo = new LoginLogic();
		User User = bo.execute(login);

		assertThat(User, is(nullValue()));
		}

	@Test
	public void getNameTest() throws Exception{
		//存在するユーザーIDと存在しないユーザーIDで名前を取得する動きを確認する
		UserDAO dao = new UserDAO();
		assertThat(dao.getName("test"), is("test"));
		assertThat(dao.getName("false"), is(nullValue()));
	}

}
