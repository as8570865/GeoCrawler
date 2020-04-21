package idv.ray.geocrawler.master.dao.hibernate;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

import idv.ray.geocrawler.master.dao.TaskDao;
import idv.ray.geocrawler.util.javabean.geodata.Task;

@Configuration
@Repository
public class HiberTaskDaoImpl extends HiberGeoDataDaoAbst<Task> implements TaskDao {

	public HiberTaskDaoImpl() {
		setClazz(Task.class);
	}
	
	public void update(Task task) {
		getCurrentSession().update(task);

	}

	public Task getReady(String geoType) {
		
		System.out.println("in hiberTaskDaoImpl getReady geoType: "+geoType);
		
		Session session=getCurrentSession();
		Criteria cr = session.createCriteria(Task.class);
		cr.add(Restrictions.eq("geoType", geoType));
		cr.add(Restrictions.eq("status", Task.Status.READY));
		cr.addOrder(Order.asc("level"));
		cr.setMaxResults(1);
		return (Task)cr.list().get(0);
	}

	public Task getRunning(String geoType) {
		Session session=getCurrentSession();
		Criteria cr = session.createCriteria(Task.class);
		cr.add(Restrictions.eq("geoType", geoType));
		cr.add(Restrictions.eq("status", Task.Status.RUNNING));
		cr.addOrder(Order.asc("level"));
		cr.setMaxResults(1);
		return (Task)cr.list().get(0);
	}

	public boolean tableIsEmpty() {
		Session session=getCurrentSession();
		Criteria cr = session.createCriteria(Task.class);
		cr.add(Restrictions.eq("level", 0));
		cr.setMaxResults(1);
		if(cr.list().isEmpty())
			return true;
		return false;
	}

}
