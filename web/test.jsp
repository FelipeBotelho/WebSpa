<%-- 
    Document   : test
    Created on : Apr 25, 2015, 4:49:46 PM
    Author     : lucas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <%@ page import="authentication.AuthTest" %>
        <%@ page import="com.nimbusds.jose.*" %>
        <%@ page import="com.nimbusds.jose.crypto.*" %>
        <%@ page import="com.nimbusds.jwt.*" %>
        <%@ page import="authentication.ValidadeUser" %>
        <%@ page import="javax.crypto.KeyGenerator" %>
        <%@ page import="javax.crypto.SecretKey" %>

        <%
            AuthTest a = new AuthTest();

            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256);
            SecretKey secretKey = keyGen.generateKey();

            String jweString = a.Test(secretKey);

            ValidadeUser v = new ValidadeUser();
            
            String token = v.GenerateToken("Lucas", secretKey, null);
                        
            out.println(token);
            
            boolean valido = v.ValidateToken(token, secretKey);
            
            out.println(valido);
        %>


    </body>
</html>
