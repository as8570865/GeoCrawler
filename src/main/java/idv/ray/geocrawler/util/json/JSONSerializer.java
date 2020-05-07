package idv.ray.geocrawler.util.json;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import idv.ray.geocrawler.util.javabean.geodata.GeoData;
import idv.ray.geocrawler.util.javabean.geodata.Task;
import idv.ray.geocrawler.util.javabean.httpbody.QueryResult;

public class JSONSerializer<T> {

	public String serialize(T obj) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(obj);
	}

	public T deserialize(String jsonString, Class<? extends T> objClass)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Reader reader = new StringReader(jsonString);

		return mapper.readValue(reader, objClass);
	}

	public static void main(String[] args) throws IOException {

		Task t1 = new Task("asd", 1, "sos", 100);
		Task t2 = new Task("dfg", 1, "sos", 101);
		Task t3 = new Task("wrt", 1, "sos", 102);
		List<GeoData> l = new ArrayList();
		l.add(t1);
		l.add(t2);
		l.add(t3);
		QueryResult qr = new QueryResult(l);

		GeoDataMixin.SrcTaskSerializer.QUERY_GEODATA_BY_ID_URL = "wwww.com?id=";

		String json = qr.serialize();
		System.out.println(json);

//		List<Task> ls = new ArrayList<Task>();
//		ls.add(t1);
//		ls.add(t2);
//		ls.add(t3);
//
//		String json = new JSONSerializer<List<Task>>().serialize(ls);
//		List<Task> ls2 = Arrays.asList(new JSONSerializer<Task[]>().deserialize(json, Task[].class));
//		System.out.println(ls2.get(2).getLink());
	}

}
