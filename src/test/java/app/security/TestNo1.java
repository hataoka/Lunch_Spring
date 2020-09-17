package app.security;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration
@WebAppConfiguration
public class TestNo1 {
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private FilterChainProxy springSecurityFilterChain;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders
					.webAppContextSetup(context)
					.apply(springSecurity(springSecurityFilterChain))
					.build();
	}

    @Test
    public void test1_1() throws Exception {
        // ログイン画面の正常系テスト
        mockMvc.perform(formLogin("/login").user("id","test").password("pass","1234"))
            .andExpect(redirectedUrl("/loginSuccess"));
    }

    @Test
    public void test1_2() throws Exception {
        // ログイン画面の異常系テスト
        mockMvc.perform(formLogin("/login").user("id","test").password("pass","0000"))
            .andExpect(redirectedUrl("/loginError"));
    }

    @Test
    public void test1_3() throws Exception {
        // ログイン画面の異常系テスト
        mockMvc.perform(formLogin("/login").user("id","test").password("pass",""))
            .andExpect(redirectedUrl("/loginError"));
    }

    @Test
    public void test1_4() throws Exception {
        // ログイン画面の異常系テスト
        mockMvc.perform(formLogin("/login").user("id","false").password("pass","1234"))
            .andExpect(redirectedUrl("/loginError"));
    }

    @Test
    public void test1_5() throws Exception {
        // ログイン画面の異常系テスト
        mockMvc.perform(formLogin("/login").user("id","false").password("pass","0000"))
            .andExpect(redirectedUrl("/loginError"));
    }

    @Test
    public void test1_6() throws Exception {
        // ログイン画面の異常系テスト
        mockMvc.perform(formLogin("/login").user("id","false").password("pass",""))
            .andExpect(redirectedUrl("/loginError"));
    }

    @Test
    public void test1_7() throws Exception {
        // ログイン画面の異常系テスト
        mockMvc.perform(formLogin("/login").user("id","").password("pass","1234"))
            .andExpect(redirectedUrl("/loginError"));
    }

    @Test
    public void test1_8() throws Exception {
        // ログイン画面の異常系テスト
        mockMvc.perform(formLogin("/login").user("id","").password("pass","0000"))
            .andExpect(redirectedUrl("/loginError"));
    }

    @Test
    public void test1_9() throws Exception {
        // ログイン画面の異常系テスト
        mockMvc.perform(formLogin("/login").user("id","").password("pass",""))
            .andExpect(redirectedUrl("/loginError"));
    }

}
