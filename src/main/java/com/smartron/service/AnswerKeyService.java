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
	
	@GET
	@Path("bystudent")
	@Produces({MediaType.APPLICATION_JSON})
	public Response byStudent() {
	    return Response
	      .status(200)
	      .entity("{\n" + 
	      		"	\"students\":[\n" + 
	      		"		{\"name\":\"Sushmita Banerjee\", \"grade\":\"A\",\"points\":\"99\",\"percent\":\"99\"},\n" + 
	      		"		{\"name\":\"Nicholas Esposito\", \"grade\":\"A\",\"points\":\"99\",\"percent\":\"99\"},\n" + 
	      		"		{\"name\":\"Robert Sgroi\", \"grade\":\"A\",\"points\":\"99\",\"percent\":\"99\"}		\n" + 
	      		"	]\n" + 
	      		"}\n" + 
	      		"")
	      .build();
	}

	@GET
	@Path("byquestion")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getResults() {
	    return Response
	      .status(200)
	      .entity("{\n" + 
	      		"	\"examName\": \"Exam 1\",\n" + 
	      		"	\"examID\": \"SDDSE\",\n" + 
	      		"	\"questionlist\": [\n" + 
	      		"		{\n" + 
	      		"			\"questionNumber\": 1,\n" + 
	      		"			\"correct\": \"B\",\n" + 
	      		"			\"data\": [\n" + 
	      		"				{\n" + 
	      		"					\"name\": \"A\",\n" + 
	      		"					\"value\": 10\n" + 
	      		"				},\n" + 
	      		"				{\n" + 
	      		"					\"name\": \"B\",\n" + 
	      		"					\"value\": 20\n" + 
	      		"				},\n" + 
	      		"				{\n" + 
	      		"					\"name\": \"C\",\n" + 
	      		"					\"value\": 5\n" + 
	      		"				},\n" + 
	      		"				{\n" + 
	      		"					\"name\": \"D\",\n" + 
	      		"					\"value\": 1\n" + 
	      		"				},\n" + 
	      		"				{\n" + 
	      		"					\"name\": \"E\",\n" + 
	      		"					\"value\": 1\n" + 
	      		"				}\n" + 
	      		"			]\n" + 
	      		"		}\n" + 
	      		"		\n" + 
	      		"	]\n" + 
	      		"}")
	      .build();
	}
	
	@GET
	@Path("examlist")
	@Produces({MediaType.APPLICATION_JSON})
	public Response examList() {
	    return Response
	      .status(200)
	      .entity("{\n" + 
	      		"	\"name\":\"Bastian Tenbergen\",\n" + 
	      		"	\"lakerNetID\":\"bastian.tenbergen@oswego.edu\",\n" + 
	      		"	\"examList\":[\n" + 
	      		"		{\n" + 
	      		"			\"examName\":\"CSC 212 Fall 2018 Exam 1\",\n" + 
	      		"			\"scanCode\":\"XHKLF\",\n" + 
	      		"			\"timeStamp\":\"\"\n" + 
	      		"		},\n" + 
	      		"		{\n" + 
	      		"			\"examName\":\"CSC212 Spring 19 Exam 2\",\n" + 
	      		"			\"scanCode\":\"BSDFS\",\n" + 
	      		"			\"timeStamp\":\"\"\n" + 
	      		"		}\n" + 
	      		"	]\n" + 
	      		"\n" + 
	      		"}\n" + 
	      		"")
	      .build();
	}
	@GET
	@Path("statistics")
	@Produces({MediaType.APPLICATION_JSON})
	public Response statistics() {
	    return Response
	      .status(200)
	      .entity("{\n" + 
	      		"	\"examName\":\"Exam 1\",\n" + 
	      		"	\"examID\":\"XHKLF\",\n" + 
	      		"	\"mean\":\"75.9\",\n" + 
	      		"	\"median\":\"80\",\n" + 
	      		"	\"max\":\"100\",\n" + 
	      		"	\"min\":\"0\",\n" + 
	      		"	\"range\":\"100\",\n" + 
	      		"	\"standardDeviation\":\"3.7\",\n" + 
	      		"	\"variance\":\"10.6\",\n" + 
	      		"	\"kr20\":\".74\",\n" + 
	      		"	\"kr21\":\".84\",\n" + 
	      		"	\"cronbach\":\".57\",\n" + 
	      		"\n" + 
	      		"	\"gradeDistribution\":[\n" + 
	      		"		{\"grade\":\"A\",\"percent\":\"10\"},\n" + 
	      		"		{\"grade\":\"B\", \"percent\":\"30\"},\n" + 
	      		"		{\"grade\":\"C\",\"percent\":\"30\"},\n" + 
	      		"        {\"grade\":\"D\", \"percent\":\"20\"},\n" + 
	      		"		{\"grade\":\"E\", \"percent\":\"10\"}\n" + 
	      		"	]\n" + 
	      		"}")
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