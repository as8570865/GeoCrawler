package idv.ray.geocrawler.master.monitor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import idv.ray.geocrawler.master.dao.TaskDao;
import idv.ray.geocrawler.util.javabean.geodata.Task;
import idv.ray.geocrawler.util.javabean.geodata.Task.Status;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskDao taskDao;

	@Override
	public Task getById(int id) {
		return taskDao.getById(id);
	}

	@Override
	public void delete(Task geoData) {

	}

	@Override
	public void insert(Task task) {
		if (!taskDao.contains(task))
			taskDao.insert(task);
	}

	@Override
	public List<Task> get(int id, String geoType, String processorIp, String workerName, int minLevel, int maxLevel,
			Status status) {

		return taskDao.get(id, geoType, processorIp, workerName, minLevel, maxLevel, status);
	}

}
