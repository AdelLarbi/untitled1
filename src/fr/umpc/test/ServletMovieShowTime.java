package fr.umpc.test;

import com.moviejukebox.allocine.AllocineException;
import com.moviejukebox.allocine.cinox.CinoxApi;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ServletMovieShowTime", urlPatterns = {"/myServletMovieShowTime"})
public class ServletMovieShowTime extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        CinoxApi cinoxApi = new CinoxApi();

        // uncomment to see the difference
        // CinoxApi.DEBUG = 1;
        JSONArray ja = null;
        try {
            ja = cinoxApi.requestMovieList(30);
        } catch (AllocineException e) {
            e.printStackTrace();
        }

        System.out.println("Movielist:\n");

        if (ja != null) {

            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.optJSONObject(i);
                System.out.println("title:       " + jo.optString(CinoxApi.PARAM_TITLE, "N/A"));
                System.out.println("releaseDate: " + jo.optString(CinoxApi.PARAM_RELEASE_DATE, "N/A"));
                System.out.println("posterHref: " + jo.optString(CinoxApi.PARAM_POSTER, "N/A"));
                System.out.println();
            }
        }

        out.print(ja);
    }
}
