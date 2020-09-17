package app.security;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

/**
 * 認証ユーザーの情報を格納するクラス
 */
public class LoginUser extends User {
	/**
	 * ログインユーザー
	 */
	private final MLoginUser mLoginUser;

	/**
	 * @param user ログインしようとしているユーザーの情報
	 * @param Mrole 管理者ユーザーか一般ユーザーかの分岐させるため
	 */
	public LoginUser(MLoginUser user, String Mrole) {

		// スーパークラスのユーザーID、パスワードに値をセットする
		// 実際の認証はスーパークラスのユーザーID、パスワードで行われる
		super(user.getLoginUserId(), user.getPassword(),
				AuthorityUtils.createAuthorityList("ROLE_" + Mrole));
		this.mLoginUser = user;
	}

	public MLoginUser getUser() {
		return mLoginUser;
	}
}