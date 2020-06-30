package idv.ray.geocrawler.master.dao.hibernate;

import java.util.List;
import java.util.Optional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import idv.ray.geocrawler.master.dao.TaskDao;
import idv.ray.geocrawler.util.javabean.geodata.Task;
import idv.ray.geocrawler.util.javabean.geodata.Task.Status;

@Repository
public class HiberTaskDaoImpl extends HiberGeoDataDaoAbst<Task> implements TaskDao {

	public HiberTaskDaoImpl() {
		setClazz(Task.class);
	}

	@Override
	public void update(Task task) {
		getCurrentSession().update(task);

	}

	@Override
	public Optional<Task> getReady(String geoType) {

		Session session = getCurrentSession();
		Criteria cr = session.createCriteria(Task.class);
		cr.add(Restrictions.eq("geoType", geoType));
		cr.add(Restrictions.eq("status", Task.Status.READY));
		cr.addOrder(Order.asc("level"));
		cr.setMaxResults(1);
		return Optional.ofNullable((Task) cr.list().get(0));
	}

	@Override
	public Optional<Task> getRunning(String geoType) {
		Session session = getCurrentSession();
		Criteria cr = session.createCriteria(Task.class);
		cr.add(Restrictions.eq("geoType", geoType));
		cr.add(Restrictions.eq("status", Task.Status.RUNNING));
		cr.addOrder(Order.asc("level"));
		cr.setMaxResults(1);
		return Optional.ofNullable((Task) cr.list().get(0));
	}

	@Override
	public boolean tableIsEmpty() {
		Session session = getCurrentSession();
		Criteria cr = session.createCriteria(Task.class);
		cr.add(Restrictions.eq("level", 0));
		cr.setMaxResults(1);
		if (cr.list().isEmpty())
			return true;
		return false;
	}

	@Override
	public List<Task> get(int id, String geoType, String ipAddress, String workerName, int minLevel, int maxLevel,
			Status status) {
		Session session = getCurrentSession();
		Query query = session.createQuery("select t from Task t left join t.worker worker where (:id is null or t.id = :id)"
				+ "and (:geoType is null or t.geoType = :geoType)"
				+ "and (:ipAddress is null or worker.ipAddress = :ipAddress)"
				+ "and (:workerName is null or worker.name = :workerName)"
				+ "and (:minLevel is null or t.level >= :minLevel)" + "and (:maxLevel is null or t.level <= :maxLevel)"
				+ "and (:status is null or t.status = :status)");

		// id is 0, which means no query conditions on id
		if (id == 0)
			query.setParameter("id", null);
		else
			query.setParameter("id", id);

		query.setParameter("geoType", geoType);
		query.setParameter("ipAddress", ipAddress);
		query.setParameter("workerName", workerName);
		query.setParameter("minLevel", minLevel);
		query.setParameter("maxLevel", maxLevel);
		query.setParameter("status", status);

		return query.list();
	}

}
