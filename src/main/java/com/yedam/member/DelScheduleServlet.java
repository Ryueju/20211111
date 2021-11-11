package com.yedam.member;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DelScheduleServlet")
public class DelScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public DelScheduleServlet() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json;charset=UTF-8");
		
		String title = request.getParameter("title");
		
		MemDAO dao = new MemDAO();
		boolean delOK = dao.delSchedule(title);
		if(delOK) {
			response.getWriter().print("{\"retCode\":\"OK\"}");
		}else {
			response.getWriter().print("{\"retCode\":\"NG\"}");
		}
		
		
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
	
	}

}
