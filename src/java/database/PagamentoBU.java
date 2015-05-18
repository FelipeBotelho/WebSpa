package database;

import java.sql.*;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;
import contracts.*;

public class PagamentoBU {

    public Connection con;

    public PagamentoBU() {
        con = Conexao.abrirConexao();
    }

    public List<Pagamento> listar() {
        try {
            Statement st = con.createStatement();
            String sql = "select * from pagamento order by vencimento;";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            if (rs != null) {
                List<Pagamento> lista = new ArrayList<Pagamento>();

                while (rs.next()) {
                    Pagamento p = new Pagamento();
                    p.ID = rs.getInt("ID");
                    p.vencimento = rs.getDate("vencimento");
                    p.descricao = rs.getString("descricao");
                    p.valor = rs.getDouble("valor");
                    p.vencido = p.vencimento.compareTo(new java.util.Date()) < 0;
                    p.dia = p.vencimento.getDate();
                    p.mes = p.vencimento.getMonth();
                    p.ano = p.vencimento.getYear();
                    java.util.Date now = new java.util.Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE", Locale.US);
                    p.semana = dateFormat.format(p.vencimento);

                    lista.add(p);
                }
                return lista;
            }

            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            Conexao.fecharConexao(con);
        }
    }

    public List<Semana> GetMonthPayments() {
//        Calendar calStart = Calendar.getInstance();
//        calStart.set(2015, 03, 01);
//        java.util.Date start = calStart.getTime();
//
//        Calendar calEnd = Calendar.getInstance();
//        calStart.set(2015, 05, 01);
//        java.util.Date end = calEnd.getTime();

        con = Conexao.abrirConexao("antevere");
        List<Pagamento> pagamentos = listar();

        List<Semana> semanas = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.YEAR, start.getYear());
//        cal.set(Calendar.MONTH, start.getMonth());
//        cal.set(Calendar.DATE, start.getDate());

        cal.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().getActualMinimum(Calendar.DAY_OF_MONTH));
//        while (cal.get(Calendar.DAY_OF_WEEK) != 1) {
//            cal.add(Calendar.DATE, -1);
//        }

        int month = cal.get(Calendar.MONTH);
        int daysNumber = 0;

//        while (cal.getTime().compareTo(end) == 0) {
//            daysNumber++;
//            cal.add(Calendar.DATE, 1);
//        }

        if (cal.get(Calendar.DAY_OF_WEEK) == 4) {
            daysNumber = 35;
        } else {
            daysNumber = 42;
        }
        while (cal.get(Calendar.DAY_OF_WEEK) != 1) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        for (int i = 0; i < daysNumber / 7; i++) {
            Semana semana = new Semana();
            semana.Numero = cal.get(Calendar.WEEK_OF_MONTH);

            for (int d = 0; d < 7; d++, cal.add(Calendar.DATE, 1)) {
                Dia dia = new Dia();
                dia.Data = cal.getTime();
                for (Pagamento p : pagamentos) {
                    if (getZeroTimeDate(p.vencimento).compareTo(getZeroTimeDate(cal.getTime())) == 0) {
                        dia.Pagamentos.add(p);
                    }
                }

                if (cal.get(Calendar.MONTH) != month) {
                    dia.OutroMes = true;
                }

                semana.Dias.add(dia);
            }

            semanas.add(semana);
        }

        Conexao.fecharConexao(con);
        return semanas;
    }

    private static java.util.Date getZeroTimeDate(java.util.Date date) {
        java.util.Date res = date;
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        res = calendar.getTime();

        return res;
    }
}
