/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contracts;

/**
 *
 * @author lucas
 */
public class Pagamento {

    public int ID;
    public java.util.Date vencimento;
    public int dia;
    public int mes;
    public int ano;
    public String semana;
    public String descricao;
    public Double valor;
    public boolean vencido;
}
