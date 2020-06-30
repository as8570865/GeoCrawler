package idv.ray.geocrawler.master.dao.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import idv.ray.geocrawler.master.dao.WorkerDao;
import idv.ray.geocrawler.util.javabean.worker.Worker;

@Repository
public class HiberWorkerImpl implements WorkerDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Worker getByIpAddress(String ipAddress) {
		return getCurrentSession().load(Worker.class, ipAddress);
	}

	@Override
	public Worker insert(Worker worker) {
		getCurrentSession().save(worker);
		return worker;

	}

	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public boolean containsIpAddress(String ipAddress) {
		Session session = getCurrentSession();
		Query query = session.createQuery("select 1 from Worker w where w.ipAddress=:ipAddress");
		query.setString("ipAddress", ipAddress);
		return (query.uniqueResult() != null);
	}

	@Override
	public boolean containsName(String name) {
		Session session = getCurrentSession();
		Query query = session.createQuery("select 1 from Worker w where w.name=:name");
		query.setString("name", name);
		return (query.uniqueResult() != null);
	}

	@Override
	public List<Worker> get(String name, String ipAddress) {
		Session session = getCurrentSession();
		Query query = session.createQuery("from Worker t where (:name is null or t.name = :name)"
				+ "and (:ipAddress is null or t.ipAddress = :ipAddress)");

		query.setParameter("name", name);
		query.setParameter("ipAddress", ipAddress);

		return query.list();
	}

	@Override
	public void update(Worker worker) {
		getCurrentSession().update(worker);
		
	}

}
