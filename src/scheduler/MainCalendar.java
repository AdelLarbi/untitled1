package scheduler;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;

public class MainCalendar {

    private static EJBContainer ejbContainer;
    private static CalendarAutoTimerDemo calculator;

    public static void main(String[] args) {

        ejbContainer = EJBContainer.createEJBContainer();

        try {
            Object object = ejbContainer.getContext().lookup("java:global/simple-stateless/CalendarAutoTimerDemo");

            calculator = (CalendarAutoTimerDemo) object;
        } catch (NamingException e) {
            e.printStackTrace();
        }

        if (ejbContainer != null) {
            ejbContainer.close();
        }
    }
}
