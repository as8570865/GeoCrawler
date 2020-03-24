package idv.ray.geocrawler.javabean.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import idv.ray.geocrawler.javabean.httpbody.HttpBody;

public class HttpBodySerializer implements JSONSerializable<HttpBody>, JSONDeserializable<HttpBody> {

	@Override
	public HttpBody deserialize(String jsonString, Class<? extends HttpBody> objClass)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		HttpBody httpBody = mapper.readValue(jsonString, objClass);

		return httpBody;

	}

	@Override
	public String serialize(HttpBody obj) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(obj);
	}

	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		HttpBodySerializer s = new HttpBodySerializer();
		String json = "{\r\n" + "  \"srcTask\": {\r\n" + "    \"id\":12,\r\n" + "    \"level\":5,\r\n"
				+ "    \"link\":\"abc.com\"\r\n" + "  },\r\n" + "  \"resource\": true,\r\n" + "  \"taskSet\": [\r\n"
				+ "    {\r\n" + "      \"id\":12,\r\n" + "      \"level\":5,\r\n" + "      \"link\":\"abc.com\"\r\n"
				+ "    },{\r\n" + "      \"id\":13,\r\n" + "      \"level\":5,\r\n" + "      \"link\":\"dsf.com\"\r\n"
				+ "    },{\r\n" + "      \"id\":14,\r\n" + "      \"level\":5,\r\n" + "      \"link\":\"fgg.com\"\r\n"
				+ "      }\r\n" + "    ]\r\n" + "}";
		HttpBody httpBody = (HttpBody) s.deserialize(json, HttpBody.class);

//		Task srcTask = new Task("abc.com", 5);
//		Task t = new Task("asd", 3);
//		Task t1 = new Task("dfg", 3);
//		Task t2 = new Task("wer", 3);
//		HttpBody httpBody = new HttpBody(srcTask);
//		httpBody.addTask(t);
//		httpBody.addTask(t1);
//		httpBody.addTask(t2);

		System.out.println(s.serialize(httpBody));
	}

}
