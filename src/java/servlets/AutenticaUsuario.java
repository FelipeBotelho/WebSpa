/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import authentication.ValidadeUser;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author lucas
 */
public class AutenticaUsuario implements Filter {

    public static Map<String, byte[]> keys;

    public class Token {

        public String userName;
        public String token;
    }

    public static void AddKey(String userName, byte[] key) {
        if (keys == null) {
            keys = new HashMap<String, byte[]>();
        }
        keys.put(userName, key);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        
        chain.doFilter(request, response);
        
        
        
        try {
            String json = httpRequest.getHeader("Authorization");
            String url = httpRequest.getRequestURI();
            
            if (json == null || json.equals("null")) {
                httpResponse.sendError(401, "Usuário não autorizado.");
                return;
            }

            Gson gson = new Gson();
            Token token = gson.fromJson(json, Token.class);

            if (keys == null || !keys.containsKey(token.userName)) {
                httpResponse.sendError(401, "Usuário não autorizado.");
                return;
            }

            SecretKey key = new SecretKeySpec(keys.get(token.userName), "AES");

            ValidadeUser v = new ValidadeUser();
            boolean eValido = v.ValidateToken(token.token, key);

            if (!eValido) {
                keys.remove(token.userName);
                httpResponse.sendError(401, "Usuário não autorizado.");
                return;
            } else {
                chain.doFilter(request, response);
                return;
            }
        } catch (Exception ex) {
            httpResponse.sendError(401, "Usuário não autorizado. Erro na autenticação: " + ex.toString());
            return;
        }
    }

    @Override
    public void destroy() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
