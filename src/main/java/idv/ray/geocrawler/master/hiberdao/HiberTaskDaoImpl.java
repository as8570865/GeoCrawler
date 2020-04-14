package idv.ray.geocrawler.master.hiberdao;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

import idv.ray.geocrawler.util.javabean.geodata.Task;

@Configuration
@Repository
public class HiberTaskDaoImpl extends HiberGeoDataDaoAbst<Task> implements TaskDao{

	public void update(Task task) {
		
		
	}

	public Task getNextNullStatus(String geoType) {
		// TODO Auto-generated method stub
		return null;
	}

	public Task getNextRunningStatus(String geoType) {
		// TODO Auto-generated method stub
		return null;
	}

}
