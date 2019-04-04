package com.smartron.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import SMARTron.Database.AnswerKeyDao;

@Path("/")
public class AnswerKeyService {
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
		try {
			obj = akDao.selectAnswerKey("asdfg", "123456789");
			System.out.println(obj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return Response
	      .status(200)
	      .header("Access-Control-Allow-Origin", "*")
	      .header("Access-Control-Allow-Credentials", "true")
	      .header("Access-Control-Allow-Headers",
	        "origin, content-type, accept, authorization")
	      .header("Access-Control-Allow-Methods", 
	        "GET, POST, PUT, DELETE, OPTIONS, HEAD")
	      .entity("[{\n" + 
					"  \"questionId\": 1,\n" + 
					"  \"answerKey\": [\n" + 
					"    \"C\"\n" + 
					"  ]\n" + 
					"}, {\n" + 
					"  \"questionId\": 2,\n" + 
					"  \"answerKey\": [\n" + 
					"    \"C\"\n" + 
					"  ]\n" + 
					"}, {\n" + 
					"  \"questionId\": 3,\n" + 
					"  \"answerKey\": [\n" + 
					"    \"C\"\n" + 
					"  ]\n" + 
					"}, {\n" + 
					"  \"questionId\": 4,\n" + 
					"  \"answerKey\": [\n" + 
					"    \"C\"\n" + 
					"  ]\n" + 
					"}, {\n" + 
					"  \"questionId\": 5,\n" + 
					"  \"answerKey\": [\n" + 
					"    \"C\"\n" + 
					"  ]\n" + 
					"}, {\n" + 
					"  \"questionId\": 6,\n" + 
					"  \"answerKey\": [\n" + 
					"    \"C\"\n" + 
					"  ]\n" + 
					"}, {\n" + 
					"  \"questionId\": 7,\n" + 
					"  \"answerKey\": [\n" + 
					"    \"C\"\n" + 
					"  ]\n" + 
					"}, {\n" + 
					"  \"questionId\": 8,\n" + 
					"  \"answerKey\": [\n" + 
					"    \"C\"\n" + 
					"  ]\n" + 
					"}, {\n" + 
					"  \"questionId\": 9,\n" + 
					"  \"answerKey\": [\n" + 
					"    \"C\"\n" + 
					"  ]\n" + 
					"}, {\n" + 
					"  \"questionId\": 10,\n" + 
					"  \"answerKey\": [\n" + 
					"    \"C\"\n" + 
					"  ]\n" + 
					"}, {\n" + 
					"  \"questionId\": 11,\n" + 
					"  \"answerKey\": [\n" + 
					"    \"C\"\n" + 
					"  ]\n" + 
					"}, {\n" + 
					"  \"questionId\": 12,\n" + 
					"  \"answerKey\": [\n" + 
					"    \"C\"\n" + 
					"  ]\n" + 
					"}, {\n" + 
					"  \"questionId\": 13,\n" + 
					"  \"answerKey\": [\n" + 
					"    \"D\",\n" + 
					"    \"E\"\n" + 
					"  ]\n" + 
					"}, {\n" + 
					"  \"questionId\": 14,\n" + 
					"  \"answerKey\": [\n" + 
					"    \"C\"\n" + 
					"  ]\n" + 
					"}, {\n" + 
					"  \"questionId\": 15,\n" + 
					"  \"answerKey\": [\n" + 
					"    \"C\"\n" + 
					"  ]\n" + 
					"}, {\n" + 
					"  \"questionId\": 16,\n" + 
					"  \"answerKey\": [\n" + 
					"    \"C\"\n" + 
					"  ]\n" + 
					"}, {\n" + 
					"  \"questionId\": 17,\n" + 
					"  \"answerKey\": [\n" + 
					"    \"C\"\n" + 
					"  ]\n" + 
					"}, {\n" + 
					"  \"questionId\": 18,\n" + 
					"  \"answerKey\": [\n" + 
					"    \"D\",\n" + 
					"    \"E\"\n" + 
					"  ]\n" + 
					"}, {\n" + 
					"  \"questionId\": 19,\n" + 
					"  \"answerKey\": [\n" + 
					"    \"C\"\n" + 
					"  ]\n" + 
					"}]\n" + 
					"")
	      .build();
	}
	

}