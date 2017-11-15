package fr.umpc.test;

import com.moviejukebox.allocine.AllocineApi;
import com.moviejukebox.allocine.AllocineException;
import com.moviejukebox.allocine.model.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.protocol.HTTP;
import org.slf4j.LoggerFactory;
import org.yamj.api.common.http.AndroidBrowserUserAgentSelector;
import org.yamj.api.common.http.DigestedResponse;
import org.yamj.api.common.http.DigestedResponseReader;
import org.yamj.api.common.http.HttpClientWrapper;

import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URL;
import org.apache.commons.lang3.StringUtils;

import static org.yamj.api.common.exception.ApiExceptionType.*;

public class main {

    private static final String PARTNER_KEY = "100043982026";
    private static final String SECRET_KEY = "29d185d98c984a359e6e6f26a0474269";

    private static AllocineApi api;


    public static void beforeClass() throws AllocineException {
        // this must be the first statement in the beforeClass method

        HttpClientWrapper wrapper = new HttpClientWrapper(HttpClients.createDefault());
        wrapper.setUserAgentSelector(new AndroidBrowserUserAgentSelector());
        //wrapper.setUserAgentSelector(new WebBrowserUserAgentSelector());

        api = new AllocineApi(PARTNER_KEY, SECRET_KEY, wrapper);
    }

    private static void testAccentSearch() throws AllocineException {

        final Search search = api.searchMovies("Mémoires de nos pères");
        boolean found = false;
        for (Movie movie : search.getMovies()) {
            if (movie.getCode() == 60580) {
                found = true;
                break;
            }
        }

        if (search.getMovies().size() > 0) {
            System.out.println("No movies found!");
        }

        if (found) {
            System.out.println("Movie not found in search");
        }
    }

    static void testGetMovieInfos() throws AllocineException {

        final MovieInfos movieInfos = api.getMovieInfos("61282");
        // 61282 - Avatar
        // 45322 - Underworld
        // 25722 - SHAFT

        //System.out.println("Movie Infos : {}" + movieInfos);

        movieInfos.getNationalities();
        movieInfos.getOriginalTitle();
        movieInfos.getProductionYear();
        movieInfos.getReleaseDate();
        movieInfos.getRuntime();
        movieInfos.getSynopsis();
        movieInfos.getTitle();
        movieInfos.getUserRating();
        movieInfos.getActors();
        movieInfos.getCertification();
        movieInfos.getWriters();
        movieInfos.getArt();
        movieInfos.getCamera();
        movieInfos.getMovie();
        movieInfos.getPosters();
        movieInfos.getPressRating();
        movieInfos.getProducers();
        movieInfos.getReleaseCountry();
        movieInfos.getSynopsisShort();


        System.out.println(movieInfos.getCode());
        System.out.println(movieInfos.getRuntime());
        System.out.println(movieInfos.getTitle());
        System.out.println(movieInfos.getOriginalTitle());
        System.out.println(movieInfos.getProductionYear());
        System.out.println(movieInfos.getReleaseDate());
        System.out.println(movieInfos.getSynopsis());
        System.out.println(movieInfos.getDistributor());
        System.out.println(movieInfos.getGenres());
        System.out.println(movieInfos.getNationalities());

        //System.out.println(movieInfos.getDirectors());
        for (MoviePerson p : movieInfos.getDirectors()) {
            System.out.println(p.getCode());
            System.out.println(p.getName());
            System.out.println(p.getPhotoURL());
            System.out.println(p.getRole());
        }
        //System.out.println(movieInfos.getWriters());
        /*for (MoviePerson p : movieInfos.getWriters()) {
            System.out.println(p.getCode());
            System.out.println(p.getName());
            System.out.println(p.getPhotoURL());
            System.out.println(p.getRole());
        }*/
        //System.out.println(movieInfos.getActors());
        /*for (MoviePerson p : movieInfos.getActors()) {
            System.out.println(p.getCode());
            System.out.println(p.getName());
            System.out.println(p.getPhotoURL());
            System.out.println(p.getRole());
        }*/
        System.out.println(movieInfos.getUserRating());
        System.out.println(movieInfos.getFestivalAwards());
        //System.out.println(movieInfos.getPosters());

        Map<String,Long> p = movieInfos.getPosters();
        Set<String> keys = new HashSet<>();
        for (Map.Entry<String, Long> entry : p.entrySet()) {
                keys.add(entry.getKey());
        }
        System.out.println(keys);
        System.out.println(movieInfos.getSynopsisShort());
    }

    static List<List<Object>> search(String query) throws AllocineException {

        final Search search = api.searchMovies(query);
        List<List<Object>> filmeInfosList = new ArrayList<>();

        System.out.println("--------------------------------------");
        for (Movie movie : search.getMovies()) {
            List<Object> filmeInfos = new ArrayList<>();
            MovieInfos info = api.getMovieInfos(String.valueOf(movie.getCode()));
            filmeInfos.add(info.getCode());
            filmeInfos.add(info.getTitle());
            filmeInfos.add(info.getProductionYear());

            System.out.println(info.getCode());
            System.out.println(info.getTitle());
            System.out.println(info.getOriginalTitle());
            System.out.println(info.getProductionYear());

            Map<String,Long> p = info.getPosters();
            List<String> keys = new ArrayList<>();
            for (Map.Entry<String, Long> entry : p.entrySet()) {
                keys.add(entry.getKey());
            }
            filmeInfos.add((keys.size() > 0)? keys.get(0) : "N/A");
            filmeInfos.add((info.getSynopsisShort() != null)? info.getSynopsisShort() : "N/A");
            filmeInfosList.add(filmeInfos);

            System.out.println((keys.size() > 0)? keys.get(0) : "N/A");
            System.out.println(info.getSynopsisShort());
            System.out.println("--------------------------------------");
        }

        return filmeInfosList;
    }

    private static final int HTTP_STATUS_300 = 300;
    private static final int HTTP_STATUS_500 = 500;

    private static String requestWebPage(URL url) throws AllocineException {
        try {
            HttpClientWrapper wrapper = new HttpClientWrapper(HttpClients.createDefault());
            wrapper.setUserAgentSelector(new AndroidBrowserUserAgentSelector());

            final HttpGet httpGet = new HttpGet(url.toURI());
            httpGet.setHeader("accept", "application/json");
            httpGet.setHeader(HTTP.USER_AGENT, new AndroidBrowserUserAgentSelector().getUserAgent());

            final DigestedResponse response = DigestedResponseReader.requestContent(wrapper, httpGet, Charset.forName("UTF-8"));

            if (response.getStatusCode() == 0) {
                throw new AllocineException(CONNECTION_ERROR, response.getContent(), response.getStatusCode(), url);
            } else if (response.getStatusCode() >= HTTP_STATUS_500) {
                throw new AllocineException(HTTP_503_ERROR, response.getContent(), response.getStatusCode(), url);
            } else if (response.getStatusCode() >= HTTP_STATUS_300) {
                throw new AllocineException(HTTP_404_ERROR, response.getContent(), response.getStatusCode(), url);
            }

            return response.getContent();
        } catch (URISyntaxException ex) {
            throw new AllocineException(INVALID_URL, "Invalid URL", url, ex);
        } catch (IOException ex) {
            throw new AllocineException(CONNECTION_ERROR, "Error retrieving URL", url, ex);
        }
    }

    private static <T> T readJsonObject(final URL url, final Class<T> object) throws AllocineException {
        final String page = requestWebPage(url);
        ObjectMapper mapper = new ObjectMapper();
        if (StringUtils.isNotBlank(page)) {
            try {
                return mapper.readValue(page, object);
            } catch (IOException ex) {
                LoggerFactory.getLogger("test").info("{}", ex);
                throw new AllocineException(MAPPING_FAILED, "Failed to read JSON object", url, ex);
            }
        }
        throw new AllocineException(MAPPING_FAILED, "Failed to read JSON object", url);
    }

    public static void main(String[] args) {

        System.out.println("Main..");

        final String base = "http://api.allocine.fr/rest/v3/";
        final String method = "showtimelist";
        final String format = "&format=json";
        final String url = base + method;
        try {
            readJsonObject(new URL(url), Search.class);

        } catch (AllocineException | MalformedURLException e) {
            e.printStackTrace();
        }

        /*try {
            //beforeClass();
            //testAccentSearch();
            //testGetMovieInfos();
            //yolo();

        } catch (AllocineException e) {
            e.printStackTrace();
        }*/
    }
}
