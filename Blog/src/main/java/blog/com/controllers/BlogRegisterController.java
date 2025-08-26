package blog.com.controllers;

import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import blog.com.models.entity.Account;
import blog.com.services.HPService;
import jakarta.servlet.http.HttpSession;

@Controller
public class BlogRegisterController {
	@Autowired
	private HPService hpservice;

	@Autowired
	private HttpSession session;

	// ブログ画面の表示
	@GetMapping("/blog/register")
	public String getBlogRegsiterPage(Model model) {// 画面にデータを渡しますので、Modelを使います
		// セッションからログインしている人の情報をaccountという変数に格納
		Account account = (Account) session.getAttribute("loginAdminInfo");
		// もし、account==null ログイン画面にリダイレクトする
		// そうでない場合は、ログインしている人の名前を画面に渡す。
		// 商品登録のhtmlを表示させる
		if (account == null) {
			return "redirect:/account/login";
		} else {
			model.addAttribute("acountName", account.getAccountName());// 如果有值，就拿出 account.getAccountName()
																		// 传到前端页面，页面上就能显示“你好，某某用户”。
			return "blog_register.html";
		}

	}

	// 記事の登録処理
	@PostMapping("/blog/register/process")
	public String blogRegisterProcess(@RequestParam String blogTitle, @RequestParam String categoryName,
			@RequestParam MultipartFile blogImage, @RequestParam String article) {
		// セッションからログインしている人の情報をaccountという変数に格納
		Account account = (Account) session.getAttribute("loginAdminInfo");

		// もし、account==null ログイン画面にリダイレクトする
		// そうでない場合は、画像のファイル名を所得
		// 画像のアップロード
		// もし、同じファイル名がない場合、保存処理
		// 商品の一覧画面にリダイレクトする
		// そうでない場合、商品登録画面にとどまります。
		if (account == null) {
			return "redirect:/account/login";
		} else {
			/**
			 * 現在の日時情報を元に、ファイル名を作成しています。SimpleDateFormatクラスを使用して、日時のフォマードを指定して、
			 * 具体的には、"yyyy-MM-dd-HH-mm-ss-"の形式でフォマードされた文字列を所得して
			 * その後、blogImageオブジェクトから元のファイル名を所得して、フォマードされた日時文字列と連結して、fileName変数に代入
			 **/
			String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date())
					+ blogImage.getOriginalFilename();
			
			//ファイルの保存作業
			Files.copy(blogImage.getInputStream(), null)
			
		}
			
	}

}
