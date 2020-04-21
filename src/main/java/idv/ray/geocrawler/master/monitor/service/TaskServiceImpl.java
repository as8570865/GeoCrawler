package idv.ray.geocrawler.master.monitor.service;

import java.awt.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import idv.ray.geocrawler.master.dao.TaskDao;
import idv.ray.geocrawler.util.javabean.geodata.Task;

@Configuration
@Transactional
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskDao taskDao;

	public Task getById(int id) {
		return taskDao.get(id);
	}

	public void delete(Task geoData) {

	}

	public List getAll(int a) {
		Task t=taskDao.getRunning("sos");
		System.out.println(t);
		t.setStatus(Task.Status.FINISHED);
		taskDao.update(t);
		
		return null;
	}

	public void insert(Task task) {
		if (!taskDao.containsLink(task.getLink()))
			taskDao.insert(task);
	}

}
