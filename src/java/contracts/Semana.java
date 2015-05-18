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
public class Semana {

    public int Numero;
    public List<Dia> Dias;

    public Semana() {
        Dias = new ArrayList<>();
    }
}
