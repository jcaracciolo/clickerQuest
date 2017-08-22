package ar.edu.itba.paw.webapp.DTO;

import javax.xml.bind.Element;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by juanfra on 08/08/17.
 */
@XmlRootElement
public class MapDTO<K, V> {

    @XmlAnyElement
    private List<JAXBElement<V>> elements = new ArrayList<>();

    public MapDTO(){}

    public MapDTO(Map<K,V> map) {
        map.forEach((k, v) ->
                elements.add(
                        new JAXBElement(new QName(k.toString()), v.getClass(), v))
        );
    }
}
