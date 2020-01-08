import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Naloga3 {
  public static FileWriter writer;

  public static void main(String[] args) {
    // Instant start = Instant.now();
    BufferedReader br = null;

    try {

      // br = new BufferedReader(new FileReader("/home/jakob/eclipse-workspace/ThirdExercise/src/inputs/I3_1.txt"));
      br = new BufferedReader(new FileReader(args[0]));

      // preberemo seznam stevilk
      String strCurrentLine = br.readLine();
      String[] valStrings = strCurrentLine.split(",");
      Integer[] valIntegers = new Integer[valStrings.length];

      for (int i = 0; i < valStrings.length; i++) {
        valIntegers[i] = Integer.parseInt(valStrings[i]);
      }

      // vrstica s stevilom ukazov
      strCurrentLine = br.readLine();
      int stUkazov = Integer.parseInt(strCurrentLine);

      String[][] ukazi = new String[stUkazov][3];

      // napolnimo 2D array z ukazi
      for (int i = 0; i < stUkazov; i++) {
        strCurrentLine = br.readLine();
        String[] arr = strCurrentLine.split(",");

        ukazi[i][0] = arr[0];
        ukazi[i][1] = arr[1];
        ukazi[i][2] = null;

        // ce ukaz ni zdruzi, potem napolnimo se tretji element v ukazu
        if (!arr[0].equals("z")) {
          ukazi[i][2] = arr[2];
        }
      }

      ListTwoWayLinked list = new ListTwoWayLinked();
      for (int i = 0; i < valIntegers.length; i++) {
        list.addLast(valIntegers[i]);
      }

      // creates a FileWriter Object
      // writer = new FileWriter("src/outX.txt");
      writer = new FileWriter(args[1]);

      //write(list.first.next, writer);

      for (int i = 0; i < ukazi.length; i++) {

        // System.out.println("ukaz: " + ukazi[i][0] + "," + ukazi[i][1] + "," + ukazi[i][2]);
        // preslikamo elemente
        if (ukazi[i][0].equals("p")) {
          char parameter = ukazi[i][1].charAt(0);
          String vrednost = ukazi[i][2];

          preslikaj(list, parameter, vrednost);

        }
        // ohranimo elemente
        else if (ukazi[i][0].equals("o")) {
          char operator = ukazi[i][1].charAt(0);
          String vrednost = ukazi[i][2];

          // System.out.println("tu se smo, ukaz " + i);
          ohrani(list, operator, vrednost);

        } else if (ukazi[i][0].equals("z")) {
          char operator = ukazi[i][1].charAt(0);
          zdruzi(list, operator);
        }
      }

      /////////////////////////////////////////

      // Writes the content to the file
      writer.write("\n");
      writer.flush();
      writer.close();

    } catch (IOException e) {

      e.printStackTrace();

    } finally {
      try {
        if (br != null)
          br.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }

    // System.out.println(Duration.between(start, Instant.now()).toMillis() / 1000f);
  }

  static void preslikaj(ListTwoWayLinked list, char parameter, String valString) throws IOException {

    // prvi element v seznamu
    ListTwoWayLinkedNode curr = list.first.next;
    int vrednost = Integer.parseInt(valString);

    while (curr != null) {

      // pristevanje
      if (parameter == '+') {
        curr.data = curr.data + vrednost;
      }
      // mnozenje
      else if (parameter == '*') {
        curr.data = curr.data * vrednost;
      }
      curr = curr.next;

    }
    write(list.first.next, writer);
  }

  static void ohrani(ListTwoWayLinked list, char operator, String valString) throws IOException {

    // prvi element v seznamu
    ListTwoWayLinkedNode curr = list.first.next;
    int vrednost = Integer.parseInt(valString);

    while (curr != null) {

      if (operator == '>') {
        if (curr.data > vrednost) {
        } else {
          list.deleteNode(curr);
        }

      } else if (operator == '<') {
        if (curr.data < vrednost) {
        } else {
          list.deleteNode(curr);
        }
      } else if (operator == '=') {
        if (curr.data == vrednost) {
        } else {
          list.deleteNode(curr);
        }

      }
      curr = curr.next;
    }
    // izpis v datoteko in konzolo
    write(list.first.next, writer);
  }

  static void zdruzi(ListTwoWayLinked list, char operator) throws IOException {
    // prvi element v seznamu
    ListTwoWayLinkedNode curr = list.first.next;
    int val = zdruziRek(curr, operator);
    // System.out.println(val);
    writer.write(Integer.toString(val));
  }

  static int zdruziRek(ListTwoWayLinkedNode node, char c) {

    if (node.next == null) {
      return node.data;
    } else {
      if (c == '*') {
        return zdruziRek(node.next, c) * node.data;
      } else {
        return zdruziRek(node.next, c) + node.data;
      }
    }
  }

  static void write(ListTwoWayLinkedNode curr, FileWriter writer) throws IOException {

    if (curr.data == null) {
      // System.out.println("Najverjetneje smo podali v izpis samo element 'first'");
    } else {
      while (curr != null) {
        int x = curr.data;
        // System.out.print(curr.data);
        writer.write(Integer.toString(x));
        curr = curr.next;
        if (curr != null) {
          // System.out.print(",");
          writer.write(",");
        }
      }
    }
    // System.out.println();
    writer.write('\n');
  }

}

class ListTwoWayLinkedNode {
  Integer data;
  ListTwoWayLinkedNode next;
  ListTwoWayLinkedNode prev;

  ListTwoWayLinkedNode(Integer data) {
    this(null, data, null);
  }

  ListTwoWayLinkedNode(ListTwoWayLinkedNode prev, Integer data, ListTwoWayLinkedNode next) {
    this.data = data;
    this.next = next;
    this.prev = prev;
  }
} // end of class ListTwoWayLinkedNode

class ListTwoWayLinked {
  protected int len;
  protected ListTwoWayLinkedNode first, last;

  ListTwoWayLinked() {
    makenull();
  }

  // Funkcija makenull inicializira seznam
  public void makenull() {
    // drzimo se implementacije iz vaj:
    // po dogovoru je na zacetku glava seznama (header)
    first = new ListTwoWayLinkedNode(null, null, null);
    last = null;
    len = 0;
  }

  public int returnLen() {
    len = 0;
    ListTwoWayLinkedNode curr = first.next;
    while (curr != null) {
      len++;
      curr = curr.next;
    }
    return len;
  }

  public void addLast(Integer value) {
    // naredimo nov element
    ListTwoWayLinkedNode newEl = new ListTwoWayLinkedNode(null, value, null);

    // ali je seznam prazen ?
    if (last == null) {
      first.next = newEl;
      last = newEl;
      newEl.prev = first;
    } else {
      newEl.prev = last;
      last.next = newEl;
      last = newEl;
    }
  }

  public void deleteNode(ListTwoWayLinkedNode curr) {
    // ce je prazen seznam
    if (curr.data == null) {
    } else {
      // ce je edini element
      if (first.next.next == null) {
        first.next = null;
        last = null;
      } else {
        ListTwoWayLinkedNode prev = curr.prev;
        ListTwoWayLinkedNode nexxt = curr.next;

        if(nexxt != null) { // ce brisemo zadnjega
          nexxt.prev = prev;
        }
        prev.next = nexxt;

        // preverimo, ce zmo izbrisali zadnjega
        if (curr == last) {
          last = prev;
        }
      }
    }
  }

} // end of class ListTwoWayLinked
