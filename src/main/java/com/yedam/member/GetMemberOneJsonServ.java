package com.yedam.member;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


@WebServlet("/GetMemberOneJsonServ")
public class GetMemberOneJsonServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
        
   
    public GetMemberOneJsonServ() {
        super();
    }
    
    //한건 조회해서 #content에 출력하려고
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		response.setContentType("text/json;charset=UTF-8");
		String id = request.getParameter("id");
		MemDAO dao = new MemDAO();
		MemberVO vo = dao.getMember(id);
		Gson gson = new GsonBuilder().create();
		response.getWriter().println(gson.toJson(vo));
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		doGet(request, response); //doGet방식으로 처리하고 싶으면 doPost에도 이렇게 doGet방식으로 실행하라는 실행문넣어주면 됨.
	}

}
