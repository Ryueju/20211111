package com.yedam.member;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@WebServlet("/FileUploadServlet")
public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
    public FileUploadServlet() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
	}

	//파일업로드 위해 cos.jar 라이브러리 다운
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		response.setContentType("text/html;charset=UTF-8");
		//서블릿이 웹페이지 실행이니까. 웹페이지 실행시 셋팅값 지정한 것.
		
		System.out.println(request.getParameter("author"));
		
		int maxSize = 1024*1024*10; //=10메가바이트, 파일크기제한설정
		ServletContext context = getServletContext(); 
		String saveDir = context.getRealPath("image"); //파일이 저장되는 실제경로
		
		MultipartRequest multi = //MultipartRequest객체를 생성하면 파일 업로드 수행함
				new MultipartRequest(request,saveDir,maxSize,"UTF-8",new DefaultFileRenamePolicy());
		
		String prod_name = multi.getParameter("prod_item");
		String prod_desc = multi.getParameter("prod_desc");
		String like_it = multi.getParameter("like_it");
		String origin_price = multi.getParameter("origin_price");
		String sale_price = multi.getParameter("sale_price");
		String prod_image = multi.getFilesystemName("prod_image");
		
		ItemVO vo = new ItemVO();
		vo.setProdName(prod_name);
		vo.setProdDesc(prod_desc);
		vo.setLikeIt(Double.parseDouble(like_it));
		vo.setOriginPrice(Integer.parseInt(origin_price));
		vo.setSalePrice(Integer.parseInt(sale_price));
		vo.setProdImage(prod_image);
		
		System.out.println("실제위치: "+saveDir);
		
		PrintWriter out = response.getWriter();
		
		
		//파일:서버, 사용자입력값:db저장.
		MemDAO dao = new MemDAO();
		dao.uploadProduct(vo);
		
		Gson gson = new GsonBuilder().create();
		out.println(gson.toJson(vo));		
		//out.println("<script>location.href=\"shop-homepage/sample.html\"</script>");
	}

}
