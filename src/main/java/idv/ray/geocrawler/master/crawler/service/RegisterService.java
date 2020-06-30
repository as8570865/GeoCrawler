package idv.ray.geocrawler.master.crawler.service;

import idv.ray.geocrawler.exception.IncorrectRegisterationException.UserNameUsedException;
import idv.ray.geocrawler.exception.IncorrectRegisterationException.WorkerExistsException;
import idv.ray.geocrawler.util.javabean.worker.Worker;

public interface RegisterService {
	public Worker regeister(Worker worker) throws UserNameUsedException, WorkerExistsException;
}
