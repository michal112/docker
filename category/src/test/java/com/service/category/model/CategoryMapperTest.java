package com.service.category.model;

import com.service.category.model.entity.CategoryEntity;
import com.service.category.model.mapper.CategoryMapper;
import com.service.category.model.mapper.PublicIdGenerator;
import com.service.category.model.payload.CategoryPayload;
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

import java.util.stream.Stream;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class CategoryMapperTest {

    private static final String PUBLIC_ID = "aadc81e5-6fee-4ad6-8b32-02ce6ef423c1";
    private static final String NAME = "CATEGORY_NAME";

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
    }

    @Autowired
    CategoryMapper categoryMapper;

    public static Stream<Pair<CategoryEntity, CategoryPayload>> toPayloadTestSource() {
        return Stream.of(
                Pair.of(
                        CategoryEntity.builder()
                                .publicId(PUBLIC_ID)
                                .name(NAME)
                                .build(), new CategoryPayload(PUBLIC_ID, NAME)
                )
        );
    }

    @ParameterizedTest
    @DisplayName("category mapper toPayload() test")
    @MethodSource("toPayloadTestSource")
    void toPayloadTest(Pair<CategoryEntity, CategoryPayload> data) {
        //given
        var givenEntity = data.getFirst();
        var expectedPayload = data.getSecond();
        //when
        var actualPayload = categoryMapper.toPayload(givenEntity);
        //then
        Assertions.assertThat(actualPayload).isEqualTo(expectedPayload);
    }

    public static Stream<Pair<CategoryPayload, CategoryEntity>> toEntityTestSource() {
        return Stream.of(
                Pair.of(new CategoryPayload(null, NAME), CategoryEntity.builder()
                        .publicId(PUBLIC_ID)
                        .name(NAME)
                        .build()
                )
        );
    }

    @ParameterizedTest
    @DisplayName("category mapper toEntity() test")
    @MethodSource("toEntityTestSource")
    void toEntityTest(Pair<CategoryPayload, CategoryEntity> data) {
        //given
        var givenPayload = data.getFirst();
        var expectedEntity = data.getSecond();
        //when
        var actualEntity = categoryMapper.toEntity(givenPayload);
        //then
        Assertions.assertThat(actualEntity).usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(expectedEntity);
    }
}
