package app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.model.GetPurchaseListLogic;
import app.model.Login;
import app.model.LoginLogic;
import app.model.Purchase;
import app.model.User;
import app.model.UserData;

@Controller
public class ChangeMonthController {

	@Autowired
	UserData userData;

	@Autowired
	LoginLogic loginLogic;

	@Autowired
	GetPurchaseListLogic getPurchaseListLogic;

	@RequestMapping("/userChangeMonth")
	public String userChangeMonth(Model model, @RequestParam("date") String date) {
		String id = userData.getUser_Id();

		Login login = new Login(id);
		User User = loginLogic.execute(login);

		//ユーザーIDを保存
		model.addAttribute("userId", User);
		//ログインユーザーの購入数のリストを取得して保存
		List<Purchase> purchaseList = getPurchaseListLogic.execute(id, date);
		model.addAttribute("purchaseList", purchaseList);

		int total = 0;
		//リストの合計を求めるための計算をして保存
		for (int i = 0; i < purchaseList.size(); i++) {
			Purchase plist = purchaseList.get(i);
			int instead = plist.getSuu();
			total = total + instead;
		}
		model.addAttribute("total", total);
		model.addAttribute("id", id);

		return "userTop";

	}
}
