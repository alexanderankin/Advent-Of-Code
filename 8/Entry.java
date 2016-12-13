import java.util.*;
import java.io.*;
import java.util.regex.*;

public class Entry {
  public static final String inputFile = "./input";

  public static void main(String[] args) throws Exception {
    Pattern rotate = Pattern.compile("rotate (row|column) [x|y]=([0-9]+) by ([0-9]+)");
    Pattern rect   = Pattern.compile("rect ([0-9]+)x([0-9]+)");

    Display d = new Display();

    BufferedReader br = new BufferedReader(new FileReader(inputFile));
    String input;
    while((input = br.readLine()) != null) {
      System.out.println(d.toString());
      // if (input.indexOf("rotate") > -1) {
      Matcher ro_m = rotate.matcher(input);
      Matcher re_m = rect.matcher(input);
      if (ro_m.matches()) {
        if (ro_m.group(1).indexOf("row") > -1) {
          d.rotateRow(Integer.parseInt(ro_m.group(2)), Integer.parseInt(ro_m.group(3)));
        } else {
          d.rotateColumn(Integer.parseInt(ro_m.group(2)), Integer.parseInt(ro_m.group(3)));
        }               
      // } else if (input.indexOf("rect") > -1) {
      } else if (re_m.matches()) {
        // Matcher m = rect.matcher(input);
        // System.out.println("lolol");
        // System.out.printf("Number of groups found: %d\n", re_m.groupCount());
        d.rect(Integer.parseInt(re_m.group(1)), Integer.parseInt(re_m.group(2)));
      } else {
        System.err.println(input);
        throw new Exception("not recognized");
      }
    }

    System.out.println(d.toString());
    System.out.println(d.cleanToString());
    System.out.printf("Total Number of Pixels Enabled: %d.\n", d.getPixelOnCount());
  }
}
