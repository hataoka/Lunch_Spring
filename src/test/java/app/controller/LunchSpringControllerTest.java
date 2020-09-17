package app.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import app.model.User;
import app.model.UserData;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class LunchSpringControllerTest {

	private MockMvc mockMvc;

	@Autowired
	LunchSpringController target;

	/*
	 * @MockBean
	 * 		MockitoではなくSpring Bootが提供しているアノテーション
	 * 		通常のMockitoのモックを作成し、それをアプリケーションコンテキスト内に登録する
	 * 		@AutowiredなどSpringのDIのメカニズムを通じて注入される
	 * 		@Componentや@Serviceなどのアノテーションがついている必要があるみたい
	 */
	@MockBean
	UserData userData;

	@MockBean
	User User;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(target).build();
	}

	@Test
	public void loginUserTest() throws Exception {
		//DBに存在している一般ユーザーの情報を入れ、DBに登録されている「kubun」
		//を参照し、一般ユーザートップ画面へ遷移するかどうか
		when(userData.getUser_Id()).thenReturn("userA");
		when(User.getName()).thenReturn("userA");
		when(User.getKubun()).thenReturn(10);

		mockMvc.perform(get("/loginSuccess"))
			.andExpect(model().attribute("total", 15))
			.andExpect(forwardedUrl("userTop"));
	}

	//テスト用のデータベースを作る(CSVから読み込む)
	@Test
	public void loginAdminTest() throws Exception {
		//DBに存在している管理者の情報を入れ、DBに登録されている「kubun」
		//を参照し、管理者トップ画面へ遷移するかどうか
		when(userData.getUser_Id()).thenReturn("kanri");
		when(User.getName()).thenReturn("kanri");
		when(User.getKubun()).thenReturn(1);

		mockMvc.perform(get("/loginSuccess"))
			.andExpect(model().attribute("total", 50))
			.andExpect(forwardedUrl("adminTop"));
	}

	@Test
	public void backUserTest() throws Exception {
		//DBに存在している一般ユーザーの情報を入れ、DBに登録されている「kubun」
		//を参照し、一般ユーザートップ画面へ遷移するかどうか
		when(userData.getUser_Id()).thenReturn("test");

		mockMvc.perform(get("/backuserTop"))
			 .andExpect(forwardedUrl("userTop"));
	}

	@Test
	public void backAdminTest() throws Exception {
		//DBに存在している管理者の情報を入れ、DBに登録されている「kubun」
		//を参照し、管理者トップ画面へ遷移するかどうか
		when(userData.getUser_Id()).thenReturn("kanri");


		mockMvc.perform(get("/backadminTop"))
			 .andExpect(forwardedUrl("adminTop"));
	}

}
