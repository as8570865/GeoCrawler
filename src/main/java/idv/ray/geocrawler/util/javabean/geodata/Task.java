package idv.ray.geocrawler.util.javabean.geodata;

import java.io.IOException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;

import idv.ray.geocrawler.util.javabean.worker.Worker;

@Entity
public class Task extends GeoData {

	public static final String TYPE_NAME = "task";

	public enum Status {
		READY, RUNNING, FINISHED
	}

	@JsonProperty("status")
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Status status = Status.READY;

	public Task() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Task(String link, int level, String geoType, int srcTaskId) {
		super(link, level, geoType, srcTaskId);
	}

	public Task(int id, String link, int level, String geoType, int srcTaskId) {
		super(id, link, level, geoType, srcTaskId);
	}

	public Task(int id, String link, int level, String geoType, Status status, int srcTaskId, Worker worker) {
		super(id, link, level, geoType, srcTaskId, worker);
		this.status = status;
	}

	@Override
	public String toString() {
		return "Task [status=" + status + ", id=" + id + ", level=" + level + ", link=" + link + ", time=" + time
				+ ", geoType=" + geoType + ", srcTaskId=" + srcTaskId + "]";
	}

	public Status getStatus() {
		return this.status;
	}

	public void setRunning() {
		this.status = Status.RUNNING;
	}

	public void setFinished(Worker worker) {
		this.worker = worker;
		this.status = Status.FINISHED;
	}

}
