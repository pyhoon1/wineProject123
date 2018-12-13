package admin.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import admin.service.AdminService;
import admin.upload.AdminUpload;
import matchfood.vo.MatchFood;
import payment.vo.Payment;
import product.vo.Product;
import review.vo.Review;
import user.vo.User;

@Controller
public class AdminController {

	@Autowired
	private AdminService adminService;

	@RequestMapping("/adminMakeMatchFoodForm.do")
	public ModelAndView getMakeMatchFoodForm() {
		return new ModelAndView("/admin/adminMakeMatchFoodForm");
	}
	
	@RequestMapping("/adminMakeWineForm.do")
	public ModelAndView getMakeWineForm(Model model) {
		List<MatchFood> matchfood = adminService.getMatchFoodList();
		model.addAttribute("matchFood",matchfood);
		return new ModelAndView("/admin/adminMakeWineForm");
	}
	

	@RequestMapping("/adminProductList.do")
	public String getAdminProductList(@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "type", required = false) String type, Model model) {

		System.out.println("condition === pro " + condition);
		if (type != null && condition != null) {
			if (type.equals("product") && condition != null) {
				System.out.println("product on ");
				List<Product> product = adminService.getSearchProductList(condition);
				List<MatchFood> matchfood = adminService.getMatchFoodList();
				model.addAttribute("productList", product);
				model.addAttribute("matchfood", matchfood);

			} else if (type.equals("matchFood") && condition != null) {
				System.out.println("matchFood on ");
				List<Product> product = adminService.getProductList();
				List<MatchFood> matchfood = adminService.getSearchMatchFoodList(condition);
				model.addAttribute("productList", product);
				model.addAttribute("matchfood", matchfood);
			}
		} else {
			System.out.println("else on ");
			List<Product> product = adminService.getProductList();
			List<MatchFood> matchfood = adminService.getMatchFoodList();
			model.addAttribute("productList", product);
			model.addAttribute("matchfood", matchfood);
		}

		return "/admin/adminProductList";
	}

	@RequestMapping("/adminUserList.do")
	public String getAdminUserList(@RequestParam(value = "condition", required = false) String condition, Model model) {

		System.out.println("condition === " + condition);
		if (condition == null) {
			List<User> user = adminService.getUserList();
			model.addAttribute("userList", user);
		} else {
			List<User> user = adminService.getSearchUserList(condition);
			model.addAttribute("userList", user);
		}
		return "/admin/adminUserList";
	}

	@RequestMapping("/adminUserView.do")
	public String getUser(Model model, @RequestParam("userId") int userId) {
		User user = adminService.getUserView(userId);
		model.addAttribute("user", user);
		return "/admin/adminUserView";
	}

	@RequestMapping("/admindeleteUser.do")
	public String deleteUser(@RequestParam("userId") int userId) {
		adminService.deleteUser(userId);
		return "/admin/adminUserList";
	}

	@RequestMapping("/hasUser.do")
	@ResponseBody
	public String hasUser(@RequestParam("loginId") String loginId) {
		int hasUser = adminService.hasUserId(loginId);
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
	public String hasMatchFood(@RequestParam(name = "matchFoodName", required = false) String matchFoodName) {
		int hasMatchFood = adminService.hasMatchFood(matchFoodName);
		String result = "";
		if (hasMatchFood > 0) {
			result = "Y";
			return result;
		} else {
			result = "N";
			return result;
		}
	}
	
	@RequestMapping("/hasWine.do")
	@ResponseBody
	public String hasWine(@RequestParam (name ="productName", required=false) String productName) {
		int hasWine = adminService.hasWine(productName);
		String result = "";
		if (hasWine > 0) {
			result = "Y";
			return result;
		} else {
			result = "N";
			return result;
		}
	}

	@RequestMapping("/warningReview.do")
	@ResponseBody
	public ModelAndView warningReview(@RequestParam("state") String state, @RequestParam("reviewId") int reviewId,
			int userId, Model model) {

		List<Review> review = adminService.getReviewUser(userId);

		model.addAttribute("reviewList", review);

		adminService.updateReviewWarning(new Review(reviewId, state));

		return new ModelAndView("/admin/adminUserReviewView");
	}

	@RequestMapping("/userReviewView.do")
	@ResponseBody
	public ModelAndView userReviewView(@RequestParam("userId") String userId, Model model) {
		int Id = Integer.parseInt(userId);

		List<Review> review = adminService.getReviewUser(Id);

		model.addAttribute("reviewList", review);

		return new ModelAndView("/admin/adminUserReviewView");

	}

	@RequestMapping("/userPaymentView.do")
	@ResponseBody
	public ModelAndView userPaymentView(@RequestParam("userId") String userId, Model model) {
		int Id = Integer.parseInt(userId);
		List<Payment> payment = adminService.getPaymentUser(Id);
		model.addAttribute("paymentList", payment);

		System.out.println("payment" + payment.size());

		return new ModelAndView("/admin/adminUserPaymentView");

	}

	@RequestMapping("/deleteReview.do")
	@ResponseBody
	public ModelAndView deleteReview(@RequestParam("reviewId") String reviewId, @RequestParam("userId") String userId,
			Model model) {

		int rId = Integer.parseInt(reviewId);

		int uId = Integer.parseInt(userId);

		System.out.println("deleteReivew 1" + rId);
		System.out.println("deleteReview 2" + uId);

		adminService.deleteReview(rId);

		List<Review> review = adminService.getReviewUser(uId);

		model.addAttribute("reviewList", review);

		return new ModelAndView("/admin/adminUserReviewView");
	}

	@RequestMapping("/updateAdmin.do")
	@ResponseBody
	public String updateAdmin(@RequestParam("userId") String Id, @RequestParam("loginId") String loginId,
			@RequestParam("password") String password, @RequestParam("userName") String userName,
			@RequestParam("email") String email, @RequestParam("address") String address,
			@RequestParam("phone") String phone, @RequestParam("birth") String birth) {

		int userId = Integer.parseInt(Id);
		int check = adminService
				.updateUser(new User(userId, loginId, password, userName, email, address, phone, birth));
		System.out.println("check" + check);
		if (check > 0) {
			String result = "Y";
			return result;
		} else {
			String result = "N";
			return result;
		}

	}

	@RequestMapping(value = "/updateMatchFood.do")
	@ResponseBody
	public String updateMatchFood(HttpServletRequest request,
			@RequestParam(value = "uploadFile", required = false) MultipartFile file,
			@RequestParam HashMap<String, String> prm) {
		if (file.isEmpty() == false) {
			String path = request.getSession().getServletContext().getRealPath("/");
			String originalFilename = file.getOriginalFilename();
			String onlyFileName = originalFilename.substring(0, originalFilename.indexOf("."));
			String extension = originalFilename.substring(originalFilename.indexOf("."));

			long time = System.currentTimeMillis();

			int price = Integer.parseInt(prm.get("price"));
			String rname = onlyFileName + "_" + time + extension;
			String fullPath = path + "/resources/img/matchFoodImg/" + rname;
				String path1 = request.getSession().getServletContext().getRealPath("/");
				File file1 = new File(path1 + prm.get("originalImg"));
				if (file1.exists()) {
					if (file1.delete()) {
						System.out.println("파일 삭제 성공");
					} else {
						System.out.println("파일 삭제 실패");
					}

				}					
			if (!file.isEmpty()) {
				try {
					byte[] bytes = file.getBytes();
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(fullPath)));
					stream.write(bytes);
					stream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			int matchFoodId = Integer.parseInt(prm.get("matchFoodId"));
			int check = adminService.updateMatchFood(new MatchFood(matchFoodId,prm.get("matchFoodName"), price, prm.get("nation"),
					"resources/img/nationImg/" + prm.get("nation") + ".png","resources/img/matchFoodImg/" + rname , prm.get("wineImg"),
					"resources/img/miniWineImg/" + prm.get("wineImg") + ".png", prm.get("weight"), prm.get("ex")));
			System.out.println("check" + check);

			if (check > 0) {
				String result = "Y";
				return result;
			} else {
				String result = "N";
				return result;
			}

		} else {	
			int matchFoodId = Integer.parseInt(prm.get("matchFoodId"));
			int price = Integer.parseInt(prm.get("price"));
			int check = adminService.updateMatchFood(new MatchFood(matchFoodId,prm.get("matchFoodName"), price, prm.get("nation"),
					"resources/img/nationImg/" + prm.get("nation") + ".png", prm.get("originalImg"), prm.get("wineImg"),
					"resources/img/miniWineImg/" + prm.get("wineImg") + ".png", prm.get("weight"), prm.get("ex")));
			if (check > 0) {
				String result = "Y";
				return result;
			} else {
				String result = "N";
				return result;
			}
		}

	} 

	@RequestMapping(value = "/adminInsertWine.do") 
	@ResponseBody
	public String insertWine(HttpServletRequest hsr, @RequestParam("uploadFile") MultipartFile file,
			@RequestParam HashMap<String, String> prm) {
			AdminUpload upload = new AdminUpload();
			
			upload.Upload(hsr, prm, file, "wine");
		
		
			if(prm.get("matchFoodName").equals("")) {
				prm.remove("matchFoodName");
				prm.put("matchFoodName", "");
				prm.remove("matchFoodId");
				prm.put("matchFoodId","" );
			}		
			if(prm.get("matchFoodName").equals("") == false || prm.get("matchFoodName")!="") {
				String matchFoodName = prm.get("matchFoodName").substring(0, prm.get("matchFoodName").length()-1);
				String matchFoodId = prm.get("matchFoodId").substring(0, prm.get("matchFoodId").length()-1);
			}else {
				String matchFoodName = prm.get("matchFoodName");
				String matchFoodId = prm.get("matchFoodId");
			}
			
			int price = Integer.parseInt(prm.get("price"));
			adminService.insertProduct(new Product(prm.get("producer"), prm.get("variety"), prm.get("wineKinds"), prm.get("productName"), prm.get("wineEx"), prm.get("brandEx"),
					price, prm.get("nation"), "resources/img/nationImg/" + prm.get("nation") + ".png", prm.get("year"), matchFoodId, matchFoodName,prm.get("alcohol"), prm.get("weight"),prm.get("temperature"), "resources/img/wineImg/" + rname));
			if (!file.isEmpty()) {
				try {
					byte[] bytes = file.getBytes();
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(fullPath)));
					stream.write(bytes);
					stream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			String result = "Y";
			return result;	
			
		}
	
	
	@RequestMapping(value="/adminInsertMatchFood.do")
	@ResponseBody
	public String insertMatchFood(HttpServletRequest request, @RequestParam("uploadFile") MultipartFile file,
			@RequestParam HashMap<String, String> prm) {

		String path = request.getSession().getServletContext().getRealPath("/");

		System.out.println(path);
		

		String pdfPath2 = request.getSession().getServletContext().getRealPath("resources/img/matchFoodImg/1234.png");
		System.out.println("접근 3" + new File(pdfPath2).exists());

		String originalFilename = file.getOriginalFilename();
		String onlyFileName = originalFilename.substring(0, originalFilename.indexOf("."));
		String extension = originalFilename.substring(originalFilename.indexOf("."));

		long time = System.currentTimeMillis();

		String rname = onlyFileName + "_" + time + extension;
		String fullPath = path + "/resources/img/matchFoodImg/" + rname;

		File paths = new File(path+"/resources/img/matchFoodImg/");
		
		if(paths.exists()) {
			
		}else {
			paths.mkdirs();
		}
		
		int price = Integer.parseInt(prm.get("price"));

		System.out.println(prm.get("wineImg"));

		adminService.insertMatchFood(new MatchFood(prm.get("matchFoodName"), price, prm.get("nation"),
				"resources/img/nationImg/" + prm.get("nation") + ".png", "resources/img/matchFoodImg/" + rname,
				prm.get("wineImg"), "resources/img/miniWineImg/" + prm.get("wineImg") + ".png", prm.get("weight"),
				prm.get("ex")));

		System.out.println(request.getSession().getServletContext().getRealPath(""));
		System.out.println("path2!~~~~~~~~~" + fullPath);
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(fullPath)));
				stream.write(bytes);
				stream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return "/admin/adminProductList";
	}

	@RequestMapping("/deleteMatchFood.do")
	public String deleteMatchFood(@RequestParam("matchFoodId") int matchFoodId, @RequestParam("img") String img,
			HttpServletRequest request) {

		String path = request.getSession().getServletContext().getRealPath("/");
		File file = new File(path + img);
		System.out.println(file.exists());
		if (file.exists()) {
			if (file.delete()) {
				System.out.println("파일 삭제 성공");
			} else {
				System.out.println("파일 삭제 실패");
			}

		}
		adminService.deleteMatchFood(matchFoodId);
		return "/admin/adminProductList";
	}
	
	@RequestMapping("/deleteWine.do")
	public String deleteMatchFood(@RequestParam("productId") int productId, Model model ,
			@RequestParam("img") String img, HttpServletRequest request) {
		
		String path = request.getSession().getServletContext().getRealPath("/");
		File file = new File(path+img);
		if(file.exists()) {
			if(file.delete()) {
				System.out.println("파일 삭제 성공");
			}else {
				System.out.println("파일 삭제 실패");
			}
		}
		adminService.deleteProduct(productId);
		return "/admin/adminProductList";
		}

	@RequestMapping("/adminmatchFoodView.do")
	public String matchFoodView(@RequestParam("matchFoodId") int matchFoodId, Model model) {
		MatchFood matchfood = adminService.getMatchFoodView(matchFoodId);
		model.addAttribute("matchFood", matchfood);
		return "/admin/adminMatchFoodView";
	}
	
	@RequestMapping("/adminWineView.do")
	public String wineView(@RequestParam("productId") int productId, Model model) {
		Product product = adminService.getProductView(productId);
		List<MatchFood> matchfood = adminService.getMatchFoodList();
		model.addAttribute("matchFood",matchfood);
		model.addAttribute("product", product);
		return "/admin/adminWineView";
	}
	
	
	

}