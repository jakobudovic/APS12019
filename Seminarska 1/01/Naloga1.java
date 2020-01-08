import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.spi.LocaleNameProvider;
import java.io.FileNotFoundException;

public class Naloga1 {
  
  static void izpis(char[][][] x) {
    System.out.println();
    for (int i = 0; i < x.length; i++) {
      
      for (int j = 0; j < x[0].length; j++) {
        if (x[i][j][1] == '#') {
          // izpis le, ce je zasedeno polje oz vneseno
          System.out.print(x[i][j][0]);
        }
        else {
          System.out.print(" ");
        }
        if(j < x[0].length) {
          System.out.print(",");
        }
      }
      System.out.println();
    }
    System.out.println();
  }
  
  static boolean resi(char[][][] polje, String[] besede, int index, FileWriter writer, Integer[][] poljaRez)
      throws IOException {

    // ce smo na koncu matrike ALI smo nasli vse besede, robni pogoj
    if (index >= besede.length) {
      return true;
    }
    // ce imamo se za preverit besede
    else {

      // izberemo besedo in njeno prvo crko
      String beseda = besede[index];
      char prviZnak = beseda.charAt(0);

      // gremo po matriki
      // vrstice
      for (int i = 0; i < polje.length; i++) {

        // stolpci
        for (int j = 0; j < polje[0].length; j++) {

          // preverimo, ce ni izpolnjeno polje in ce matcha prva crka s poljem
          if (polje[i][j][1] == '.' && prviZnak == polje[i][j][0]) {

            // ce je samo 1 crka
            if (beseda.length() == 1) {

              polje[i][j][1] = '#';

              if (resi(polje, besede, index + 1, writer, poljaRez)) { // rek klic
                poljaRez[index][0] = poljaRez[index][2] = i;
                poljaRez[index][1] = poljaRez[index][3] = j;
                return true;
              }
              // ce ne returnamo, vpisemo piko aka "prazno"
              polje[i][j][1] = '.';

            }

            // preverjamo v vse smeri, ce jo lahko vnesemo
            for (int smer = 7; smer >= 0; smer--) {

              if (preveri(beseda, smer, polje, i, j)) {

                // lahko jo vpisemo (true vrednost, ker vnasamo #. V primeru false "brisemo" oz
                // vnasamo pike)
                vnesi(beseda, smer, polje, i, j, true);

                /*
                System.out.println("-------------------");
                System.out.println("-------------------");
                System.out.println(beseda + ": " + i + "," + j + " - smer: " + smer);
                izpis(polje);
                 */
                if (resi(polje, besede, index + 1, writer, poljaRez)) {
                  int[] arr = koncneKoordinate(beseda.length(), i, j, smer);

                  poljaRez[index][0] = i;
                  poljaRez[index][1] = j;
                  poljaRez[index][2] = arr[0];
                  poljaRez[index][3] = arr[1];
                  // System.out.println(beseda + ": " + i + "," + j + ";" + arr[0] + "," + arr[1]);
                  return true;
                }
                // se ne izide - pobrisemo besedo oz vnesemo pike v 3. dimenzijo
                vnesi(beseda, smer, polje, i, j, false);

              }
            }
          }
        }
      } // konec sprehajanja po matriki
      return false;
    }
  }

  static int[] koncneKoordinate(int len, int i, int j, int smer) {
    len = len - 1;
    int[] arr = { i, j };
    if (smer == 0) {
      arr[1] += len; // za v desno povecas samo j
    } else if (smer == 1) { // desno gor
      arr[0] -= len; // za dol gor
      arr[1] += len; // za dol desno
    } else if (smer == 2) { // gor
      arr[0] -= len;
    } else if (smer == 3) {
      arr[0] -= len;
      arr[1] -= len;
    } else if (smer == 4) { // levo
      arr[1] -= len;
    } else if (smer == 5) {
      arr[0] += len;
      arr[1] -= len;
    } else if (smer == 6) { // dol
      arr[0] += len;
    } else if (smer == 7) {
      arr[0] += len;
      arr[1] += len;
    }
    return arr;
  }

  static void vnesi(String beseda, int smer, char[][][] polje, int i, int j, boolean vnesi) {
    int dolBesede = beseda.length();

    for (int x = 0; x < dolBesede; x++) {

      if (vnesi) {
        polje[i][j][1] = '#';
      } else {
        polje[i][j][1] = '.';
      }

      if (smer == 0) {
        j++;
      } else if (smer == 1) {
        i--;
        j++;
      } else if (smer == 2) {
        i--;
      } else if (smer == 3) {
        i--;
        j--;
      } else if (smer == 4) {
        j--;
      } else if (smer == 5) {
        i++;
        j--;
      } else if (smer == 6) {
        i++;
      } else if (smer == 7) {
        i++;
        j++;
      }
    }
  }

  static boolean preveri(String beseda, int smer, char[][][] polje, int i, int j) {
    int sirina = polje.length;
    int visina = polje[0].length;
    int count = 0;
    int besedaLen = beseda.length();

    // gremo od DRUGE crke preverjat ustreznost besede
    for (int x = 1; x < besedaLen; x++) {
      char crka = beseda.charAt(x);

      // desno
      if (smer == 0 && (j + x) < sirina && polje[i][j + x][1] == '.' && polje[i][j + x][0] == crka) {
        count++;
      }
      // desno gor
      else if (smer == 1 && (i - x) >= 0 && (j + x) < sirina && polje[i - x][j + x][1] == '.'
          && polje[i - x][j + x][0] == crka) {
        count++;
      }
      // gor
      else if (smer == 2 && (i - x) >= 0 && polje[i - x][j][1] == '.' && polje[i - x][j][0] == crka) {
        count++;
      }
      // levo gor
      else if (smer == 3 && (i - x) >= 0 && (j - x) >= 0 && polje[i - x][j - x][1] == '.'
          && polje[i - x][j - x][0] == crka) {
        count++;
      }
      // levo
      else if (smer == 4 && (j - x) >= 0 && polje[i][j - x][1] == '.' && polje[i][j - x][0] == crka) {
        count++;
      }
      // levo dol
      else if (smer == 5 && (i + x) < visina && (j - x) >= 0 && polje[i + x][j - x][1] == '.'
          && polje[i + x][j - x][0] == crka) {
        count++;
      }
      // dol
      else if (smer == 6 && (i + x) < visina && polje[i + x][j][1] == '.' && polje[i + x][j][0] == crka) {
        count++;
      }
      // desno dol
      else if (smer == 7 && (i + x) < visina && (j + x) < sirina && polje[i + x][j + x][1] == '.'
          && polje[i + x][j + x][0] == crka) {
        count++;
      }
    } // end of for

    if (count == besedaLen - 1)
      return true;
    return false;

  } // end of preveri();

  public static Integer[] besedeIdx;

  public static void main(String[] args) throws FileNotFoundException {
    // Instant start = Instant.now();
    BufferedReader br = null;
    try {

      // br = new BufferedReader(new FileReader("/home/jakob/eclipse-workspace/FirstExercise/src/inputs/I1_6.txt"));
      br = new BufferedReader(new FileReader(args[0]));
      
      // preberemo int-a
      String strCurrentLine = br.readLine();
      String[] values = strCurrentLine.split(",");
      int V = Integer.parseInt(values[0]);
      int S = Integer.parseInt(values[1]);

      char[][][] seznam = new char[V][S][2];

      for (int i = 0; i < V; i++) {
        strCurrentLine = br.readLine(); // preberemo vrstico
        String[] values1 = strCurrentLine.split(",");

        for (int j = 0; j < S; j++) {
          seznam[i][j][0] = values1[j].charAt(0);
          seznam[i][j][1] = '.';
        }
      }
      
      // preberemo stevilo besed
      int b = Integer.parseInt(br.readLine());
      String[] besede1 = new String[b];
      besedeIdx = new Integer[b];

      for (int i = b - 1; i >= 0; i--) { // dajemo besede od nazaj v seznam
        besede1[i] = br.readLine();
        besedeIdx[i] = (-1) * (i - b) - 1; // formula, da obrne zacetne indexe od 0 do b in ne od b do 0 (tako kot
                                           // beremo - od zadaj)
      }

      besede1 = urediPoVrsti(besede1, besedeIdx);

      Integer[][] poljaRez = new Integer[b][4];

      // konec sortiranja, rekurzija:
      // creates a FileWriter Object
      // FileWriter writer = new FileWriter("src/out.txt");
      FileWriter writer = new FileWriter(args[1]);

      if (resi(seznam, besede1, 0, writer, poljaRez)) {
        System.out.println("reseno ");
        // izpis besed po originalnem vrstnem redu
        // System.out.println("-------------- koncen izpis: -----------");
        for (int i = 0; i < besede1.length; i++) {
          for (int j = 0; j < b; j++) {
            if (besedeIdx[j] == i) {
              // System.out.println("beseda " + besede1[j] + " na mestu: " + poljaRez[j][0] +
              // "," + poljaRez[j][1] + "," + poljaRez[j][2] + "," + poljaRez[j][3]);
              writer.write(besede1[j] + "," + poljaRez[j][0] + "," + poljaRez[j][1] + "," + poljaRez[j][2] + ','
                  + poljaRez[j][3] + '\n');
              // System.out.println(besede1[j] + "," + poljaRez[j][0] + "," + poljaRez[j][1] +
              // "," + poljaRez[j][2] + ',' + poljaRez[j][3]);
            }
          }
        }
        // System.out.println("");
        // System.out.println("reseno");
      } else {
        System.out.println("Ni resitve");
      }

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
    // float x = Duration.between(start, Instant.now()).toMillis() / 1000f;
    // System.out.println(x);
  }

  public static String[] urediPoVrsti(String[] besede, Integer[] besedeIdx) {

    for (int i = 0; i < besede.length; i++) {
      String beseda = besede[i];
      int idx = besedeIdx[i];

      // Insert besede[j] at its correct position
      int j = i - 1;
      while (j >= 0 && beseda.length() > besede[j].length()) {
        besede[j + 1] = besede[j];
        besedeIdx[j + 1] = besedeIdx[j];
        j--;
      }
      besede[j + 1] = beseda;
      besedeIdx[j + 1] = idx;
    }
    return besede;
  }

}
