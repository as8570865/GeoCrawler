package idv.ray.geocrawler.master.dao;

import java.time.Duration;
import java.util.List;

import idv.ray.geocrawler.util.javabean.worker.Worker;

public interface WorkerDao {

	public Worker getByIpAddress(String ipAddress);

	public List<Worker> get(String name, String ipAddress);
	
	public List<Worker> getIdle(Duration duration);

	public Worker insert(Worker worker);
	
	public void update(Worker worker);

	public boolean containsIpAddress(String ipAddress);

	public boolean containsName(String name);
}
