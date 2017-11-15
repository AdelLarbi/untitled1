package scheduler;

import java.util.Date;
import javax.ejb.*;

@Stateless
public class CalendarAutoTimerDemo {

    @Schedule(second="*", minute="*", hour="*")
    public void execute(Timer timer) {
        System.out.println("Executing ...");
        System.out.println("Execution Time : " + new Date());
        System.out.println("____________________________________________");
    }

}
