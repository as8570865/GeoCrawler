package idv.ray.geocrawler.worker.exception;

public class GeoCrawlerWorkerException {

	public static class FailToRegisterException extends Exception {

		public FailToRegisterException(String msg) {
			super(msg);
		}

		public FailToRegisterException(String msg, Throwable err) {
			super(msg, err);
		}
	}
	
	public static class FailToGetNextTaskException extends Exception {

		public FailToGetNextTaskException(String msg) {
			super(msg);
		}

		public FailToGetNextTaskException(String msg, Throwable err) {
			super(msg, err);
		}
	}
	
	public static class MasterException extends Exception {

		public MasterException(String msg) {
			super(msg);
		}

		public MasterException(String msg, Throwable err) {
			super(msg, err);
		}
	}
	
	public static class CrawledResultException extends Exception {

		public CrawledResultException(String msg, Throwable err) {
			super(msg, err);
		}
	}
}
