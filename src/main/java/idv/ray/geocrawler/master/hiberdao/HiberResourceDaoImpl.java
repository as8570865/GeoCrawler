package idv.ray.geocrawler.master.hiberdao;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

import idv.ray.geocrawler.util.javabean.geodata.Resource;

@Configuration
@Repository
public class HiberResourceDaoImpl extends AbstDaoHiber<Resource> implements ResourceDao {

	public HiberResourceDaoImpl() {
		setClazz(Resource.class);
	}

	@Bean(name = "hiberResourceDao")
	public HiberResourceDaoImpl hiberResourceDaoImpl() {
		return new HiberResourceDaoImpl();
	}
}
