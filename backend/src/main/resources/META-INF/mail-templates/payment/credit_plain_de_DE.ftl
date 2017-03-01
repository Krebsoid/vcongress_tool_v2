<#if person.gender() == "MALE">Sehr geehrter Herr</#if><#if person.gender() == "FEMALE">Sehr geehrte Frau</#if><#if person.title() != ""> ${person.title()!""}</#if> ${person.lastName()}
aufgrund Ihrer Stornierung erhalten Sie die Rechnungskorrektur zu Ihrer Rechnung zur folgenden Veranstaltung:

${event.name()}
${event.description()!""}
${event.location()!""}
${event.dateRangeAsString()!""}

Sollten Sie den Rechnungsbetrag bereits überwiesen haben, senden Sie uns bitte Ihre vollständigen Kontodaten (Kreditinstitut, Kontoinhaber, IBAN, BIC) für die Erstattung der Rechnungskorrektur.
<#if dynamicPlain?has_content>

${dynamicPlain}

<#else></#if>
<#if event.contact()?has_content>
Für Fragen wenden Sie sich bitte an das Veranstaltungsteam:
${event.contact()!""}

</#if>

Mit freundlichen Grüßen,
Ihr Veranstaltungsteam


------------------------------------------------------

Impressum: SciSerTec, Dr. Breuer/Bruweleit, c/o Medizinische Hochschule Hannover (MHH), Veranstaltungsmanagement OE 0060, Carl-Neuberg-Straße 1, D-30625 Hannover, T +49 511 10 54 894
http://www.scisertec.de

Watch videos and pictures of conferences SciSerTec organized: http://www.vcongress.de/referenzen.php

Learn more about SciSerTec products:
Congress management with vCongress Scientific - http://www.vcongress.de
Webdesign with sWebsites - http://www.dwwebsites.de
Networking and student's jobs with SciSerNet - http://www.sciser.net

Stay connected:
Google+ - https://plus.google.com/102133653331103179354/posts
Xing - https://www.xing.com/profile/Daniel_Breuer28
LinkedIn - http://de.linkedin.com/pub/dr-daniel-breuer-wicke/71/777/939
ResearchGate - http://www.researchgate.net/profile/Daniel_Breuer_Wicke/
Facebook - https://www.facebook.com/daniel.wicke.18
Twitter - https://twitter.com/DBreuerWicke
Youtube - https://www.youtube.com/results?search_query=scisertec&sm=3