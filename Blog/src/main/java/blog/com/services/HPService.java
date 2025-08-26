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

}
