package test;

import org.junit.*;
import org.junit.Assert;
import scraper.MediaGetter;
import scraper.MetacriticScraper;

/**
 * Created by maxsparrow on 1/20/16.
 */
public class TestMetacriticScraper {

    private MetacriticScraper scraper;

    private static String MOVIE_EXAMPLE_1 = "He named me Malala";
    private static String TV_EXAMPLE_1 = "Fear the Walking Dead: Season 1";

    @Before
    public void setUp() {
        scraper = new MetacriticScraper();
    }

    @Test
    public void testGetMovieScore() {
        int score = scraper.getScore(MediaGetter.MOVIE, MOVIE_EXAMPLE_1);
        Assert.assertEquals(score, 61);
    }

    @Test
    public void testGetMovieLink() {
        String link = scraper.getLink(MediaGetter.MOVIE, MOVIE_EXAMPLE_1);
        Assert.assertEquals(link, "http://www.metacritic.com/movie/he-named-me-malala");
    }

    @Test
    public void testGetTVScore() {
        int score = scraper.getScore(MediaGetter.TV, TV_EXAMPLE_1);
        Assert.assertEquals(score, 66);
    }

    @Test
    public void testGetTVLink() {
        String link = scraper.getLink(MediaGetter.TV, TV_EXAMPLE_1);
        Assert.assertEquals(link, "http://www.metacritic.com/tv/fear-the-walking-dead");
    }

}
