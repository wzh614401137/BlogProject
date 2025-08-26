package blog.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import blog.com.models.entity.Account;
import blog.com.services.AccountService;
import jakarta.servlet.http.HttpSession;

@Controller
public class AccountLoginController {

	@Autowired
	private AccountService accountService;

	// Sessionが使えるように宣言必
	@Autowired
	private HttpSession session;

	// ログイン画面の表示
	@GetMapping("/account/login")
	public String getAccountLoginPage() {
		return "admin_login.html";
	}

	// ログイン処理
	@PostMapping("/account/login/process") // POSTは、ウェブで入力したら、このメソッドを実行する
	public String accountLoginProcess(@RequestParam("adminEmail") String accountEmail, @RequestParam String password) {
		// loginCheckメソッドを呼び出してその結果をaccountという変数に格納
		Account account = accountService.loginCheck(accountEmail, password);
		// もし、admin==nullログイン画面にとどまります。
		// そうでない場合、sessionにログイン情報に保存
		// ブログHP画面にリダイレクトする/blog/hp
		if (account == null) {
			return "admin_login.html";
		} else {
			session.setAttribute("loginAdminInfo", account);// (key,value)keyは属性名,このKEYの名前でサーバで保存する
			// 後でsession.getAttribute("loginAdminInfo");
			// を使って、ログインしたかどうかをチェックする
			return "redirect:/blog/hp";
		}
	}
	

	
	
	
	
}
