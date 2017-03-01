package de.scisertec.core.application.api.template;

import de.scisertec.core.domain.model.template.TemplateContent;

import java.io.InputStream;

public interface TemplateService<E> {

    E processTemplate(InputStream inputStream, TemplateContent templateContent);

}
