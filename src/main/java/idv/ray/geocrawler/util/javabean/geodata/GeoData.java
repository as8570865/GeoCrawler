package idv.ray.geocrawler.util.javabean.geodata;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import idv.ray.geocrawler.util.javabean.httpbody.Monitorable;
import idv.ray.geocrawler.util.javabean.worker.Worker;
import idv.ray.geocrawler.util.json.JSONSerializable;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class GeoData implements JSONSerializable, Monitorable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonProperty("id")
	protected int id;

	@Column(nullable = false)
	@JsonProperty("level")
	protected int level;

	@Column(nullable = false, unique = true)
	@JsonProperty("link")
	protected String link;

	@JsonProperty("time")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	protected LocalDateTime time;

	@Column(nullable = false)
	@JsonProperty("geotype")
	protected String geoType;

	@Column(nullable = false)
	@JsonProperty("srctask_Id")
	protected int srcTaskId;

	@JsonProperty("worker")
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "workIp",nullable = true)
	protected Worker worker;

	public GeoData() {

	}

	public GeoData(String link, int level, String geoType, int srcTaskId) {
		this.link = link;
		this.level = level;
		this.geoType = geoType;
		this.srcTaskId = srcTaskId;
	}

	public GeoData(int id, String link, int level, String geoType, int srcTaskId) {
		this.id = id;
		this.link = link;
		this.level = level;
		this.geoType = geoType;
		this.srcTaskId = srcTaskId;
	}

	public GeoData(int id, String link, int level, String geoType, int srcTaskId, Worker worker) {
		this(id, link, level, geoType, srcTaskId);
		this.worker = worker;
	}

	public int getSrcTaskId() {
		return srcTaskId;
	}

	public void setSrcTaskId(int srcTaskId) {
		this.srcTaskId = srcTaskId;
	}

	public String getGeoType() {
		return geoType;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public int getLevel() {
		return level;
	}

	public int getId() {
		return id;
	}

	public String getLink() {
		return link;
	}

	public Worker getWorker() {
		return worker;
	}

	public void setWorker(Worker worker) {
		this.worker = worker;
	}

	@JsonIgnore
	public boolean isValid() {
		if (id == 0 && link == null)
			return false;
		else
			return true;
	}

	@Override
	public abstract String toString();

	@Override
	public String serialize() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		return mapper.writeValueAsString(this);
	}

	public static GeoData deserialize(String json, Class<? extends GeoData> clazz)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		Reader reader = new StringReader(json);
		return mapper.readValue(reader, clazz);
	}

}
