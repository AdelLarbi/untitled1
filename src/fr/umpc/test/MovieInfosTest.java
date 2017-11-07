package fr.umpc.test;

import com.moviejukebox.allocine.AllocineException;
import dao.MyMovieInfos;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;

import static fr.umpc.test.main.beforeClass;
import static fr.umpc.test.main.search;

public class MovieInfosTest {

    static void start() throws AllocineException {
        //session factory
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();

        List<List<Object>> filmeInfosList = new ArrayList<>();
        try {
            beforeClass();
            filmeInfosList = search("alone");

        } catch (AllocineException e) {
            e.printStackTrace();
        }

        for (List<Object> filmeInfos : filmeInfosList) {
            //Class
            MyMovieInfos c2 = new MyMovieInfos(
                    (Integer)filmeInfos.get(0),
                    (String)filmeInfos.get(1),
                    (Integer)filmeInfos.get(2),
                    (String)filmeInfos.get(3),
                    (String)filmeInfos.get(4)
            );
            session.save(c2);
        }

        session.getTransaction().commit();
        session.close();
    }

    public static void main(String[] args) {
        try {
            start();
        } catch (AllocineException e) {
            e.printStackTrace();
        }
    }
}
