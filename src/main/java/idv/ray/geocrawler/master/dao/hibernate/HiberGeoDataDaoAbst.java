package idv.ray.geocrawler.master.dao.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import idv.ray.geocrawler.master.dao.GeoDataDao;
import idv.ray.geocrawler.util.javabean.geodata.GeoData;

public abstract class HiberGeoDataDaoAbst<T extends GeoData> implements GeoDataDao<T> {

	protected Class<T> clazz;

	@Autowired
	private SessionFactory sessionFactory;

	public void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public T getById(int id) {
		return getCurrentSession().get(clazz, id);
	}

	@Override
	public T insert(T geoData) {
		getCurrentSession().save(geoData);
		return geoData;
	}

	@Override
	public void delete(int id) {
		T entity = getById(id);
		getCurrentSession().delete(entity);
	}

	@Override
	public void init(String tableSchema) {

	}

	@Override
	public boolean isInitialized() {
		return false;
	}

	@Override
	public boolean contains(GeoData geoData) {
		Session session = getCurrentSession();
		Query query = session.createQuery("select 1 from "+geoData.getClass().getName()+" g where g.link =:link");
		query.setString("link", geoData.getLink());
		return (query.uniqueResult() != null);
	}

	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

}
