package idv.ray.geocrawler.util.javabean.httpbody;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import idv.ray.geocrawler.util.javabean.geodata.GeoData;
import idv.ray.geocrawler.util.json.GeoDataMixin;
import idv.ray.geocrawler.util.json.JSONSerializable;

public class QueryResult implements JSONSerializable {

	private int resultSize;

	private List<? extends Monitorable> result;
	
	private Map<String,String> queryMap;

	public Map<String, String> getQueryMap() {
		
		return queryMap;
	}

	public void setQueryMap(Map<String, String> queryMap) {
		this.queryMap = queryMap;
	}

	public QueryResult(List<? extends Monitorable> result) {
		setResult(result);
	}

	public int getResultSize() {
		return resultSize;
	}

	public void setResultSize(int resultSize) {
		this.resultSize = resultSize;
	}

	public List getResult() {
		return result;
	}

	public void setResult(List<? extends Monitorable> result) {
		this.result = result;
		setResultSize(result.size());
	}

	@Override
	public String serialize() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		return mapper.writeValueAsString(this);
	}

	public String GeoDataSerialize() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.addMixInAnnotations(GeoData.class, GeoDataMixin.class);
		return mapper.writeValueAsString(this);
	}

}
