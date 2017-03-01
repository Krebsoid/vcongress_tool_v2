package de.scisertec.core.application.impl.template;


import de.scisertec.core.application.api.template.TemplateService;
import de.scisertec.core.domain.model.template.TemplateContent;
import de.scisertec.core.infrastructure.qualifier.template.TextTemplate;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import org.apache.commons.io.IOUtils;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Locale;

@TextTemplate
@ApplicationScoped
public class PlainTextTemplateService implements TemplateService<String> {

    public String processTemplate(InputStream inputStream, TemplateContent templateContent) {
        try {
            String documentContent = IOUtils.toString(inputStream, "UTF-8");

            Configuration cfg = new Configuration(new Version(2, 3, 22));
            StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
            stringTemplateLoader.putTemplate("template", documentContent);
            cfg.setLocale(Locale.GERMANY);
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateLoader(stringTemplateLoader);

            Template mail = cfg.getTemplate("template");
            StringWriter stringWriter = new StringWriter();
            mail.process(templateContent.getContent(), stringWriter);
            return stringWriter.toString();
        } catch (IOException e) {
            return "";
        } catch (TemplateException e) {
            return "";
        }
    }

}
