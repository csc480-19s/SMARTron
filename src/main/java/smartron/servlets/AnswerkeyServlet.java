package smartron.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Database.AnswerKeyDao;
import smartron.entities.Answerkey;

public class AnswerkeyServlet extends HttpServlet {
	private Gson gson = new Gson();
	private static HashMap<String, String> optionAnswerKey = new HashMap<>();
	AnswerKeyDao akDao = new AnswerKeyDao();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String examId = request.getParameter("examId");
		String instId = request.getParameter("instId");

		List<String> obj = null;
		List<Answerkey> keyList = new ArrayList<>();
		Answerkey anserKey;
		try {
			int idCounter = 1;

			obj = akDao.selectUpdatedAnswerKey(examId, instId);
			if (obj == null || obj.isEmpty()) {
				obj = akDao.selectAnswerKey(examId, instId);
			}
			String cleanString = obj.toString();
			cleanString = cleanString.replaceAll("\\[", "");
			cleanString = cleanString.replaceAll("\\]", "");
			String[] answerKeyStr = cleanString.split(",");
			buildKeys();
			for (String key : answerKeyStr) {
				System.out.println(key);
				anserKey = new Answerkey();
				anserKey.setQuestionId(idCounter);
				String[] keys = { optionAnswerKey.get(key.trim()) };
				anserKey.setAnswerKey(keys);
				keyList.add(anserKey);
				idCounter++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		String questionJsonString = this.gson.toJson(keyList);

		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(questionJsonString);
		out.flush();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String examId = request.getParameter("examId");
		System.out.println(examId);
		BufferedReader bufferAnswerkey = request.getReader();
		String jsonString = null;
		String answerKey = "";
		while ((jsonString = bufferAnswerkey.readLine()) != null) {
			answerKey += jsonString;
		}
		System.out.println(answerKey);
		List<Answerkey> k = gson.fromJson(answerKey, new TypeToken<List<Answerkey>>() {
		}.getType());
		try {
			buildKeys();
			StringBuffer updatedAnswerKey = new StringBuffer("[");
			for (Answerkey akey : k) {
				for (String key : akey.getAnswerKey()) {
					System.out.println(key);
					String keyCode = getKey(optionAnswerKey, key);
					updatedAnswerKey.append(keyCode);
					updatedAnswerKey.append(",");
				}
			}
			updatedAnswerKey.deleteCharAt(updatedAnswerKey.length() - 1);
			updatedAnswerKey.append("]");

			akDao.addUpdatedAnswerKey(examId, updatedAnswerKey.toString());
			System.out.println(updatedAnswerKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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

	public String getKey(Map<String, String> keyList, Object val) {
		for (Object key : keyList.keySet()) {
			if (keyList.get(key).equals(val)) {
				return key.toString();
			}
		}
		return null;
	}
}