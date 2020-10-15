package be.abis.exercise.utilities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
	
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
    public LocalDate unmarshal(String v) throws Exception {   	
        return LocalDate.parse(v,formatter);
    }

    public String marshal(LocalDate v) throws Exception {
        return formatter.format(v);
    }
}
