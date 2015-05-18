/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import database.Conexao;
import database.PagamentoBU;
import database.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import contracts.*;
import authentication.ValidadeUser;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Login extends HttpServlet {

    private class User {

        public String userName;
        public String password;
    }

    private class Response {

        public String Token;
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, IllegalStateException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            Gson gson = new GsonBuilder().setDateFormat("dd/MM/yy (EE)").create();
            User user = gson.fromJson(request.getParameter("data"), User.class);
            JsonResult<Response> json = new JsonResult<>();
            
            try {

                Usuario usuario = new Usuario();
                if (!usuario.ValidaUsuario(user.userName, user.password)) {
                    json.Mensagem = "Usuário ou senha inválido.";
                    json.Sucesso = false;
                } else {
                    KeyGenerator keyGen = KeyGenerator.getInstance("AES");
                    keyGen.init(256);
                    SecretKey secretKey = keyGen.generateKey();
                    byte[] keyByte = secretKey.getEncoded();

                    AutenticaUsuario.AddKey(user.userName, keyByte);
                    ValidadeUser v = new ValidadeUser();
                    String token = v.GenerateToken(user.userName, secretKey, null);

                    Response r = new Response();
                    r.Token = token;

                    json.Sucesso = true;
                    json.Resultado = r;
                }

            } catch (Exception e) {
                json.Sucesso = false;
                json.Mensagem = e.toString();
            }
            out.println(json.serializar());
        }
    }
}
