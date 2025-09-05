package blog.com.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import blog.com.models.entity.Account;
import blog.com.services.AccountService;


@SpringBootTest
@AutoConfigureMockMvc //ｈｔｔｐ通信
public class AccountLoginControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean // 模拟依赖
	private AccountService accountService;
	
	
	// 1. ログインページを正しく取得するテスト
	@Test
	public void testGetAccountLoginPage_Successd() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/account/login");
		mockMvc.perform(request)
		   .andExpect(view().name("admin_login.html"));
	}

	// 2. 正しいemail & password → ログイン成功 + Session保存 + リダイレクト
    @Test
    public void testAccountLogin_Success() throws Exception {
    	Account mockAccount = new Account("oushigou", "oushigou@blog.com", "123");
    	when(accountService.loginCheck("oushigou@blog.com", "123")).thenReturn(mockAccount);
    	
    	 mockMvc.perform(post("/account/login/process")
                 .param("adminEmail", "oushigou@blog.com")
                 .param("password", "123"))
                 .andExpect(status().is3xxRedirection())
                 .andExpect(redirectedUrl("/blog/hp"))
                 .andExpect(request().sessionAttribute("loginAdminInfo", mockAccount));
    	
    }
    
    // 3. 誤ったemail → ログイン失敗 → admin_login.html + Session未設定
    public void testLoginFailure_WrongEmail() throws Exception {
    	when(accountService.loginCheck("test@blog.com", "123")).thenReturn(null);
    	
    	 mockMvc.perform(post("/account/login/process")
    			 .param("adminEmail", "test@blog.com")
                 .param("password", "123"))
    	 		 .andExpect(status().isOk())
    	 		 .andExpect(view().name("admin_login.html"))
    	 		 .andExpect(request().sessionAttributeDoesNotExist("loginAdminInfo"));
    }
    
    // 4. 誤ったpassword → ログイン失敗
    @Test
    void testLoginFailure_WrongPassword() throws Exception {
        when(accountService.loginCheck("oushigou@blog.com", "567")).thenReturn(null);

        mockMvc.perform(post("/account/login/process")
                .param("adminEmail", "oushigou@blog.com")
                .param("password", "567"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin_login.html"))
                .andExpect(request().sessionAttributeDoesNotExist("loginAdminInfo"));
    }
	
    // 5. email正しいがpassword間違い → 失敗
    @Test
    void testLoginFailure_WrongPassword2() throws Exception {
        when(accountService.loginCheck("test@test.com", "1234")).thenReturn(null);

        mockMvc.perform(post("/account/login/process")
                .param("adminEmail", "test@test.com")
                .param("password", "1234"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin_login.html"))
                .andExpect(request().sessionAttributeDoesNotExist("loginAdminInfo"));
    }
    
   // 6. 初期画面表示（入力値が空）
    @Test
    void testLoginWithEmptyInput() throws Exception {
        when(accountService.loginCheck("", "")).thenReturn(null);

        mockMvc.perform(post("/account/login/process")
                .param("adminEmail", "")
                .param("password", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("admin_login.html"))
                .andExpect(request().sessionAttributeDoesNotExist("loginAdminInfo"));
    }
}
