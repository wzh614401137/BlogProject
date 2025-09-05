package blog.com.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import blog.com.models.entity.Blog;
import jakarta.transaction.Transactional;

@Repository // データベースに関するメソッドを提供するDao層のinterfaceであることをspingに伝える
@Transactional // スライド安全
public interface BlogDao extends JpaRepository<Blog, Long> {
	// 保存処理と更新処理 insert update
	Blog save(Blog blog);

	// SELECT * FROM blog
	// 用途：ブログの一覧を表示させるときに使用。
	List<Blog> findAll();
	
	//SELECT * FROM blog WHERE account_id = ?
	// 用途：Accountごとのblogを表示させる
	List<Blog> findByAccountId(Long accountId);

	// SELECT * FROM blog WHERE blog_title = ?
	// 用途：記事の登録チェックに使用。
	Blog findByBlogTitle(String blogTitle);

	// SELECT * FROM blog WHERE blog_id = ?
	// 用途：編集画面を表示する時に使用。
	Blog findByBlogId(Long blogId);

	// DLETE FROM blog WHERE blog_id = ?
	// 用途：削除 ！！！@Transactional が宣言必要です
	void deleteByBlogId(Long blogId);
	
	//	SELECT * 
	//	FROM blog 
	//	WHERE (account_id = ? AND blog_title LIKE %?%) 
	//	OR (account_id = ? AND article LIKE %?%);
	List<Blog> findByAccountIdAndBlogTitleContainingOrAccountIdAndArticleContaining(
	        Long accountId, String titleKeyword,
	        Long accountId2, String articleKeyword);


}
