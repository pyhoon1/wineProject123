package admin.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;

import javax.jws.WebParam.Mode;
import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import admin.service.AdminService;
import matchfood.vo.MatchFood;
import payment.vo.Payment;
import product.vo.Product;
import review.vo.Review;
import user.vo.User;

@Controller
public class AdminController {

	@Autowired
	private AdminService adminservice;

	@RequestMapping("/adminMakeMatchFoodForm.do")
	public ModelAndView getMakeMatchFoodForm() {
		return new ModelAndView("adminMakeMatchFoodForm");
	}
	
	
	
	
	@RequestMapping("/adminProductList.do")
	public String getAdminProductList(@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "type", required = false) String type, Model model) {

		System.out.println("condition === pro " + condition);
		
		if(type != null && condition != null) {
		if (type.equals("product") && condition != null) {
			System.out.println("product on ");
			List<Product> product = adminservice.getSearchProductList(condition);
			List<MatchFood> matchfood = adminservice.getMatchFoodList();
			model.addAttribute("productList", product);
			model.addAttribute("matchfood", matchfood);

		} else if (type.equals("matchFood") && condition != null) {
			System.out.println("matchFood on ");
			List<Product> product = adminservice.getProductList();
			List<MatchFood> matchfood = adminservice.getSearchMatchFoodList(condition);
			model.addAttribute("productList", product);
			model.addAttribute("matchfood", matchfood);
		}
		}else {		
			System.out.println("else on ");
			List<Product> product = adminservice.getProductList();
			List<MatchFood> matchfood = adminservice.getMatchFoodList();
			model.addAttribute("productList", product);
			model.addAttribute("matchfood", matchfood);
		}
			
		

		return "adminProductList";
	}

	@RequestMapping("/adminUserList.do")
	public String getAdminUserList(@RequestParam(value = "condition", required = false) String condition, Model model) {

		System.out.println("condition === " + condition);
		if (condition == null) {
			List<User> user = adminservice.getUserList();
			model.addAttribute("userList", user);
		} else {
			List<User> user = adminservice.getSearchUserList(condition);
			model.addAttribute("userList", user);
		}
		return "adminUserList";
	}

	@RequestMapping("/adminUserView.do")
	public String getUser(Model model, @RequestParam("userId") int userId) {
		User user = adminservice.getUserView(userId);
		model.addAttribute("user", user);
		return "adminUserView";
	}

	@RequestMapping("/admindeleteUser.do")
	public String deleteUser(@RequestParam("userId") int userId) {
		adminservice.deleteUser(userId);
		return "adminUserList";
	}

	@RequestMapping("/hasUser.do")
	@ResponseBody
	public String hasUser(@RequestParam("loginId") String loginId) {
		int hasUser = adminservice.hasUserId(loginId);
		String result = "";
		if (hasUser > 0) {
			result = "Y";
			return result;
		} else {
			result = "N";
			return result;
		}
	}
	
	@RequestMapping("/hasMatchFood.do")
	@ResponseBody
	public String hasMatchFood(@RequestParam("matchFoodName") String matchFoodName) {
		int hasMatchFood = adminservice.hasMatchFood(matchFoodName);
		String result = "";
		if (hasMatchFood > 0) {
			result = "Y";
			return result;
		}else {
			result = "N";
			return result;
		}
	}

	@RequestMapping("/warningReview.do")
	@ResponseBody
	public ModelAndView warningReview(@RequestParam("state") String state, @RequestParam("reviewId") int reviewId,
			int userId, Model model) {

		List<Review> review = adminservice.getReviewUser(userId);

		model.addAttribute("reviewList", review);

		adminservice.updateReviewWarning(new Review(reviewId, state));

		return new ModelAndView("adminUserReviewView");
	}

	@RequestMapping("/userReviewView.do")
	@ResponseBody
	public ModelAndView userReviewView(@RequestParam("userId") String userId, Model model) {
		int Id = Integer.parseInt(userId);

		List<Review> review = adminservice.getReviewUser(Id);

		model.addAttribute("reviewList", review);

		return new ModelAndView("adminUserReviewView");

	}

	@RequestMapping("/userPaymentView.do")
	@ResponseBody
	public ModelAndView userPaymentView(@RequestParam("userId") String userId, Model model) {
		int Id = Integer.parseInt(userId);
		List<Payment> payment = adminservice.getPaymentUser(Id);
		model.addAttribute("paymentList", payment);

		System.out.println("payment" + payment.size());

		return new ModelAndView("adminUserPaymentView");

	}

	@RequestMapping("/deleteReview.do")
	@ResponseBody
	public ModelAndView deleteReview(@RequestParam("reviewId") String reviewId, @RequestParam("userId") String userId,
			Model model) {

		int rId = Integer.parseInt(reviewId);

		int uId = Integer.parseInt(userId);

		System.out.println("deleteReivew 1" + rId);
		System.out.println("deleteReview 2" + uId);

		adminservice.deleteReview(rId);

		List<Review> review = adminservice.getReviewUser(uId);

		model.addAttribute("reviewList", review);

		return new ModelAndView("adminUserReviewView");
	}

	@RequestMapping("/updateAdmin.do")
	@ResponseBody
	public String updateAdmin(@RequestParam("userId") String Id, @RequestParam("loginId") String loginId,
			@RequestParam("password") String password, @RequestParam("userName") String userName,
			@RequestParam("email") String email, @RequestParam("address") String address,
			@RequestParam("phone") String phone, @RequestParam("birth") String birth) {

		int userId = Integer.parseInt(Id);
		System.out.println(userId);
		System.out.println(loginId);
		System.out.println(password);
		System.out.println(email);
		System.out.println(address);
		System.out.println(userName);
		System.out.println(phone);
		System.out.println(birth);
		int check = adminservice.updateUser(new User(userId, loginId, password, userName, email, address, phone, birth));
		System.out.println("check" + check);
		if (check > 0) {
			String result = "Y";
			return result;	
		} else {
			String result = "N";
			return result;
		}

	}
	
	
	@RequestMapping(value="/insertMatchFood.do", method=RequestMethod.POST)
	@ResponseBody
	public String insertMatchFood(HttpServletRequest request, @RequestParam("uploadFile")MultipartFile file) {
		
		String path = request.getSession().getServletContext().getRealPath("");
		
		System.out.println("path~~~~~~~~~~" + request.getSession().getServletContext().getResourcePaths("/"));
		System.out.println("path~~~~~~~~~~2" + request.getSession().getServletContext().getRealPath(""));
	
		
		String originalFilename = file.getOriginalFilename();
		String onlyFileName = originalFilename.substring(0,originalFilename.indexOf("."));
		String extension = originalFilename.substring(originalFilename.indexOf("."));
		
		long time = System.currentTimeMillis();
		
		String rname = onlyFileName +"_" + time + extension;
		String fullPath = "/matchFoodImg/"+ rname;
		
		System.out.println("path2!~~~~~~~~~"+fullPath);
		if(!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(fullPath)));
				stream.write(bytes);
				stream.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			
			
			
		}	
		return "adminProductList";
	}
					
	
}