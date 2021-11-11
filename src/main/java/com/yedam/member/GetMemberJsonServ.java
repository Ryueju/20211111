package com.yedam.member;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@WebServlet("/GetMemberJsonServ")
public class GetMemberJsonServ extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GetMemberJsonServ() {
		super();
	}

	//전체리스트 불러와서 화면에 출력
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		// {"name":"Hongkildong", "age":20, "phone":"010-1234-5678"}
		// "{"name":"hong"}";
		// out.println("{\"name\":\"Hongkildong\", \"age\":20,
		// \"phone\":\"010-1234-5678\"}");
		// -> 문자열안에서 ""를 문자열로 인식시키기 위해 \를 사용한 것

		// "[{\"id\":\"?\", \"name\":\"?\", birth:?, gender:?},{},{}]";
		MemDAO dao = new MemDAO();
		List<MemberVO> list = dao.getMemberList();
		Gson gson = new GsonBuilder().create(); // 밑에처럼 노가다로 적지말고 라이브러리 활용해도 ㄱㅊ
		out.println(gson.toJson(list));
//		int cnt = list.size();
//		out.println("["); //배열기호
//		for(int i=0; i<cnt; i++) {
//			out.println("{\"id\":\""+list.get(i).getUserId()
//					+"\", \"name\":\""+list.get(i).getUserName()
//					+"\", \"birth\":\""+list.get(i).getBirthDate()//.substring(0,10)
//					+"\", \"gender\":\""+list.get(i).getGender()
//					+"\"}");
//			if(i != cnt-1) { //마지막데이터 제외하고는 데이터한줄 출력하고 ,를 찍어주겠다. 마지막데이터에는 ,안붙여주겠다.
//				out.println(",");
//			}
//		}
//		out.println("]");
	}

	//insert - memberJson.html에서 값을 받아오면 여기서 처리함.
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//한글 안깨지도록 처리
		request.setCharacterEncoding("UTF-8"); // 사용자 입력값에 한글이 포함되어있으면 
		response.setCharacterEncoding("UTF-8"); //DB에서 가져오는 값에 한글이 포함되어있으면
		response.setContentType("text/json;charset=UTF-8"); //json에서 가져오는 값에 한글이 포함되어있으면
		
		// 입력하는 기능.
		String userId = request.getParameter("u_i");
		String userName = request.getParameter("u_n");
		String address = request.getParameter("u_a");
		String phone = request.getParameter("u_p");
		String birth = request.getParameter("u_b");
		String gender = request.getParameter("u_g");

		MemberVO vo = new MemberVO();
		vo.setUserId(userId);
		vo.setUserName(userName);
		vo.setAddress(address);
		vo.setPhone(phone);
		vo.setBirthDate(birth);
		vo.setGender(gender);
		
		//입력성공 -> OK,입력한값출력 | 입력실패 -> 오류메시지창화면에띄우기
		//{"retCode":"OK","retVal":{vo}}	//vo객체 자체를 이용해서 입력성공,실패시 결과값 주려면 이런식으로-!
		//{"retCode":"NG","retVal":"담당자에게 문의!"}
		MemDAO dao = new MemDAO();
		JsonObject jsonObj = new JsonObject();	//json 데이터 만들기.
		Gson gson = new GsonBuilder().create(); //json 반환.javaObject->json문자열로변환/json문자열->javaObject로변환 두가지모두가능	
		if(dao.insertMember(vo)) {
			//response.getWriter().print(gson.toJson(jsonObj)); // 처리결과?		
			jsonObj.addProperty("retCode", "OK"); // 성공했을 경우, returnCode에는 ok (넘겨줄 코드와 값)
			jsonObj.addProperty("userId", vo.getUserId());
			jsonObj.addProperty("userName", vo.getUserName());
			jsonObj.addProperty("birthDate", vo.getBirthDate());
			jsonObj.addProperty("address", vo.getAddress());
			jsonObj.addProperty("phone", vo.getPhone());
			jsonObj.addProperty("gender", vo.getGender()); //성공했을땐 vo에 저장된 값들을 이렇게 다 넘김
			
		}else {
			//response.getWriter().print("{\"retCode\":\"NG\",\"retVal\":\"담당자에게 문의!\"}"); //이렇게쓰면 JSON타입
			jsonObj.addProperty("retCode","NG");
			jsonObj.addProperty("retMsg", "[오류발생]\n 담당자(000-111)에게 문의");
		}
		response.getWriter().println(gson.toJson(jsonObj));
	}

}
