package com.yedam.member;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebServlet("/GetScheduleServlet")
public class GetScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public GetScheduleServlet() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json;charset=UTF-8");
		MemDAO dao = new MemDAO();
		List<Map<String,String>> list = dao.getSchedules();
		
		Gson gson = new GsonBuilder().create();
		response.getWriter().println(gson.toJson(list));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); //요청정보에 한글이 포함되어 있으면..
		response.setContentType("text/json;charset=UTF-8");
		
		String title = request.getParameter("title"); //title파라미터값(title)을 title에 담기
		String start = request.getParameter("start"); //start파라미터값(arg.startStr)을 start에 담기
		String end = request.getParameter("end"); //end파라미터값(arg.endStr)을 end에 담기
		
		MemDAO dao = new MemDAO();
		boolean insertOK = dao.addSchedule(title, start, end);
		if(insertOK) { 
			//값이 정상적으로 들어갔으면 {"retCode":"OK"}
			response.getWriter().print("{\"retCode\":\"OK\"}");
		}else { 
			//그렇지 않으면 {"retCode":"NG"}
			response.getWriter().print("{\"retCode\":\"NG\"}");
		}
	}

}
