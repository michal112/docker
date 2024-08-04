package com.app.integration.adapter.section;

import com.app.integration.service.config.JacksonUtils;
import com.app.integration.service.section.payload.Section;
import org.apache.camel.builder.RouteBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class SectionAdapter extends RouteBuilder {

    @Inject
    JacksonUtils utils;

    @Override
    public void configure() throws Exception {
        rest()
            .get("/section")
                .route()
                .to("direct:section-cbr-get-all")
                .marshal(utils.getJacksonDataFormat(Section.class, true))
                .endRest()
            .put("/section/{sectionPublicId}/assign/category/{categoryPublicId}")
                .route()
                .to("direct:section-category-assign")
                .marshal(utils.getJacksonDataFormat(Section.class, false))
                .endRest();
    }
}
