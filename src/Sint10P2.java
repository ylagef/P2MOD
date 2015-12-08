import javax.servlet.*;
import javax.servlet.http.*;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

public class Sint10P2 extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

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
                    pantalla22(req, res);
                }
                break;

            case "21":
                pantalla31(req, res);
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

        LinkedList<String> listaInterpretes = XML_DTD.getNombreInterpretes();

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

        for (int i = 0; i < listaInterpretes.size(); i++) {
            out.println("<input type='radio' name='" + listaInterpretes.get(i) + "' value='" + listaInterpretes.get(i) + "' checked>" + listaInterpretes.get(i) + "<br>");
        
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
    public void pantalla31(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
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
        out.println("<h2>Consulta 1, Intérprete=" + req.getParameter("interprete") + "</h2>");
        out.println("<h3>Selecciona un álbum:</h3>");
        out.println("<form method='POST' action='?fase=31' >");
        out.println("<input type='hidden' name='interprete' value='" + req.getParameter("interprete") + "'>");
        out.println("<input type='radio' name='album' value='Álbum-1' checked> Álbum 1 <BR>");
        out.println("<input type='radio' name='album' value='Álbum-2'> Álbum 2 <br>");
        out.println("<input type='radio' name='album' value='Álbum-3'> Álbum 3 <br>");
        out.println("<input type='radio' name='album' value='Álbum-4'> Álbum 4 <br>");
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
    public void pantalla22(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
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
        out.println("<h2>Consulta 2</h2>");
        out.println("<h3>Selecciona un año:</h3>");
        out.println("<form method='POST' action='?fase=22' >");
        out.println("<input type='radio' name='anho' value='Año-1' checked> Año 1 <br>");
        out.println("<input type='radio' name='anho' value='Año-2'> Año 2 <br>");
        out.println("<input type='radio' name='anho' value='Año-3'> Año 3 <br>");
        out.println("<input type='radio' name='anho' value='Año-4'> Año 4 <br>");
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

}
