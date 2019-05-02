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
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Gson gson = null;
	private static HashMap<String, String> optionAnswerKey = new HashMap<>();
	AnswerKeyDao akDao = new AnswerKeyDao();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String examId = request.getParameter("examId");
        String instId = request.getParameter("instId").split("@")[0];
		List<String> obj = null;
		List<Answerkey> keyList = null;
		Answerkey anserKey = null;
		String questionJsonString = null;
		try {
			int idCounter = 1;

			obj = akDao.selectUpdatedAnswerKey(examId, instId);
			if (obj == null || obj.isEmpty()) {
				obj = akDao.selectAnswerKey(examId, instId);
			}
			if(!obj.isEmpty()) {
				String cleanString = obj.toString();
				cleanString = cleanString.replaceAll("\\[", "");
				cleanString = cleanString.replaceAll("\\]", "");
				String[] answerKeyStr = cleanString.split(",");
				buildKeys();
				keyList = new ArrayList<Answerkey>();
				for (String key : answerKeyStr) {
					key = key.trim();
					System.out.println(key);
					anserKey = new Answerkey();
					anserKey.setQuestionId(idCounter);
					String keys[] = new String[5];
					if (!(key.equals("-1"))) {
						for (int i = 0; i < key.length(); i++) {
							keys[i] = optionAnswerKey.get(key.substring(i, i + 1));
						}
					} else if(key != null){
						keys[0] = key;
					}
					anserKey.setAnswerKey(keys);
					keyList.add(anserKey);
					idCounter++;
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		gson = new Gson();
		questionJsonString = gson.toJson(keyList);

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
		gson = new Gson();
		while ((jsonString = bufferAnswerkey.readLine()) != null) {
			answerKey += jsonString;
		}
		System.out.println(answerKey);
		List<Answerkey> k = gson.fromJson(answerKey, new TypeToken<List<Answerkey>>() {
		}.getType());
		try {
			buildKeys();
			StringBuffer updatedAnswerKey = new StringBuffer("[");
			boolean foundAnAnswer;
            for (Answerkey akey : k) {
                foundAnAnswer = false;
                for (String key : akey.getAnswerKey()) {
                    if (key != null) {
                        String keyCode = getKey(optionAnswerKey, key);
                        updatedAnswerKey.append(keyCode);
                        foundAnAnswer = true;
                    } 
                }
                if (!foundAnAnswer) {
                    updatedAnswerKey.append("error");
                }
                updatedAnswerKey.append(",");
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