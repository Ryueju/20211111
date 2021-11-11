package com.yedam.member;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.oreilly.servlet.multipart.FileRenamePolicy;


@WebServlet("/CkeditorServlet")
public class CkeditorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public CkeditorServlet() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String saveDir = "upload";
			ServletContext context = request.getServletContext();
			//servletcontext를 기준으로 "upload"
			saveDir = context.getRealPath(saveDir);
			int maxSize =  1024 * 1024 * 10; //보기좋게 10메가단위로하는것
			String encoding = "UTF-8";
			FileRenamePolicy policy = new DefaultFileRenamePolicy(); //똑같은이름이있으면 다시 리네임하는 메소드
			
			MultipartRequest multi = null;
			multi = new MultipartRequest(request, saveDir,maxSize,encoding,policy);
			
			Enumeration names =  multi.getFileNames();
			//열거형 루핑돌면서 하나씩하나씩 넘겨주기위해서 ?????
			while(names.hasMoreElements()) {
				String name = (String)names.nextElement(); //파일이름이올라와있으면 열거형타입에 넣고 hasMoreElements에서 하나하나..??형태변환한다?
				
				String originalName = multi.getOriginalFileName(name);
				String fileSystemName = multi.getFilesystemName(name);
				String fileType = multi.getContentType(name);
				System.out.println("originalName : " + originalName);
				System.out.println("fileSystemName : " + fileSystemName);
				System.out.println("fileType : " +fileType);
				
				String fileUrl = request.getContextPath() + "/upload/"+ fileSystemName;
						//fileUrl이랑 file이름들과 실제로 변경 또는 성공,실패됐다는 메시지를 json타입으로 넘겨준 것
				JsonObject json = new JsonObject();
				json.addProperty("uploaded", 1); //0:error, 1:success
				json.addProperty("fileName", fileSystemName);
				json.addProperty("url", fileUrl);
				
				response.setContentType("application/json;charset=UTF-8");
				response.getWriter().print(json);
						}
	//	doGet(request, response);
	}

}
