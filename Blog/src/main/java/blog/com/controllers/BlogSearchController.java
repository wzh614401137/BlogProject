package blog.com.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import blog.com.models.entity.Account;
import blog.com.models.entity.Blog;
import blog.com.services.HPService;
import jakarta.servlet.http.HttpSession;

@Controller
public class BlogSearchController {

	@Autowired
	private HPService hpService;

	@Autowired
	private HttpSession session;

	@GetMapping("/blog/search")
	public String searchBlog(@RequestParam("keyword") String keyword, Model model) {

		// セッションからログインしている人の情報をaccountという変数に格納
		Account account = (Account) session.getAttribute("loginAdminInfo");
		// ログインしていない場合はログインページにリダイレクト
		if (account == null) {
			return "redirect:/account/login";
		} else {
			List<Blog> blogs = hpService.searchBlogByKeyword(keyword);
			if (blogs == null || blogs.isEmpty()) {
				// 没有找到，回到列表页
				return "redirect:/blog/hp";
			}else {
				// 找到 → 直接跳转到编辑页面
				Long blogId = blogs.get(0).getBlogId();
				return "redirect:/blog/edit/" + blogId;
			}
			
		}

	}

}
