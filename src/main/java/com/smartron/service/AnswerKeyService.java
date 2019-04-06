package com.smartron.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import SMARTron.Database.AnswerKeyDao;
import entity.AnswerKey;

@Path("/")
public class AnswerKeyService {
	private static String[] optionAnswerKey = {"-1","0","1","2","3","4","error","null"};
	AnswerKeyDao akDao = new AnswerKeyDao();
	
	@Path("{example}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String example(@PathParam("example") String example) {
		return example;
	}
	
	
	@GET
	@Path("answerkey")
	@Produces({MediaType.APPLICATION_JSON})
	public Response index() {
		Object obj = null;
		List<AnswerKey> keyList = new ArrayList<>();
		AnswerKey anserKey;
		try {
			int idCounter = 1;
			obj = akDao.selectAnswerKey("asdfg", "123456789");
			String[] answerKeyStr = obj.toString().split(",");
			System.out.println(answerKeyStr);
			for(String key: answerKeyStr) {
				anserKey = new AnswerKey();
				anserKey.setQuestionId(idCounter);
				String[] keys = {convertCodeToAnsewekey(key.trim())};
				anserKey.setAnswerKey(keys);
				keyList.add(anserKey);
				idCounter++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(AnswerKey k: keyList) {
			System.out.println("questionId: "+ k.getQuestionId());
			for(String str : k.getAnswerKey()) {
				System.out.println(str);
			}
		}
		//header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
	    return Response
	      .status(200)
	      .header("Access-Control-Allow-Origin", "*")
	      .header("Access-Control-Allow-Credentials", "true")
	      .header("Access-Control-Allow-Headers",
	        "origin, content-type, accept, authorization")
	      .header("Access-Control-Allow-Methods", 
	        "GET")
	      .entity(keyList)
	      .build();
	}

	// for test only
	public String convertCodeToAnsewekey(String key) {
		String answerKey;
		if(key.equals(optionAnswerKey[0])|| key.equals(optionAnswerKey[7])) {
			answerKey = "Err";
		}else if(key.equals(optionAnswerKey[1])) {
			answerKey = "A";

		}else if(key.equals(optionAnswerKey[2])) {
			answerKey = "B";

		}else if(key.equals(optionAnswerKey[3])) {
			answerKey = "C";

		}else if (key.equals(optionAnswerKey[4])) {
			answerKey = "D";
		}else if (key.equals(optionAnswerKey[5])) {
			answerKey = "E";
		}else {
			answerKey = "Err";
		}
		return answerKey;
	}
}