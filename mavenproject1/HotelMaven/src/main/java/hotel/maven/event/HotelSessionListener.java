/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.maven.event;

import java.util.Date;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 *
 * @author cvadmin
 */
public class HotelSessionListener implements HttpSessionListener{
    public static int sessionCount = 0;
    
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        ++sessionCount;
        se.getSession().getServletContext().setAttribute("sessionCount", 
                HotelSessionListener.sessionCount);
        
        String id = se.getSession().getId();
        System.out.println("*** SessionCreated(" + id + ") " 
                + new Date() + " ***");
    
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        --sessionCount;
        se.getSession().getServletContext().setAttribute("sessionCount", 
                HotelSessionListener.sessionCount);
        
        String id = se.getSession().getId();
        System.out.println("*** SessionDestroyed(" + id + ") " 
                + new Date() + " ***");
  
    
    }
    
}
