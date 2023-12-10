package proxyFlyweight;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * The flyweight design pattern is used in this application,
 * to such that the user will  only create one proxy object for any given event id, 
 * for example:
 * If the user navigates to page 2 when browsing,
 * then returns to age one, we don't want the user to generate a new set of proxies, 
 * but rather use the ones already created when first browsing the page.
 * The same logic will apply when a user checks the details of an event,
 * then returns to  the browsing page. 
 */

//This factory is responsible for creating all proxy objects
public class proxyFactory {
    //storage for all proxy objects created by the user
    private static final Map<Integer,proxyEvent> proxyStorage = new HashMap<>();
    //Checking the existence of a proxy for this event before creating a new one.
    public static proxyEvent getProxyEvent(int id, String name, String location, Date date   ) {
        if(!proxyStorage.containsKey(id)) {
            proxyStorage.put(id, new proxyEvent(id, name, location, date));
        }
        return proxyStorage.get(id);
    }
}
