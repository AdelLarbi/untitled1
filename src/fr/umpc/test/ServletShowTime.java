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

@WebServlet(name = "ServletShowTime", urlPatterns = {"/myServletShowTime"})
public class ServletShowTime extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("LOG: myServletShowTime");

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JSONObject jsonObj = new JSONObject(request.getParameter("para"));

        String movieTitle = jsonObj.optString("title", "N/A");
        int zoneCode = jsonObj.optInt("code", -1);

        CinoxApi cinoxApi = new CinoxApi();

        // uncomment to see the difference
        // CinoxApi.DEBUG = 2;
        JSONArray ja = null;
        try {
            ja = cinoxApi.requestShowtimeList(movieTitle, String.valueOf(zoneCode));
        } catch (AllocineException e) {
            e.printStackTrace();
        }

        if (ja != null && ja.length() > 0) {
            System.out.println("movieTitle = \"" + movieTitle + "\", zoneCode = \"" + zoneCode + "\"\n");

            for(int i = 0; i < ja.length(); ++i) {
                JSONObject jo = ja.optJSONObject(i);
                System.out.println("name:       " + jo.optString("name", "N/A"));
                System.out.println("adress:     " + jo.optString("adress", "N/A"));
                System.out.println("postalCode: " + jo.optString("postalCode", "N/A"));
                System.out.println("city:       " + jo.optString("city", "N/A"));
                System.out.println("date:       " + jo.optString("date", "N/A"));
                System.out.println("times:      " + jo.optJSONArray("time"));
                System.out.println();
            }

        } else {
            System.out.println("Aucune résultat trouvée pour cette recherche");
        }

        out.print(ja);
    }
}
