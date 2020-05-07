package idv.ray.geocrawler.util.javabean.geodata;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.InetAddress;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import idv.ray.geocrawler.util.json.JSONSerializable;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class GeoData implements JSONSerializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonProperty("id")
	protected int id;

	@Column(nullable = false)
	@JsonProperty("level")
	protected int level;

	@Column(nullable = false)
	@JsonProperty("link")
	protected String link;

	@JsonProperty("time")
	protected long time;

	@Column(nullable = false)
	@JsonProperty("geoType")
	protected String geoType;

	@Column(nullable = false)
	@JsonProperty("srcTaskId")
	protected int srcTaskId;

	@Column(nullable = true)
	@JsonIgnore
	protected String processorIp;

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

	public int getSrcTaskId() {
		return srcTaskId;
	}

	public void setSrcTaskId(int srcTaskId) {
		this.srcTaskId = srcTaskId;
	}

	public String getGeoType() {
		return geoType;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
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

	public String getProcessorIp() {
		return processorIp;
	}

	public void setProcessorIp(String processorIp) {
		this.processorIp = processorIp;
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
		return mapper.writeValueAsString(this);
	}

	public static GeoData deserialize(String json, Class<? extends GeoData> clazz)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Reader reader = new StringReader(json);
		return mapper.readValue(reader, clazz);
	}

}
