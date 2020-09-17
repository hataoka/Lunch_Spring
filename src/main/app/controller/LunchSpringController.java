package app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import app.model.GetPurchaseListLogic;
import app.model.GetPurchaseSumListLogic;
import app.model.Login;
import app.model.LoginLogic;
import app.model.Purchase;
import app.model.PurchaseSum;
import app.model.User;
import app.model.UserData;


@Controller
public class LunchSpringController {

	@Autowired
	UserData userData;

	@Autowired
	LoginLogic loginlogic;

	@Autowired
	GetPurchaseListLogic getPurchaseListLogic;

	@Autowired
	GetPurchaseSumListLogic getPurchaseSumListLogic;

	/**
	 * ログイン認証が成功すると、ここの処理に。
	 * セッションスコープへのデータの保存とkubunをもとに画面遷移する役割。
	 * @param model
	 * @return kubunが「1」→"adminTop.html"、管理者ユーザートップ画面へ
	 *                 「10」→"userTop.html"、一般ユーザートップ画面へ
	 */
	@RequestMapping("/loginSuccess")
	public String loginSuccess(Model model) {

		String id = userData.getUser_Id();

		Login login = new Login(id);
		User User = loginlogic.execute(login);

		String name = User.getName();
		int kubun = User.getKubun();

		//ユーザーIDを保存
		model.addAttribute("userId", User);

		//ログインユーザーの購入数のリストを取得して保存
		List<Purchase> purchaseList = getPurchaseListLogic.execute(id);
		model.addAttribute("purchaseList", purchaseList);

		int total = 0;
		if (kubun == 10) { //ログイン成功時

			//ユーザーIDをセッションスコープに保存
			userData.setUser_Id(id);
			userData.setUser_Name(name);
			userData.setUser_Kubun(kubun);

			//リストの合計を求めるための計算をして保存
			for (int i = 0; i < purchaseList.size(); i++) {
				Purchase plist = purchaseList.get(i);
				int instead = plist.getSuu();
				total = total + instead;
			}
			model.addAttribute("total", total);

			return "userTop";

		} else if (kubun == 1) {

			//管理者IDをセッションスコープに保存
			userData.setKUser_Id(id);
			userData.setUser_Kubun(kubun);

			//ユーザーごとの合計のリストを取得して保存
			List<PurchaseSum> purchasesumList = getPurchaseSumListLogic.execute();
			model.addAttribute("purchasesumList", purchasesumList);

			//リストの合計を求めるための計算をして保存
			for (int i = 0; i < purchasesumList.size(); i++) {
				PurchaseSum pslist = purchasesumList.get(i);
				int instead = pslist.getSuu();
				total = total + instead;
			}
			model.addAttribute("total", total);

			return "adminTop";

		} else {//ログイン失敗時
				//リダイレクト
			return "loginForm";
		}
	}

	@RequestMapping("/")
	public String loginReturn() {
		return "loginForm";
	}

	@RequestMapping("/loginForm")
	public String loginForm() {
		return "loginForm";
	}

	@RequestMapping("/loginError")
	public String loginError() {
		return "loginForm";
	}



	/**
	 * 一般ユーザートップ画面へ戻る際の処理
	 * @param model
	 * @return 一般ユーザートップ画面
	 */
	@RequestMapping("/backuserTop")
	public String backuserForm(Model model) {
		String id = userData.getUser_Id();

		Login login = new Login(id);
		User User = loginlogic.execute(login);

		//ユーザーIDを保存
		model.addAttribute("userId", User);

		//ログインユーザーの購入数のリストを取得して保存
		List<Purchase> purchaseList = getPurchaseListLogic.execute(id);
		model.addAttribute("purchaseList", purchaseList);

		int total = 0;
		//リストの合計を求めるための計算をして保存
		for (int i = 0; i < purchaseList.size(); i++) {
			Purchase plist = purchaseList.get(i);
			int instead = plist.getSuu();
			total = total + instead;
		}
		model.addAttribute("total", total);

		return "userTop";
	}

	/**
	 * 管理者トップ画面へ戻る際の処理
	 * @param model
	 * @return adminTop
	 */

	@RequestMapping("/backadminTop")
	public String backadminTop(Model model) {

		//ユーザーごとの合計のリストを取得して保存
		List<PurchaseSum> purchasesumList = getPurchaseSumListLogic.execute();
		model.addAttribute("purchasesumList", purchasesumList);

		int total = 0;
		//リストの合計を求めるための計算をして保存
		for (int i = 0; i < purchasesumList.size(); i++) {
			PurchaseSum pslist = purchasesumList.get(i);
			int instead = pslist.getSuu();
			total = total + instead;
		}
		model.addAttribute("total", total);

		return "adminTop";

	}

}
