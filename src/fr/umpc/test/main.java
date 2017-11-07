package fr.umpc.test;

import com.moviejukebox.allocine.AllocineApi;
import com.moviejukebox.allocine.AllocineException;
import com.moviejukebox.allocine.model.*;
import org.yamj.api.common.http.AndroidBrowserUserAgentSelector;
import org.yamj.api.common.http.HttpClientWrapper;

import org.apache.http.impl.client.HttpClients;

import java.util.*;

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

    public static void testGetMovieInfos() throws AllocineException {

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

    public static void main(String[] args) {

        System.out.println("Main..");

        try {
            beforeClass();
            //testAccentSearch();
            //testGetMovieInfos();
            //yolo();

        } catch (AllocineException e) {
            e.printStackTrace();
        }
    }
}
