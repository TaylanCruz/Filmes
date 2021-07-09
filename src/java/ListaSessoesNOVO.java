
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Taylan
 */
@WebServlet(name = "ListaSessoes", urlPatterns = {"/ListaSessoes"})
public class ListaSessoesNOVO extends HttpServlet {

    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/pweb3";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String saida = null;
        Connection connection = null; // gerencia a conexÃƒÂ£o
        Statement statement = null;

        try (PrintWriter out = response.getWriter()) {
            Class.forName(JDBC_DRIVER); // carrega classe de driver do banco de dados

            // estabelece conexao com o banco de dados
            connection = DriverManager.getConnection(DATABASE_URL, "postgres", "ella4s");
            // cria Statement para consultar banco de dados
            statement = connection.createStatement();
            Statement st;
            st = connection.createStatement();
            ResultSet rec; //COMENTAR
            rec = st.executeQuery(
                    "SELECT *\n" + "  FROM public.filmes inner  join public.sessoes\n"
                    + "on public.filmes.fk_numero_sessao = public.sessoes.numero_sessao");
            if (rec.next()) {
                // consulta o banco de dados 
                ResultSet resultSet = statement.executeQuery(
                        "SELECT *\n" + "  FROM public.filmes inner  join public.sessoes\n"
                        + "on public.filmes.fk_numero_sessao = public.sessoes.numero_sessao");
                // processa resultados da consulta
                //saida = "<h1>FILMES EM CARTAZ:</h1><br /><h1/><br />";
                /*saida = saida + "<tr>  <th>Nome do Filme</th> </tr>";
                while (rec.next()) {
                    saida = saida + "<tr>"
                            + "<td>" + rec.getString("nome_filme") + "</td>"
                            //+ "<td>" + rec.getString("data_sessao") + "</td>"
                            ;
                    //saida = saida + "<input type='submit'>";
                };*/

                saida = saida + "<table border='1'  bordercolor=black  align= 'center'>";
                saida = "<h1>Informe se o ingresso será meia ou inteira:</h1><br /><h1/><br /><form>";

                out.println("<td><input type='radio' name='radio' value='1'>Meia</td>");
                out.println("<td><input type='radio' name='radio' value='2'>Inteira</td>");
                out.println("</table>");

                ResultSetMetaData metaData = resultSet.getMetaData();
                int numberOfColumns = metaData.getColumnCount();
                /////////////////////////////////////////////////////////
                //saida = "<h1>LISTA DE FILMES EM CARTAZ:</h1><br /><h1/><br/><form>";
                //saida = saida + "<tr> <th>Nome do filme</th> <th> Selecionar </th> </tr>";
                saida = "<h1>"+ "<center>" +"Filmes em cartaz: "+"<center>\n"+"</h1><br /><h1/><br /><form>"
                        + "<th>Nome do Filme</th> "
                        + "<th>Horário</th> "
                        + "<th>Número da Sala</th> "
                        + "<th>Data da Sessão</th>"
                        + "<th>Código do Filme</th>"
                        + "<th>Número da Sessão</th>"
                        + "<th> Selecionar </th></tr>";
                while (resultSet.next()) {
                    saida = saida + "<tr>"
                            + "<td>" + resultSet.getString("nome_filme") + "</td>"
                            + "<td>" + resultSet.getString("horario") + "</td>"
                            + "<td>" + resultSet.getString("numero_sala") + "</td>"
                            + "<td>" + resultSet.getString("data_sessao") + "</td>"
                            + "<td>" + resultSet.getString("codigo_filme") + "</td>"
                            + "<td>" + resultSet.getString("numero_sessao") + "</td>";
                    saida = saida + "<td> <input type=\"radio\" id=\"codigo_filme\"\n"
                            + "name=\"filmes\" </td>";
                    saida = saida + "</tr>";/*o radio button tem um valor(value) que ao selecioná-lo e clicar em enviar eu envio
                    as informações de código da turma e código da disciplina que estão dentro do array codigos [0] e codigo[1]*/
                }
                saida = saida + "<input type='submit' value=\"Selecionar1\"/>";
                /////////////////////////////////////////////////////////////////
                /*while (resultSet.next()) {
                    saida = saida + "<tr>"
                            + "<td>" + resultSet.getString("nome_filme") + "</td>";

                    saida = saida + "<td> <input type=\"radio\" id=\"nome_filme\"\n"
                            + "name=\"filme\" value=" + resultSet.getString("nome_filme") + "</td>";
                }*/
                saida = saida + "<input type='submit' value=\"Selecionar2\"/>";
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Lista 2</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<body bgcolor=\"beige\">");
                
                out.println("<table border='1'  bordercolor=black  align= 'center' width {background-color: 12;}\n" + ">"
                        + "<form action='ListaFilmeNOVO' method='GET'>" + saida + "</form></table>");
                
                //out.println("<table border='1' ><form action='ListaFilmeNOVO' method='GET'>" + "</form></table>");
                //out.println("<table border='1'  align= 'center' >" + saida + "</table>");
                out.println("</body>");
                out.println("</html>");
            }
            //saida = saida + "<input type='submit' value=\"Comprar1\"/>";
            out.println("<br /> <center>\n" +"<input type=\"button\" value=\"Voltar\" onClick=\"history.go(-1)\" />" +" </center>\n");
            out.close();
        } // fim do try
        catch (SQLException | ClassNotFoundException sqlException) {
            sqlException.printStackTrace();
            return;
        } // fim do catch
        // fim do catch
        finally // assegura que a instruÃƒÂ§ÃƒÂ£o e conexÃƒÂ£o sÃƒÂ£o fechadas adequadamente
        {
            try {
                statement.close();
                connection.close();
            } // fim do try
            catch (Exception exception) {
                exception.printStackTrace();
                return;
            } // fim do catch
        } // fim do finally                                             

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
