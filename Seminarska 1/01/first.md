# Prva naloga

Podana je mreža črk in seznam besed. Vsaka beseda iz seznama je vpisana v mrežo v eni izmed osmih
smeri: vodoravno, navpično in diagonalno, naprej in nazaj. Naloga je poiskati vse besede iz seznama
tako, da posamezna črka v mreži pripada samo eni besedi.
Implementirajte razred **Naloga1**, ki vsebuje metodo **main**. Metoda v argumentih prejme poti do
vhodne in izhodne datoteke (args[0] in args[1]). Metoda naj prebere vhodne podatke, poišče besede
v mreži in v izhodno datoteko zapiše njihove pozicije.
Tekstovna vhodna datoteka je podana v naslednjem formatu:
V prvi vrstici sta zapisani dve celi števili, ločeni z vejico. Zapis V,S določa dimenzije mreže, pri
čemer je V število vrstic in S število stolpcev mreže.
* V naslednjih V vrsticah so zapisani elementi mreže. Vsaka vrstica vsebuje S znakov, ločenih z
vejicami.
* V naslednji vrstici je celo število B. Ta predstavlja število besed, ki jih iščemo v mreži.
* V vsaki izmed naslednjih B vrstic je zapisana ena beseda.

Tekstovna izhodna datoteka naj vsebuje B vrstic. V vsaki vrstici naj bo najprej zapisana beseda, nato
še štiri cela števila, ločena z vejico. Prvi par števil V1,S1 določa pozicijo prve črke besede v mreži,
drugi par V2,S2 pa pozicijo zadnje črke besede v mreži. **Pozor: indeksiranje začne z 0.**

Pri tej nalogi je možnih več enakovrednih rešitev.

| Vhodna datoteka:      | Izhodna datoteka:      |
|------------|-------------|
|5,5<br>p,n,x,g,f<br>l,p,w,g,z<br>b,o,m,e,j<br>j,p,b,k,p<br>h,p,c,z,a<br>12xgf<br>mo<br>zk<br>jp<br>plb<br>nw<br>hpc<br>p<br>pa<br>z<br>jg<br>be<br>| xgf,0,2,0,4mo,2,2,2,1<br>zk,4,3,3,3<br>jp,3,0,3,1<br>plb,0,0,2,0<br>nw,0,1,1,2<br>hpc,4,0,4,2<br>p,1,1,1,1<br>pa,3,4,4,4<br>z,1,4,1,4<br>jg,2,4,1,3<br>be,3,2,2,3<br>

Razlaga primera:

![pic1](img1.png)