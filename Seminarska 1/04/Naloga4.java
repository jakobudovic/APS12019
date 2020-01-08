import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Naloga4 {
  public static FileWriter writer;

  public static void main(String[] args) {
    // Instant start = Instant.now()
    BufferedReader br = null;

    try { 

      // br = new BufferedReader(new FileReader("/home/jakob/eclipse-workspace/FourthExercise/src/inputs/I4_10.txt"));
      br = new BufferedReader(new FileReader(args[0]));

      // writer = new FileWriter("src/outX.txt");
      writer = new FileWriter(args[1]);

      String strCurrentLine = br.readLine();
      int stUkazov = Integer.parseInt(strCurrentLine);

      BagTwoWayLinked bags = new BagTwoWayLinked();

      for (int i = 0; i < stUkazov; i++) {
        strCurrentLine = br.readLine();

        String[] arr = strCurrentLine.split(",");

        char ukaz = arr[0].charAt(0);

        if (ukaz == 'U') {
          int bagName = Integer.parseInt(arr[1]);

          ListTwoWayLinked bag = new ListTwoWayLinked(bagName, null, null);
          bags.addBagLastObj(bag);

          for (int j = 0; j < arr.length - 2; j++) {
            String dvojica = arr[j + 2];
            String[] dvojicaArr = dvojica.split(":");
            int value = Integer.parseInt(dvojicaArr[0]);
            int data = Integer.parseInt(dvojicaArr[1]);

            ListTwoWayLinkedNode node = new ListTwoWayLinkedNode(value, data);

            bag.addNodeInOrder(node);
          }
        } 
        else {  
          
          ListTwoWayLinked bag1 = bags.returnBag(Integer.parseInt(arr[1]));

          if (ukaz == 'I') {
            if (bag1 != null) {
              bag1.write(bag1.first, writer);
            }
          } 

          else if (ukaz == 'Z') {
            ListTwoWayLinked bag2 = bags.returnBag(Integer.parseInt(arr[2]));
            bags.zdruzi(bag1, bag2);
          }

          else if (ukaz == 'R') {
            ListTwoWayLinked bag2 = bags.returnBag(Integer.parseInt(arr[2]));
            bags.razlika(bag1, bag2);
          }

          else if (ukaz == 'S') {
            ListTwoWayLinked bag2 = bags.returnBag(Integer.parseInt(arr[2]));
            bags.presek(bag1, bag2);
          }

          else if (ukaz == 'P') {
            int konstanta = Integer.parseInt(arr[2]);
            bags.porezi(bag1, konstanta);
          }

          else if (ukaz == 'O') {
            int konstanta = Integer.parseInt(arr[2]);
            bags.obdrzi(bag1, konstanta);
          }
        }

      } 

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

  static void write(ListTwoWayLinkedNode curr, FileWriter writer) throws IOException {

    if (curr.data == null) {
      System.out.println("Najverjetneje smo podali v izpis samo element 'first'");
    } else {
      while (curr != null) {
        int x = (int) curr.data;
        System.out.print(curr.data);
        writer.write(Integer.toString(x));
        curr = curr.next;
        if (curr != null) {
          System.out.print(",");
          writer.write(",");
        }
      }
    }
    System.out.println();
    writer.write('\n');
  }

}

/////////////////////////////////////////////////////////////////////////////////////

// elementi v vreci
class ListTwoWayLinkedNode {
  Object value;
  Object data;
  ListTwoWayLinkedNode next;
  ListTwoWayLinkedNode prev;

  public ListTwoWayLinkedNode() {
    this.value = null;
    this.data = null;
    this.next = null;
    this.prev = null;
  }

  ListTwoWayLinkedNode(Object value, Object data) {
    this.value = value;
    this.data = data;
    this.next = null;
    this.prev = null;
  }

  ListTwoWayLinkedNode(Integer value, Integer data, ListTwoWayLinkedNode prev, ListTwoWayLinkedNode next) {
    this.value = value;
    this.data = data;
    this.next = next;
    this.prev = prev;
  }
} // end of class ListTwoWayLinkedNode

///////////////////////////////////////////////////////////////////////////////////////

// BAG
class ListTwoWayLinked {
  protected int len;
  protected Object bagName;
  protected ListTwoWayLinkedNode first, last;
  protected ListTwoWayLinked prev, next;

  ListTwoWayLinked() { 
    makenull();
  }

  ListTwoWayLinked(Object name, ListTwoWayLinked prev, ListTwoWayLinked next) {
    this.bagName = name;
    this.first = new ListTwoWayLinkedNode();
    this.prev = null;
    this.next = null;
  }

  public void makenull() {
    bagName = null;
    first = new ListTwoWayLinkedNode();
    last = null;
    prev = null; 
    next = null;
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
  
  public void addNodeAfter(ListTwoWayLinkedNode vstaviZaMano, ListTwoWayLinkedNode vstaviMe) {
    if (vstaviZaMano == null) {
      addNodeLast(vstaviMe);
    }
    else {
      vstaviMe.next = vstaviZaMano.next;
      vstaviZaMano.next.prev = vstaviMe;
      vstaviMe.prev = vstaviZaMano;
      vstaviZaMano.next = vstaviMe;
    }
  }
  
  public void addNodeBefore(ListTwoWayLinkedNode vstaviPredMano, ListTwoWayLinkedNode vstaviMe) {
    if (vstaviPredMano == null) {
      addNodeLast(vstaviMe);
    }
    else {
      vstaviMe.next = vstaviPredMano;
      vstaviMe.prev = vstaviPredMano.prev;
      vstaviPredMano.prev.next = vstaviMe;
      vstaviPredMano.prev = vstaviMe;
    }
  }
  
  public void addNodeInOrder(ListTwoWayLinkedNode node) {
    if(last == null) {
      addNodeLast(node);
    }
    else {
      ListTwoWayLinkedNode curr = first.next;
      int preveri = 0;
      while (curr != null && preveri == 0) {
        if ((int) curr.value > (int) node.value) {
          preveri++;
          node.next = curr;
          node.prev = curr.prev;
          curr.prev.next = node;
          curr.prev = node;
        }
        curr = curr.next;
      }
      
      if (preveri == 0) {
        node.prev = last;
        last.next = node;
        last = node;
      }
    }
  }

  public void addNodeLast(ListTwoWayLinkedNode node) {

    if (last == null) {
      first.next = node;
      last = node;
      node.prev = first;
    } else {
      node.prev = last;
      last.next = node;
      last = node;
    }
  }

  public void deleteNode(ListTwoWayLinkedNode curr) {

    if (curr == null) {
      System.out.println("Najverjetneje smo podali v izpis samo element 'first'");
    } else {

      if (first.next.next == null) {
        first.next = null;
        last = null;
      } else {
        ListTwoWayLinkedNode prev = curr.prev;
        ListTwoWayLinkedNode nexxt = curr.next;

        if (curr != last) {
          nexxt.prev = prev;
        }
        prev.next = nexxt;

        if (curr == last) {
          last = prev;
        }
      }
    }
  }
  
  public void izpis(ListTwoWayLinkedNode curr) {

    if (curr == first)
      curr = curr.next;

    while (curr != null) {
      System.out.print(curr.value + ":" + curr.data);
      if (curr.next != null) {
        System.out.print(",");
      }
      curr = curr.next;
    }
    System.out.println();
  }

  public void write(ListTwoWayLinkedNode curr, FileWriter writer) throws IOException {

    if (curr == first)
      curr = curr.next;

    while (curr != null) {
      writer.write(curr.value + ":" + curr.data);
      System.out.print(curr.value + ":" + curr.data);
      if (curr.next != null) {
        writer.write(",");
        System.out.print(",");
      }
      curr = curr.next;
    }
    writer.write("\n");
    System.out.println();
  }
  
  public ListTwoWayLinkedNode findNode(int value) {

    ListTwoWayLinkedNode curr = first.next;

    while (curr != null) {
      if ((int) curr.value == value) {
        return curr;
      }
      curr = curr.next;
    }
    return null;
  }
} 

/////////////////////////////////////////////////////////////////////////////////////

class BagTwoWayLinked {

  protected ListTwoWayLinked first, last;

  BagTwoWayLinked() {
    makenull();
  }

  public void makenull() {
    first = new ListTwoWayLinked(); 
    last = null;
  }

  public void addBagLastObj(ListTwoWayLinked bag) {

    if (first.next == null && last == null) {
      first.next = bag;
      last = bag;
    } else {
      last.next = bag;
      last = last.next;
    }
  }

  public ListTwoWayLinked returnBag(Object num) {

    ListTwoWayLinked curr = first;

    while (curr != null) {
      if (curr.bagName == num) 
        return curr;
      curr = curr.next;
    } System.out.println("nismo nasli vrece z imenom " + num);
    return null;
  }

  public void zdruzi(ListTwoWayLinked bag1, ListTwoWayLinked bag2) {

    if (bag1 == null || bag2 == null)
      return;

    ListTwoWayLinkedNode vozlisce1 = bag1.first.next;
    ListTwoWayLinkedNode vozlisce2 = bag2.first.next;

    while(vozlisce1 != null && vozlisce2 != null) {
      if ((int) vozlisce1.value < (int)vozlisce2.value) {
        
        vozlisce1 = vozlisce1.next;
      }
      else if((int) vozlisce1.value == (int)vozlisce2.value){
        
        vozlisce1.data = (int)vozlisce1.data + (int)vozlisce2.data;
        
        vozlisce1 = vozlisce1.next;
        vozlisce2 = vozlisce2.next;
      }
      else {
        ListTwoWayLinkedNode hrani = vozlisce2.next;
        ListTwoWayLinkedNode novo = new ListTwoWayLinkedNode((int)vozlisce2.value, (int)vozlisce2.data);
        bag1.addNodeBefore(vozlisce1, novo);
        
        vozlisce2 = hrani;
      }
    }
    
    while (vozlisce2 != null) {
      ListTwoWayLinkedNode novo = new ListTwoWayLinkedNode(vozlisce2.value, vozlisce2.data);
      bag1.addNodeLast(novo);
      vozlisce2 = vozlisce2.next;
    }
  }

  public void razlika(ListTwoWayLinked bag1, ListTwoWayLinked bag2) {

    ListTwoWayLinkedNode vozlisce2 = bag2.first.next;
    ListTwoWayLinkedNode vozlisce1 = bag1.first.next;
    
    while (vozlisce1 != null && vozlisce2 != null) {
      
      if ((int) vozlisce1.value < (int)vozlisce2.value) {
        vozlisce1 = vozlisce1.next;
      }
      else if((int) vozlisce1.value == (int)vozlisce2.value){
        
        vozlisce1.data = (int)vozlisce1.data - (int)vozlisce2.data;
        ListTwoWayLinkedNode hrani = vozlisce1.next;
        if ((int)vozlisce1.data <= 0) {
          bag1.deleteNode(vozlisce1);
        }
        
        vozlisce1 = hrani;
        vozlisce2 = vozlisce2.next;
      }
      else {
        vozlisce2 = vozlisce2.next;
      }
      
    }
  }

  public void presek(ListTwoWayLinked bag1, ListTwoWayLinked bag2) {
    ListTwoWayLinkedNode vozlisce1 = bag1.first.next;
    ListTwoWayLinkedNode vozlisce2 = bag2.first.next;
    
    while (vozlisce1 != null && vozlisce2 != null) {
      
      if ((int) vozlisce1.value < (int)vozlisce2.value) {
        bag1.deleteNode(vozlisce1);
        vozlisce1 = vozlisce1.next;
      }
      else if((int) vozlisce1.value == (int)vozlisce2.value){
        int min = Math.min((int) vozlisce1.data, (int) vozlisce2.data);
        vozlisce1.data = min;
        vozlisce1 = vozlisce1.next;
        vozlisce2 = vozlisce2.next;
      }
      else {
        vozlisce2 = vozlisce2.next;
      }
      
    }
    while (vozlisce1 != null) {
      bag1.deleteNode(vozlisce1);
      vozlisce1 = vozlisce1.next;
    }
  } 

  public void porezi(ListTwoWayLinked bag1, int konstanta) {
    ListTwoWayLinkedNode vozlisce1 = bag1.first.next;

    while (vozlisce1 != null) {

      if (konstanta <= 0) {
        System.out.println("dodelaj, ce bodo skriti primeri taki, da izbrises celo vreco");
      }
      if ((int) vozlisce1.data > konstanta) {
        vozlisce1.data = konstanta;
      }
      vozlisce1 = vozlisce1.next;
    }
  } 

  public void obdrzi(ListTwoWayLinked bag1, int konstanta) {
    ListTwoWayLinkedNode vozlisce1 = bag1.first.next;

    while (vozlisce1 != null) {

      if ((int) vozlisce1.data < konstanta) {
        bag1.deleteNode(vozlisce1);
      }
      vozlisce1 = vozlisce1.next;
    }
  } 

  public void write(ListTwoWayLinked curr) {
    while (curr != null) {
      System.out.print("vreca " + curr.bagName);
      if (curr.next != null) {
        System.out.print("; ");
      }
      curr = curr.next;
    }
    System.out.println("");
  }
}
