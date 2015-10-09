# Aiheen kuvaus

**Aihe:** Puzzle Course

**Kuvaus:**
Puzzle Quest -pelin inspiroima peli. Pelialustana on värin perusteella luokiteltuja esineitä täynnä oleva 8x8 taulukko. Käyttäjän tehtävänä on muodostaa kolmen tai useamman samanvärisen esineen yhtenäisiä pysty- tai vaakasuuntaisia jonoja. Onnistuneen jononmuodostuksen seurauksena käyttäjä saa jonon esinetyypistä riippuvia etuja ja jonon esineet poistetaan laudalta samalla "pudottaen" esineet ylemmiltä riveiltä täyttämään vapautunut tila. Ylimmälle riville pudotuksissa luodaan uusi esine (toistuvasti tarvittaessa).

Käyttäjän työkaluna on vaihtaa kahden särmänaapuriesineen paikat keskenään. Ainoastaan onnistuneeseen jononmuodostukseen johtavat siirrot hyväksytään. Jos laudalla ei ole hyväksyttäviä siirtoja, lauta tyhjennetään ja täytetään uudestaan.

Voitto- ja häviötilat määritellään tarkemmin myöhemmin, kunhan ne ensi viikolla päätän.

**Käyttäjät:** Virikettä kaipaavat

**Kaikkien käyttäjien toiminnot:**
- Uuden pelin aloittaminen
- Vierekkäisten värikkäiden muotojen paikkojen vaihtaminen
  - Vain hyväksyttäville siirroille

# Rakenne

## Erä

Pelin säännöt ja tilat säilötään GameRound-luokassa. Yksi erä sisältää pelilaudan (Board-luokka) ja kaksi pelaajaa (Player- ja Opponent-luokat). Pelilaudalla on 64 semisattumanvaraista pelinappulaa (ColorPiece-luokka), joista jokainen kuuluu johonkin 6:sta luokasta (tallennettu kokonaislukuna luokassa). Pelilauta säilöö nappulat 8x8 matriisissa, ja nappuloihin viitataan yleensä (y-koordinaatti, x-koordinaatti)-pareilla (erityisesti Coordinate-luokka).

Erä on vuoropohjainen ja antaa pelaajien tehdä vaihtoja (Move-luokka). Todetessaan vaihdon olevan sääntöjenmukainen, erä hyväksyy vaihdon ja antaa vuoron seuraavalle pelaajalle. Ensimmäisellä pelaajalla on myös käytettävissään kykyjä (Ability-luokka), joita hän voi käyttää vuorollaan täyttämällä kyvyn ehdot. Jos laudalla ei ole sääntöjenmukaisia vaihtoja, erä uusii pelilaudan.

Erän edetessä pelaajat keräävät resursseja, ja erä päättyy huomatessaan jommankumman pelaajan keränneen voittoehtonsa täyttävät resurssit.

Erä käyttää TextFileLoader-luokkaa apunaan luodakseen pelaajat kykyineen ja dialogia (Dialog-luokka) erän erinäisissä merkittävissä tiloissa.

## Graafinen

Pelin graafinen toteutus on tehty JavaFX-grafiikkapaketeilla.

Graafinen käyttöliittymä koostuu useista näkymistä: pelinäkymä, dialoginäkymä ja tulosnäkymä. Näkymät ovat pinottu päällekkäin, ja niiden päällimmäisyyttä ja näkyvyyttä muokataan tarpeenmukaisesti. Tulosnäkymä lisätään pinoon vasta tarvittaessa.

Pelinäkymä koostuu pelaajapaneeleista (PlayerPanel-luokka), jotka sisältävät tietoa pelaajista, ja pelilaudasta, joka näyttää pelilaudan tilanteen.

Erä laskee pelin kulkemisen ja samalla tallentaa animaatioita BoardDrawCoordinates-luokassa olevaan animaatiojonoon odottamaan piirtoa.

Animaatioiden päivityksessä käytetään lähinnä n. 33 kertaa sekunnissa toistuvaa kutsua G_Updater-luokan tarjoamalle päivitysmetodille. Metodi tarkistaa tärkeysjärjestyksessä, mitä sen tulisi tällä kertaa päivittää. Jos pelinappuloiden liikeanimaatioita (BoardDrawCoordinates-luokka) on odottamassa animointia, metodi pyytää päivittämään nappuloiden piirtosijainnit. Todetessaan dialogipäivityksen tarpeelliseksi, se pyytää DialogLayer-luokkaa tulemaan esille ja päivittämään dialoginsa. Jos päivityksellä ei ole muita töitä ja se huomaa erän loppuneen, se lähettää GUI-luokalle pyynnön siirtyä lopetusruutuun.