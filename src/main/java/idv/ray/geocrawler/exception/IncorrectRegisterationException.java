package idv.ray.geocrawler.exception;

public class IncorrectRegisterationException {

	public static class WorkerExistsException extends Exception {

		public WorkerExistsException(String msg) {
			super(msg);
		}
	}

	public static class UserNameUsedException extends Exception {

		public UserNameUsedException(String msg) {
			super(msg);
		}
	}

}
