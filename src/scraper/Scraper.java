package scraper;

import java.net.*;
import java.text.*;
import java.util.*;

import org.apache.commons.lang3.*;
import org.apache.http.client.utils.*;

import com.jaunt.*;

public class Scraper {

	String DVD_RELEASE_BASE_URL = "http://www.dvdsreleasedates.com/releases/";

	public String buildDVDReleaseURL(int year, int month) {
		DateFormatSymbols dfs = new DateFormatSymbols();
		String[] months = dfs.getMonths();

		StringBuilder DVDReleaseURL = new StringBuilder();
		DVDReleaseURL.append(DVD_RELEASE_BASE_URL);
		DVDReleaseURL.append(year + "/" + month + "/");
		DVDReleaseURL.append("new-dvd-releases-" + months[month] + "-" + year);
		return DVDReleaseURL.toString();
	}

	public List<String> findDVDReleases(int year, int month) {

		String DVDReleaseURL = buildDVDReleaseURL(year, month);

		List<String> DVDList = new ArrayList<String>();

		try {
			UserAgent userAgent = new UserAgent(); // create new userAgent
													// (headless browser).
			userAgent.visit(DVDReleaseURL); // visit

			// Find all the tables
			Elements tables = userAgent.doc.findEach("<table>");
			System.out.println("Found " + tables.size() + " tables:");

			// Loop through rows in first table,
			System.out.println("Checking first table");
			Elements trs = tables.getElement(0).findEach("<tr>");
			System.out.println("Found " + trs.size() + " TRs:");
			for (Element tr : trs) {
				// Find specific rows with link style for dvd releases
				Elements links = tr.findEach("<a style='color:#000;'>");
				for (Element link : links) {
					String linkText = link.getText();
					if (linkText.indexOf('[') != -1)
						linkText = linkText.substring(0, linkText.indexOf('[') - 1);

					// Fix special html escape characters
					linkText = StringEscapeUtils.unescapeHtml4(linkText);

					DVDList.add(linkText);
				}
			}
		} catch (JauntException e) { // if an HTTP/connection error occurs
			System.err.println(e);
		}

		return DVDList;
	}

	public String findMetacriticScore(String movieName) {
		String uri = "";
		try {
			uri = new URIBuilder()
					.setScheme("http")
					.setHost("http://www.metacritic.com/")
					.setPath("/search/all/" + movieName + "/results")
					.build().toASCIIString();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return uri;
	}

	public static void main(String args[]) {
		Scraper scraper = new Scraper();
		List<String> DVDList;
		DVDList = scraper.findDVDReleases(2015, 12);
		System.out.println(DVDList);
	}
}
