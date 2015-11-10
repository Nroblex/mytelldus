package utils;

import DBmanager.SchemaDevice;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/**
 * Created by ueh093 on 11/10/15.
 */
public class Comparer implements Comparable<Map<Integer, SchemaDevice>> {

    Map<Integer, SchemaDevice> deviceMap;
    public Comparer(Map<Integer, SchemaDevice> deviceMap){
        this.deviceMap=deviceMap;
    }

    public int compareTo(Map<Integer, SchemaDevice> o) {
        Comparable valuea = (Comparable) deviceMap.get(o).getTimePoint();
        Comparable valueb = (Comparable) o.get(deviceMap).getTimePoint();
        return valueb.compareTo(valuea);
    }
}
