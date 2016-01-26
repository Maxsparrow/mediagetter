package test;

import org.junit.*;
import org.junit.Assert;
import scraper.DVDReleaseScraper;

import java.util.List;

/**
 * Created by maxsparrow on 1/25/16.
 */
public class TestDVDReleaseScraper {

    private static final int YEAR_EXAMPLE_1 = 2015;
    private static final int MONTH_EXAMPLE_1 = 12;
    private static final String[] MOVIE_LIST_1 = new String[]{"Amy", "90 Minutes in Heaven", "Mistress America",
            "Goodnight Mommy", "Mississippi Grind", "Desert Dancer", "Cooties", "Fear the Walking Dead: Season 1",
            "Grace of Monaco", "Lost in the Sun", "Momentum", "Some Kind of Beautiful", "Yakuza Apocalypse", "Minions",
            "Ant-Man", "The Transporter Refueled", "Knock Knock", "Family Guy Season 13", "Hannibal: Season 3",
            "Under the Dome: Season 3", "Walt Before Mickey", "Mission: Impossible 5 Rogue Nation", "Ted 2",
            "Maze Runner 2: Scorch Trials", "Fantastic Four", "He Named Me Malala", "Time Out of Mind",
            "Csi: Crime Scene Investigation - The Final Csi", "Extant: Season 2", "Marco Polo: Season 1",
            "Teen Wolf: Season 5 - Part 1", "Wolf Totem", "War Room", "Pan", "Pawn Sacrifice", "Nasty Baby",
            "Dragon Blade", "12 Rounds 3: Lockdown", "Defiance: Season 3", "Dominion: Season 2", "The Perfect Guy",
            "A Walk in the Woods", "Hitman Agent 47", "Jenny's Wedding", "Bone Tomahawk", "Heist",
            "Ray Donovan: Season 3", "Shameless: Season 5"};

    private DVDReleaseScraper scraper;

    @Before
    public void SetUp() {
        scraper = new DVDReleaseScraper();
    }

    @Test
    public void testGetDVDReleases() {
        List<String> DVDList;
        DVDList = scraper.getDVDReleases(YEAR_EXAMPLE_1, MONTH_EXAMPLE_1);
        Assert.assertArrayEquals(DVDList.toArray(), MOVIE_LIST_1);
    }
}
