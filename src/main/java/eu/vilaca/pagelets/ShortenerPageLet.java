/**
 * 
 */
package eu.vilaca.pagelets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.sun.net.httpserver.HttpExchange;

import eu.vilaca.keystore.Database;

/**
 * @author vilaca
 * 
 */
public class ShortenerPageLet extends AbstractPageLet {

	@Override
	public byte[] main(final HttpExchange params) throws IOException {

		try (final InputStream is = params.getRequestBody();
				final InputStreamReader sr = new InputStreamReader(is);
				final BufferedReader br = new BufferedReader(sr);) {

			final String postBody = br.readLine();

			if ( postBody == null ) throw new IOException("Badly formed Request Body.");
			
			// format for form content is 'fieldname=value'
			final String[] formContents = postBody.split("=");

			return Database.getDatabase().add(formContents[1]);
		}

	}

	@Override
	public int getResponseCode() {
		return 200;
	}

	@Override
	public String getMimeType() {
		return "text/plain";
	}
}
