package blog.com.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import blog.com.models.entity.Account;

@Repository // データベースに関するメソッドを提供するDao層のinterfaceであることをspingに伝える
public interface AccountDao extends JpaRepository<Account, Long> {

	// 保存処理と更新処理 insert update
	Account save(Account account);// かっこの中は、entityのクラス名と変数

	// SELECT * FROM account WHERE account_email = ?
	// 用途：管理者の登録処理をするときに、同じメールアドレスがあったら 登録処理させないようにする
	// 1行だけしかレコードは所得できない
	Account findByAccountEmail(String accountEmail);

	// SELECT * FROM account WHERE account_email = ? AND password = ?
	// 用途：ログイン処理に使用。入力したメールアドレスとパスワードが一致してるときだけ データを取る
	Account findByAccountEmailAndPassword(String accountEmail, String password);

}
