package fr.umpc.test;

/*import static org.junit.Assert.*;

import com.moviejukebox.allocine.model.*;
import org.apache.http.impl.client.HttpClients;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yamj.api.common.http.AndroidBrowserUserAgentSelector;
import org.yamj.api.common.http.HttpClientWrapper;*/

public class AllocineApiTest {

    /*private static final Logger LOG = LoggerFactory.getLogger(AllocineApiTest.class);
    private static final String PARTNER_KEY = "100043982026";
    private static final String SECRET_KEY = "29d185d98c984a359e6e6f26a0474269";

    private static AllocineApi api;

    @BeforeClass
    @SuppressWarnings("resource")
    public static void beforeClass() throws AllocineException {
        // this must be the first statement in the beforeClass method
        TestLogger.configure();

        HttpClientWrapper wrapper = new HttpClientWrapper(HttpClients.createDefault());
        wrapper.setUserAgentSelector(new AndroidBrowserUserAgentSelector());
        api = new AllocineApi(PARTNER_KEY, SECRET_KEY, wrapper);
    }

    @Test
    public void testAccentSearch() throws AllocineException {
        LOG.info("testAccentSearch");
        final Search search = api.searchMovies("Mémoires de nos pères");
        boolean found = false;
        for (Movie movie : search.getMovies()) {
            if (movie.getCode() == 60580) {
                found = true;
                break;
            }
        }
        assertTrue("No movies found!", search.getMovies().size() > 0);
        assertTrue("Movie not found in search", found);
    }

    @Test
    public void testSearchMovies() throws AllocineException {
        LOG.info("testSearchMovieInfos");
        final Search search = api.searchMovies("avatar");
        assertEquals(10, search.getMovies().size());
    }

    @Test
    public void testSearchTvseriesInfos() throws AllocineException {
        LOG.info("testSearchTvseriesInfos");
        final Search search = api.searchTvSeries("glee");
        assertEquals(1, search.getTvSeries().size());
    }

    @Test
    public void testSearchPersons() throws AllocineException {
        LOG.info("testSearchPersons");
        final Search search = api.searchPersons("Sam Worthington");
        assertEquals(1, search.getPersons().size());
    }

    @Test
    public void testGetMovieInfos() throws AllocineException {
        LOG.info("testGetMovieInfos");
        final MovieInfos movieInfos = api.getMovieInfos("61282");
        // 61282 - Avatar
        // 45322 - Underworld
        // 25722 - SHAFT
        LOG.debug("Movie Infos : {}", movieInfos);

        assertEquals(61282, movieInfos.getCode());
        assertEquals(9720, movieInfos.getRuntime());
        assertEquals("Avatar", movieInfos.getTitle());
        assertEquals("Avatar", movieInfos.getOriginalTitle());
        assertEquals(2009, movieInfos.getProductionYear());
        assertEquals("2009-12-16", movieInfos.getReleaseDate());
        assertNotNull(movieInfos.getSynopsis());
        assertEquals("Twentieth Century Fox France", movieInfos.getDistributor());
        assertEquals(2, movieInfos.getGenres().size());
        assertEquals(1, movieInfos.getNationalities().size());
        assertEquals(1, movieInfos.getDirectors().size());
        assertEquals(1, movieInfos.getWriters().size());
        assertEquals(42, movieInfos.getActors().size());
        assertEquals(85, movieInfos.getUserRating());
        assertFalse(movieInfos.getFestivalAwards().isEmpty());
    }

    @Test
    public void testGetTvSeriesInfos() throws AllocineException {
        LOG.info("testGetTvSeriesInfos");
        final TvSeriesInfos tvseriesInfos = api.getTvSeriesInfos("132");
        LOG.debug("TV Series Infos : {}", tvseriesInfos);

        assertEquals(132, tvseriesInfos.getCode());
        assertEquals("Mon oncle Charlie", tvseriesInfos.getTitle());
        assertEquals("Two and a Half Men", tvseriesInfos.getOriginalTitle());
        assertEquals(2003, tvseriesInfos.getYearStart());
        assertEquals(2015, tvseriesInfos.getYearEnd());
        assertEquals("CBS", tvseriesInfos.getOriginalChannel());
        assertEquals("La vie d'un riche célibataire est bouleversée lorsque son frère divorcé et son neveu de 10 ans débarquent dans sa propriété de Malibu. Malgré leurs différences, les deux frères décident de co-habiter pour offrir un foyer au jeune Jake.", tvseriesInfos.getSynopsis());
        assertEquals(1, tvseriesInfos.getGenres().size());
        assertEquals(1, tvseriesInfos.getNationalities().size());
        assertTrue(tvseriesInfos.getActors().size() >= 5);
        assertEquals(70, tvseriesInfos.getUserRating());
        assertEquals(12, tvseriesInfos.getSeasonCount());
        assertEquals(12, tvseriesInfos.getSeasonList().size());
        assertFalse(tvseriesInfos.getFestivalAwards().isEmpty());
    }

    @Test
    public void testGetTvSeasonInfos() throws AllocineException {
        LOG.info("testGetTvSeasonInfos");
        final TvSeasonInfos tvseasonInfos = api.getTvSeasonInfos("22242");
        LOG.debug("TV Season Infos : {}", tvseasonInfos);

        assertEquals(22242, tvseasonInfos.getCode());
        assertEquals(4, tvseasonInfos.getSeasonNumber());
        assertEquals(2014, tvseasonInfos.getYearStart());
        assertEquals(2014, tvseasonInfos.getYearEnd());
        assertEquals(10, tvseasonInfos.getEpisodeList().size());

        assertFalse("No Director", tvseasonInfos.getDirectors().isEmpty());
        assertFalse("No Writer", tvseasonInfos.getWriters().isEmpty());
        assertFalse("No Actor", tvseasonInfos.getActors().isEmpty());
    }

    @Test
    @Ignore
    public void testCertification() throws AllocineException {
        LOG.info("testCertification");
        MovieInfos movieInfos = api.getMovieInfos("21189"); // Fight club, should be a "16"
        assertEquals("Incorrect certificate", "16", movieInfos.getCertification());
        movieInfos = api.getMovieInfos("61282"); // Avatar - has no certificate, should be "All"
        assertEquals("Incorrect certificate", "All", movieInfos.getCertification());
    }

    @Test
    public void testGetPersonInfos() throws AllocineException {
        LOG.info("testGetPersonInfos");
        final PersonInfos personInfos = api.getPersonInfos("8504");
        assertEquals(8504, personInfos.getCode());
        assertEquals("1954-12-28", personInfos.getBirthDate());
        assertEquals("Denzel", personInfos.getFirstName());
        assertEquals("Washington", personInfos.getLastName());
        assertFalse(personInfos.getFestivalAwards().isEmpty());
    }

    @Test
    public void testGetPersonFilmography() throws AllocineException {
        LOG.info("testGetPersonFilmography");
        final FilmographyInfos filmographyInfos = api.getPersonFilmography("80927");
        for (Participance p : filmographyInfos.getParticipances()) {
            if (p.isTvShow()) {
                LOG.trace("TV SHOW ({}) {}: {} - {}", p.getCode(), p.getTitle(), p.getYearStart(), p.getYearEnd());
            } else {
                LOG.trace("MOVIE ({}) {}: {} ({})", p.getCode(), p.getTitle(), p.getYear(), p.getReleaseDate());
            }
        }
    }

    @Test
    public void testGetEpisodeInfos() throws AllocineException {
        LOG.info("testGetEpisodeInfos");
        final EpisodeInfos episodeInfos = api.getEpisodeInfos("493491");
        assertEquals(493491, episodeInfos.getCode());

        assertFalse("No Director", episodeInfos.getDirectors().isEmpty());
        assertFalse("No Writer", episodeInfos.getWriters().isEmpty());
        assertFalse("No Actor", episodeInfos.getActors().isEmpty());
    }*/
}

