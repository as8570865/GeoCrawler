package idv.ray.geocrawler.master.crawler.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import idv.ray.geocrawler.exception.IncorrectRegisterationException;
import idv.ray.geocrawler.exception.IncorrectRegisterationException.UserNameUsedException;
import idv.ray.geocrawler.exception.IncorrectRegisterationException.WorkerExistsException;
import idv.ray.geocrawler.master.dao.WorkerDao;
import idv.ray.geocrawler.util.javabean.worker.Worker;

@Service
@Transactional
public class RegisterServiceImpl implements RegisterService {

	@Autowired
	private WorkerDao workerDao;

	@Override
	public Worker regeister(Worker worker) throws UserNameUsedException, WorkerExistsException {

		if (!workerDao.containsIpAddress(worker.getIpAddress())) {
			if (!workerDao.containsName(worker.getName())) {
				workerDao.insert(worker);
				return worker;
			} else
				throw new IncorrectRegisterationException.UserNameUsedException("this name has already been used");
		} else {
			throw new IncorrectRegisterationException.WorkerExistsException("this worker ip address already existed");
		}

	}

}
