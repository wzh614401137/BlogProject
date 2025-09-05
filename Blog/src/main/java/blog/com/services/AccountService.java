package blog.com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.com.models.dao.AccountDao;
import blog.com.models.entity.Account;

@Service
public class AccountService {
	@Autowired // 自動的に、インスタンス生成
	private AccountDao accountDao;

	// 保存処理(登録処理)
	public boolean createAccount(String accountName, String accountEmail, String password) {
		// もし、findByAdminEmail==nullだったら 登録します
		// save メソッドを使用して登録処理をする
		// 保存ができたっら true
		// そうでない場合、 false
		if (accountDao.findByAccountEmail(accountEmail) == null) {
			accountDao.save(new Account(accountName, accountEmail, password));
			return true;
		} else {
			return false;
		}
	}

	// ログインチェック用のメソッド メソッド名「loginCheck」
	// もし、「emailとpasswordの組み合わせ:findByAccountEmailAndPassword」が存在していない場合、
	// その場合、存在しないnullであることをコントローラクラスに知らせる
	public Account loginCheck(String accountEmail, String password) {
		Account account = accountDao.findByAccountEmailAndPassword(accountEmail, password);
		if (account == null) {
			return null;
		} else {
			return account;
		}
	}

}
