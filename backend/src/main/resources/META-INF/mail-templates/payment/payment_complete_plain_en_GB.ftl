Dear <#if person.title() != "">${person.title()!""}</#if> ${person.firstName()} ${person.lastName()},
we have received your payment and stored your participant status “paid” in our system.

${event.name()}
${event.description()!""}
${event.location()!""}
${event.dateRangeAsString()!""}

Our Online-Congress-Management-System allows you to monitor your current status at: ${event.registrationLink()}. Therefore please log in with your logon data.
<#if dynamicPlain?has_content>

${dynamicPlain}

<#else></#if>
<#if event.contact()?has_content>

If you have any further questions, please contact the event management:
${event.contact()!""}

</#if>

Kind regards your event management team