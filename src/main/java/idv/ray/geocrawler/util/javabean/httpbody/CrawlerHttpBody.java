package idv.ray.geocrawler.util.javabean.httpbody;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import idv.ray.geocrawler.util.javabean.geodata.Task;

public class CrawlerHttpBody implements HttpBody {

	@JsonProperty("urlList")
	private List<String> urlList = new ArrayList<String>();

	@JsonProperty("srcTask")
	private Task srcTask;

	@JsonProperty("resource")
	private boolean resource;

	public CrawlerHttpBody(Task srcTask) {
		this.srcTask = srcTask;
	}

	public CrawlerHttpBody() {
	}

	public boolean isResource() {
		return resource;
	}

	public void setResource(boolean isResource) {
		this.resource = isResource;
	}

	public Task getSrcTask() {
		return srcTask;
	}

	public void addUrlString(String urlString) {
		urlList.add(urlString);

	}

	public List<String> getUrlList() {
		return urlList;
	}

	public void setUrlList(List<String> urlList) {
		this.urlList = urlList;
	}

	@Override
	public String serialize() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(this);
	}

	public static CrawlerHttpBody deserialize(String json)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Reader reader = new StringReader(json);
		return mapper.readValue(reader, CrawlerHttpBody.class);
	}

}
