package idv.ray.geocrawler.master.monitor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import idv.ray.geocrawler.master.dao.WorkerDao;
import idv.ray.geocrawler.util.javabean.worker.Worker;

@Service
@Transactional
public class WorkerServiceImpl implements WorkerService {

	@Autowired
	private WorkerDao workerDao;

	@Override
	public List<Worker> get(String name, String ipAddress) {
		return workerDao.get(name, ipAddress);
	}

}
