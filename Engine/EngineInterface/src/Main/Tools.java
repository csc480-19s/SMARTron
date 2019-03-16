package Main;

import java.util.ArrayList;

public class Tools {

    public String alphaConverter(String entry){
            int c = Integer.parseInt(entry);
            if(c >= 0 && c <= 26) {
            c = c + 64;
            char cc = (char) c;
            return String.valueOf(cc);
        }

        return "";
    }
}
