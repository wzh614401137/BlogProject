package blog.com.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import blog.com.models.entity.Account;
import blog.com.models.entity.Blog;
import blog.com.services.HPService;
import jakarta.servlet.http.HttpSession;

@Controller
public class BlogHPController {

	@Autowired
	private HttpSession session;

	@Autowired
	private HPService hpService;

	// ブログHP画面に表示
	@GetMapping("/blog/hp")
	public String getBlogHP(Model model) {
		// セッションからログインしている人の情報を取得
		Account account = (Account) session.getAttribute("loginAdminInfo");
		// もし、account==null ログイン画面にリダイレクト
		// そうでない場合、ログインしている一人の名前の情報を画面に渡す
		// ブログHP画面のhtmlを表示.
		if (account == null) {
			return "redirect:/account/login";
		} else {
			// 商品の情報を取得
			List<Blog> Bloglist = hpService.selectAllBlog(account.getAccountId());// accountの情報をserviceクラスに渡して
			model.addAttribute("accountName", account.getAccountEmail()); // 页面要展示什么，就通过 model.addAttribute("xxx", xxx)
																			// 传到前端去。
			model.addAttribute("Bloglist", Bloglist);// (key,value)(frontEnd,backEnd)
			return "blog_hp.html";
		}

	}

}
