## Četrta naloa

Podatkovna struktura vreča (ang. bag) je vrsta seznama, pri katerem vrstni red elementov ni
pomemben, ponavljanje elementov pa je dovoljeno. Primer vreče je denimo seznam z elementi
{1,2,1,5,1,3,2}. V tem primeru se element 1 pojavi trikrat, element 2 dvakrat, elementa 3 in 5 pa po
enkrat. Enako vrečo bi predstavljal tudi seznam {1,1,1,2,2,3,5}, saj je za vrečo pomembno le, kateri
elementi so v njej in kolikokrat se ti pojavijo.

Naloga je implementirati metode, ki izvajajo operacije nad to podatkovno strukturo.

- *ustvari*: ustvari novo vrečo s podanim imenom in vsebino. Na primer, s tem ukazom bi
lahko ustvarili vreči A = {1,1,1,2,2,3,5} in B = {1,1,2,2,2,4,5,5}.

- *združi*: v eno vrečo doda vsebino druge vreče. Po združevanju vreče A = {1,1,1,2,2,3,5} z
vrečo B = {1,1,2,2,2,4,5,5} bi v vreči A bili naslednji elementi: {1,1,1,1,1,2,2,2,2,2,3,4,5,5,5}.

- *razlika*: iz ene vreče odstrani elemente druge vreče. Po izračunu razlike vreče A =
{1,1,1,2,2,3,5} z vrečo B = {1,1,2,2,2,4,5,5} bi v vreči A ostali {1,3}.

- *skupno*: v vreči obdrži le elemente, ki so skupni v obeh vrečah. Izračun preseka vreče A =
{1,1,1,2,2,3,5} z vrečo B = {1,1,2,2,2,4,5,5} bi v vreči A pustil {1,1,2,2,5}.

- *poreži*: število pojavitev elementov v vreči omeji na podano vrednost. Če vrečo A =
{1,1,1,1,2,2,2,5} porežemo s konstanto 2 dobimo kot rezultat A = {1,1,2,2,5}.

- *obdrži*: v vreči ohrani elemente, ki se v njej pojavijo vsaj tolikokrat kot je vrednost podane
konstante. Klic te funkcije nad vrečo A = {1,1,1,2,3,3,4,5} s konstanto 2 nam da novo stanje
vreče A = {1,1,1,3,3}.

- *izpiši*: izpiše trenutno vsebino vreče.

<br>

Implementirajte razred **Naloga4**, ki vsebuje metodo **main**. Metoda v argumentih prejme poti do
vhodne in izhodne datoteke (args[0] in args[1]). Vhodna datoteka vsebuje opise operacij nad vrečami.
V izhodni datoteki naj bodo izpisani rezultati operacij "izpiši".

Tekstovna vhodna datoteka v prvi vrstici vsebuje celo število N, ki predstavlja število ukazov. V
naslednjih N vrsticah sledijo opisi posameznih ukazov. Ukaz je lahko v enem izmed sledečih formatov:


- *U,V,E1:N1,E2:N2,...,EK:NK* - ustvari vrečo z imenom V, ki vsebuje N1 pojavitev elementa
E1, N2 pojavitev elementa E2, ... , NK pojavitev elementa EK. Lahko predpostavite, da bodo
elementi E1,...,EK in imena vreč V vedno cela števila. Prav tako naj velja, da bodo E1,...,EK med
seboj različni.

- *Z,V1,V2* - vreča V1 je rezultat združevanja vreč V1 in V2.

- *R,V1,V2* - vreča V1 je rezultat izračuna razlike med vrečama V1 in V2.

- *S,V1,V2* - vreča V1 vsebuje skupne elemente vreč V1 in V2.

- *P,V,C* - v vreči V ostanejo elementi, ki ostanejo po klicu funkcije "poreži" s konstanto C

- *O,V,C* - rezultat operacije je nova vsebina vreče V, ki predstavlja rezultat klica "obdrži" s
konstanto C nad vrečo V

- *I,V* - v izhodno datoteko zapiše vsebino vreče V v formatu E1:N1,E2:N2,E3:N3,...,EK:NK pri
čemer so E1,...,EK elementi, N1,...,NK pa njihova števila pojavitev. Pri izpisu naj velja E1 < E2 < ...
< EK.

<br>

| Vhodna datoteka:      | Izhodna datoteka:      |
|------------|-------------|
|12<br>U,1,3:2,1:5,9:1,7:4<br>U,2,3:1,2:5,1:3,4:1,6:1<br>Z,1,2<br>I,1<br>O,1,4<br>I,1<br>P,2,3<br>I,2<br>S,1,2<br>I,1<br>R,2,1<br>I,2|1:8,2:5,3:3,4:1,6:1<br>7:4,9:1<br>1:8,2:5,7:4<br>1:3,2:3,3:1,4:1,6:1<br>1:3,2:3<br>3:1,4:1,6:1|

