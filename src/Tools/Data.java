/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Zamba
 */
public class Data {
    
    private Calendar data;
    
    public Data(Calendar data){
        this.data = data;
    }

    @Override
    public String toString() {
        return data.get(Calendar.YEAR)+"-"+data.get(Calendar.MONTH)+"-"+data.get(Calendar.DAY_OF_MONTH)+" "+
                data.get(Calendar.HOUR)+":"+data.get(Calendar.MINUTE)+":"+data.get(Calendar.SECOND);
    }
    
    public static String toString(Date data) {
        return (data.getYear()+1900)+"/"+(data.getMonth()+1)+"/"+data.getDate()+" "+
                data.getHours()+":"+data.getMinutes()+":"+data.getSeconds();
    }
    
    public static Date FormatarData(String d){
        Date data = new Date();
        String [] l = d.split("/");
        
        data.setYear(Integer.parseInt(l[0])-1900);
        data.setMonth(Integer.parseInt(l[1])-1);
        data.setDate(Integer.parseInt(l[2]));
        return data;
    }
    
    public static Date FormatarData2(String d){
        Date data = new Date();
        String [] l = d.split("/");
        
        data.setYear(Integer.parseInt(l[0]));
        data.setMonth(Integer.parseInt(l[1]));
        data.setDate(Integer.parseInt(l[2]));
        return data;
    }
}
