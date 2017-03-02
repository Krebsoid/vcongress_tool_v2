vCongress - CMS Version
==============

Dieses System stellt die Weiterentwicklung des älteren vCongress Konferenzverwaltungs-Systems aus dem Jahre 2009 dar.
Es sollte die Möglichkeit geschaffen werden für Kunden ihr System selbst konfigurieren zu können, ohne mit uns in Kontakt treten zu müssen. Dazu musste das System viel Flexibilität anbieten, um möglichst viele Szenarien abdecken zu können.
Konkret gesagt sollten die Kunden ihre eigenen Teilnahmeoptionen (verschiedene Tarife), Preise, Deadlines und Rahmenbedingungen definieren. Die besondere Herausforderung war dabei trotz der Komplexität die Bedienung intuitiv zu gestalten.

Dadurch, dass es eine Neuentwicklung ist, war es der optimale Moment um neuere Technologien einzusetzen.
Hier eine Auflistung der benutzten Technologien: Wildfly 9, Java 8, Java EE7, CDI, Hibernate (JPA), Apache Deltaspike, Jasper Reporting, PostgreSQL, AngularJS 1.4, SASS, Gulp, Amazon Webservice Integration (EC2, S3, SES, Route 53), 

Versionen
----------------

Das System sollte in mehreren Versionen verfügbar sein:

- Eine Basisversion, die alle Funktionalitäten anbietet, in der Form eines Multi Tenancy System als eine Art Online Shop, für ein breites Kundenspektrum, ohne individuelle Wünsche zum schnellen Konfigurieren und sofortigem produktiven Einsetzen zu einem Pauschalpreis.

- Eine individuelle CMS Version auf Kunden zugeschnitten (Look&Feel, eigene Prozesse) zur Verwaltung von mehreren Veranstaltungen. Zielgruppe sollten spezialisierte Veranstaltungsmanagements sein, die mehrere Veranstaltungen pro Jahr organisieren.

- Einzelversion, für kleinere Kunden, die nur eine einzelne Veranstaltung hosten wollen, aber trotzdem individuelle Funktionalitäten innerhalb des Systems brauchen

Architektur
-----------------

Während der Entwicklung der älteren Version, sowie einiger anderer Projekte habe ich mich immer mehr mit dem Domain Driven Design angefreundet und so versucht möglichst viele Aspekte in dieser Version unterzubringen.
Daher die Package Struktur:

- **Infrastructure** -> Technische Komponenten, Validatoren, Exception-Handling

- **Domain** -> fachliche Models, Logik, Factories, Events

- **Application** -> Fassade für domainspezifische Funktionalitäten, Verarbeitung von Commands von außen, Aufbereitung von Domain models in Representations (DTO) für die Außenwelt

- **Client** -> REST Schnittstelle, Validierung von einkommenden Daten, Requests mappen auf Commands im Application Layer

Als besonders wertvoll hat sich die Verwendung von Domain Events erwiesen. Bestimmte definierte Vorkommnisse innerhalb des System werden als Events modelliert und bei Auftreten losgeschickt und jegliche Komponente hat die Möglichkeit auf diese Events zu reagieren, was eine gut-wartbare lose Kopplung verschiedener Komponenten ermöglicht.
Falls nötig könnte man diese Events auch persistieren, um zum Beispiel Statistiken, Audits oder Analysen anbieten zu können.

Die Oberfläche wird komplett unabhängig vom Backend entwickelt, um mögliche Portierungen zu vereinfachen. Zum Beispiel das Entwickeln einer Mobile App, die sich dann genauso nur der REST Schnittstelle bedienen kann.

Anwendung
----------------

Die vCongress CMS Version wird aktuell an 3 Standorten in Deutschland zur Abhandlung von mehreren Veranstaltungen eingesetzt. 
Mitunter das Uniklinikum Leipzig, das letztes Jahr ca. 25 Veranstaltungen mit über 1600 Teilnehmern über das vCongress System organisiert hat.

Eine Registrierung als Teilnehmer zu einer Beispielveranstaltung ist verfügbar unter **http://cms.vcongress.de/test**

Die Administration zu der Beispielveranstaltung ist verfügbar unter **http://cms.vcongress.de/administration** mit den Benutzerdaten **user@user.de** und Passwort **user**. 
