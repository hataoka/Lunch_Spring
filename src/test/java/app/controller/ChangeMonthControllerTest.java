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

import app.model.Login;
import app.model.LoginLogic;
import app.model.PurchaseData;
import app.model.User;
import app.model.UserData;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ChangeMonthControllerTest {

	private MockMvc mockMvc;

	@Autowired
	ChangeMonthController target;

	@MockBean
	UserData userData;

	@MockBean
	PurchaseData P_Data;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(target).build();
	}

	@Test
	public void userChangeMonthTest() throws Exception {
		when(userData.getUser_Id()).thenReturn("test");

		Login login = new Login("test");
		LoginLogic bo = new LoginLogic();
		User User = bo.execute(login);

		mockMvc.perform(get("/userChangeMonth")
			.param("date", "2020-01"))
			.andExpect(model().attribute("userId", User))
			.andExpect(model().attribute("total", 20))
			.andExpect(model().attribute("id", "test"))
			.andExpect(forwardedUrl("userTop"));
	}



}