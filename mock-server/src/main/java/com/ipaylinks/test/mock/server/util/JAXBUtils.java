package com.ipaylinks.test.mock.server.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;

/**
 * JAVA对象与XML转换
 * @author Libo
 * Created by Libo on 2017/12/5.
 */
public class JAXBUtils {
    private static final String ENCODING = "UTF-8";
    private static Logger logger = LoggerFactory.getLogger(JAXBUtils.class);

    public static <T> T formXML(String xml,Class<T> clazz) {
        JAXBContext jaxbContext = null;
        T object = null;
        if (xml != null && !"".equals(xml)) {
            try {
                jaxbContext = JAXBContext.newInstance(clazz);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(xml.getBytes(ENCODING));
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                JAXBElement<T> jaxbElement = unmarshaller.unmarshal(new StreamSource(byteArrayInputStream), clazz);
                object = (T) jaxbElement.getValue();
            } catch (Exception e) {
                logger.error("error when unmarshalling from a xml string",e);
            }
        }
        return object;
    }
    
    public static <T> String toXML(T object) {
        String xml = "";
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            // 富友接口xml不格式化
            // marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            // 设置编码方式
            marshaller.setProperty(Marshaller.JAXB_ENCODING, ENCODING);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            marshaller.marshal(object, byteArrayOutputStream);
            byte[] buf = byteArrayOutputStream.toByteArray();
            xml = new String(buf, 0, buf.length, ENCODING);
            // 富友xml接口，xml头不能出现standalone信息
            xml = xml.replace("standalone=\"yes\"", "");
        } catch (Exception e) {
            logger.error("error when marshalling to a xml string",e);
        }
        return xml;
    }

    public static <T> T formXMLIgnoreNamespace(String xml,Class<T> clazz) {
        JAXBContext jaxbContext = null;
        T object = null;
        if (xml != null && !"".equals(xml)) {
            try {
                jaxbContext = JAXBContext.newInstance(clazz);
                StringReader reader = new StringReader(xml);
                SAXParserFactory sax = SAXParserFactory.newInstance();
                sax.setNamespaceAware(false);
                XMLReader xmlReader = sax.newSAXParser().getXMLReader();
                Source source = new SAXSource(xmlReader, new InputSource(reader));
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                JAXBElement<T> jaxbElement = unmarshaller.unmarshal(source, clazz);
                object = (T) jaxbElement.getValue();
            } catch (Exception e) {
                logger.error("error when unmarshalling from a xml string",e);
            }
        }
        return object;
    }


}
