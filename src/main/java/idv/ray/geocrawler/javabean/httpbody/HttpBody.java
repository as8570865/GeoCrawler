package idv.ray.geocrawler.javabean.httpbody;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import idv.ray.geocrawler.javabean.geodata.Task;

public class HttpBody {

	static class UrlSetSerializer extends JsonSerializer<Set<String>> {

		@Override
		public void serialize(Set<String> value, JsonGenerator jgen, SerializerProvider serializers)
				throws IOException {
			jgen.writeStartArray();
			for (String urls : value) {
				jgen.writeObject(urls);
			}
			jgen.writeEndArray();
		}
	}

	static class UrlSetDeserializer extends JsonDeserializer<Set<String>> {

		@Override
		public Set<String> deserialize(JsonParser jsonParser, DeserializationContext ctxt)
				throws IOException, JsonProcessingException {
			ObjectMapper mapper = new ObjectMapper();
			String jsonString = jsonParser.readValueAsTree().toString();
			Set<String> urlSet = mapper.readValue(jsonString,
					mapper.getTypeFactory().constructCollectionType(Set.class, String.class));

			return urlSet;
		}

	}

	@JsonDeserialize(using = UrlSetDeserializer.class)
	@JsonSerialize(using = UrlSetSerializer.class)
	@JsonProperty("urlSet")
	private Set<String> urlSet = new HashSet<String>();

	@JsonProperty("srcTask")
	private Task srcTask;

	@JsonProperty("resource")
	private boolean resource;

	public HttpBody(Task srcTask) {
		this.srcTask = srcTask;
	}

	public HttpBody() {
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

	public void addTask(String urlString) {
		urlSet.add(urlString);

	}

	public Set<String> getUrlSet() {
		return urlSet;
	}

	public void setUrlSet(Set<String> urlSet) {
		this.urlSet = urlSet;
	}
}
