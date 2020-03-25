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

	static class TaskSetSerializer extends JsonSerializer<Set<Task>> {

		@Override
		public void serialize(Set<Task> value, JsonGenerator jgen, SerializerProvider serializers) throws IOException {
			jgen.writeStartArray();
			for (Task task : value) {
				jgen.writeObject(task);
			}
			jgen.writeEndArray();
		}
	}

	static class TaskSetDeserializer extends JsonDeserializer<Set<Task>> {

		@Override
		public Set<Task> deserialize(JsonParser jsonParser, DeserializationContext ctxt)
				throws IOException, JsonProcessingException {
			ObjectMapper mapper = new ObjectMapper();
			String jsonString = jsonParser.readValueAsTree().toString();
			Set<Task> taskSet = mapper.readValue(jsonString,
					mapper.getTypeFactory().constructCollectionType(Set.class, Task.class));

			return taskSet;
		}

	}

	@JsonDeserialize(using = TaskSetDeserializer.class)
	@JsonSerialize(using = TaskSetSerializer.class)
	@JsonProperty("taskSet")
	private Set<Task> taskSet = new HashSet<Task>();

	@JsonProperty("srcTask")
	private Task srcTask;

	@JsonProperty("resource")
	private boolean resource;

	public HttpBody() {

	}
	
	public HttpBody(Task srcTask) {
		this.srcTask = srcTask;
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

	public void addTask(Task task) {
		taskSet.add(task);

	}

	public Set<Task> getTaskSet() {
		return taskSet;
	}
	
	public void setTaskSet(Set<Task> taskSet) {
		this.taskSet = taskSet;
	}
}
