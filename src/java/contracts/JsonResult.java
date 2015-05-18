/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contracts;
import com.google.gson.GsonBuilder;
import com.google.gson.Gson;

/**
 *
 * @author lucas
 * @param <T>
 */
public class JsonResult<T> {
    public boolean Sucesso;
    public String Mensagem;
    public T Resultado;
            
    public String serializar(){
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        return gson.toJson(this);
    }
}
