package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import database.Conexao;
import database.PagamentoBU;
import contracts.*;
import java.sql.*;
import java.util.*;
import authentication.ValidadeUser;
import Library.MailManager;

public class Pagamento extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, IllegalStateException {
        /*
         protected void processRequest(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {*/
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            PagamentoBU pg = new PagamentoBU();

            

            new Thread() {
                @Override
                public void run() {
                    MailManager m = new MailManager();
                    //m.send();
                }
            }.start();

            

            JsonResult<List<contracts.Semana>> json = new JsonResult<>();

            try {
                json.Resultado = pg.GetMonthPayments();
                json.Sucesso = true;
            } catch (Exception ex) {
                json.Sucesso = false;
                json.Mensagem = ex.toString();
            }

            out.print(json.serializar());
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, IllegalStateException {
        /*
         protected void processRequest(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {*/
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            PagamentoBU pg = new PagamentoBU();
            pg.con = Conexao.abrirConexao("antevere");

            Gson gson = new GsonBuilder().setDateFormat("dd/MM/yy (EE)").create();

            out.print("");
        }
    }
}
