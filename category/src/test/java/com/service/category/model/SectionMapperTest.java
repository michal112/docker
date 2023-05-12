package com.service.category.model;

import com.service.category.model.entity.CategoryEntity;
import com.service.category.model.entity.SectionEntity;
import com.service.category.model.mapper.CategoryMapper;
import com.service.category.model.mapper.PublicIdGenerator;
import com.service.category.model.mapper.SectionMapper;
import com.service.category.model.payload.CategoryPayload;
import com.service.category.model.payload.SectionPayload;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.util.Pair;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.Set;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class SectionMapperTest {

    private static final String PUBLIC_ID = "952e8b57-7f85-418e-96d0-3e8d57726942";

    private static final String NAME = "SECTION_NAME";

    private static final CategoryPayload CATEGORY_PAYLOAD = new CategoryPayload("023d0182-0fd2-4577-835d-0f2604b9a3f7",
            "CATEGORY_NAME");
    private static final CategoryEntity CATEGORY_ENTITY = CategoryEntity.builder()
            .name("CATEGORY_NAME")
            .publicId("023d0182-0fd2-4577-835d-0f2604b9a3f7")
            .build();

    @TestConfiguration
    static class ContextConfiguration {

        @Bean
        PublicIdGenerator publicIdGenerator() {
            var mock = Mockito.mock(PublicIdGenerator.class);
            when(mock.getPublicId()).thenReturn(PUBLIC_ID);
            return mock;
        }

        @Bean
        CategoryMapper categoryMapper(PublicIdGenerator publicIdGenerator) {
            return new CategoryMapper(publicIdGenerator);
        }

        @Bean
        SectionMapper sectionMapper(CategoryMapper categoryMapper, PublicIdGenerator publicIdGenerator) {
            return new SectionMapper(categoryMapper, publicIdGenerator);
        }
    }

    @Autowired
    SectionMapper sectionMapper;

    public static Stream<Pair<SectionEntity, SectionPayload>> toPayloadTestSource() {
        return Stream.of(
                Pair.of(SectionEntity.builder()
                        .publicId(PUBLIC_ID)
                        .name(NAME)
                        .categories(Set.of(CATEGORY_ENTITY))
                        .build(), new SectionPayload(PUBLIC_ID, NAME, Set.of(CATEGORY_PAYLOAD)))
        );
    }

    @ParameterizedTest
    @DisplayName("section mapper toPayload() test")
    @MethodSource("toPayloadTestSource")
    void toPayloadTest(Pair<SectionEntity, SectionPayload> data) {
        //given
        var givenEntity = data.getFirst();
        var expectedPayload = data.getSecond();
        //when
        var actualPayload = sectionMapper.toPayload(givenEntity);
        //then
        Assertions.assertThat(actualPayload).usingRecursiveComparison()
                .isEqualTo(expectedPayload);
    }

    public static Stream<Pair<SectionPayload, SectionEntity>> toEntityTestSource() {
        return Stream.of(
                Pair.of(new SectionPayload(null, NAME, null), SectionEntity.builder()
                        .publicId(PUBLIC_ID)
                        .name(NAME)
                        .build()
                )
        );
    }

    @ParameterizedTest
    @DisplayName("section mapper toEntity() test")
    @MethodSource("toEntityTestSource")
    void toEntityTest(Pair<SectionPayload, SectionEntity> data) {
        //given
        var givenPayload = data.getFirst();
        var expectedEntity = data.getSecond();
        //when
        var actualEntity = sectionMapper.toEntity(givenPayload);
        //then
        Assertions.assertThat(actualEntity).usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(expectedEntity);
    }
}
