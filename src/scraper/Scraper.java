package scraper;

import java.net.*;
import java.text.*;
import java.util.*;

import org.apache.commons.lang3.*;
import org.apache.http.client.utils.*;

import com.jaunt.*;

public class Scraper {

    private final String DVD_RELEASE_BASE_URL = "http://www.dvdsreleasedates.com/releases/";
    private UserAgent userAgent;

    public Scraper() {
        userAgent = new UserAgent();
    }

    public String buildDVDReleaseURL(int year, int month) {
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();

        String DVDReleaseURL = DVD_RELEASE_BASE_URL +
                year + "/" + month + "/" +
                "new-dvd-releases-" +
                months[month] + "-" + year;
        return DVDReleaseURL;
    }

    private Element getTableFromURL(String url, int tableNumber) throws NotFound {
        try {
            userAgent.visit(url); // visit
        } catch (ResponseException e) {
            e.printStackTrace();
        }

        // Find all the tables
        Elements tables = userAgent.doc.findEach("<table>");
        System.out.println("Found " + tables.size() + " tables:");
        System.out.println("Returning table number " + tableNumber);

        return tables.getElement(tableNumber);
    }

    public List<String> findDVDReleases(int year, int month) {

        String DVDReleaseURL = buildDVDReleaseURL(year, month);

        List<String> DVDList = new ArrayList<String>();

        Element table;
        try {
            table = getTableFromURL(DVDReleaseURL, 0);
        } catch (NotFound notFound) {
            notFound.printStackTrace();
            return DVDList;
        }

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

                DVDList.add("\"" + linkText + "\"");
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
