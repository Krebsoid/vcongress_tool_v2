<#if person.gender() == "MALE">Sehr geehrter Herr</#if><#if person.gender() == "FEMALE">Sehr geehrte Frau</#if><#if person.title() != ""> ${person.title()!""}</#if> ${person.lastName()}
wir haben Ihre Zahlung <#if paymentMethod == "BANK_TRANSFER">per Banküberweisung</#if><#if paymentMethod == "PAYPAL">per PayPal</#if> erhalten und Ihre Teilnahme im System mit dem Status „bezahlt“ vermerkt.

${event.name()}
${event.description()!""}
${event.location()!""}
${event.dateRangeAsString()!""}

Ihren Teilnehmerstatus können Sie im System unter ${event.registrationLink()} einsehen. Bitte melden Sie sich dafür mit Ihrem Login an.
<#if dynamicPlain?has_content>

${dynamicPlain}

<#else></#if>
<#if event.contact()?has_content>
Für Fragen wenden Sie sich bitte an das Veranstaltungsteam:
${event.contact()!""}

</#if>

Mit freundlichen Grüßen,
Ihr Veranstaltungsteam