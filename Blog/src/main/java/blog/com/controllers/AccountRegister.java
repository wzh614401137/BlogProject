package blog.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import blog.com.services.AccountService;

@Controller
public class AccountRegister {

	@Autowired
	private AccountService accountService;

	// 登録画面の表示
	@GetMapping("/account/register")
	public String getAccountRegisterPage() {
		return "admin_register.html";
	}

	// 登録処理
	@PostMapping("/account/register/process")//RequestParam()の中で、.htmlのname属性を受け入り
	public String accountRegisterprocess(@RequestParam("adminName") String accountName, 
			@RequestParam("adminEmail") String accountEmail,
			@RequestParam String password) {
		// もし、adminEmailが存在しない場合true admin_register.htmlに遷移
		// そうでない場合、admin_register.htmlにとどまり、登録処理を行ってログイン画面を表示する
		if(accountService.createAccount(accountName,accountEmail,password)) {
			return "admin_login.html";
		}else {
			return "admin_register.html";
		}
	
	}
}
