package fr.umpc.test;

import com.moviejukebox.allocine.AllocineException;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static fr.umpc.test.main.beforeClass;
import static fr.umpc.test.main.search;

@WebServlet(name = "ServletTest", urlPatterns = {"/myservlet"})
public class ServletTest extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf8");
        response.setContentType("application/json");

        List<List<Object>> filmeInfosList = new ArrayList<>();

        PrintWriter out = response.getWriter();
        JSONObject jsonObj = new JSONObject(request.getParameter("para"));
        //JSONObject jsonObj = (JSONObject) JSONValue.parse(request.getParameter("para"));

        try {
            beforeClass();
            filmeInfosList = search((String)jsonObj.get("titleSearch"));

        } catch (AllocineException e) {
            out.print(e);
            e.printStackTrace();
        }

        JSONArray array = new JSONArray();
        for (List<Object> filmeInfos : filmeInfosList) {
            JSONObject obj = new JSONObject();
            obj.put("code", filmeInfos.get(0));
            obj.put("title", filmeInfos.get(1));
            obj.put("productionYear", filmeInfos.get(2));
            obj.put("posters", filmeInfos.get(3));
            obj.put("synopsisShort", filmeInfos.get(4));
            array.put(obj);
        }
        //obj.put("message", "hello from server");
        out.print(array);
    }
}
