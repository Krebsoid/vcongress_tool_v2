package de.scisertec.account.infrastructure.repository;

import de.scisertec.account.domain.model.*;
import de.scisertec.account.domain.model.event.Login;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

import java.util.List;

@Repository
public abstract class UserRepository extends AbstractEntityRepository<User, Long> implements CriteriaSupport<User> {

    public User findByLogin(Login login) {
        return criteria()
                .join(User_.credential,
                        where(Credential.class)
                .eq(Credential_.username, login.username()))
                .getOptionalResult();
    }

    public User findByAutoLoginToken(String autoLoginToken) {
        return criteria()
                .eq(User_.autoLoginToken, autoLoginToken)
                .getOptionalResult();
    }

    public User findByRemoteLoginToken(String remoteLoginToken) {
        return criteria()
                .eq(User_.remoteEditingToken, remoteLoginToken)
                .getOptionalResult();
    }

    public List<User> findByGroup(Group group) {
        return criteria()
                .join(User_.relationships,
                        where(Relationship.class).eq(Relationship_.group, group))
                .getResultList();
    }

    public User findByGroupAndLogin(Group group, Login login) {
        return criteria()
                .join(User_.relationships,
                        where(Relationship.class).eq(Relationship_.group, group))
                .join(User_.credential,
                        where(Credential.class).eq(Credential_.username, login.username()))
                .getOptionalResult();
    }

    public User findByMailAddress(String mailAddress) {
        return criteria()
                .join(User_.credential,
                        where(Credential.class)
                .eq(Credential_.username, mailAddress))
                .getOptionalResult();
    }

    public User findByGroupAndMailAddress(Group group, String mailAddress) {
        return criteria()
                .join(User_.relationships,
                        where(Relationship.class).eq(Relationship_.group, group))
                .join(User_.credential,
                        where(Credential.class).eq(Credential_.username, mailAddress))
                .getOptionalResult();
    }

}
