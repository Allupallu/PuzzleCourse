# Aiheen kuvaus ja rakenne

**Aihe:** Puzzle Course

**Kuvaus:**
Puzzle Quest -pelin inspiroima peli. Pelialustana on värin perusteella luokiteltuja esineitä täynnä oleva 8x8 taulukko. Käyttäjän tehtävänä on muodostaa kolmen tai useamman samanvärisen esineen yhtenäisiä pysty- tai vaakasuuntaisia jonoja. Onnistuneen jononmuodostuksen seurauksena käyttäjä saa jonon esinetyypistä riippuvia etuja ja jonon esineet poistetaan laudalta samalla "pudottaen" esineet ylemmiltä riveiltä täyttämään vapautunut tila. Ylimmälle riville pudotuksissa luodaan uusi esine (toistuvasti tarvittaessa).

Käyttäjän työkaluna on vaihtaa kahden särmänaapuriesineen paikat keskenään. Ainoastaan onnistuneeseen jononmuodostukseen johtavat siirrot hyväksytään. Jos laudalla ei ole hyväksyttäviä siirtoja, lauta tyhjennetään ja täytetään uudestaan.

Voitto- ja häviötilat määritellään tarkemmin myöhemmin vastaantulevien ohjelmointihaasteiden selvitessä. Alustavasti häviö/voittotilaan päädytään, kun "Liikkeitä jäljellä"-laskuri laskee nollaan, jolloin pelin tavoitteena on pistemäärät.

**Käyttäjät:** Virikettä kaipaavat

**Kaikkien käyttäjien toiminnot:**
- Uuden pelin aloittaminen
- Vierekkäisten värikkäiden muotojen paikkojen vaihtaminen
-- Vain hyväksyttäville siirroille