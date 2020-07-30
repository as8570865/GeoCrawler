package idv.ray.geocrawler.util.javabean.worker;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import idv.ray.geocrawler.util.javabean.httpbody.Monitorable;
import idv.ray.geocrawler.util.json.JSONSerializable;

@Entity
public class Worker implements JSONSerializable, Monitorable {

	@Id
	@Column(nullable = false, unique = true)
	@JsonProperty("ipAddress")
	private String ipAddress;

	@Column(nullable = false, unique = true)
	@JsonProperty("name")
	private String name;

	@JsonProperty("lastCrawlingTime")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime lastCrawlingTime;

	public LocalDateTime getLastCrawlingTime() {
		return lastCrawlingTime;
	}

	public void setLastCrawlingTime(LocalDateTime lastCrawlingTime) {
		this.lastCrawlingTime = lastCrawlingTime;
	}

	public String getName() {
		return name;
	}

	public Worker() {

	}

	public Worker(String name) {
		this.name = name;
	}

	public Worker(String name, String ipAddress) {
		this.name = name;
		this.ipAddress = ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	@Override
	public String serialize() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		return mapper.writeValueAsString(this);
	}

	public static Worker deserialize(String json) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		Reader reader = new StringReader(json);
		return mapper.readValue(reader, Worker.class);
	}

	@Override
	public String toString() {
		return "Worker [ipAddress=" + ipAddress + ", name=" + name + "]";
	}

}
