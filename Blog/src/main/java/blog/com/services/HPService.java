package blog.com.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.com.models.dao.BlogDao;
import blog.com.models.entity.Blog;

@Service
public class HPService {
	@Autowired
	private BlogDao blogDao;

	// blog一覧のチェック
	// もしaccountId==null 戻り値としてnull
	// findAll内容のコントローラクラスに渡す
	public List<Blog> selectAllBlog(Long accountId) {
		if (accountId == null) {
			return null;
		} else {
			return blogDao.findAll();
		}
	}

	// 記事の登録チェック
	// もし、findByBlogTitle==nullだったら
	// 保存処理 ture
	// そうでない場合、false
	public boolean createBlog(String blogTitle, String categoryName, String blogImage, String article, Long accountId) {
		if (blogDao.findByBlogTitle(blogTitle) == null) {
			blogDao.save(new Blog(blogTitle, categoryName, blogImage, article, accountId));
			return true;
		} else {
			return false;
		}

	}

	// 編集画面を表示するときのチェック
	// もし、blogId == null return null
	// そうでない場合、
	// findByBlogIdの情報をコントローラークラスに渡す
	public Blog blogEditCheck(Long blogId) {
		if (blogId == null) {
			return null;
		} else {
			return blogDao.findByBlogId(blogId);
		}
	}

	// 更新処理のチェック
	//もし、productId==nullだったら、更新処理しない
	//false
	//そうでない場合、更新処理する
	//コントローラークラスからもらった、productIdを使って、編集する前んのデータを取得
	//変更すべきところだけ、セッターを使用してデータの更新をする
	//trueを返す
	public boolean blogUpdate(Long blogId,
			String blogTitle,
			String categoryName,
			String blogImage,
			String article,
			Long accountId) {
		if(blogId == null) {
			return false;
		}else {
			Blog blog = blogDao.findByBlogId(blogId);//blogIdが一致したら、データを取ってくる
			blog.setBlogTitle(blogTitle);
			blog.setCategoryName(categoryName);
			blog.setBlogImage(blogImage);
			blog.setArticle(article);
			blog.setAccountId(accountId);
			blogDao.save(blog);
			return true;
		}
		
	}
	
	
	
	
}
