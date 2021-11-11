package com.yedam.member;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemDAO extends DAO {
	//datatable 리스트
	public List<DataTable> getDataTables(){
		connect();
		String sql = "select * from data_table";
		List<DataTable> list = new ArrayList<DataTable>();
		try {
			psmt = conn.prepareStatement(sql);
			rs=psmt.executeQuery();
			while(rs.next()) {
				DataTable dt = new DataTable();
				dt.setName(rs.getString("first_name"));
				dt.setPosition(rs.getString("last_name"));
				dt.setOffice(rs.getString("position"));
				dt.setExtn(rs.getString("office"));
				dt.setStartDate(rs.getString("start_date"));
				dt.setSalary(rs.getString("salary"));
				
				list.add(dt);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}
	
	
	//dataTable 업로드
	public void insertDataTable(DataTable dt) {
		connect();
		String sql = "insert into data_table values(?,?,?,?,?,?)";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, dt.getName());
			psmt.setString(2, dt.getPosition());
			psmt.setString(3, dt.getOffice());
			psmt.setString(4, dt.getExtn());
			psmt.setString(5, dt.getStartDate());
			psmt.setString(6, dt.getSalary());
			int r = psmt.executeUpdate();
			System.out.println(r + " insert.");
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			disconnect();
		}
	//	return ;
		
	}
	
	//상품조회 (상품아이디)
	public ItemVO searchItem(String id) {
		connect();
		String sql = "select * from item where prod_id=?";
		ItemVO vo = null; //초기화 꼭 해줘야 함
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1,Integer.parseInt(id));
			
			rs = psmt.executeQuery(); //쿼리실행
			
			if(rs.next()) {
				vo = new ItemVO();
				vo.setLikeIt(rs.getDouble("like_it"));
				vo.setOriginPrice(rs.getInt("origin_price"));
				vo.setProdDesc(rs.getString("prod_desc"));
				vo.setProdId(rs.getInt("prod_id"));
				vo.setProdImage(rs.getString("prod_image"));
				vo.setProdName(rs.getString("prod_name"));
				vo.setSalePrice(rs.getInt("sale_price"));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return vo; //조회했는데 값이 없는경우 null이넘어가게 됨
		
	}
	
	
	//파일업로드 - 상품업로드
	public ItemVO uploadProduct(ItemVO vo) {
		connect();
		String sql = "insert into item(prod_id,prod_item,prod_desc,like_it,origin_price,sale_price,prod_image)"
				+ "values(?,?,?,?,?,?,?)";
				
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select max(prod_id)+1 from item"); //아이디값을 rs에다 담음
			//prod_id컬럼에서 가장 큰 값에다 +1이면 그다음 순번을 의미
			//그래서 prod_id는 pk이므로 중복값없이 순차적으로 값 넣을 수 있게됨
			int nextId = -1;
			if(rs.next()) { //rs에 값이 담겼다면
				nextId = rs.getInt(1); //첫번째 칼럼 값을 가져오겠다(max(prod_id)+1)
				vo.setProdId(nextId); //매개값의 참조변수의 값을 변경.
			}
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, nextId);
			psmt.setString(2, vo.getProdName());
			psmt.setString(3, vo.getProdDesc());
			psmt.setDouble(4, vo.getLikeIt());
			psmt.setInt(5, vo.getOriginPrice());
			psmt.setInt(6, vo.getSalePrice());
			psmt.setString(7, vo.getProdImage());
			int r = psmt.executeUpdate(); //실제 쿼리 실행.
			System.out.println(r+"건 입력"); //처리 후 메시지 출력.
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return vo;
		
		
	}
	
	//부트스트랩-상품리스트
	public List<ItemVO> getItemList() {
		List<ItemVO> items = new ArrayList<ItemVO>();
		connect();
		String sql="select * from item order by 1";
		try {
			stmt = conn.createStatement();
			rs=stmt.executeQuery(sql);
			while(rs.next()) {
				ItemVO vo = new ItemVO();
				vo.setProdId(rs.getInt("prod_id"));
				vo.setProdName(rs.getString("prod_item"));
				vo.setProdDesc(rs.getString("prod_desc"));
				vo.setLikeIt(rs.getDouble("like_it"));
				vo.setOriginPrice(rs.getInt("origin_price"));
				vo.setSalePrice(rs.getInt("sale_price"));
				vo.setProdImage(rs.getString("prod_image"));
				items.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return items;
	}
	
	//구글 fullCalender용
	//스케줄 삭제 메소드(title)
	public boolean delSchedule(String title) {
		connect();
		String sql = "delete from schedule where title = ?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, title);
			int r = psmt.executeUpdate();
			if(r>0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return false;
	}
	
	//구글 fullCalender용
	//스케줄 등록 메소드(title,start,end);
	public boolean addSchedule(String title, String start, String end) {
		connect();
		String sql = "insert into schedule values(?,?,?)";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, title);
			psmt.setString(2, start);
			psmt.setString(3, end);
			int r = psmt.executeUpdate();
			if(r>0) { //정상적으로 등록이 되었으면 1이상임.
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect(); //성공하건 실패하건 우선 연결끊는건 필수
		}
		return false; //실패했으면 false를 리턴
	}
	
	
	//구글 fullCalender용 샘플 데이터
	//List<Map<String, String>> -> List<map<title,?> , map<start,?> , map<end,?>>
	public List<Map<String,String>> getSchedules(){
		List<Map<String,String>> schedules = new ArrayList<Map<String,String>>();
		connect();
		String sql="select * from schedule";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				Map<String,String> map = new HashMap<String, String>();
				map.put("title", rs.getString("title")); //title이라는 key는 rs가 가지고 있는 title이라는 컬럼 값을 담음
				map.put("start", rs.getString("start_date"));
				map.put("end", rs.getString("end_date"));
				schedules.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return schedules;
	}
	
	//구글차트API수업
	//차트수업-부서별 인원
	public Map<String,Integer> getMemberByDept(){ //String부서이름,Integer부서별인원
		connect();
		String sql="select 'Administration', 1 from dual\r\n"
				+ "union all\r\n"
				+ "select 'Accounting', 2 from dual\r\n"
				+ "union all\r\n"
				+ "select 'IT', 3 from dual\r\n"
				+ "union all\r\n"
				+ "select 'Executive', 3 from dual\r\n"
				+ "union all\r\n"
				+ "select 'Shipping', 5 from dual\r\n"
				+ "union all\r\n"
				+ "select 'Sales', 3 from dual\r\n"
				+ "union all\r\n"
				+ "select 'Marketing', 2 from dual";
		Map<String,Integer> map = new HashMap<String, Integer>();
		try {
			stmt = conn.createStatement();
			rs =stmt.executeQuery(sql);
			while(rs.next()) {
				map.put(rs.getString(1), rs.getInt(2)); //각각 첫번째,두번째 컬럼 가져오겠다.
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return map;
	}
	
	//수정기능.
	public Map<String, Object> updateMember(MemberVO vo) {
		//성공시 -> retCode:OK, retVal:vo값 전달
		//실패시 -> retCode:NG, retVal:errMsg
		String sql = "update member";
		sql += "      set    user_name = ?";
		sql += "            ,birth_date = ?";
		sql += "            ,gender = ?";
		sql += "            ,address = ?";
		sql += "            ,phone = ?";
		sql += "        where user_id=?";
		Map<String, Object> map = new HashMap<String, Object>();
		//우선 디폴트로 리턴코드는 ng, 리턴값은 에러!
		map.put("retCode", "NG"); //object는 최상위클래스이니까 string이든 int든 모두 가능
		map.put("retVal", "Error!"); //리턴값에는 에러
		connect();
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, vo.getUserName());
			psmt.setString(2, vo.getBirthDate());
			psmt.setString(3, vo.getGender());
			psmt.setString(4, vo.getAddress());
			psmt.setString(5, vo.getPhone());
			psmt.setString(6, vo.getUserId());
			int r = psmt.executeUpdate();
			System.out.println(r+"건 수정");
			if(r>0) { //수정이 완료되었으면 완료된 건수만큼 숫자리턴. 0이하일경우는 수정실패란 의미.
				//수정이 성공했으면 map의 디폴트값을 수정
				//리턴코드는 ok로, 리턴값은 vo객체!
				map.put("retCode", "OK");
				map.put("retVal", vo);
				return map;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			map.put("retVal", e.getMessage());
			return map;
			
		}finally {
			disconnect();
		}
		return map;
	}
	
	//한건 삭제.
	public boolean deleteMember(String id) {
		String sql = "delete from member where user_id=?";
		connect();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			int r = psmt.executeUpdate();
			System.out.println(r+"건 삭제.");
			if(r>0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	//한건 조회.
	public MemberVO getMember(String id) {
		String sql = "select * from member where user_id = ?";
		connect();
		MemberVO vo = null;
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			rs = psmt.executeQuery();
			if(rs.next()) { //값을 가져왔는지
				vo = new MemberVO();
				vo.setAddress(rs.getString("address"));
				vo.setBirthDate(rs.getString("birth_date"));
				vo.setGender(rs.getString("gender"));
				vo.setPhone(rs.getString("phone"));
				vo.setUserId(rs.getString("user_id"));
				vo.setUserName(rs.getString("user_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}finally {
			disconnect();
		}
		return vo;
	}
	
	//한건 입력.
	public boolean insertMember(MemberVO vo) {
		String sql = "insert into member(user_id,user_name,address,phone,birth_date,gender) values(?,?,?,?,?,?)";
		connect();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, vo.getUserId());
			psmt.setString(2, vo.getUserName());
			psmt.setString(3, vo.getAddress());
			psmt.setString(4, vo.getPhone());
			psmt.setString(5, vo.getBirthDate());
			psmt.setString(6, vo.getGender());
			
			int r = psmt.executeUpdate();
			System.out.println(r+"건 입력됨");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally {
			disconnect();
		}
		return true;
	}
	
	//전체 리스트 가져오기.
	public List<MemberVO> getMemberList(){
		String sql = "select * from member order by 1";
		connect();
		List<MemberVO> memberList = new ArrayList<>();
		try {
			stmt = conn.createStatement(); //Statement stmt = new Statement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				MemberVO vo = new MemberVO();
				vo.setAddress(rs.getString("address"));
				vo.setBirthDate(rs.getString("birth_date"));
				vo.setGender(rs.getString("gender"));
				vo.setPhone(rs.getString("phone"));
				vo.setUserId(rs.getString("user_id"));
				vo.setUserName(rs.getString("user_name"));
				memberList.add(vo);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			disconnect();
		}
		return memberList;
	}
}
