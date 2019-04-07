package com.smartron.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.smartron.entity.AnswerKey;

import SMARTron.Database.AnswerKeyDao;

@Path("/")
public class AnswerKeyService {
	
	private static HashMap<String, String> optionAnswerKey = new HashMap<>();
	AnswerKeyDao akDao = new AnswerKeyDao();
	
	public static void buildKeys() {
		optionAnswerKey.put("-1", "-1");
		optionAnswerKey.put("0", "A");
		optionAnswerKey.put("1", "B");
		optionAnswerKey.put("2", "C");
		optionAnswerKey.put("3", "D");
		optionAnswerKey.put("4", "E");
		optionAnswerKey.put("error", "error");
		// remove after grader fix
		optionAnswerKey.put("null", "null");
	}
	
	@GET
	@Path("answerkey")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getAnswerKeys() {
		List<String> obj = null;
		List<AnswerKey> keyList = new ArrayList<>();
		AnswerKey anserKey;
		try {
			int idCounter = 1;
			obj = akDao.selectUpdatedAnswerKey("asdfg", "123456789");
			if(obj.isEmpty()) {
				obj = akDao.selectAnswerKey("asdfg", "123456789");				
			}
			String[] answerKeyStr = obj.toString().split(",");
			buildKeys();
			for(String key: answerKeyStr) {
				System.out.println(key);
				anserKey = new AnswerKey();
				anserKey.setQuestionId(idCounter);
				String[] keys = {optionAnswerKey.get(key.trim())};
				anserKey.setAnswerKey(keys);
				keyList.add(anserKey);
				idCounter++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return Response
	      .status(200)
	      .entity(keyList)
	      .build();
	}
	
	@POST
	@Path("updateAnswerKey")
	@Consumes({MediaType.APPLICATION_JSON})
	public void updateAnswers(List<AnswerKey> k) {
		try {
		buildKeys();
		StringBuffer updatedAnswerKey = new StringBuffer("[");
		for(AnswerKey akey: k) {
			for(String key : akey.getAnswerKey()) {
				String keyCode = getKey(optionAnswerKey,key);
				updatedAnswerKey.append(keyCode);
				updatedAnswerKey.append(",");
			}
		}
		updatedAnswerKey.deleteCharAt(updatedAnswerKey.length()-1);
		updatedAnswerKey.append("]");

		akDao.addUpdatedAnswerKey("asdfg",updatedAnswerKey.toString());
		System.out.println(updatedAnswerKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getKey(Map<String, String> keyList, Object val) {
		for(Object key: keyList.keySet()) {
			if(keyList.get(key).equals(val)){
				return key.toString();
			}
		}
		return null;
	}
}