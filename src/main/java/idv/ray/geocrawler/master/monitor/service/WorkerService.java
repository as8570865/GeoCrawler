package idv.ray.geocrawler.master.monitor.service;

import java.util.List;

import idv.ray.geocrawler.util.javabean.worker.Worker;

public interface WorkerService {

	public List<Worker> get(String name,String ipAddress);
}
