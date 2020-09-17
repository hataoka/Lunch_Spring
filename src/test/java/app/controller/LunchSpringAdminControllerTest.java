package app.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class LunchSpringAdminControllerTest {

	private MockMvc mockMvc;

	@Autowired
	LunchSpringAdminController target;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(target).build();
	}


	@Test
	public void adminDetailTest() throws Exception {
		//選択したユーザーの情報を次の画面に渡すことができているか
		mockMvc.perform(get("/getAdminDetail")
			.param("id", "test"))
			.andExpect(model().attribute("name", "test"))
			.andExpect(model().attribute("total",25))
			.andExpect(forwardedUrl("adminDetail"));
	}

}