package scraper;

import java.net.*;
import java.text.*;
import java.util.*;

import org.apache.commons.lang3.*;
import org.apache.http.client.utils.*;

import com.jaunt.*;

public class Scraper {

	private String DVD_RELEASE_BASE_URL = "http://www.dvdsreleasedates.com/releases/";
	private UserAgent userAgent;

    public Scraper() {
        userAgent = new UserAgent();
    }

	public String buildDVDReleaseURL(int year, int month) {
		DateFormatSymbols dfs = new DateFormatSymbols();
		String[] months = dfs.getMonths();

		StringBuilder DVDReleaseURL = new StringBuilder();
		DVDReleaseURL.append(DVD_RELEASE_BASE_URL);
		DVDReleaseURL.append(year + "/" + month + "/");
		DVDReleaseURL.append("new-dvd-releases-" + months[month] + "-" + year);
		return DVDReleaseURL.toString();
	}

    private Element getTableFromURL(String url, int tableNumber) {
        try {
            userAgent.visit(url); // visit

            // Find all the tables
            Elements tables = userAgent.doc.findEach("<table>");
            System.out.println("Found " + tables.size() + " tables:");
            System.out.println("Returning table number " + tableNumber);

            return tables.getElement(tableNumber);
        }
        catch (JauntException e){
            System.out.println(e);
        }
    }

	public List<String> findDVDReleases(int year, int month) {

		String DVDReleaseURL = buildDVDReleaseURL(year, month);

		List<String> DVDList = new ArrayList<String>();

        Element table = getTableFromURL(DVDReleaseURL, 0);

        // Loop through rows in first table,
        Elements trs = table.findEach("<tr>");
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
