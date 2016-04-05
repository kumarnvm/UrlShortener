package pt.go2.fileio;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pt.go2.response.AbstractResponse;
import pt.go2.response.GenericResponse;

public class ErrorPages {

	private static final Logger LOGGER = LogManager.getLogger();

	/**
	 * Canned responses for errors
	 */
	public enum Error {
		PAGE_NOT_FOUND, PHISHING, MALWARE
	}

	private final Map<Error, AbstractResponse> errors = new EnumMap<>(Error.class);

	/**
	 * Cache error responses
	 *
	 * @param config
	 * @return
	 * @throws IOException
	 */
	public ErrorPages() throws IOException {

		try {
			this.errors.put(Error.PAGE_NOT_FOUND,
					GenericResponse.createFromFile(ErrorPages.class.getResourceAsStream("/404.html"), 404));
		} catch (final IOException e) {
			LOGGER.fatal("Cannot read 404 page.", e);
			throw e;
		}

		try {
			this.errors.put(Error.PHISHING,
					GenericResponse.createFromFile(ErrorPages.class.getResourceAsStream("/403-phishing.html"), 403));
		} catch (final IOException e) {
			LOGGER.fatal("Cannot read 403-phishing page.", e);
			throw e;
		}

		try {
			this.errors.put(Error.MALWARE,
					GenericResponse.createFromFile(ErrorPages.class.getResourceAsStream("/403-malware.html"), 403));
		} catch (final IOException e) {
			LOGGER.fatal("Cannot read 403-malware page.", e);
			throw e;
		}
	}

	/**
	 * Return error response
	 *
	 * @param badRequest
	 * @return
	 */
	public AbstractResponse get(Error error) {
		return errors.get(error);
	}
}
