package networking;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MyHttpURLConnection {

	private final String USER_AGENT = "Mozilla/5.0";

	public static void main(String[] args) throws Exception {
		String url = args[0];
		String filePath = args[1];

		MyHttpURLConnection http = new MyHttpURLConnection();

		System.out.println("Testing 1 - Send Http GET request");
		http.sendGet(url, filePath);

	}

	// HTTP GET request
	private void sendGet(String url, String filePath) {
		try {
		URL obj = new URL(url);
	
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");

			// add request header
			con.setRequestProperty("User-Agent", USER_AGENT);

			int responseCode = con.getResponseCode();

			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			try {
				FileOutputStream fileOut = new FileOutputStream(filePath);
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(response);
				out.close();
				fileOut.close();
				System.out.println("Response is saved in " + filePath);
			} catch (IOException ex) {
				System.out.println(ex);
			}
			// print result
			System.out.println("Response from input stream " + '\n' + response.toString());

			try {
				FileInputStream fileIn = new FileInputStream(filePath);
				ObjectInputStream inStream = new ObjectInputStream(fileIn);
				response = (StringBuffer) inStream.readObject();
				inStream.close();
				fileIn.close();
				System.out.println("Response read from file " + '\n' + response);
			} catch (IOException ex) {
				System.err.println(ex);
			} catch (ClassNotFoundException ex) {
				System.err.println(ex);
			}
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}

}
