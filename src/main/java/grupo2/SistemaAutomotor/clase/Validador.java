package grupo2.SistemaAutomotor.clase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validador {

    public boolean isNotDNI(String str) {
        if (str == null || str.isEmpty()) {
            return true;
        }
        try {
            Integer.parseInt(str);
        }catch (NumberFormatException e) {
            return true;
        }
        return str.length() != 8;
    }

    public boolean isNotDominio(String dominio) {
        Pattern pattern = Pattern.compile("^[a-zA-Z]{2,3}\\d{3}[a-zA-Z]{0,2}");
        Matcher matcher = pattern.matcher(dominio);
        return !matcher.find();
    }
}
