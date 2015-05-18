/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contracts;

import database.PagamentoBU;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lucas
 */
public class Dia {
    
    public List<Pagamento> Pagamentos;
    public boolean OutroMes;
    public java.util.Date Data;

    public Dia() {
        Pagamentos = new ArrayList<>();
    }
}
