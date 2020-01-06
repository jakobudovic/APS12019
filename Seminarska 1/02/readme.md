# Druga naloga

Pri eni od najpogostejših tehnik mešanja kart (the overhand shuffle) karte najprej razdelimo na dva
dela. Potem drugi del postopoma vstavljamo v prvi del na izbrano mesto; lahko na začetek, lahko na
konec ali pa kam vmes. Postopek večkrat ponovimo.

Napišite program, ki bo mešal karte po tem postopku. Karte so označene s številkami in črkami
(namesto barv, jokerjev, kraljev in podobnih znakov). Vsaka karta je označena s številko (<32768) in
črko ('A' do 'Z'). Kup kart je predstavljen s seznamom, kjer je na prvem mestu napisana karta na vrhu
kupa. V spodnjem primeru je 1A na vrhu kupa, na dnu pa je 3B:

*1A,2B,1C,2E,2K,17A,3A,1A,3B*

Postopek mešanja poteka na naslednji način. Kup se najprej razdelili na dva dela, recimo jima K1 in
K2. Potem se nekaj prvih kart iz kupa K2 vstavi v K1 na točno določeno mesto. Nato se naslednjih
nekaj kart iz kupa K2 vstavi na isto mesto v K1 in se postopek ponavlja, dokler se K2 ne izprazni. V
vsakem koraku se vstavi isto število kart, razen v zadnjem, če preostalih kart ni dovolj. Vsako mešanje
kart je določeno z vrednostmi D, V in S:

* D predstavlja karto, ki razdeli kup na K1 in K2. Pri tem velja, da je D po razdelitvi zadnja karta
v kupu K1. Če take karte v kupu ni, potem se premaknejo vse karte na K2, K1 pa ostane
prazen. Če se v kupu nahaja več kart D, se ob delitvi upošteva položaj prve pojavitve.
* Karta V določa mesto vstavljanja v K1 (vstavlja se za karto V). Če karte v kupu K1 ni, se
vstavlja na začetek kupa K1; če jih je več, se spet upošteva prva pojavitev.
* S je število kart, ki se vstavijo v eni iteraciji postopka. 

Implementirajte razred **Naloga2**, ki vsebuje metodo main. Metoda v argumentih prejme poti do
vhodne in izhodne datoteke (args[0] in args[1]), prebere vhodne podatke, izvede mešanje in v
izhodno datoteko zapiše dobljeno zaporedje kart.

Tekstovna vhodna datoteka je podana v naslednjem formatu:

* V prvi vrstici sta zapisani dve celi števili, ločeni z vejico. Prva vrednost določa število kart v
začetnem kupu. Druga vrednost določa število mešanj.
* Druga vrstica vsebuje začetno zaporedje kart. Oznake kart so ločene z vejico.
* Sledijo navodila za mešanje. Vsako navodilo je v svoji vrstici predstavljeno s trojčkom D,V,S.

Tekstovna izhodna datoteka vsebuje zaporedje kart po vseh mešanjih. Oznake kart so ločene z vejico.

Primer:

| Vhodna datoteka:      | Izhodna datoteka:      |
|------------|-------------|
|9,1<br>1A,2B,1C,2E,2K,17A,3A,1A,3B<br>2E,1A,3|1A,1A,3B,2K,17A,3A,2B,1C,2E|

Razlaga primera:
Dan je kup devetih kart: *1A,2B,1C,2E,2K,17A,3A,1A,3B*. Podano je samo eno navodilo za mešanje po
pravilu D=2E, V=1A in S=3, ki se izvede s sledečimi koraki.

Karte se razdelijo za karto 2E:<br>
K1: 1A,2B,1C,2E<br>
K2: 2K,17A,3A,1A,3B

Vrednost I=1A pomeni, da se karte vstavljajo za karto 1A. Nastavitev S=3 določa, da se prenašajo po tri karte
naenkrat iz *K2* v *K1*. Ker je na začetku v *K2* pet kart, se celoten postopek izvede v dveh iteracijah.

Stanje po koncu prve iteracije:

*K1: 1A,2K,17A,3A,2B,1C,2E*<br>
*K2: 1A,3B*

Stanje po koncu druge iteracije:

*K1: 1A,1A,3B,2K,17A,3A,2B,1C,2E*<br>
*K2:*<br>

K2 je prazen, zato je mešanje zaključeno.