## Tretja naloga

Implementirajte seznam celih števil, ki podpira naslednje metode:

- public void preslikaj(char op, int val)
- public void ohrani(char op, int val)
- public void zdruzi(char op)


Metoda *void preslikaj(op,val)* se sprehodi čez elemente seznama ter vsak element e
nadomesti z vrednostjo *e op val*.
<br>Parameter *op* lahko zavzame dve možni vrednosti: '+' in '*'.
Drugače povedano, metoda preslikaj vsakemu elementu seznama bodisi prišteje vrednost *val* bodisi
ga z *val* pomnoži.

Metoda *void ohrani(op,val)* v seznamu obdrži samo elemente za katere velja *e op val*.
Parameter *op* lahko zavzame tri možne vrednosti: '>', '<' in '='. Drugače povedano, metoda
ohrani obdrži le tiste elemente seznama, ki so večji, manjši oziroma enaki parametru *val*.

Metoda *void združi(op)* bodisi sešteje bodisi zmnoži vse elemente seznama, v odvisnosti od
vrednosti parametra *op* ('+' ali '*'). Ob zaključku seznam vsebuje en sam element, ki predstavlja
rezultat operacije (seštevek ali zmnožek elementov).

Implementirajte razred **Naloga3**, ki vsebuje metodo **main**. Metoda v argumentih prejme poti do
vhodne in izhodne datoteke (args[0] in args[1]). Vhodna datoteka vsebuje začetno stanje seznama in
navodila za operacije, ki naj se nad njim izvedejo. Izhodna datoteka vsebuje elemente seznama po
končani izvedbi vsake zahtevane operacije.

<br>
Tekstovna vhodna datoteka je podana v naslednjem formatu:

- V prvi vrstici je zapisano začetno stanje seznama. Vrednosti posameznih elementov so ločene
z vejico.

- V drugi vrstici je število operacij.

- Sledijo navodila za operacije. Vsako navodilo je v eni izmed spodnjih oblik:

    - *p,A,B* označuje klic *preslikaj(A,B)*
    - *o,A,B* označuje klic *ohrani(A,B)*
    - *z,A* označuje klic  *zdruzi(A)*

<br>

| Vhodna datoteka:      | Izhodna datoteka:      |
|------------|-------------|
|5,8,3,2,9,3,4,8<br>4<br>a,>,5<br>p,+,2<br>o,=,10<br>z,*|8,9,8<br>10,11,10<br>10,10<br>100|