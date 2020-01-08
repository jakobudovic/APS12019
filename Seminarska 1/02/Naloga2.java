import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Naloga2 {

  public static ListTwoWayLinked karte = new ListTwoWayLinked();
  
  public static void izpisiNode(ListTwoWayLinkedNode node, FileWriter writer) throws IOException {
    if (node == null) {
      //System.out.println("prazen seznam");
    } else {
      //if (node.data == null)
        //System.out.print("izpisujemo tudi prvi element, z vrednostjo null: ");
      while (node != null) {
        writer.write(node.data);
        node = node.next;
        if (node != null) {
          writer.write(",");
        }
      }
      writer.write("\n");
    }
  }
  
  public static void izpisiNodeConsole(ListTwoWayLinkedNode node) {
    if (node == null) {
      System.out.println("prazen seznam");
    } else {
      if (node.data == null)
        System.out.print("izpisujemo tudi prvi element, z vrednostjo null: ");
      while (node != null) {
        System.out.print(node.data);
        node = node.next;
        if (node != null) {
          System.out.print(",");
        }
      }
      System.out.println();
    }
  }

  public static void izpisiNodeConsoleReverse(ListTwoWayLinkedNode node, ListTwoWayLinkedNode first) {
    if (node == null) {
      System.out.println("neuspesen poizkus izpisa nodov");
    } else {
      if (node.data == null)
        System.out.print("izpisujemo tudi prvi element, z vrednostjo null: ");
      System.out.println("reverse izpis!: ");
      while (node != first) {
        System.out.print(node.data);
        node = node.prev;
        if (node != null) {
          System.out.print(",");
        }
      }
      System.out.println();
    }
  }

  public static void main(String[] args) throws FileNotFoundException {
    // Instant start = Instant.now();
    karte = new ListTwoWayLinked();
    BufferedReader br = null;

    try {
      // br = new BufferedReader(new FileReader("/home/jakob/eclipse-workspace/SecondExercise1/src/inputs/I2_1.txt"));
      
      br = new BufferedReader(new FileReader(args[0]));
      
      // preberemo int-a
      String strCurrentLine = br.readLine();
      String[] inputs = strCurrentLine.split(",");
      int stMesanj = Integer.parseInt(inputs[1]);

      // naredimo 2d array ukazov za mesanje kart
      String[][] ukazi = new String[stMesanj][3];

      // preberemo vrstico s kartami
      strCurrentLine = br.readLine();
      String[] cards = strCurrentLine.split(",");

      // preberemo "stMesanj" vrstic trojckov D,V,S navodil za mesanje
      for (int i = 0; i < stMesanj; i++) {
        strCurrentLine = br.readLine();
        // System.out.println(strCurrentLine);
        String[] trojcek = strCurrentLine.split(",");
        ukazi[i][0] = trojcek[0];
        ukazi[i][1] = trojcek[1];
        ukazi[i][2] = trojcek[2];
      }
      if (cards[0] != null) {
        for (int i = 0; i < cards.length; i++) {
          ListTwoWayLinkedNode node = new ListTwoWayLinkedNode(cards[i]);
          karte.addNodeLast(node);
        }
      }

      // gremo cez vse ukaze mesanj
      for (int i = 0; i < stMesanj; i++) {
        premesaj(ukazi[i][0], ukazi[i][1], Integer.parseInt(ukazi[i][2]), karte);
      }
      System.out.println("izpis v datoteko: ");

      // FileWriter writer = new FileWriter("src/out.txt");
      FileWriter writer = new FileWriter(args[1]);
      
      izpisiNode(karte.first.next, writer);
      // Writes the content to the file
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
  } // end of main

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////// METODE //////////////////////////  
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  static void premesaj(String karta1, String karta2, int razpon, ListTwoWayLinked karte) {

    ListTwoWayLinked k1 = new ListTwoWayLinked();
    ListTwoWayLinked k2 = new ListTwoWayLinked();

    ListTwoWayLinkedNode split = karte.returnCustom(karta1);
    
    if (split == karte.last) {
      System.out.println("vse karte gredo v k1, k2 je prazen");
    }
    else {

    if (split != null) {
      k1.first.next = karte.first.next;
      k1.first.next.prev = k1.first;

      k1.last = split;

      k2.first.next = split.next;
      k2.first.next.prev = k2.first;
      k2.last = karte.last;
      k2.last.next = null;

      k1.last.next = null;
    } else {
      k2.first.next = karte.first.next;
      k2.first.next.prev = k2.first;
      k2.last = karte.last;
      k2.last.next = null;
    }

    // dolzina kupcka 2;
    int len2 = k2.returnLen();
    int ost = len2 % razpon;
    int celihDelov = len2 / razpon;

    ListTwoWayLinkedNode vstaviZa = null;
    vstaviZa = k1.returnCustom(karta2);

    if (vstaviZa == null) {
      // nimamo karte za katero bi vstavljali, zato 
      // vstavljamo na "1, mesto" v k1, takoj za headerjem
      vstaviZa = k1.first;
    }
    ListTwoWayLinkedNode curr = k2.first.next;

    if (razpon == 1) { // niti ni ostanka

      for (int i = 0; i < len2; i++) {
        curr = k1.insertNodeBehindNode(vstaviZa, curr);
      }

    } else {

      ListTwoWayLinkedNode listStart = k2.first.next;
      ListTwoWayLinkedNode listEnd = k2.returnNodeAfterNSteps(listStart, razpon - 1);
      
      for (int i = 0; i < celihDelov; i++) {
        
        ListTwoWayLinkedNode hranilistStartNext = listEnd.next;

        k1.insertListBehindNode(vstaviZa, listStart, listEnd);

        listStart = hranilistStartNext; // listStart =? null
        if(hranilistStartNext != null) listStart.prev = null;
        k2.first.next = listStart;  
        if(hranilistStartNext != null) k2.first.next.prev = k2.first;

        listEnd = k2.returnNodeAfterNSteps(listStart, razpon - 1);
      }
  
      if (ost > 0) {
        listStart = k2.first.next;
        listEnd = k2.last;
        
        k1.insertListBehindNode(vstaviZa, listStart, listEnd);
      }
    }

    karte.first.next = k1.first.next;
    karte.first.next.prev = karte.first;
    karte.last = k1.last;
    }
  
  } // konec premesaj metode

} // konec razreda Naloga2.java

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

class ListTwoWayLinkedNode {
  String data;
  ListTwoWayLinkedNode next;
  ListTwoWayLinkedNode prev;

  ListTwoWayLinkedNode(String data) {
    this(null, data, null);
  }

  ListTwoWayLinkedNode(ListTwoWayLinkedNode prev, String data, ListTwoWayLinkedNode next) {
    this.data = data;
    this.next = next;
    this.prev = prev;
  }
} // end of class ListTwoWayLinkedNode

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

class ListTwoWayLinked {
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
  }

  public int returnLen() {
    int len = 0;
    ListTwoWayLinkedNode curr = first.next;
    while (curr != null) {
      len++;
      curr = curr.next;
    }
    return len;
  }

  public ListTwoWayLinkedNode returnCustom(String value) {
    ListTwoWayLinkedNode node = first.next;
    while (node != null) {
      if (node.data.equals(value)) {
        return node;
      }
      node = node.next;
    }
    // ce ne najdemo
    return null;
  }

  public ListTwoWayLinkedNode returnNodeAfterNSteps(ListTwoWayLinkedNode listFirst, int num) {

    ListTwoWayLinkedNode curr = listFirst;

    for (int i = 0; i < num; i++) {
      if (curr == null)
        return null;
      curr = curr.next;
    }

    return curr;
  }

  public void addNodeLast(ListTwoWayLinkedNode newEl) {
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

  public ListTwoWayLinkedNode insertNodeBehindNode(ListTwoWayLinkedNode vstaviZa, ListTwoWayLinkedNode curr) {

    ListTwoWayLinkedNode next = null;

    if (vstaviZa == last) {
      addNodeLast(curr);
    } else {
      next = curr.next;
      curr.next = vstaviZa.next;
      if (vstaviZa.next != null)
        vstaviZa.next.prev = curr;

      vstaviZa.next = curr;
      vstaviZa.next.prev = vstaviZa;
    }
    return next;
  }

  public void insertListBehindNode(ListTwoWayLinkedNode vstaviZa, ListTwoWayLinkedNode listStart,
      ListTwoWayLinkedNode listEnd) {

    if (listStart == listEnd) {
      ListTwoWayLinkedNode x = insertNodeBehindNode(vstaviZa, listStart);
    } else {

      if (vstaviZa.next == null) {
        last = listEnd;
      }
      listEnd.next = vstaviZa.next;
      if (listEnd.next != null)
        listEnd.next.prev = listEnd;

      vstaviZa.next = listStart;
      listStart.prev = vstaviZa;
    }
  }

} // end of class ListTwoWayLinked

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
