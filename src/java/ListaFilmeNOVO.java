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
@WebServlet(urlPatterns = {"/ListaFilmeNOVO"})
public class ListaFilmeNOVO extends HttpServlet {
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
        Connection connection = null;// gerencia a conexÃƒÂ£o
        Statement statement = null;
        try (PrintWriter out = response.getWriter()) {
        Class.forName(JDBC_DRIVER); // carrega classe de driver do banco de dados

            // estabelece conexao com o banco de dados
            connection = DriverManager.getConnection(DATABASE_URL, "postgres", "ella4s");
            // cria Statement para consultar banco de dados
            statement = connection.createStatement();
            Statement st;
            String[] codigos = request.getParameter("filme").split(",");

            st = connection.createStatement();//statement guarda o código dentro de uma string e envia para o banco de dados.
            ResultSet rec;
            ResultSet rec2;

            rec = st.executeQuery(
                    "SELECT *\n" + "  FROM public.filmes inner  join public.sessoes\n"
                    + "on public.filmes.fk_numero_sessao = public.sessoes.numero_sessao");/*comparo o codigo do filme selecionada 
            com numero_sessao(numero da sessao)*/
            saida = "<h1>" + "<center>" + "Informações do Filme:" + "<center>\n" + "</h1><br /><h1/><br />";
            saida = saida + "<tr>  <th>Nome do Filme</th> "+ "<th>Horário do Filme</th> <th>Número da Sala</th> "
                    + "<th>Data da Sessão</th>" + "</tr>";

            while (rec.next()) {
                //String dia_campo1 = rec.getString("dia_campo1");
                //String dia_campo2 = rec.getString("dia_campo2");
                //String dia_campo3 = rec.getString("dia_campo3");

                saida = saida + "<tr>"
                        + "<td>" + rec.getString("nome_filme") + "</td>"
                        + "<td>" + rec.getString("horario") + "</td>"
                        + "<td>" + rec.getString("numero_sala") + "</td>"
                        + "<td>" + rec.getString("data_sessao") + "</td>"
                        //+ "<td>" + horario(dia_campo1) + horario(dia_campo2) + horario(dia_campo3) + "</td>"
                        ;

                //saida = saida + "<input type='submit'>";
            }
            saida = saida + "</table><table border='1'  bordercolor=black  align= 'center'>";/*fechando a tabela disciplina para fazer outra
            tabela para exibir os alunos*/
            /*rec2 = st.executeQuery(
                    "SELECT * FROM alunos WHERE codigo_Disciplina = '" + codigos[1] + "';");
            saida = saida + "<tr> <th>Matrícula do aluno</th> <th>Nome do aluno</th> <th>Código do curso</th></tr>";
            while (rec2.next()) {
                saida = saida + "<tr>"
                        + "<td>" + rec2.getString("matricula") + "</td>"
                        + "<td>" + rec2.getString("nome_aluno") + "</td>"
                        + "<td>" + rec2.getString("codigo_curso") + "</td>";
            }*/

// fim do try
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Lista 1</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<body bgcolor=\"#a3a3c2\">");
            out.println("<table border='1'  bordercolor=black  align= 'center'>" + saida + "</table>");

            out.println("</body>");
            out.println("</html>");
            out.println("<br /> <center>\n" +"<input type=\"button\" value=\"Comprar2\"/>"+" </center>\n");
            out.println("<br /> <center>\n" + " <input type=\"button\" value=\"Cancelar\" onClick=\"history.go(-1)\"    </center>\n");

            out.close();
        } catch (SQLException | ClassNotFoundException sqlException) {
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
