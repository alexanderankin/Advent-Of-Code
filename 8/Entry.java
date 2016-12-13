import java.util.*;
import java.io.*;
import java.util.regex.*;

public class Entry {
  public static final String inputFile = "./input";

  public static void main(String[] args) throws Exception {
    Pattern rotate = Pattern.compile("rotate (row|column) x=([0-9]+) by ([0-9]+)");
    Pattern rect   = Pattern.compile("rect ([0-9]+)x([0-9]+)");

    Display d = new Display();

    BufferedReader br = new BufferedReader(new FileReader(inputFile));
    String input;
    while((input = br.readLine()) != null) {
      System.out.println(d.toString());
      if (input.indexOf("rotate") > -1) {
        Matcher m = rotate.matcher(input);
        if (m.group(0).indexOf("row") > -1) {
          d.rotateRow(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
        } else {
          d.rotateColumn(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
        }               
      } else if (input.indexOf("rect") > -1) {
        Matcher m = rect.matcher(input);
        System.out.println("lolol");
        d.rect(Integer.parseInt(m.group(0)), Integer.parseInt(m.group(1)));
      }
    }

    System.out.println(d.toString());
  }
}
