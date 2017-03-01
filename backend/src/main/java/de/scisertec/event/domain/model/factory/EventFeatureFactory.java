package de.scisertec.event.domain.model.factory;

import de.scisertec.event.domain.model.EventFeature;
import de.scisertec.event.domain.model.feature.*;
import de.scisertec.paper.domain.model.feature.*;
import de.scisertec.payment.domain.model.feature.BankTransferFeature;
import de.scisertec.payment.domain.model.feature.CancellationFeature;
import de.scisertec.payment.domain.model.feature.PayPalFeature;

public class EventFeatureFactory {

    public static EventFeature create(EventFeatureType eventFeatureType) {
        switch(eventFeatureType) {
            case PARTICIPANT_STATUS: return new ParticipantStatusFeature();
            case SELECTION: return new ParticipantStatusFeature();
            case DINNER: return new DinnerFeature();
            case DINNER_COMBO: return new DinnerComboFeature();
            case DISCLAIMER: return new DisclaimerFeature();
            case CANCELLATION: return new CancellationFeature();
            case BANK_TRANSFER: return new BankTransferFeature();
            case PAYPAL: return new PayPalFeature();
            case PAPER_GENERAL: return new PaperFeature();
            case PAPER_SESSION: return new SessionFeature();
            case PAPER_SUBMISSION_TYPE: return new SubmissionTypeFeature();
            case PAPER_ATTACHMENT: return new AttachmentFeature();
            case PAPER_REFERENCE: return new ReferenceFeature();
            default: return null;
        }
    }

}
