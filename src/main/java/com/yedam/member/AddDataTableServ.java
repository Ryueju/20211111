package com.yedam.member;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;


@WebServlet("/AddDataTableServ")
public class AddDataTableServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public AddDataTableServ() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String json = request.getReader().lines().collect(Collectors.joining());
		System.out.println(json);
		
		Gson gson = new Gson();
		DataTable dt = gson.fromJson(json, DataTable.class);
		
		MemDAO dao = new MemDAO();
		dao.insertDataTable(dt);
		
		response.getWriter().print("insert");
	}

}
