package idv.ray.geocrawler.exception;

public class MonitorException {

	public static class BadRequestException extends Exception {

		public BadRequestException(String msg, Throwable err) {
			super(msg, err);
		}

		public BadRequestException(String msg) {
			super(msg);
		}
	}

	public static class MasterException extends Exception {

		public MasterException(String msg, Throwable err) {
			super(msg, err);
		}

		public MasterException(String msg) {
			super(msg);
		}
	}
}
