package idv.ray.geocrawler.master.dao.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import idv.ray.geocrawler.master.dao.ResourceDao;
import idv.ray.geocrawler.util.javabean.geodata.Resource;

@Repository
public class HiberResourceDaoImpl extends HiberGeoDataDaoAbst<Resource> implements ResourceDao {

	public HiberResourceDaoImpl() {
		setClazz(Resource.class);
	}

	@Override
	public List<Resource> get(int id, String geoType, String ipAddress, String workerName, int minLevel, int maxLevel) {
		Session session = getCurrentSession();
		Query query = session
				.createQuery("select t from Resource t left join t.worker worker where (:id is null or t.id = :id)"
						+ "and (:geoType is null or t.geoType = :geoType)"
						+ "and (:ipAddress is null or worker.ipAddress = :ipAddress)"
						+ "and (:workerName is null or worker.name = :workerName)"
						+ "and (:minLevel is null or t.level >= :minLevel)"
						+ "and (:maxLevel is null or t.level <= :maxLevel)");

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

		return query.list();
	}
	
	@Override
	public int getMaxLevel() {
		Session session = getCurrentSession();
		Query query = session.createQuery("select max(r.level) from Resource r");
		return (Integer)query.uniqueResult();
	}
}
