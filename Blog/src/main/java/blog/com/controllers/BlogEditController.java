package blog.com.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import blog.com.models.entity.Account;
import blog.com.models.entity.Blog;
import blog.com.services.HPService;
import jakarta.servlet.http.HttpSession;

@Controller
public class BlogEditController {
	@Autowired
	private HPService hpService;

	@Autowired
	private HttpSession session;

	// 編集画面の表示
	@GetMapping("/blog/edit/{blogId}")
	public String getBlogEditPage(@PathVariable Long blogId, Model model) {
		// セッションからログインしている人の情報をaccountという変数に格納
		Account account = (Account) session.getAttribute("loginAdminInfo");
		// もし、account==null ログイン画面にリダイレクトする
		if (account == null) {
			return "redirect:/account/login";
		} else {
			// 編集画面に表示させる情報を変数に格納 blog
			Blog blog = hpService.blogEditCheck(blogId);
			if (blog == null) {
				return "redirect:/blog/hp";
			} else {
				model.addAttribute("accountName", account.getAccountName());// 名前を獲得
				model.addAttribute("blog", blog);
				return "blog_edit.html";
			}
		}
	}

	// 更新処理
	@PostMapping("/blog/edit/process")
	public String blogUpdate(@RequestParam String blogTitle, 
			@RequestParam String categoryName,
			@RequestParam MultipartFile blogImage, 
			@RequestParam String article, 
			@RequestParam Long blogId) {
		// セッションからログインしている人の情報をaccountという変数に格納
		Account account = (Account) session.getAttribute("loginAdminInfo");
		// もし、account==null ログイン画面にリダイレクトする
		// そうでない場合、
		/**
		 * 現在の日時情報を元に、ファイル名を作成しています。SimpleDateFormatクラスを使用して、日時のフォマードを指定して、
		 * 具体的には、"yyyy-MM-dd-HH-mm-ss-"の形式でフォマードされた文字列を所得して
		 * その後、blogImageオブジェクトから元のファイル名を所得して、フォマードされた日時文字列と連結して、fileName変数に代入
		 **/
		// ファイルの保存
		// もし、blogUpdateの結果がtrueの場合、ブログ一覧にリダイレクトする
		// そうでない場合、ブログ編集画面にリダイレクトする
		if (account == null) {
			return "redirect:/account/login";
		} else {
			String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date())
					+ blogImage.getOriginalFilename();
			try {
				Files.copy(blogImage.getInputStream(), Path.of("src/main/resources/static/blog_img/" + fileName));
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(hpService.blogUpdate(blogId, blogTitle, categoryName, fileName, article, account.getAccountId())) {
				return "redirect:/blog/hp";
			}else {
				return "redirect:/blog/edit" + blogId;
			}

		}
	}

}
