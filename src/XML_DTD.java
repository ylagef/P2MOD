import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.xml.xpath.*;

public class XML_DTD {
    public static LinkedList listaInterpretes = new LinkedList<Document>(); //Lista donde se almacenarán los intérpretes. Se pone fuera del Main para poder acceder a ella desde cualquier método.

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {

        crearListaInterpretes(listaInterpretes); //Crear la lista con todos los xml

        /*
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();

       for (int j = 0; j < listaInterpretes.size(); j++) { //Mostrar los nombres de todos los interpretes
            System.out.println(getNombreInterpretes((Document) listaInterpretes.get(j), xpath).toString());
        }
        */

    }

    //GESTIÓN DE LOS .XML Y CREAR LA LISTA CON LOS DOCUMENTOS

    public static void crearListaInterpretes(LinkedList<Document> listaInterpretes) throws XPathExpressionException, IOException, SAXException, ParserConfigurationException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        String URL = "sabina.xml";

        LinkedList listaLeidos = new LinkedList<String>(); //AQUÍ SE ALMACENAN LOS QUE YA SE LEYERON Y SE INTRODUJERON EN LISTAINTERPRETES
        LinkedList listaNoLeidos = new LinkedList<String>(); //SE LEYÓ EL CAMPO IML PERO NO SE INTRODUJO EN LISTAINTERPRETES
        LinkedList listaIML = new LinkedList<String>(); //LISTA DE IMLS PARA AÑADIR

        listaNoLeidos.add(URL);

        while (listaNoLeidos.size() > 0) {
            añadirIMLS(listaIML, (String) listaNoLeidos.getFirst()); //Añade a la lista de IML los de los campos IML del doc Sabina
            listaLeidos.add(listaNoLeidos.getFirst()); //Sabina ya está leído
            IMLStoNoLeidos(listaIML, listaNoLeidos, listaLeidos); //Pasa los IML (String) a la lista de los No Leídos (Document)
            listaNoLeidos.removeFirst();
        }

        for (int p = 0; p < listaLeidos.size(); p++) {
            Document docAuxiliar = builder.parse((String) listaLeidos.get(p));
            listaInterpretes.add(docAuxiliar);
        }

    }

    public static void añadirIMLS(LinkedList listaIML, String IN) throws XPathExpressionException, ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();

        Document documento = builder.parse(IN);

        XPathExpression expr = xpath.compile("/Interprete/Album/Cancion/Version/IML/text()");
        NodeList nodes = (NodeList) expr.evaluate(documento, XPathConstants.NODESET);

        for (int i = 0; i < nodes.getLength(); i++) { //Añade los IML del documento a la lista de IMLS
            listaIML.add(nodes.item(i).getNodeValue());
        }

    }

    public static void IMLStoNoLeidos(LinkedList listaIML, LinkedList listaNL, LinkedList listaL) throws ParserConfigurationException, IOException, SAXException {

        for (int i = 0; i < listaIML.size(); i++) { //Encontrar los enlaces a otros IML en los campos versión y añadir los documentos relacionados a la lista
            if (!listaL.contains(listaIML.get(i))) {
                listaNL.add(listaIML.get(i));
            }
        }
        listaIML.clear();
    }

    //MÉTODOS PARA ACCEDER A LA INFORMACIÓN DEPENDIENDO DE LA PANTALLA EN LA QUE SE ESTÉ


    public static List<String> getNombreCanciones(Document doc, XPath xpath) throws XPathExpressionException {
        List<String> list = new ArrayList<>();
        XPathExpression expr = xpath.compile("/Interprete/Album/Cancion/NombreT/text()");
        NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); i++)
            list.add(nodes.item(i).getNodeValue());
        return list;
    }

    public static List<String> getNombreAlbums(Document doc, XPath xpath) throws XPathExpressionException {
        List<String> list = new ArrayList<>();
        XPathExpression expr = xpath.compile("/Interprete/Album/NombreA/text()");
        NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); i++)
            list.add(nodes.item(i).getNodeValue());
        return list;
    }

    //Devuelve una lista de Strings con los Nombres de todos los intérpretes.
    public static LinkedList<String> getNombreInterpretes() throws XPathExpressionException {
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();

        XPathExpression expr = xpath.compile("/Interprete/Nombre/NombreC/text() | /Interprete/Nombre/NombreG/text()");

        for (int i = 0; i < listaInterpretes.size(); i++) {

            NodeList nodes = (NodeList) expr.evaluate(listaInterpretes.get(i), XPathConstants.NODESET);

            for (int j = 0; j < nodes.getLength(); j++)

                listaInterpretes.add(nodes.item(j).getNodeValue());

        }
        return listaInterpretes;
    }
}