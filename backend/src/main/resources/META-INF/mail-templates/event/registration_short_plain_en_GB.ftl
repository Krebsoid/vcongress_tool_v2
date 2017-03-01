Dear <#if person.title() != "">${person.title()!""}</#if> ${person.firstName()} ${person.lastName()},
thank you for submitting your data for registration at the following event:

${event.name()}
${event.description()!""}
${event.location()!""}
${event.dateRangeAsString()!""}

Your login name is: ${person.user().name()}

Your application was successful.
<#if dynamicPlain?has_content>

${dynamicPlain}

<#else></#if>
<#if event.contact()?has_content>
If you have any further questions, please contact the event management:
${event.contact()!""}

</#if>

Kind regards your event management team


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