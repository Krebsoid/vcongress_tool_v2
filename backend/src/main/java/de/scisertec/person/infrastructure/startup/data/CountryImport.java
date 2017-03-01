package de.scisertec.person.infrastructure.startup.data;


import de.scisertec.core.application.api.data.DataImportUnit;
import de.scisertec.core.domain.model.LocaleString;
import de.scisertec.person.domain.model.Country;
import de.scisertec.person.infrastructure.repository.CountryRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Locale;

@ApplicationScoped
public class CountryImport extends DataImportUnit {

    @Inject
    CountryRepository countryRepository;

    @Override
    protected Class importUnitClass() {
        return this.getClass();
    }

    protected void initialize() {

        Country country1 = new Country();
        country1.isoCode("AF")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Afghanistan")
                                .add(Locale.UK, "Afghanistan")
                );
        countryRepository.save(country1);

        Country country2 = new Country();
        country2.isoCode("EG")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Ägypten")
                                .add(Locale.UK, "Egypt")
                );
        countryRepository.save(country2);

        Country country3 = new Country();
        country3.isoCode("AL")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Albanien")
                                .add(Locale.UK, "Albania")
                );
        countryRepository.save(country3);

        Country country4 = new Country();
        country4.isoCode("DZ")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Algerien")
                                .add(Locale.UK, "Algeria")
                );
        countryRepository.save(country4);

        Country country5 = new Country();
        country5.isoCode("AD")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Andorra")
                                .add(Locale.UK, "Andorra")
                );
        countryRepository.save(country5);

        Country country6 = new Country();
        country6.isoCode("AO")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Angola")
                                .add(Locale.UK, "Angola")
                );
        countryRepository.save(country6);

        Country country7 = new Country();
        country7.isoCode("AG")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Antigua und Barbuda")
                                .add(Locale.UK, "Antigua and Barbuda")
                );
        countryRepository.save(country7);

        Country country8 = new Country();
        country8.isoCode("GQ")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Äquatorialguinea")
                                .add(Locale.UK, "Equatorial Guinea")
                );
        countryRepository.save(country8);

        Country country9 = new Country();
        country9.isoCode("AR")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Argentinien")
                                .add(Locale.UK, "Argentina")
                );
        countryRepository.save(country9);

        Country country10 = new Country();
        country10.isoCode("AM")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Armenien")
                                .add(Locale.UK, "Armenia")
                );
        countryRepository.save(country10);

        Country country11 = new Country();
        country11.isoCode("AZ")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Aserbaidschan")
                                .add(Locale.UK, "Azerbaijan")
                );
        countryRepository.save(country11);

        Country country12 = new Country();
        country12.isoCode("ET")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Äthiopien")
                                .add(Locale.UK, "Ethiopia")
                );
        countryRepository.save(country12);

        Country country13 = new Country();
        country13.isoCode("AU")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Australien")
                                .add(Locale.UK, "Australia")
                );
        countryRepository.save(country13);

        Country country14 = new Country();
        country14.isoCode("BS")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Bahamas")
                                .add(Locale.UK, "Bahamas")
                );
        countryRepository.save(country14);

        Country country15 = new Country();
        country15.isoCode("BH")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Bahrain")
                                .add(Locale.UK, "Bahrain")
                );
        countryRepository.save(country15);

        Country country16 = new Country();
        country16.isoCode("BD")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Bangladesch")
                                .add(Locale.UK, "Bangladesh")
                );
        countryRepository.save(country16);

        Country country17 = new Country();
        country17.isoCode("BB")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Barbados")
                                .add(Locale.UK, "Barbados")
                );
        countryRepository.save(country17);

        Country country18 = new Country();
        country18.isoCode("BE")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Belgien")
                                .add(Locale.UK, "Belgium")
                );
        countryRepository.save(country18);

        Country country19 = new Country();
        country19.isoCode("BZ")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Belize")
                                .add(Locale.UK, "Belize")
                );
        countryRepository.save(country19);

        Country country20 = new Country();
        country20.isoCode("BJ")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Benin")
                                .add(Locale.UK, "Benin")
                );
        countryRepository.save(country20);

        Country country21 = new Country();
        country21.isoCode("BT")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Bhutan")
                                .add(Locale.UK, "Bhutan")
                );
        countryRepository.save(country21);

        Country country22 = new Country();
        country22.isoCode("BO")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Bolivien")
                                .add(Locale.UK, "Bolivia")
                );
        countryRepository.save(country22);

        Country country23 = new Country();
        country23.isoCode("BA")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Bosnien und Herzegowina")
                                .add(Locale.UK, "Bosnia and Herzegovina")
                );
        countryRepository.save(country23);

        Country country24 = new Country();
        country24.isoCode("BW")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Botswana")
                                .add(Locale.UK, "Botswana")
                );
        countryRepository.save(country24);

        Country country25 = new Country();
        country25.isoCode("BR")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Brasilien")
                                .add(Locale.UK, "Brazil")
                );
        countryRepository.save(country25);

        Country country26 = new Country();
        country26.isoCode("BN")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Brunei")
                                .add(Locale.UK, "Brunei")
                );
        countryRepository.save(country26);

        Country country27 = new Country();
        country27.isoCode("BG")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Bulgarien")
                                .add(Locale.UK, "Bulgaria")
                );
        countryRepository.save(country27);

        Country country28 = new Country();
        country28.isoCode("BF")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Burkina Faso")
                                .add(Locale.UK, "Burkina Faso")
                );
        countryRepository.save(country28);

        Country country29 = new Country();
        country29.isoCode("BI")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Burundi")
                                .add(Locale.UK, "Burundi")
                );
        countryRepository.save(country29);

        Country country30 = new Country();
        country30.isoCode("CL")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Chile")
                                .add(Locale.UK, "Chile")
                );
        countryRepository.save(country30);

        Country country31 = new Country();
        country31.isoCode("TW")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Republik China")
                                .add(Locale.UK, "Republic of China")
                );
        countryRepository.save(country31);

        Country country32 = new Country();
        country32.isoCode("CN")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Volksrepublik China")
                                .add(Locale.UK, "China")
                );
        countryRepository.save(country32);

        Country country33 = new Country();
        country33.isoCode("CK")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Cookinseln")
                                .add(Locale.UK, "Cook Islands")
                );
        countryRepository.save(country33);

        Country country34 = new Country();
        country34.isoCode("CR")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Costa Rica")
                                .add(Locale.UK, "Costa Rica")
                );
        countryRepository.save(country34);

        Country country35 = new Country();
        country35.isoCode("DK")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Dänemark")
                                .add(Locale.UK, "Denmark")
                );
        countryRepository.save(country35);

        Country country36 = new Country();
        country36.isoCode("DE")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Deutschland")
                                .add(Locale.UK, "Germany")
                );
        countryRepository.save(country36);

        Country country37 = new Country();
        country37.isoCode("DM")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Dominica")
                                .add(Locale.UK, "Dominica")
                );
        countryRepository.save(country37);

        Country country38 = new Country();
        country38.isoCode("DO")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Dominikanische Republik")
                                .add(Locale.UK, "Dominican Republic")
                );
        countryRepository.save(country38);

        Country country39 = new Country();
        country39.isoCode("DJ")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Dschibuto")
                                .add(Locale.UK, "Djibouti")
                );
        countryRepository.save(country39);

        Country country40 = new Country();
        country40.isoCode("EC")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Ecuador")
                                .add(Locale.UK, "Ecuador")
                );
        countryRepository.save(country40);

        Country country41 = new Country();
        country41.isoCode("SV")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "El Salvador")
                                .add(Locale.UK, "El Salvador")
                );
        countryRepository.save(country41);

        Country country42 = new Country();
        country42.isoCode("CI")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Elfenbeinküste")
                                .add(Locale.UK, "Ivory Coast")
                );
        countryRepository.save(country42);

        Country country43 = new Country();
        country43.isoCode("ER")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Eritrea")
                                .add(Locale.UK, "Eritrea")
                );
        countryRepository.save(country43);

        Country country44 = new Country();
        country44.isoCode("EE")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Estland")
                                .add(Locale.UK, "Estonia")
                );
        countryRepository.save(country44);

        Country country45 = new Country();
        country45.isoCode("FJ")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Fidschi")
                                .add(Locale.UK, "Fiji")
                );
        countryRepository.save(country45);

        Country country46 = new Country();
        country46.isoCode("FI")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Finnland")
                                .add(Locale.UK, "Finland")
                );
        countryRepository.save(country46);

        Country country47 = new Country();
        country47.isoCode("FR")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Frankreich")
                                .add(Locale.UK, "France")
                );
        countryRepository.save(country47);

        Country country48 = new Country();
        country48.isoCode("GA")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Gabun")
                                .add(Locale.UK, "Gabon")
                );
        countryRepository.save(country48);

        Country country49 = new Country();
        country49.isoCode("GM")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Gambia")
                                .add(Locale.UK, "Gambia")
                );
        countryRepository.save(country49);

        Country country50 = new Country();
        country50.isoCode("GE")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Georgien")
                                .add(Locale.UK, "Georgia")
                );
        countryRepository.save(country50);

        Country country51 = new Country();
        country51.isoCode("GH")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Ghana")
                                .add(Locale.UK, "Ghana")
                );
        countryRepository.save(country51);

        Country country52 = new Country();
        country52.isoCode("GD")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Grenada")
                                .add(Locale.UK, "Grenada")
                );
        countryRepository.save(country52);

        Country country53 = new Country();
        country53.isoCode("GR")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Griechenland")
                                .add(Locale.UK, "Greece")
                );
        countryRepository.save(country53);

        Country country54 = new Country();
        country54.isoCode("GT")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Guatemala")
                                .add(Locale.UK, "Guatemala")
                );
        countryRepository.save(country54);

        Country country55 = new Country();
        country55.isoCode("GN")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Guinea")
                                .add(Locale.UK, "Guinea")
                );
        countryRepository.save(country55);

        Country country56 = new Country();
        country56.isoCode("GW")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Guinea-Bissau")
                                .add(Locale.UK, "Guinea-Bissau")
                );
        countryRepository.save(country56);

        Country country57 = new Country();
        country57.isoCode("GY")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Guyana")
                                .add(Locale.UK, "Guyana")
                );
        countryRepository.save(country57);

        Country country58 = new Country();
        country58.isoCode("HT")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Haiti")
                                .add(Locale.UK, "Haiti")
                );
        countryRepository.save(country58);

        Country country59 = new Country();
        country59.isoCode("HN")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Honduras")
                                .add(Locale.UK, "Honduras")
                );
        countryRepository.save(country59);

        Country country60 = new Country();
        country60.isoCode("IN")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Indien")
                                .add(Locale.UK, "India")
                );
        countryRepository.save(country60);

        Country country61 = new Country();
        country61.isoCode("ID")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Indonesien")
                                .add(Locale.UK, "Indonesia")
                );
        countryRepository.save(country61);

        Country country62 = new Country();
        country62.isoCode("IQ")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Irak")
                                .add(Locale.UK, "Iraq")
                );
        countryRepository.save(country62);

        Country country63 = new Country();
        country63.isoCode("IR")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Iran")
                                .add(Locale.UK, "Iran")
                );
        countryRepository.save(country63);

        Country country64 = new Country();
        country64.isoCode("IE")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Irland")
                                .add(Locale.UK, "Ireland")
                );
        countryRepository.save(country64);

        Country country65 = new Country();
        country65.isoCode("IS")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Island")
                                .add(Locale.UK, "Iceland")
                );
        countryRepository.save(country65);

        Country country66 = new Country();
        country66.isoCode("IL")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Israel")
                                .add(Locale.UK, "Israel")
                );
        countryRepository.save(country66);

        Country country67 = new Country();
        country67.isoCode("IT")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Italien")
                                .add(Locale.UK, "Italy")
                );
        countryRepository.save(country67);

        Country country68 = new Country();
        country68.isoCode("JM")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Jamaika")
                                .add(Locale.UK, "Jamaica")
                );
        countryRepository.save(country68);

        Country country69 = new Country();
        country69.isoCode("JP")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Japan")
                                .add(Locale.UK, "Japan")
                );
        countryRepository.save(country69);

        Country country70 = new Country();
        country70.isoCode("YE")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Jemen")
                                .add(Locale.UK, "Yemen")
                );
        countryRepository.save(country70);

        Country country71 = new Country();
        country71.isoCode("JO")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Jordanien")
                                .add(Locale.UK, "Jordan")
                );
        countryRepository.save(country71);

        Country country72 = new Country();
        country72.isoCode("KH")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Kombodscha")
                                .add(Locale.UK, "Combodia")
                );
        countryRepository.save(country72);

        Country country73 = new Country();
        country73.isoCode("CM")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Kamerun")
                                .add(Locale.UK, "Cameroon")
                );
        countryRepository.save(country73);

        Country country74 = new Country();
        country74.isoCode("CA")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Canada")
                                .add(Locale.UK, "Canada")
                );
        countryRepository.save(country74);

        Country country75 = new Country();
        country75.isoCode("CV")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Kap Verde")
                                .add(Locale.UK, "Cape Verde")
                );
        countryRepository.save(country75);

        Country country76 = new Country();
        country76.isoCode("KZ")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Kasachstan")
                                .add(Locale.UK, "Kazakhstan")
                );
        countryRepository.save(country76);

        Country country77 = new Country();
        country77.isoCode("QA")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Katar")
                                .add(Locale.UK, "Qatar")
                );
        countryRepository.save(country77);

        Country country78 = new Country();
        country78.isoCode("KE")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Kenia")
                                .add(Locale.UK, "Kenya")
                );
        countryRepository.save(country78);

        Country country79 = new Country();
        country79.isoCode("KG")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Kirgisistan")
                                .add(Locale.UK, "Kyrgyzstan")
                );
        countryRepository.save(country79);

        Country country80 = new Country();
        country80.isoCode("KI")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Kiribati")
                                .add(Locale.UK, "Kiribati")
                );
        countryRepository.save(country80);

        Country country81 = new Country();
        country81.isoCode("CO")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Kolumbien")
                                .add(Locale.UK, "Columbia")
                );
        countryRepository.save(country81);

        Country country82 = new Country();
        country82.isoCode("KM")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Komoren")
                                .add(Locale.UK, "Comoros")
                );
        countryRepository.save(country82);

        Country country83 = new Country();
        country83.isoCode("CD")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Kongo, Demokratische Republik")
                                .add(Locale.UK, "Congo, Democratic Republic")
                );
        countryRepository.save(country83);

        Country country84 = new Country();
        country84.isoCode("CG")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Kongo, Republik")
                                .add(Locale.UK, "Congo, Republic")
                );
        countryRepository.save(country84);

        Country country85 = new Country();
        country85.isoCode("KP")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Nord Korea")
                                .add(Locale.UK, "Republic of North Korea")
                );
        countryRepository.save(country85);

        Country country86 = new Country();
        country86.isoCode("KR")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Süd Korea")
                                .add(Locale.UK, "Republic of South Korea")
                );
        countryRepository.save(country86);

        Country country87 = new Country();
        country87.isoCode("HR")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Kroatien")
                                .add(Locale.UK, "Croatia")
                );
        countryRepository.save(country87);

        Country country88 = new Country();
        country88.isoCode("CU")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Kuba")
                                .add(Locale.UK, "Cuba")
                );
        countryRepository.save(country88);

        Country country89 = new Country();
        country89.isoCode("KW")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Kuwait")
                                .add(Locale.UK, "Kuwait")
                );
        countryRepository.save(country89);

        Country country90 = new Country();
        country90.isoCode("LA")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Laos")
                                .add(Locale.UK, "Lao")
                );
        countryRepository.save(country90);

        Country country91 = new Country();
        country91.isoCode("LS")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Lesotho")
                                .add(Locale.UK, "Lesotho")
                );
        countryRepository.save(country91);

        Country country92 = new Country();
        country92.isoCode("LV")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Lettland")
                                .add(Locale.UK, "Latvia")
                );
        countryRepository.save(country92);

        Country country93 = new Country();
        country93.isoCode("LB")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Libanon")
                                .add(Locale.UK, "Lebanon")
                );
        countryRepository.save(country93);

        Country country94 = new Country();
        country94.isoCode("LR")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Liberia")
                                .add(Locale.UK, "Liberia")
                );
        countryRepository.save(country94);

        Country country95 = new Country();
        country95.isoCode("LY")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Libyen")
                                .add(Locale.UK, "Libya")
                );
        countryRepository.save(country95);

        Country country96 = new Country();
        country96.isoCode("LI")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Liechtenstein")
                                .add(Locale.UK, "Liechtenstein")
                );
        countryRepository.save(country96);

        Country country97 = new Country();
        country97.isoCode("LT")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Litauen")
                                .add(Locale.UK, "Lithuania")
                );
        countryRepository.save(country97);

        Country country98 = new Country();
        country98.isoCode("LU")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Luxemburg")
                                .add(Locale.UK, "Luxembourg")
                );
        countryRepository.save(country98);

        Country country99 = new Country();
        country99.isoCode("MG")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Madagaskar")
                                .add(Locale.UK, "Madagascar")
                );
        countryRepository.save(country99);

        Country country100 = new Country();
        country100.isoCode("MW")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Malawi")
                                .add(Locale.UK, "Malawi")
                );
        countryRepository.save(country100);

        Country country101 = new Country();
        country101.isoCode("MY")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Malaysia")
                                .add(Locale.UK, "Malaysia")
                );
        countryRepository.save(country101);

        Country country102 = new Country();
        country102.isoCode("MV")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Malediven")
                                .add(Locale.UK, "Maldives")
                );
        countryRepository.save(country102);

        Country country103 = new Country();
        country103.isoCode("ML")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Mali")
                                .add(Locale.UK, "Mali")
                );
        countryRepository.save(country103);

        Country country104 = new Country();
        country104.isoCode("MT")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Malta")
                                .add(Locale.UK, "Malta")
                );
        countryRepository.save(country104);

        Country country105 = new Country();
        country105.isoCode("MA")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Marokko")
                                .add(Locale.UK, "Morocco")
                );
        countryRepository.save(country105);

        Country country106 = new Country();
        country106.isoCode("MH")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Marshallinseln")
                                .add(Locale.UK, "Marshall Islands")
                );
        countryRepository.save(country106);

        Country country107 = new Country();
        country107.isoCode("MR")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Mauretanien")
                                .add(Locale.UK, "Mauritania")
                );
        countryRepository.save(country107);

        Country country108 = new Country();
        country108.isoCode("MU")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Mauritius")
                                .add(Locale.UK, "Mauritius")
                );
        countryRepository.save(country108);

        Country country109 = new Country();
        country109.isoCode("MK")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Mazedonien")
                                .add(Locale.UK, "Macedonia")
                );
        countryRepository.save(country109);

        Country country110 = new Country();
        country110.isoCode("MX")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Mexiko")
                                .add(Locale.UK, "Mexico")
                );
        countryRepository.save(country110);

        Country country111 = new Country();
        country111.isoCode("FM")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Mikronesien")
                                .add(Locale.UK, "Micronesia")
                );
        countryRepository.save(country111);

        Country country112 = new Country();
        country112.isoCode("MD")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Moldawien")
                                .add(Locale.UK, "Moldova")
                );
        countryRepository.save(country112);

        Country country113 = new Country();
        country113.isoCode("MC")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Monaco")
                                .add(Locale.UK, "Monaco")
                );
        countryRepository.save(country113);

        Country country114 = new Country();
        country114.isoCode("MN")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Mongolei")
                                .add(Locale.UK, "Mongolia")
                );
        countryRepository.save(country114);

        Country country115 = new Country();
        country115.isoCode("ME")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Montenegro")
                                .add(Locale.UK, "Montenegro")
                );
        countryRepository.save(country115);

        Country country116 = new Country();
        country116.isoCode("MZ")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Mosambik")
                                .add(Locale.UK, "Mozambique")
                );
        countryRepository.save(country116);

        Country country117 = new Country();
        country117.isoCode("MM")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Myanmar")
                                .add(Locale.UK, "Myanmar")
                );
        countryRepository.save(country117);

        Country country118 = new Country();
        country118.isoCode("NA")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Namibia")
                                .add(Locale.UK, "Namibia")
                );
        countryRepository.save(country118);

        Country country119 = new Country();
        country119.isoCode("NR")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Nauru")
                                .add(Locale.UK, "Nauru")
                );
        countryRepository.save(country119);

        Country country120 = new Country();
        country120.isoCode("NP")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Nepal")
                                .add(Locale.UK, "Nepal")
                );
        countryRepository.save(country120);

        Country country121 = new Country();
        country121.isoCode("NZ")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Neuseeland")
                                .add(Locale.UK, "New Zealand")
                );
        countryRepository.save(country121);

        Country country122 = new Country();
        country122.isoCode("NI")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Nicaragua")
                                .add(Locale.UK, "Nicaragua")
                );
        countryRepository.save(country122);

        Country country123 = new Country();
        country123.isoCode("NL")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Niederlande")
                                .add(Locale.UK, "Netherlands")
                );
        countryRepository.save(country123);

        Country country124 = new Country();
        country124.isoCode("NE")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Niger")
                                .add(Locale.UK, "Niger")
                );
        countryRepository.save(country124);

        Country country125 = new Country();
        country125.isoCode("NG")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Nigeria")
                                .add(Locale.UK, "Nigeria")
                );
        countryRepository.save(country125);

        Country country126 = new Country();
        country126.isoCode("NU")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Niue")
                                .add(Locale.UK, "Niue")
                );
        countryRepository.save(country126);

        Country country127 = new Country();
        country127.isoCode("NO")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Norwegen")
                                .add(Locale.UK, "Norway")
                );
        countryRepository.save(country127);

        Country country128 = new Country();
        country128.isoCode("OM")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Oman")
                                .add(Locale.UK, "Oman")
                );
        countryRepository.save(country128);

        Country country129 = new Country();
        country129.isoCode("AT")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Österreich")
                                .add(Locale.UK, "Austria")
                );
        countryRepository.save(country129);

        Country country130 = new Country();
        country130.isoCode("TL")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Osttimor")
                                .add(Locale.UK, "East Timor")
                );
        countryRepository.save(country130);

        Country country131 = new Country();
        country131.isoCode("PK")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Pakistan")
                                .add(Locale.UK, "Pakistan")
                );
        countryRepository.save(country131);

        Country country132 = new Country();
        country132.isoCode("PS")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Palästina")
                                .add(Locale.UK, "Palestine")
                );
        countryRepository.save(country132);

        Country country133 = new Country();
        country133.isoCode("PW")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Palau")
                                .add(Locale.UK, "Palau")
                );
        countryRepository.save(country133);

        Country country134 = new Country();
        country134.isoCode("PA")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Panama")
                                .add(Locale.UK, "Panama")
                );
        countryRepository.save(country134);

        Country country135 = new Country();
        country135.isoCode("PG")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Papua-Neuguinea")
                                .add(Locale.UK, "Papua New Guinea")
                );
        countryRepository.save(country135);

        Country country136 = new Country();
        country136.isoCode("PY")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Paraguay")
                                .add(Locale.UK, "Paraguay")
                );
        countryRepository.save(country136);

        Country country137 = new Country();
        country137.isoCode("PE")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Peru")
                                .add(Locale.UK, "Peru")
                );
        countryRepository.save(country137);

        Country country138 = new Country();
        country138.isoCode("PH")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Philippinen")
                                .add(Locale.UK, "Philippines")
                );
        countryRepository.save(country138);

        Country country139 = new Country();
        country139.isoCode("PL")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Polen")
                                .add(Locale.UK, "Poland")
                );
        countryRepository.save(country139);

        Country country140 = new Country();
        country140.isoCode("PT")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Portugal")
                                .add(Locale.UK, "Portugal")
                );
        countryRepository.save(country140);

        Country country141 = new Country();
        country141.isoCode("RW")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Ruanda")
                                .add(Locale.UK, "Rwanda")
                );
        countryRepository.save(country141);

        Country country142 = new Country();
        country142.isoCode("RO")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Rumänien")
                                .add(Locale.UK, "Romania")
                );
        countryRepository.save(country142);

        Country country143 = new Country();
        country143.isoCode("RU")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Russland")
                                .add(Locale.UK, "Russia")
                );
        countryRepository.save(country143);

        Country country144 = new Country();
        country144.isoCode("SB")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Salomonen")
                                .add(Locale.UK, "Solomon Islands")
                );
        countryRepository.save(country144);

        Country country145 = new Country();
        country145.isoCode("ZM")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Sambia")
                                .add(Locale.UK, "Zambia")
                );
        countryRepository.save(country145);

        Country country146 = new Country();
        country146.isoCode("WS")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Samoa")
                                .add(Locale.UK, "Samoa")
                );
        countryRepository.save(country146);

        Country country147 = new Country();
        country147.isoCode("SM")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "San Marino")
                                .add(Locale.UK, "San Marina")
                );
        countryRepository.save(country147);

        Country country148 = new Country();
        country148.isoCode("ST")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "São Tomé und Príncipe")
                                .add(Locale.UK, "São Tomé and Príncipe")
                );
        countryRepository.save(country148);

        Country country149 = new Country();
        country149.isoCode("SA")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Saudi-Arabien")
                                .add(Locale.UK, "Saudi Arabia")
                );
        countryRepository.save(country149);

        Country country150 = new Country();
        country150.isoCode("SE")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Schweden")
                                .add(Locale.UK, "Sweden")
                );
        countryRepository.save(country150);

        Country country151 = new Country();
        country151.isoCode("CH")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Schweiz")
                                .add(Locale.UK, "Switzerland")
                );
        countryRepository.save(country151);

        Country country152 = new Country();
        country152.isoCode("SN")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Senegal")
                                .add(Locale.UK, "Senegal")
                );
        countryRepository.save(country152);

        Country country153 = new Country();
        country153.isoCode("RS")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Serbien")
                                .add(Locale.UK, "Serbia")
                );
        countryRepository.save(country153);

        Country country154 = new Country();
        country154.isoCode("SC")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Seychellen")
                                .add(Locale.UK, "Seychelles")
                );
        countryRepository.save(country154);

        Country country155 = new Country();
        country155.isoCode("SL")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Sierra Leone")
                                .add(Locale.UK, "Sierra Leone")
                );
        countryRepository.save(country155);

        Country country156 = new Country();
        country156.isoCode("ZW")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Simbabwe")
                                .add(Locale.UK, "Zimbabwe")
                );
        countryRepository.save(country156);

        Country country157 = new Country();
        country157.isoCode("SG")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Singapur")
                                .add(Locale.UK, "Singapore")
                );
        countryRepository.save(country157);

        Country country158 = new Country();
        country158.isoCode("SK")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Slowakei")
                                .add(Locale.UK, "Slovakia")
                );
        countryRepository.save(country158);

        Country country159 = new Country();
        country159.isoCode("SI")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Slowenien")
                                .add(Locale.UK, "Slovenia")
                );
        countryRepository.save(country159);

        Country country160 = new Country();
        country160.isoCode("SO")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Somalia")
                                .add(Locale.UK, "Somalia")
                );
        countryRepository.save(country160);

        Country country161 = new Country();
        country161.isoCode("ES")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Spanien")
                                .add(Locale.UK, "Spain")
                );
        countryRepository.save(country161);

        Country country162 = new Country();
        country162.isoCode("LK")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Sri Lanka")
                                .add(Locale.UK, "Sri Lanka")
                );
        countryRepository.save(country162);

        Country country163 = new Country();
        country163.isoCode("KN")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "St. Kitts und Nevis")
                                .add(Locale.UK, "Saint Kitts and Nevis")
                );
        countryRepository.save(country163);

        Country country164 = new Country();
        country164.isoCode("LC")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "St. Lucia")
                                .add(Locale.UK, "Saint Lucia")
                );
        countryRepository.save(country164);

        Country country165 = new Country();
        country165.isoCode("VC")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "St. Vincent und die Grenadinen")
                                .add(Locale.UK, "Saint Vincent and the Grenadines")
                );
        countryRepository.save(country165);

        Country country166 = new Country();
        country166.isoCode("ZA")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Südafrika")
                                .add(Locale.UK, "South Africa")
                );
        countryRepository.save(country166);

        Country country167 = new Country();
        country167.isoCode("SD")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Sudan")
                                .add(Locale.UK, "Sudan")
                );
        countryRepository.save(country167);

        Country country168 = new Country();
        country168.isoCode("SS")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Südsudan")
                                .add(Locale.UK, "South Sudan")
                );
        countryRepository.save(country168);

        Country country169 = new Country();
        country169.isoCode("SR")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Suriname")
                                .add(Locale.UK, "Suriname")
                );
        countryRepository.save(country169);

        Country country170 = new Country();
        country170.isoCode("SZ")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Swasiland")
                                .add(Locale.UK, "Swaziland")
                );
        countryRepository.save(country170);

        Country country171 = new Country();
        country171.isoCode("SY")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Syrien")
                                .add(Locale.UK, "Syria")
                );
        countryRepository.save(country171);

        Country country172 = new Country();
        country172.isoCode("TJ")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Tadschikistan")
                                .add(Locale.UK, "Tajikistan")
                );
        countryRepository.save(country172);

        Country country173 = new Country();
        country173.isoCode("TZ")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Tansania")
                                .add(Locale.UK, "Tanzania")
                );
        countryRepository.save(country173);

        Country country174 = new Country();
        country174.isoCode("TH")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Thailand")
                                .add(Locale.UK, "Thailand")
                );
        countryRepository.save(country174);

        Country country175 = new Country();
        country175.isoCode("TG")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Togo")
                                .add(Locale.UK, "Togo")
                );
        countryRepository.save(country175);

        Country country176 = new Country();
        country176.isoCode("TO")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Tonga")
                                .add(Locale.UK, "Tonga")
                );
        countryRepository.save(country176);

        Country country177 = new Country();
        country177.isoCode("TT")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Trinidad und Tobago")
                                .add(Locale.UK, "Trinidad and Tobago")
                );
        countryRepository.save(country177);

        Country country178 = new Country();
        country178.isoCode("TD")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Tschad")
                                .add(Locale.UK, "Chad")
                );
        countryRepository.save(country178);

        Country country179 = new Country();
        country179.isoCode("CZ")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Tschechien")
                                .add(Locale.UK, "Czech Republic")
                );
        countryRepository.save(country179);

        Country country180 = new Country();
        country180.isoCode("TN")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Tunesien")
                                .add(Locale.UK, "Tunisia")
                );
        countryRepository.save(country180);

        Country country181 = new Country();
        country181.isoCode("TR")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Türkei")
                                .add(Locale.UK, "Turkey")
                );
        countryRepository.save(country181);

        Country country182 = new Country();
        country182.isoCode("TM")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Turkmenistan")
                                .add(Locale.UK, "Turkmenistan")
                );
        countryRepository.save(country182);

        Country country183 = new Country();
        country183.isoCode("TV")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Tuvalu")
                                .add(Locale.UK, "Tuvalu")
                );
        countryRepository.save(country183);

        Country country184 = new Country();
        country184.isoCode("UG")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Uganda")
                                .add(Locale.UK, "Uganda")
                );
        countryRepository.save(country184);

        Country country185 = new Country();
        country185.isoCode("UA")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Ukraine")
                                .add(Locale.UK, "Ukraine")
                );
        countryRepository.save(country185);

        Country country186 = new Country();
        country186.isoCode("HU")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Ungarn")
                                .add(Locale.UK, "Hungary")
                );
        countryRepository.save(country186);

        Country country187 = new Country();
        country187.isoCode("UY")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Uruguay")
                                .add(Locale.UK, "Uruguay")
                );
        countryRepository.save(country187);

        Country country188 = new Country();
        country188.isoCode("UZ")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Usbekistan")
                                .add(Locale.UK, "Uzbekistan")
                );
        countryRepository.save(country188);

        Country country189 = new Country();
        country189.isoCode("VU")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Vanuatu")
                                .add(Locale.UK, "Vanuatu")
                );
        countryRepository.save(country189);

        Country country190 = new Country();
        country190.isoCode("VA")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Vatikanstadt")
                                .add(Locale.UK, "Vatican City")
                );
        countryRepository.save(country190);

        Country country191 = new Country();
        country191.isoCode("VE")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Venezuela")
                                .add(Locale.UK, "Venezuela")
                );
        countryRepository.save(country191);

        Country country192 = new Country();
        country192.isoCode("AE")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Vereinigte Arabische Emirate")
                                .add(Locale.UK, "United Arab Emirates")
                );
        countryRepository.save(country192);

        Country country193 = new Country();
        country193.isoCode("US")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Vereinigte Staaten")
                                .add(Locale.UK, "United States")
                );
        countryRepository.save(country193);

        Country country194 = new Country();
        country194.isoCode("GB")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Vereinigtes Königreich")
                                .add(Locale.UK, "United Kingdom")
                );
        countryRepository.save(country194);

        Country country195 = new Country();
        country195.isoCode("VN")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Vietnam")
                                .add(Locale.UK, "Vietnam")
                );
        countryRepository.save(country195);

        Country country196 = new Country();
        country196.isoCode("BY")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Weißrussland")
                                .add(Locale.UK, "Belarus")
                );
        countryRepository.save(country196);

        Country country197 = new Country();
        country197.isoCode("EH")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Westsahara")
                                .add(Locale.UK, "Western Sahara")
                );
        countryRepository.save(country197);

        Country country198 = new Country();
        country198.isoCode("CF")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Zentralafrikanische Republik")
                                .add(Locale.UK, "Central African Republic")
                );
        countryRepository.save(country198);

        Country country199 = new Country();
        country199.isoCode("CY")
                .name(new LocaleString()
                                .add(Locale.GERMANY, "Zypern")
                                .add(Locale.UK, "Cyprus")
                );
        countryRepository.save(country199);
    }
}
