package com.yedam.member;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebServlet("/GetMemberByDeptServ")
public class GetMemberByDeptServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public GetMemberByDeptServ() {
        super();
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		response.setContentType("text/json;charset=UTF-8");
		MemDAO dao = new MemDAO();
		Map<String,Integer> map = dao.getMemberByDept();
		Gson gson = new GsonBuilder().create();
		response.getWriter().println(gson.toJson(map));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
	}

}
