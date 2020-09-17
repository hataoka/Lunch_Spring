package app.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.sql.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import app.model.PurchaseData;
import app.model.UserData;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class LunchSpringPurchaseControllerTest {

	private MockMvc mockMvc;

	@Autowired
	LunchSpringPurchaseController target;

	@MockBean
	UserData userData;

	@MockBean
	PurchaseData P_Data;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(target).build();
	}

	@Test
	public void purchaseCheckTest() throws Exception {
		//入力された日付と個数を次のページに渡すことができているか(userConfirm)
		mockMvc.perform(post("/purchaseCheck")
				.param("date", "2020-01-01")
				.param("suu", "5"))
				.andExpect(model().attribute("date", "2020-01-01"))
				.andExpect(model().attribute("suu", 5))
				.andExpect(forwardedUrl("userConfirm"));
	}

	@Test
	public void purchaseRegisterTest() throws Exception {
		//入力された日付と個数を次のページに渡すことができているか(userComplete)

		when(userData.getUser_Id()).thenReturn("testin");
		when(P_Data.getDate()).thenReturn("2020-01-01");
		when(P_Data.getSuu()).thenReturn(5);
	    String testString = "2020-01-01";

		mockMvc.perform(post("/purchaseRegister"))
				.andExpect(model().attribute("date", Date.valueOf(testString)))
				.andExpect(model().attribute("suu", 5))
				.andExpect(forwardedUrl("userComplete"));

	}

}
