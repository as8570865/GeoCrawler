package idv.ray.geocrawler.util.javabean.httpbody;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import idv.ray.geocrawler.util.javabean.geodata.GeoData;
import idv.ray.geocrawler.util.json.GeoDataMixin;
import idv.ray.geocrawler.util.json.JSONSerializable;

public class QueryResult implements JSONSerializable {

	private int resultSize;

	private List<? extends GeoData> result;

	public QueryResult(List<? extends GeoData> result) {
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

	public void setResult(List<? extends GeoData> result) {
		this.result = result;
		setResultSize(result.size());
	}

	@Override
	public String serialize() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.addMixInAnnotations(GeoData.class, GeoDataMixin.class);
		return mapper.writeValueAsString(this);
	}
}
