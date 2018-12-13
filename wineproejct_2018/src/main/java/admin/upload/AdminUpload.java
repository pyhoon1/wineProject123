package admin.upload;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.apache.bcel.classfile.Field;
import org.springframework.web.multipart.MultipartFile;

public class AdminUpload {

	public void Upload(HttpServletRequest hsr ,HashMap<String, String> prm,MultipartFile file, String type) {
		
		String path = hsr.getSession().getServletContext().getRealPath("/");
		
		String originalFilename = file.getOriginalFilename();
		
		String onlyFileName = originalFilename.substring(0, originalFilename.indexOf("."));
		
		String extension = originalFilename.substring(originalFilename.indexOf("."));
		
		long time = System.currentTimeMillis();
		
		String winepath = "/resources/img/wineImg/";
		String matchfoodpath = "/resources/img/matchfoodImg/"
		if(type.equals("wine")) {
			
			
			
			File paths = new File(path+"/resources/img/wineImg/");
			
			if(!paths.exists()) {
			   paths.mkdirs();
			}
			String rname = onlyFileName + "_" + time + extension;
			String fullPath = path + "/resources/img/wineImg/" + rname;
			
		}else{
			
		}
	}
	
}
