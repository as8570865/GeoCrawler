package idv.ray.geocrawler.master.dao.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import idv.ray.geocrawler.master.dao.GeoDataDao;
import idv.ray.geocrawler.util.javabean.geodata.GeoData;

public abstract class HiberGeoDataDaoAbst<T extends GeoData> implements GeoDataDao<T> {

	protected Class<T> clazz;

	@Autowired
	private SessionFactory sessionFactory;

	public void setClazz(Class<T> clazz) {
		this.clazz=clazz;
	}

	public T get(int id) {
		return (T) getCurrentSession().get(clazz, id);
	}

	public List getAll() {

		return getCurrentSession().createQuery("from " + clazz.getName()).list();
	}

	public T insert(T geoData) {
		getCurrentSession().save(geoData);
		return geoData;
	}

	public void delete(int id) {
		T entity = get(id);
		getCurrentSession().delete(entity);
	}

	public void init(String tableSchema) {
		
	}

	public boolean isInitialized() {
		return false;
	}

	public boolean containsLink(String link) {
		return false;
	}

	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

}