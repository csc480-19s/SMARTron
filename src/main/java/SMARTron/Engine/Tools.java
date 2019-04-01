package SMARTron.Engine;

public class Tools {

    public String alphaConverter(String entry) {
        int c = Integer.parseInt(entry);
        if (c >= 0 && c <= 26) {
            c = c + 64;
            char cc = (char) c;
            return String.valueOf(cc);
        }

        return " ";
    } //This is my pride and joy, This method makes me very happy.
//It takes in a string such as "1" and converts it to the indexed alpha character that corasponds: "A"
}