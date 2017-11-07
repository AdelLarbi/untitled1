package dao;

import util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class Test {

	public static void main(String[] args) {
		//session factory
		SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();
        
        //Class
        User c2 = new User("yolo", "yyoo");
        session.save(c2);

        session.getTransaction().commit();
        session.close();
	}
}
