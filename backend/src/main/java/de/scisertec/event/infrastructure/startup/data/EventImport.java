package de.scisertec.event.infrastructure.startup.data;

import de.scisertec.account.domain.model.Group;
import de.scisertec.account.domain.model.Role;
import de.scisertec.account.domain.model.User;
import de.scisertec.account.infrastructure.repository.GroupRepository;
import de.scisertec.account.infrastructure.repository.UserRepository;
import de.scisertec.core.application.api.data.DataImportUnit;
import de.scisertec.customer.application.api.service.OrderService;
import de.scisertec.customer.domain.model.Customer;
import de.scisertec.customer.domain.model.Order;
import de.scisertec.customer.infrastructure.repository.CustomerRepository;
import de.scisertec.customer.infrastructure.service.ProductProvider;
import de.scisertec.customer.infrastructure.startup.data.CustomerImport;
import de.scisertec.event.domain.model.*;
import de.scisertec.event.domain.model.feature.*;
import de.scisertec.event.infrastructure.repository.EventFeatureRepository;
import de.scisertec.event.infrastructure.repository.EventItemRepository;
import de.scisertec.event.infrastructure.repository.EventRepository;
import de.scisertec.payment.domain.model.PaymentItemContainer;
import de.scisertec.payment.domain.model.TransactionStatus;
import de.scisertec.payment.domain.model.feature.BankTransferFeature;
import de.scisertec.payment.domain.model.feature.Cancellation;
import de.scisertec.payment.domain.model.feature.CancellationFeature;
import de.scisertec.payment.domain.model.feature.PaymentFeature;
import de.scisertec.payment.infrastructure.repository.CancellationRepository;
import de.scisertec.payment.infrastructure.repository.OrderRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.stream.Collectors;

@ApplicationScoped
public class EventImport extends DataImportUnit {

    @Inject
    EventRepository eventRepository;

    @Inject
    CustomerImport customerImport;

    @Inject
    EventFeatureRepository eventFeatureRepository;

    @Inject
    EventItemRepository eventItemRepository;

    @Inject
    GroupRepository groupRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    OrderService orderService;

    @Inject
    CancellationRepository cancellationRepository;

    @Inject
    ProductProvider productProvider;

    @Inject
    OrderRepository orderRepository;

    @Inject
    CustomerRepository customerRepository;

    Event event;

    @Override
    protected Class importUnitClass() {
        return this.getClass();
    }

    protected void initialize() {
        event = new Event();
        event.name("SciSerTec Conference on Event Management");
        event.description("Subtitel if applicable");
        event.location("Hannover Medical School");
        event.startTime("09:00").endTime("18:00");
        event.website("http://www.scisertec.com");
        event.additionalInformation("Additional information possible\n" +
                "more text possible\n" +
                "even more text possible");
        event.contact("Breuer/ Bruweleit\n" +
                "SciSerTec\n" +
                "team@vcongress.de\n" +
                "mobile +49 (0) 176 27 19 25 88");
        GregorianCalendar today = new GregorianCalendar();
        today.add(Calendar.YEAR, 1);
        GregorianCalendar end = new GregorianCalendar();
        end.add(Calendar.YEAR, 1);
        end.add(Calendar.DAY_OF_MONTH, 3);
        event.startDate(today);
        event.endDate(end);
        event.identifier("test");
        event.eventStatus(EventStatus.TESTMODE);
        event.addEventModule(EventModule.REGISTRATION).addEventModule(EventModule.ACCOUNT);
        event.eventLicense(EventLicense.NON_FOR_PROFIT);

        EventLogo eventLogo = new EventLogo();
        eventLogo.event(event);
        eventLogo.linkLarge("http://static.vcongress.de/cms/test_120.png");
        eventLogo.linkSmall("http://static.vcongress.de/cms/test_70.png");
        event.eventLogo(eventLogo);

        eventRepository.save(event);

        MailFeature commonMail = new MailFeature();
        commonMail.event(event);
        commonMail.eventFeatureType(EventFeatureType.MAIL_GENERAL);
        commonMail.content("ADDITIONAL TEXT IS POSSIBLE");
        eventFeatureRepository.save(commonMail);
        MailFeature registrationMail = new MailFeature();
        registrationMail.event(event);
        registrationMail.eventFeatureType(EventFeatureType.MAIL_REGISTRATION);
        registrationMail.applyGeneral(Boolean.TRUE);
        eventFeatureRepository.save(registrationMail);
        MailFeature paymentMail = new MailFeature();
        paymentMail.event(event);
        paymentMail.eventFeatureType(EventFeatureType.MAIL_PAYMENT);
        paymentMail.applyGeneral(Boolean.TRUE);
        eventFeatureRepository.save(paymentMail);
        MailFeature creditMail = new MailFeature();
        creditMail.event(event);
        creditMail.eventFeatureType(EventFeatureType.MAIL_CREDIT);
        creditMail.applyGeneral(Boolean.TRUE);
        eventFeatureRepository.save(creditMail);
        MailFeature paymentCompleteMail = new MailFeature();
        paymentCompleteMail.event(event);
        paymentCompleteMail.eventFeatureType(EventFeatureType.MAIL_PAYMENT_COMPLETE);
        paymentCompleteMail.applyGeneral(Boolean.TRUE);
        eventFeatureRepository.save(paymentCompleteMail);

        DisclaimerFeature disclaimerFeature1 = new DisclaimerFeature();
        disclaimerFeature1.event(event);
        String disclaimer1 = "Ich akzeptiere die Allgemeinen Geschäftsbedingungen (<a href=\"http://static.vcongress.de/ukl/AGB_UKL_vCongress_Stand_20151117.pdf\" target=\"_blank\">hier einsehen</a>).\n" +
                "I accept the terms and conditions (<a href=\"http://static.vcongress.de/ukl/AGB_UKL_vCongress_Stand_20151117_en.pdf\" target=\"_blank\">read here</a>).";
        disclaimerFeature1.mandatory(true).content(disclaimer1).index(1);
        eventFeatureRepository.save(disclaimerFeature1);

        DisclaimerFeature disclaimerFeature2 = new DisclaimerFeature();
        disclaimerFeature2.event(event);
        String disclaimer2 = "Die Stornierungsbedingungen habe ich zur Kenntnis genommen (<a href=\"http://static.vcongress.de/ukl/AGB_UKL_vCongress_Stand_20151117.pdf\" target=\"_blank\">hier einsehen</a>).\n" +
                "I took note of the canccellation terms (<a href=\"http://static.vcongress.de/ukl/AGB_UKL_vCongress_Stand_20151117_en.pdf\" target=\"_blank\">read here</a>).";
        disclaimerFeature2.mandatory(true).content(disclaimer2).index(2);
        eventFeatureRepository.save(disclaimerFeature2);

        DisclaimerFeature disclaimerFeature3 = new DisclaimerFeature();
        disclaimerFeature3.event(event);
        String disclaimer3 = "Ich habe den Datenschutzhinweis zur Kenntnis genommen (<a href=\"http://static.vcongress.de/ukl/AGB_UKL_vCongress_Stand_20151117.pdf\" target=\"_blank\">hier einsehen</a>).\n" +
                "I took note of the dataprotection notice (<a href=\"http://static.vcongress.de/ukl/AGB_UKL_vCongress_Stand_20151117_en.pdf\" target=\"_blank\">read here</a>).";
        disclaimerFeature3.mandatory(true).content(disclaimer3).index(3);
        eventFeatureRepository.save(disclaimerFeature3);

        DisclaimerFeature disclaimerFeature4 = new DisclaimerFeature();
        disclaimerFeature4.event(event);
        String disclaimer4 = "Ich bin damit einverstanden, in Zukunft Informationsmaterial zu Folge- und themenverwandten Veranstaltungen per E-Mail oder Post zu erhalten.\n" +
                "I agree to receive information material on follow-up and topic-related events via mail and e-mail.";
        disclaimerFeature4.mandatory(false).content(disclaimer4).index(4);
        eventFeatureRepository.save(disclaimerFeature4);

        DisclaimerFeature disclaimerFeature5 = new DisclaimerFeature();
        disclaimerFeature5.event(event);
        disclaimerFeature5.mandatory(true).content("Special disclaimer for this event...").index(5);
        eventFeatureRepository.save(disclaimerFeature5);

        ParticipantStatusFeature participantStatusFeature = new ParticipantStatusFeature();
        participantStatusFeature.event(event);
        participantStatusFeature.eventFeatureType(EventFeatureType.PARTICIPANT_STATUS);
        participantStatusFeature.required(Boolean.TRUE);
        participantStatusFeature.label("Status and Fees for the conference");
        participantStatusFeature.description("Additional information possible \n" +
                "more text possible\n" +
                "even more text possible");
        eventFeatureRepository.save(participantStatusFeature);

        EventItem regular = new EventItem();
        regular.cost(new BigDecimal(500));
        regular.addEventFlag("PARTICIPANT_STATUS");
        regular.name("Regular Tarif");
        regular.tax(19);
        regular.participantLimit(100);
        regular.eventFeature(participantStatusFeature);
        eventItemRepository.save(regular);

        EventItem student = new EventItem();
        student.cost(new BigDecimal(250));
        student.addEventFlag("PARTICIPANT_STATUS");
        student.name("Student");
        student.tax(19);
        student.participantLimit(100);
        student.eventFeature(participantStatusFeature);
        eventItemRepository.save(student);

        EventItem vendor = new EventItem();
        vendor.cost(new BigDecimal(1000));
        vendor.addEventFlag("PARTICIPANT_STATUS");
        vendor.name("Vendor");
        vendor.tax(19);
        vendor.participantLimit(50);
        vendor.eventFeature(participantStatusFeature);
        eventItemRepository.save(vendor);

        DinnerComboFeature dinnerComboFeature = new DinnerComboFeature();
        dinnerComboFeature.event(event);
        dinnerComboFeature.label("Social Program");
        dinnerComboFeature.description("Additional information possible \n" +
                "more text possible\n" +
                "even more text possible");

        DinnerFeature dinnerFeature = new DinnerFeature();
        dinnerFeature.event(event);
        dinnerFeature.label("Gala Dinner");
        dinnerFeature.description("Additional information possible");
        eventFeatureRepository.save(dinnerFeature);

        EventItem dinner = new EventItem();
        dinner.name("Gala Dinner");
        dinner.description("Additional information possible");
        dinner.cost(new BigDecimal(50));
        dinner.tax(19);
        dinner.eventFeature(dinnerFeature);
        eventItemRepository.save(dinner);

        DinnerFeature dinnerFeature2 = new DinnerFeature();
        dinnerFeature2.event(event);
        dinnerFeature2.label("Excursion Hannover");
        dinnerFeature2.description("Additional information possible");
        eventFeatureRepository.save(dinnerFeature2);

        EventItem dinner2 = new EventItem();
        dinner2.name("Excursion Hannover");
        dinner2.description("Additional information possible");
        dinner2.cost(new BigDecimal(20));
        dinner2.tax(19);
        dinner2.participantLimit(100);
        dinner2.eventFeature(dinnerFeature2);
        eventItemRepository.save(dinner2);

        DinnerFeature dinnerFeature3 = new DinnerFeature();
        dinnerFeature3.event(event);
        dinnerFeature3.label("Welcome Reception");
        dinnerFeature3.description("Additional information possible");
        eventFeatureRepository.save(dinnerFeature3);

        EventItem dinner3 = new EventItem();
        dinner3.name("Welcome Reception");
        dinner3.description("Additional information possible");
        dinner3.eventFeature(dinnerFeature3);
        eventItemRepository.save(dinner3);

        dinnerComboFeature.dinnerFeatures().add(dinnerFeature);
        dinnerComboFeature.dinnerFeatures().add(dinnerFeature2);
        dinnerComboFeature.dinnerFeatures().add(dinnerFeature3);
        eventFeatureRepository.save(dinnerComboFeature);


        ParticipantStatusFeature parallelWorkshops = new ParticipantStatusFeature();
        parallelWorkshops.event(event);
        parallelWorkshops.eventFeatureType(EventFeatureType.SELECTION);
        parallelWorkshops.label("Parallel Workshops");
        parallelWorkshops.description("Additional information possible \n" +
                "more text possible\n" +
                "even more text possible");
        parallelWorkshops.required(Boolean.TRUE);
        eventFeatureRepository.save(parallelWorkshops);

        EventItem session1 = new EventItem();
        session1.name("Session 1");
        session1.cost(new BigDecimal(100));
        session1.participantLimit(50);
        session1.eventFeature(parallelWorkshops);
        eventItemRepository.save(session1);

        EventItem session2 = new EventItem();
        session2.name("Session 2");
        session2.cost(new BigDecimal(100));
        session2.participantLimit(60);
        session2.eventFeature(parallelWorkshops);
        eventItemRepository.save(session2);

        EventItem session3 = new EventItem();
        session3.name("Session 3");
        session3.cost(new BigDecimal(100));
        session3.participantLimit(70);
        session3.eventFeature(parallelWorkshops);
        eventItemRepository.save(session3);



        ParticipantStatusFeature hotelFeature = new ParticipantStatusFeature();
        hotelFeature.event(event);
        hotelFeature.eventFeatureType(EventFeatureType.SELECTION);
        hotelFeature.label("Hotel Contingent");
        hotelFeature.description("You need to book directly at the hotel. \n" +
                "This is just an internal reservation to have an overview about the contingents. \n" +
                "Please make your choice here and contact the hotel afterwards. \n" +
                "\n" +
                "!!!The hotel will receive an automatic email with all your contact information via vCongress!!!");
        eventFeatureRepository.save(hotelFeature);

        EventItem hotel1 = new EventItem();
        hotel1.name("Hayett (200€ per night)");
        hotel1.participantLimit(50);
        hotel1.eventFeature(hotelFeature);
        eventItemRepository.save(hotel1);

        EventItem hotel2 = new EventItem();
        hotel2.name("Mariott (150€ per night)");
        hotel2.participantLimit(100);
        hotel2.eventFeature(hotelFeature);
        eventItemRepository.save(hotel2);

        EventItem hotel3 = new EventItem();
        hotel3.name("Hilton (300€ per night)");
        hotel3.participantLimit(70);
        hotel3.eventFeature(hotelFeature);
        eventItemRepository.save(hotel3);



        DinnerComboFeature transportFeature = new DinnerComboFeature();
        transportFeature.event(event);
        transportFeature.label("Public Transport");
        transportFeature.description("If you want to receive a week ticket for the local public transport at the event reception please check this filed.");

        DinnerFeature transportFeature2 = new DinnerFeature();
        transportFeature2.event(event);
        transportFeature2.label("Week Public Transport Ticket");
        eventFeatureRepository.save(transportFeature2);

        EventItem transportTicket = new EventItem();
        transportTicket.name("Week Public Transport Ticket");
        transportTicket.cost(new BigDecimal(13.45));
        transportTicket.tax(19);
        transportTicket.eventFeature(transportFeature2);
        eventItemRepository.save(transportTicket);

        transportFeature.dinnerFeatures().add(transportFeature2);
        eventFeatureRepository.save(transportFeature);



        DeadlineFeature deadlineFeature = new DeadlineFeature();
        deadlineFeature.event(event);
        deadlineFeature.eventFeatureType(EventFeatureType.REGISTRATION);
        deadlineFeature.deadline(event.endDate());
        eventFeatureRepository.save(deadlineFeature);

        NotificationFeature notificationFeature = new NotificationFeature();
        notificationFeature.eventFeatureType(EventFeatureType.INDEX);
        notificationFeature.event(event);
        eventFeatureRepository.save(notificationFeature);

        Group group = new Group();
        Role participant = Role.create("TEILNEHMER");
        Role staff = Role.create("MITARBEITER");
        Role organizer = Role.create("ORGANISATOR");
        Role reviewer = Role.create("GUTACHTER");
        group.name(event.identifier())
                .identifier(event.identifier())
                .addRole(participant)
                .addRole(staff)
                .addRole(organizer)
                .addRole(reviewer)
                .defaultRole(participant);
        groupRepository.save(group);

        User user = customerImport.getCustomer().person().user();
        user.addRelationship(group, Collections.singletonList(organizer).stream().collect(Collectors.toSet()));
        userRepository.save(user);

        CancellationFeature cancellationFeature = new CancellationFeature();
        cancellationFeature.event(event);
        eventFeatureRepository.save(cancellationFeature);

        Cancellation firstCancellationLimit = new Cancellation();
        firstCancellationLimit.dayLimit(14).percent(20);
        firstCancellationLimit.cancellationFeature(cancellationFeature);
        cancellationRepository.save(firstCancellationLimit);

        Cancellation secondCancellationLimit = new Cancellation();
        secondCancellationLimit.dayLimit(7).percent(50);
        secondCancellationLimit.cancellationFeature(cancellationFeature);
        cancellationRepository.save(secondCancellationLimit);

        Cancellation thirdCancellationLimit = new Cancellation();
        thirdCancellationLimit.dayLimit(2).percent(80);
        thirdCancellationLimit.cancellationFeature(cancellationFeature);
        cancellationRepository.save(thirdCancellationLimit);


        PaymentFeature paymentFeature = new PaymentFeature();
        paymentFeature.event(event);
        eventFeatureRepository.save(paymentFeature);

        BankTransferFeature bankTransferFeature = new BankTransferFeature();
        String holder = "SciSerTec";
        String creditInstitute = "Deutsche Bank";
        String iban = "DE1234567890";
        String bic = "DEUTDE8LXYZ";
        String invoiceSender = "SciSerTec\n" +
                "c/o MHH \n" +
                "Carl-Neuberg-Str. 1\n" +
                "D-30625 Hannover, Germany";
        bankTransferFeature.event(event);
        bankTransferFeature.bic(bic).creditInstitute(creditInstitute).iban(iban).holder(holder);
        bankTransferFeature.invoiceSender(invoiceSender);
        eventFeatureRepository.save(bankTransferFeature);

        Order order = orderRepository.findByEvent(event);
        if(order != null) {
            if(order.transactionStatus().equals(TransactionStatus.PENDING)) {
                order.paymentItems().clear();
            }
        } else {
            order = orderRepository.createNewPendingOrder(customerImport.getCustomer(), event);
        }
        final Order finalOrder = order;
        event.eventModules().forEach(eventModule -> {
            PaymentItemContainer product = productProvider.getProduct(event.eventLicense(), eventModule);
            finalOrder.paymentItems().put((long) eventModule.ordinal(), product);
            product.transaction(finalOrder);
        });
        orderRepository.save(finalOrder);

        event.eventFeatures().add(dinnerComboFeature);
        event.eventFeatures().add(dinnerFeature);
        event.eventFeatures().add(dinnerFeature2);
        event.eventFeatures().add(dinnerFeature3);
        event.eventFeatures().add(participantStatusFeature);
        event.eventFeatures().add(hotelFeature);
        event.eventFeatures().add(parallelWorkshops);
        event.eventFeatures().add(transportFeature);
        event.eventFeatures().add(transportFeature2);

    }

    public Event getEvent() {
        return event;
    }
}
