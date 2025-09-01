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

	@GetMapping("/blog/search")//データの獲得を目的にするのものなので、Getメソッドを使います。
	public String searchBlog(@RequestParam("keyword") String keyword, Model model) {

		// セッションからログインしている人の情報をaccountという変数に格納
		Account account = (Account) session.getAttribute("loginAdminInfo");
		// ログインしていない場合はログインページにリダイレクト
		if (account == null) {
			return "redirect:/account/login";
		} else {
			//検査内容に関係あるのブログの情報を取得
			List<Blog> blogs = hpService.searchBlogByKeyword(keyword);
			if (blogs == null || blogs.isEmpty()) {
				//該当するブログは見つからなかった場合、メッセージを表示し、リダイレクトする。
				 return "redirect:/blog/hp?message=notfound";
			} else {
				// 見つかったら、編集画面にリダイレクトする。
//				Long blogId = blogs.get(0).getBlogId();
//				return "redirect:/blog/edit/" + blogId;
				model.addAttribute("accountName", account.getAccountEmail()); 
				model.addAttribute("Bloglist", blogs);// (key,value)(frontEnd,backEnd)
				return "blog_hp.html";
			}

		}

	}

}
