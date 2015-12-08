import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Sint10P2 extends HttpServlet {

    public static LinkedList<Document> listaInterpretes = new LinkedList(); //Lista donde se almacenarán los intérpretes.

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        try {
            crearListaInterpretes(listaInterpretes);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        inicio(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        String valorFase = req.getParameter("fase");
        String valorConsulta = req.getParameter("consulta");

        switch (valorFase) {

            case "1":
                if (valorConsulta.equals("1")) {
                    try {
                        pantalla21(req, res);
                    } catch (XPathExpressionException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        pantalla22(req, res);
                    } catch (XPathExpressionException e) {
                        e.printStackTrace();
                    }
                }
                break;

            case "21":
                try {
                    pantalla31(req, res);
                } catch (XPathExpressionException e) {
                    e.printStackTrace();
                }
                break;

            case "31":
                pantalla41(req, res);
                break;

            case "22":
                pantalla32(req, res);
                break;

            case "32":
                pantalla42(req, res);
                break;

            case "42":
                pantalla52(req, res);
                break;

            case "0":
                inicio(req, res);
                break;

            default:
                inicio(req, res);
                break;
        }

    }

    //IMPRIME LA PANTALLA DE INICIO
    public void inicio(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Práctica 2</title>");
        out.println("<link rel='stylesheet' href='iml.css'>");
        out.println("<link href='https://fonts.googleapis.com/css?family=Quicksand' rel='stylesheet' type='text/css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servicio de consulta de información musical</h1>");
        out.println("<h3>Selecciona una consulta:</h3>");
        out.println("<form method='POST' action='?fase=1'>");
        out.println("<input type='radio' name='consulta' value='1' checked> Lista de canciones de un álbum<br>");
        out.println("<input type='radio' name='consulta' value='2'> Número de canciones de un estilo<br>");
        out.println("<p><input type='submit' value='Enviar' class='btn'>");
        out.println("</form>");
        out.println("<footer><p><span>- Diseñado por Yeray Lage -</span></p><p><span>2015</span></p></footer>");
        out.println("</body>");
        out.println("</html>");
    }

    //FASE 2 DE LA CONSULTA 1
    public void pantalla21(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException, XPathExpressionException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        LinkedList<String> listaNombres = getNombreInterpretes();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Práctica 2</title>");
        out.println("<link rel='stylesheet' href='iml.css'>");
        out.println("<link href='https://fonts.googleapis.com/css?family=Quicksand' rel='stylesheet' type='text/css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servicio de consulta de información musical</h1>");
        out.println("<h2>Consulta 1</h2>");
        out.println("<h3>Selecciona un intéprete:</h3>");
        out.println("<form method='POST' action='?fase=21' >");
        for (int i = 0; i < listaNombres.size(); i++) {
            out.println("<input type='radio' name='interprete' value='" + listaNombres.get(i) + "'> " + listaNombres.get(i) + "<br>");
        }
        out.println("<input type='radio' name='interprete' value='todos'> Todos <br>");
        out.println("<p><input type='submit' value='Enviar' class='btn'><br>");
        out.println("<input type='submit' value='Atrás' onClick='document.forms[0].action=\"?fase=0\"' class='btn'>");
        out.println("<input type='submit' value='Inicio' onClick='document.forms[0].action=\"?fase=0\"' class='btn'>");
        out.println("</form>");
        out.println("<footer><p><span>- Diseñado por Yeray Lage -</span></p><p><span>2015</span></p></footer>");
        out.println("</body>");
        out.println("</html>");
    }

    //FASE 3 DE LA CONSULTA 1
    public void pantalla31(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException, XPathExpressionException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        LinkedList<String> listaAlbums = getAlbumsInterprete(req.getParameter("interprete"));

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Práctica 2</title>");
        out.println("<link rel='stylesheet' href='iml.css'>");
        out.println("<link href='https://fonts.googleapis.com/css?family=Quicksand' rel='stylesheet' type='text/css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servicio de consulta de información musical</h1>");
        out.println("<h2>Consulta 1, Intérprete=" + req.getParameter("interprete") + "</h2>");
        out.println("<h3>Selecciona un álbum:</h3>");
        out.println("<form method='POST' action='?fase=31' >");
        out.println("<input type='hidden' name='interprete' value='" + req.getParameter("interprete") + "'>");
        for (int i = 0; i < listaAlbums.size(); i++) {
            out.println("<input type='radio' name='" + listaAlbums.get(i) + "' value='" + listaAlbums.get(i) + "'> " + listaAlbums.get(i) + "<br>");
        }
        out.println("<input type='radio' name='album' value='todos'> Todos <br>");
        out.println("<p><input type='submit' value='Enviar' class='btn'><br>");
        out.println("<input type='submit' value='Atrás' onClick='document.forms[0].action=\"?fase=1&consulta=1\"' class='btn'>");
        out.println("<input type='submit' value='Inicio' onClick='document.forms[0].action=\"?fase=0\"' class='btn'>");
        out.println("</form>");
        out.println("<footer><p><span>- Diseñado por Yeray Lage -</span></p><p><span>2015</span></p></footer>");
        out.println("</body>");
        out.println("</html>");
    }

    //FASE 4 DE LA CONSULTA 1
    public void pantalla41(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Práctica 2</title>");
        out.println("<link rel='stylesheet' href='iml.css'>");
        out.println("<link href='https://fonts.googleapis.com/css?family=Quicksand' rel='stylesheet' type='text/css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servicio de consulta de información musical</h1>");
        out.println("<h2>Consulta 1, Intérprete=" + req.getParameter("interprete") + ", Álbum=" + req.getParameter("album") + "</h2>");
        out.println("<h3>Estas son sus canciones:</h3>");
        out.println("<form method='POST' action='?fase=41' >");
        out.println("<input type='hidden' name='interprete' value='" + req.getParameter("interprete") + "'>");
        out.println("<input type='hidden' name='album' value='" + req.getParameter("album") + "'>");
        out.println("<li> Canción 1 (duración, descripción) <br>");
        out.println("<li> Canción 2 (duración, descripción) <br>");
        out.println("<li> Canción 3 (duración, descripción) <br>");
        out.println("<li> Canción 4 (duración, descripción) <br>");
        out.println("<input type='submit' value='Atrás' onClick='document.forms[0].action=\"?fase=21\"' class='btn'>");
        out.println("<input type='submit' value='Inicio' onClick='document.forms[0].action=\"?fase=0\"' class='btn'>");
        out.println("</form>");
        out.println("<footer><p><span>- Diseñado por Yeray Lage -</span></p><p><span>2015</span></p></footer>");
        out.println("</body>");
        out.println("</html>");
    }

    /////////////////////////////////////////////////// CONSULTA 2 ////////////////////////////////////////////////

    //FASE 2 DE LA CONSULTA 2
    public void pantalla22(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException, XPathExpressionException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        LinkedList<String> listaAnhos = getAnhos();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Práctica 2</title>");
        out.println("<link rel='stylesheet' href='iml.css'>");
        out.println("<link href='https://fonts.googleapis.com/css?family=Quicksand' rel='stylesheet' type='text/css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servicio de consulta de información musical</h1>");
        out.println("<h2>Consulta 2</h2>");
        out.println("<h3>Selecciona un año:</h3>");
        out.println("<form method='POST' action='?fase=22' >");
        for (int i = 0; i < listaAnhos.size(); i++) {
            out.println("<input type='radio' name='anho' value='" + listaAnhos.get(i) + "'> " + listaAnhos.get(i) + "<br>");
        }
        out.println("<input type='radio' name='anho' value='todos'> Todos <br>");
        out.println("<p><input type='submit' value='Enviar' class='btn'><br>");
        out.println("<input type='submit' value='Atrás' onClick='document.forms[0].action=\"?fase=0\"' class='btn'>");
        out.println("<input type='submit' value='Inicio' onClick='document.forms[0].action=\"?fase=0\"' class='btn'>");
        out.println("</form>");
        out.println("<footer><p><span>- Diseñado por Yeray Lage -</span></p><p><span>2015</span></p></footer>");
        out.println("</body>");
        out.println("</html>");
    }

    //FASE 3 DE LA CONSULTA 2
    public void pantalla32(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Práctica 2</title>");
        out.println("<link rel='stylesheet' href='iml.css'>");
        out.println("<link href='https://fonts.googleapis.com/css?family=Quicksand' rel='stylesheet' type='text/css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servicio de consulta de información musical</h1>");
        out.println("<h2>Consulta 2, Año=" + req.getParameter("anho") + "</h2>");
        out.println("<h3>Selecciona un álbum:</h3>");
        out.println("<form method='POST' action='?fase=32' >");
        out.println("<input type='hidden' name='anho' value='" + req.getParameter("anho") + "'>");
        out.println("<input type='radio' name='album' value='Álbum-1' checked> Álbum 1 <br>");
        out.println("<input type='radio' name='album' value='Álbum-2'> Álbum 2 <br>");
        out.println("<input type='radio' name='album' value='Álbum-3'> Álbum 3 <br>");
        out.println("<input type='radio' name='album' value='Álbum-4'> Álbum 4 <br>");
        out.println("<input type='radio' name='album' value='todos'> Todos <br>");
        out.println("<p><input type='submit' value='Enviar' class='btn'><br>");
        out.println("<input type='submit' value='Atrás' onClick='document.forms[0].action=\"?fase=1&consulta=2\"' class='btn'>");
        out.println("<input type='submit' value='Inicio' onClick='document.forms[0].action=\"?fase=0\"' class='btn'>");
        out.println("</form>");
        out.println("<footer><p><span>- Diseñado por Yeray Lage -</span></p><p><span>2015</span></p></footer>");
        out.println("</body>");
        out.println("</html>");
    }

    //FASE 4 DE LA CONSULTA 2
    public void pantalla42(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Práctica 2</title>");
        out.println("<link rel='stylesheet' href='iml.css'>");
        out.println("<link href='https://fonts.googleapis.com/css?family=Quicksand' rel='stylesheet' type='text/css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servicio de consulta de información musical</h1>");
        out.println("<h2>Consulta 2, Año=" + req.getParameter("anho") + ", Álbum=" + req.getParameter("album") + "</h2>");
        out.println("<h3>Selecciona un estilo:</h3>");
        out.println("<form method='POST' action='?fase=42' >");
        out.println("<input type='hidden' name='anho' value='" + req.getParameter("anho") + "'>");
        out.println("<input type='hidden' name='album' value='" + req.getParameter("album") + "'>");
        out.println("<input type='radio' name='estilo' value='Estilo-1' checked> Estilo 1 <br>");
        out.println("<input type='radio' name='estilo' value='Estilo-2'> Estilo 2 <br>");
        out.println("<input type='radio' name='estilo' value='Estilo-3'> Estilo 3 <br>");
        out.println("<input type='radio' name='estilo' value='Estilo-4'> Estilo 4 <br>");
        out.println("<input type='radio' name='estilo' value='todos'> Todos <br>");
        out.println("<p><input type='submit' value='Enviar' class='btn'><br>");
        out.println("<input type='submit' value='Atrás' onClick='document.forms[0].action=\"?fase=22\"' class='btn'>");
        out.println("<input type='submit' value='Inicio' onClick='document.forms[0].action=\"?fase=0\"' class='btn'>");
        out.println("</form>");
        out.println("<footer><p><span>- Diseñado por Yeray Lage -</span></p><p><span>2015</span></p></footer>");
        out.println("</body>");
        out.println("</html>");
    }

    //FASE 5 DE LA CONSULTA 2
    public void pantalla52(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Práctica 2</title>");
        out.println("<link rel='stylesheet' href='iml.css'>");
        out.println("<link href='https://fonts.googleapis.com/css?family=Quicksand' rel='stylesheet' type='text/css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servicio de consulta de información musical</h1>");
        out.println("<h2>Consulta 2, Año=" + req.getParameter("anho") + ", Álbum=" + req.getParameter("album") + ", Estilo=" + req.getParameter("estilo") + "</h2>");
        out.println("<form method='POST' action='fase=52' >");
        out.println("<input type='hidden' name='anho' value='" + req.getParameter("anho") + "'>");
        out.println("<input type='hidden' name='album' value='" + req.getParameter("album") + "'>");
        out.println("<input type='hidden' name='estilo' value='" + req.getParameter("estilo") + "'>");
        out.println("<h5> El número de canciones es de: 10 </h5>");
        out.println("<input type='submit' value='Atrás' onClick='document.forms[0].action=\"?fase=32\"' class='btn'>");
        out.println("<input type='submit' value='Inicio' onClick='document.forms[0].action=\"?fase=0\"' class='btn'>");
        out.println("</form>");
        out.println("<footer><p><span>- Diseñado por Yeray Lage -</span></p><p><span>2015</span></p></footer>");
        out.println("</body>");
        out.println("</html>");
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


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


    ////////                            MÉTODOS PARA ACCEDER A LA INFORMACIÓN DEPENDIENDO DE LA PANTALLA EN LA QUE SE ESTÉ


    //Devuelve una lista de Strings con los Nombres de todos los intérpretes.
    public static LinkedList<String> getNombreInterpretes() throws XPathExpressionException {

        LinkedList<String> interpretes = new LinkedList<>();

        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();

        XPathExpression expr = xpath.compile("/Interprete/Nombre/NombreC/text() | /Interprete/Nombre/NombreG/text()");

        for (int i = 0; i < listaInterpretes.size(); i++) {

            Document doc = listaInterpretes.get(i);

            NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

            for (int j = 0; j < nodes.getLength(); j++)
                if (!interpretes.contains(nodes.item(j).getNodeValue())) {
                    interpretes.add(nodes.item(j).getNodeValue());
                }
        }
        return interpretes;
    }

    public static LinkedList<String> getAlbumsInterprete(String interprete) throws XPathExpressionException {
        LinkedList<String> albums = new LinkedList<>();

        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();

        XPathExpression exprAlbum = xpath.compile("/Interprete/Album/Cancion/NombreT/text()");
        XPathExpression exprInterprete = xpath.compile("/Interprete/Nombre/NombreC/text() | /Interprete/Nombre/NombreG/text()");

        for (int i = 0; i < listaInterpretes.size(); i++) {

            Document doc = listaInterpretes.get(i);

            NodeList nodesI = (NodeList) exprInterprete.evaluate(doc, XPathConstants.NODESET);
            NodeList nodesA = (NodeList) exprAlbum.evaluate(doc, XPathConstants.NODESET);

            if (nodesI.item(i).getNodeValue() == interprete) {
                for (int j = 0; j < nodesI.getLength(); j++) {
                    if (!albums.contains(nodesA.item(j).getNodeValue())) {
                        albums.add(nodesA.item(j).getNodeValue());
                    }
                }
            }
        }

        return albums;
    }

    public static List<String> getCancionesAlbum(String interprete, String album) throws XPathExpressionException {
        LinkedList<String> canciones = new LinkedList<>();

        return canciones;
    }

    public static LinkedList<String> getAnhos() throws XPathExpressionException {

        LinkedList<String> anhos = new LinkedList<>();

        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();

        XPathExpression expr = xpath.compile("/Interprete/Album/Año/text()");

        for (int i = 0; i < listaInterpretes.size(); i++) {

            Document doc = listaInterpretes.get(i);

            NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

            for (int j = 0; j < nodes.getLength(); j++)

                if (!anhos.contains(nodes.item(j).getNodeValue())) {
                    anhos.add(nodes.item(j).getNodeValue());
                }
        }
        return anhos;
    }


}
