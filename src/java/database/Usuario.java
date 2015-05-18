/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import contracts.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author lucas
 */
public class Usuario {

    public Connection con;

    public Usuario() {
        con = Conexao.abrirConexao();
    }

    public boolean ValidaUsuario(String usuario, String senha) {
        try {
            Statement st = con.createStatement();
            String sql = "select * from usuario where usuario = '" + usuario + "' and senha = '" + senha + "';";
            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            if (rs == null) {
                return false;
            }

            int count = 0;

            while (rs.next()) {
                ++count;
            }

            if (count != 1) {
                return false;
            }

            return true;
        } catch (Exception e) {
            return false;
        } finally{
            Conexao.fecharConexao(con);
        }
    }
}
