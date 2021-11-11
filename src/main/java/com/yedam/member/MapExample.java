package com.yedam.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MapExample {
	public static void main(String[] args) {
		Map<String,Integer> map = new HashMap<String, Integer>();
		map.put("위정아", 80);
		map.put("이소정", 81);
		map.put("이정은", 82);
		
		Map<String,Integer> map2 = new HashMap<String, Integer>();
		map2.put("위정아", 82); //키 값이 같으면 새로운 값을 기존값에 덮어씀.
		map2.put("이소정", 83); //그래서 키값이 같다면 이렇게 map2라고 map을 새로 생성하는 것도 방법.
		map2.put("이정은", 84);
		
		List<Map<String,Integer>> list = new ArrayList<Map<String,Integer>>();
		list.add(map); //그런후 이렇게 map을 list로 감싸면,,
		list.add(map2);
		
		Gson gson = new GsonBuilder().create();
		System.out.println(gson.toJson(list)); //이렇게 출력되어 볼 수 있음
	}
}
