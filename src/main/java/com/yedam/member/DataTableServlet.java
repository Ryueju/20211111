package com.yedam.member;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


@WebServlet("/DataTableServlet")
public class DataTableServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    public DataTableServlet() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		response.setContentType("text/json;charset=UTF-8");
	MemDAO dao = new MemDAO();
	List<DataTable> list = dao.getDataTables();
	JsonArray outer = new JsonArray();
	for(DataTable dt : list) {
		JsonArray inner = new JsonArray();
		//dt에들어있는값을 루핑돌면서 하나씩 담아보자
		inner.add(dt.getName()); //name
		inner.add(dt.getPosition());
		inner.add(dt.getOffice());
		inner.add(dt.getExtn());
		inner.add(dt.getStartDate());
		inner.add(dt.getSalary());
		 //내부의 배열에 add를 사용해 담아준 것 내부의 배열이 outer로 추가되면 됨
		outer.add(inner);
		
	}
//	JsonObject obj = new JsonObject();
//	obj.addProperty("draw", 1);
//	obj.addProperty("recordsTotal", 36);
//	//[[],[],[],[]]
	//{"draw":1, "recordsTotal":36, "recordsFiltered":36, "data":[[],[],[],[]]}
	//json타입으로 리턴해줘야함
	Gson gson = new GsonBuilder().create();
	response.getWriter().print(gson.toJson(outer));
	}
	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
