package de.scisertec.core.infrastructure.interceptor;

import de.scisertec.account.domain.model.User;
import de.scisertec.core.infrastructure.qualifier.Active;
import de.scisertec.core.infrastructure.qualifier.Logging;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.logging.Logger;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;

@Logging
@Interceptor
public class LoggingInterceptor implements Serializable {

    @Inject
    @Active
    Instance<User> user;

    @Inject
    @Active
    Instance<String> group;

    @Inject
    HttpServletRequest request;

    @AroundInvoke
    Object aroundInvoke(InvocationContext invocationContext) throws Exception {
        Method method = invocationContext.getMethod();

        Object outcome = null;
        Exception exception = null;

        boolean methodAnnotationPresent = method.isAnnotationPresent(Logging.class);
        if(methodAnnotationPresent) {
            try {
                Object result = invocationContext.proceed();
                if(result != null) {
                    outcome = result;
                }
                return outcome;
            } catch (Exception e) {
                exception = e;
                throw e;
            } finally {
                final Logger logger = Logger.getLogger(method.getDeclaringClass());
                final Logging annotation = method.getAnnotation(Logging.class);
                final Object[] parameters = invocationContext.getParameters();
                final String template = "%-32s %-12s %-12s %-24s IN:[%s] OUT:[%s]";

                final String userActionString = makeUserActionString(annotation.value(), method);
                final String remoteAddr = request.getRemoteAddr();

                final String inString = makeInString(annotation, parameters);
                final String outString = makeOutString(annotation, outcome);

                if (exception != null) {
                    if(exception.getClass().equals(Exception.class)) {
                        logger.error(String.format(template, makeUserString(), remoteAddr, "ERROR", userActionString, inString, outString));
                    }
                }
                else {
                    logger.info(String.format(template, makeUserString(), remoteAddr, "Authorized", userActionString, inString, outString));
                }
            }

        } else {
            return invocationContext.proceed();
        }
    }

    private String makeUserActionString(String message, Method method) {
        if(message.isEmpty()) {
            String simpleName = method.getDeclaringClass().getSimpleName();
            if(simpleName.endsWith("ResourceBean")) {
                simpleName = simpleName.replace("ResourceBean", "");
            }
            return simpleName + " " + method.getName();
        }
        else {
            return message;
        }
    }

    private String makeOutString(Logging logging, Object outcome) {
        if(logging.out() && outcome != null) {
            return ToStringBuilder.reflectionToString(outcome);
        }
        else {
            return "";
        }
    }

    private String makeInString(Logging logging, Object[] parameters) {
        if(logging.in() && parameters.length > 0) {
            String result = "";
            final ObjectMapper objectMapper = new ObjectMapper();
            for(Object o : parameters) {
                try {
                    result += objectMapper.writeValueAsString(o) + ", ";
                } catch (JsonProcessingException ignored) {

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return result.length() > 3 ? result.substring(0, result.length()-2) : "";
        }
        else {
            return "";
        }
    }

    private String makeUserString() {
        if(user.get().isUser()) {
            return user.get().credential().username() + "(" + group.get() + ")";
        }
        else {
            return "Unknown user" + "(" + group.get() + ")";
        }
    }
}
