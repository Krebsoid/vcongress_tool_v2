package de.scisertec.event.application.api.model.command.feature;

import de.scisertec.core.application.api.model.command.Command;
import de.scisertec.core.application.api.model.command.LocaleStringUpdateCommand;

public interface MailFeatureUpdateCommand extends Command {

    String getContent();
    Boolean getApplyGeneral();
    LocaleStringUpdateCommand getLocaleContent();

}
