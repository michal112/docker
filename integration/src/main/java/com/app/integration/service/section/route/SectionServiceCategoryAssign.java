package com.app.integration.service.section.route;

import com.app.integration.service.config.BaseRoute;
import com.app.integration.service.config.CategoryRouteConfig;
import com.app.integration.service.section.payload.Section;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SectionServiceCategoryAssign extends BaseRoute {

    public SectionServiceCategoryAssign(CategoryRouteConfig config) {
        withRoute(
                routeBuilder()
                    .from("direct:section-service-category-assign")
                    .to(config.getHostName() + "/section/${header.sectionPublicId}/assign/category/${header.categoryPublicId}")
                    .method("PUT")
                    .unmarshal(Section.class)
                .end()
        );
    }
}