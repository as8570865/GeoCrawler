package idv.ray.geocrawler.exception;

public abstract class GeoCrawlerException {

	public static class FailToInitializeException extends Exception {

		public FailToInitializeException(String msg) {
			super(msg);
		}
	}

}
