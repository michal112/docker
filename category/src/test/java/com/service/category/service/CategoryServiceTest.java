package com.service.category.service;

import com.service.category.exception.EntityNotFoundException;
import com.service.category.model.entity.CategoryEntity;
import com.service.category.model.mapper.CategoryMapper;
import com.service.category.model.mapper.PublicIdGenerator;
import com.service.category.model.payload.CategoryPayload;
import com.service.category.model.repository.CategoryReactiveRepository;
import org.assertj.core.api.Assertions;
import org.assertj.core.description.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Example;
import org.springframework.data.util.Pair;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class CategoryServiceTest {

    private static final String PUBLIC_ID = "aadc81e5-6fee-4ad6-8b32-02ce6ef423c1";
    private static final String NAME = "CATEGORY_NAME";
    private static final String NEW_NAME = "NEW_CATEGORY_NAME";
    private static final CategoryEntity CATEGORY_ENTITY = CategoryEntity.builder()
            .id(BigInteger.ONE)
            .name(NAME)
            .publicId(PUBLIC_ID)
            .build();
    private static final CategoryEntity UPDATED_CATEGORY_ENTITY = CategoryEntity.builder()
            .name(NEW_NAME)
            .publicId(PUBLIC_ID)
            .build();

    @TestConfiguration
    static class ContextConfiguration {

        @Bean
        CategoryReactiveRepository categoryReactiveRepository() {
            var mock = Mockito.mock(CategoryReactiveRepository.class);
            when(mock.save(any())).thenAnswer(invocationOnMock -> Mono.just(invocationOnMock.<CategoryEntity>getArgument(0)));
            return mock;
        }

        @Bean
        PublicIdGenerator publicIdGenerator() {
            var publicIdGenerator = Mockito.mock(PublicIdGenerator.class);
            when(publicIdGenerator.getPublicId()).thenReturn(PUBLIC_ID);
            return publicIdGenerator;
        }

        @Bean
        CategoryMapper categoryMapper(PublicIdGenerator publicIdGenerator) {
            return new CategoryMapper(publicIdGenerator);
        }

        @Bean
        CategoryService categoryService(CategoryMapper categoryMapper, CategoryReactiveRepository categoryReactiveRepository) {
            return new CategoryService(categoryMapper, categoryReactiveRepository);
        }
    }

    @Autowired
    CategoryReactiveRepository categoryReactiveRepository;

    @Autowired
    CategoryService categoryService;

    private static Stream<Pair<CategoryPayload, CategoryPayload>> createTestSource() {
        return Stream.of(
                Pair.of(
                        new CategoryPayload(null, NAME), new CategoryPayload(PUBLIC_ID, NAME)
                )
        );
    }

    @ParameterizedTest
    @DisplayName("category service create() test")
    @MethodSource("createTestSource")
    void createTest(Pair<CategoryPayload, CategoryPayload> data) {
        //given
        var givenPayload = data.getFirst();
        var expectedPayload = data.getSecond();

        var entityCaptor = ArgumentCaptor.forClass(CategoryEntity.class);
        //when
        //then
        StepVerifier.create(categoryService.create(givenPayload))
                .expectNext(expectedPayload)
                .verifyComplete();
        verify(categoryReactiveRepository, times(1)).save(entityCaptor.capture());
        Assertions.assertThat(entityCaptor.getValue()).usingRecursiveComparison()
                .ignoringActualNullFields()
                .isEqualTo(CATEGORY_ENTITY);
    }

    private static Stream<Pair<Pair<CategoryPayload, String>, CategoryPayload>> updateTestSource() {
        return Stream.of(
                Pair.of(
                        Pair.of(
                                new CategoryPayload(null, NEW_NAME), PUBLIC_ID
                        ), new CategoryPayload(PUBLIC_ID, NEW_NAME)
                )
        );
    }

    @ParameterizedTest
    @DisplayName("category service update() test")
    @MethodSource("updateTestSource")
    void updateTest(Pair<Pair<CategoryPayload, String>, CategoryPayload> data) {
        //given
        var givenPayload = data.getFirst().getFirst();
        var givenPublicId = data.getFirst().getSecond();
        var expectedPayload = data.getSecond();

        var entityCaptor = ArgumentCaptor.forClass(CategoryEntity.class);
        var exampleCaptor = ArgumentCaptor.forClass(Example.class);

        when(categoryReactiveRepository.findOne(any())).thenReturn(Mono.just(CATEGORY_ENTITY));
        when(categoryReactiveRepository.deleteById(any(BigInteger.class))).thenReturn(Mono.empty());
        //when
        //then
        StepVerifier.create(categoryService.update(givenPayload, givenPublicId))
                .expectNext(expectedPayload)
                .verifyComplete();
        verify(categoryReactiveRepository, times(1)).findOne(exampleCaptor.capture());
        Assertions.assertThat(exampleCaptor.getValue()).usingRecursiveComparison()
                .ignoringActualNullFields()
                .isEqualTo(Example.of(CategoryEntity.builder()
                        .publicId(givenPublicId)
                        .build()));
        verify(categoryReactiveRepository, times(1)).deleteById(eq(CATEGORY_ENTITY.getId()));
        verify(categoryReactiveRepository, times(1)).save(entityCaptor.capture());
        Assertions.assertThat(entityCaptor.getValue()).usingRecursiveComparison()
                .isEqualTo(UPDATED_CATEGORY_ENTITY);
    }

    @Test
    @DisplayName("category service update() exception test")
    void updateExceptionTest() {
        //given
        when(categoryReactiveRepository.findOne(any())).thenReturn(Mono.empty());
        //when
        //then
        Assertions.assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> categoryService.update(new CategoryPayload(null, null), PUBLIC_ID).block());
    }

    private static Stream<Pair<String, CategoryPayload>> getByPublicIdTestSource() {
        return Stream.of(
                Pair.of(
                        PUBLIC_ID, new CategoryPayload(PUBLIC_ID, NAME)
                )
        );
    }

    @ParameterizedTest
    @DisplayName("category service getByPublicId() test")
    @MethodSource("getByPublicIdTestSource")
    void getByPublicIdTest(Pair<String, CategoryPayload> data) {
        //given
        var givenPublicId = data.getFirst();
        var expectedPayload = data.getSecond();

        var exampleCaptor = ArgumentCaptor.forClass(Example.class);

        when(categoryReactiveRepository.findOne(any())).thenReturn(Mono.just(CATEGORY_ENTITY));
        //when
        //then
        StepVerifier.create(categoryService.getByPublicId(givenPublicId))
                .expectNext(expectedPayload)
                .verifyComplete();
        verify(categoryReactiveRepository, times(1)).findOne(exampleCaptor.capture());
        Assertions.assertThat(exampleCaptor.getValue()).usingRecursiveComparison()
                .ignoringActualNullFields()
                .isEqualTo(Example.of(CategoryEntity.builder()
                        .publicId(givenPublicId)
                        .build()));
    }

    @Test
    @DisplayName("category service getByPublicId() exception test")
    void getByPublicIdExceptionTest() {
        //given
        when(categoryReactiveRepository.findOne(any())).thenReturn(Mono.empty());
        //when
        //then
        Assertions.assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> categoryService.getByPublicId(PUBLIC_ID).block());
    }

    private static Stream<List<CategoryPayload>> getAllTestSource() {
        return Stream.of(
                List.of(
                         new CategoryPayload(PUBLIC_ID, NAME)
                )
        );
    }

    @ParameterizedTest
    @DisplayName("category service getAll() test")
    @MethodSource("getAllTestSource")
    void getAllTest(List<CategoryPayload> expectedPayloadList) {
        //given
        when(categoryReactiveRepository.findAll()).thenReturn(Flux.fromStream(Stream.of(CATEGORY_ENTITY)));
        //when
        //then
        StepVerifier.create(categoryService.getAll())
                .expectNextSequence(expectedPayloadList)
                .verifyComplete();
    }

    @Test
    @DisplayName("category service delete() test")
    void deleteTest() {
        //given
        when(categoryReactiveRepository.deleteByPublicId(anyString())).thenReturn(Mono.empty());
        //when
        categoryService.delete(PUBLIC_ID);
        //then
        verify(categoryReactiveRepository, times(1)).deleteByPublicId(eq(PUBLIC_ID));
    }

    @AfterEach
    void after() {
        Mockito.clearInvocations(categoryReactiveRepository);
    }
}
