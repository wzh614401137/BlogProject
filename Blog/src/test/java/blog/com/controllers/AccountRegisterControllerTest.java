package blog.com.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import blog.com.services.AccountService;

@WebMvcTest(AccountRegisterController.class)
public class AccountRegisterControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AccountService accountService;

	// No.1 表示テスト
	@Test
	public void testGetAccountRegisterPage() throws Exception {
		mockMvc.perform(get("/account/register")).andExpect(status().isOk())
				.andExpect(view().name("admin_register.html"));
	}

	// No.2 ユーザー登録テスト（新規成功）
	@Test
	void testAccountRegister_Success() throws Exception {
		when(accountService.createAccount("oushigou", "oushigou@blog.com", "123")).thenReturn(true);
		mockMvc.perform(post("/account/register/process")
				.param("adminName", "oushigou")
				.param("adminEmail", "oushigou@blog.com")
				.param("password", "123"))
				.andExpect(status().isOk())
				.andExpect(view().name("admin_login.html"));

		// createAccount が 1回呼ばれたことを検証
		verify(accountService, times(1)).createAccount("oushigou", "oushigou@blog.com", "123");
	}

	// No.3 ユーザー登録テスト（既存失敗）
	@Test
	void testAccountRegister_Failure() throws Exception {
		when(accountService.createAccount("oushigou", "oushigou@blog.com", "123")).thenReturn(false);

		mockMvc.perform(post("/account/register/process")
				.param("adminName", "oushigou")
				.param("adminEmail", "oushigou@blog.com")
				.param("password", "123"))
				.andExpect(status().isOk())
				.andExpect(view().name("admin_register.html"));

		// createAccount が 1回呼ばれたことを検証
		verify(accountService, times(1)).createAccount("oushigou", "oushigou@blog.com", "123");
	}

	// No.4 初期表示（入力値が空白）
	@Test
	void testAccountRegister_EmptyInput() throws Exception {
		when(accountService.createAccount("", "", "")).thenReturn(false);

		mockMvc.perform(post("/account/register/process")
				.param("adminName", "")
				.param("adminEmail", "")
				.param("password", ""))
				.andExpect(status().isOk())
				.andExpect(view().name("admin_register.html"));

		verify(accountService, times(1)).createAccount("", "", "");
	}

}
