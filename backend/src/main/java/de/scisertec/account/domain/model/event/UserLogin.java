package de.scisertec.account.domain.model.event;

import de.scisertec.account.domain.model.User;

public interface UserLogin {

    Boolean success();
    User user();
    String group();

}
