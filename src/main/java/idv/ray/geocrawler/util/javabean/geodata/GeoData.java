package idv.ray.geocrawler.util.javabean.geodata;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class GeoData {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonProperty("id")
	protected int id;

	@JsonProperty("level")
	protected int level;

	@JsonProperty("link")
	protected String link;

	@JsonProperty("time")
	protected long time;

	@JsonProperty("geoType")
	protected String geoType;

	@JsonProperty("srcTaskId")
	protected int srcTaskId;

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

	@JsonIgnore
	public boolean isValid() {
		if (id == 0 && link == null)
			return false;
		else
			return true;
	}

	@Override
	public abstract String toString();

}
