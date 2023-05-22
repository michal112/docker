package com.service.category.service;

import com.service.category.exception.AssignEntityNotFoundException;
import com.service.category.exception.EntityNotFoundException;
import com.service.category.model.entity.CategoryEntity;
import com.service.category.model.entity.SectionEntity;
import com.service.category.model.mapper.CategoryMapper;
import com.service.category.model.mapper.PublicIdGenerator;
import com.service.category.model.mapper.SectionMapper;
import com.service.category.model.payload.CategoryPayload;
import com.service.category.model.payload.SectionPayload;
import com.service.category.model.repository.CategoryReactiveRepository;
import com.service.category.model.repository.SectionReactiveRepository;
import org.assertj.core.api.Assertions;
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
import org.springframework.data.util.Pair;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigInteger;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class SectionServiceTest {

    private static final String SECTION_PUBLIC_ID = "673d45b8-bd78-4da3-b700-ff5666b5c1bf";
    private static final String CATEGORY_PUBLIC_ID = "9c24e58e-362f-4512-af24-d20098ec2859";
    private static final String SECOND_CATEGORY_PUBLIC_ID = "a4675650-9bc9-48ff-9395-f0a011bf4b9d";
    private static final String SECTION_NAME = "SECTION_NAME";
    private static final String CATEGORY_NAME = "CATEGORY_NAME";
    private static final CategoryEntity CATEGORY_ENTITY = CategoryEntity.builder()
            .publicId(CATEGORY_PUBLIC_ID)
            .name(CATEGORY_NAME)
            .build();
    private static final CategoryEntity SECOND_CATEGORY_ENTITY = CategoryEntity.builder()
            .publicId(SECOND_CATEGORY_PUBLIC_ID)
            .name(CATEGORY_NAME)
            .build();
    private static final Set<String> CATEGORIES = Set.of(
            CATEGORY_PUBLIC_ID
    );
    private static final Set<String> MULTIPLE_CATEGORIES = new HashSet<>(Set.of(
            CATEGORY_PUBLIC_ID, SECOND_CATEGORY_PUBLIC_ID
    ));
    private static final List<CategoryPayload> CATEGORIES_PAYLOAD = List.of(
            new CategoryPayload(CATEGORY_PUBLIC_ID, CATEGORY_NAME)
    );
    private static final List<CategoryPayload> SECOND_CATEGORY_PAYLOAD = List.of(
            new CategoryPayload(SECOND_CATEGORY_PUBLIC_ID, CATEGORY_NAME)
    );
    private static final String NEW_SECTION_NAME = "NEW_SECTION_NAME";
    private static final SectionEntity SECTION_ENTITY = SectionEntity.builder()
            .id(BigInteger.ONE)
            .name(SECTION_NAME)
            .publicId(SECTION_PUBLIC_ID)
            .categories(new HashSet<>())
            .build();
    private static final SectionEntity FULL_SECTION_ENTITY = SectionEntity.builder()
            .id(BigInteger.ONE)
            .name(SECTION_NAME)
            .publicId(SECTION_PUBLIC_ID)
            .categories(CATEGORIES)
            .build();
    private static final SectionEntity UPDATED_SECTION_ENTITY = SectionEntity.builder()
            .name(NEW_SECTION_NAME)
            .publicId(SECTION_PUBLIC_ID)
            .categories(CATEGORIES)
            .build();
    private static final SectionEntity FULL_SECTION_ENTITY_WITH_MULTIPLE_CATEGORIES = SectionEntity.builder()
            .id(BigInteger.ONE)
            .name(SECTION_NAME)
            .publicId(SECTION_PUBLIC_ID)
            .categories(MULTIPLE_CATEGORIES)
            .build();
    private static final SectionEntity FULL_SECTION_ENTITY_WITH_REMOVED_CATEGORY = SectionEntity.builder()
            .id(BigInteger.ONE)
            .name(SECTION_NAME)
            .publicId(SECTION_PUBLIC_ID)
            .categories(Set.of(SECOND_CATEGORY_PUBLIC_ID))
            .build();
    @TestConfiguration
    static class ContextConfiguration {

        @Bean
        SectionReactiveRepository sectionReactiveRepository() {
            var mock = Mockito.mock(SectionReactiveRepository.class);
            when(mock.save(any())).thenAnswer(invocationOnMock -> Mono.just(invocationOnMock.<SectionEntity>getArgument(0)));
            return mock;
        }

        @Bean
        PublicIdGenerator publicIdGenerator() {
            var publicIdGenerator = Mockito.mock(PublicIdGenerator.class);
            when(publicIdGenerator.getPublicId()).thenReturn(SECTION_PUBLIC_ID);
            return publicIdGenerator;
        }

        @Bean
        CategoryMapper categoryMapper(PublicIdGenerator publicIdGenerator) {
            return new CategoryMapper(publicIdGenerator);
        }

        @Bean
        SectionMapper sectionMapper(CategoryMapper categoryMapper, PublicIdGenerator publicIdGenerator) {
            return new SectionMapper(categoryMapper, publicIdGenerator);
        }

        @Bean
        public CategoryReactiveRepository categoryReactiveRepository(PublicIdGenerator publicIdGenerator) {
            return mock(CategoryReactiveRepository.class);
        }

        @Bean
        SectionService sectionService(SectionMapper sectionMapper, SectionReactiveRepository sectionReactiveRepository,
                                      CategoryReactiveRepository categoryReactiveRepository) {
            return new SectionService(sectionMapper, sectionReactiveRepository, categoryReactiveRepository);
        }
    }

    @Autowired
    SectionReactiveRepository sectionReactiveRepository;

    @Autowired
    CategoryReactiveRepository categoryReactiveRepository;

    @Autowired
    SectionService sectionService;

    private static Stream<Pair<SectionPayload, SectionPayload>> createTestSource() {
        return Stream.of(
                Pair.of(
                        new SectionPayload(null, SECTION_NAME, null), new SectionPayload(SECTION_PUBLIC_ID, SECTION_NAME, Collections.emptyList())
                )
        );
    }

    @ParameterizedTest
    @DisplayName("section service create() test")
    @MethodSource("createTestSource")
    void createTest(Pair<SectionPayload, SectionPayload> data) {
        //given
        var givenPayload = data.getFirst();
        var expectedPayload = data.getSecond();

        var entityCaptor = ArgumentCaptor.forClass(SectionEntity.class);
        //when
        //then
        StepVerifier.create(sectionService.create(givenPayload))
                .expectNext(expectedPayload)
                .verifyComplete();
        verify(sectionReactiveRepository, times(1)).save(entityCaptor.capture());
        Assertions.assertThat(entityCaptor.getValue()).usingRecursiveComparison()
                .ignoringActualNullFields()
                .isEqualTo(SECTION_ENTITY);
    }

    private static Stream<Pair<Pair<SectionPayload, String>, SectionPayload>> updateTestSource() {
        return Stream.of(
                Pair.of(
                        Pair.of(
                                new SectionPayload(null, NEW_SECTION_NAME, null), SECTION_PUBLIC_ID
                        ), new SectionPayload(SECTION_PUBLIC_ID, NEW_SECTION_NAME, CATEGORIES_PAYLOAD)
                )
        );
    }

    @ParameterizedTest
    @DisplayName("section service update() test")
    @MethodSource("updateTestSource")
    void updateTest(Pair<Pair<SectionPayload, String>, SectionPayload> data) {
        //given
        var givenPayload = data.getFirst().getFirst();
        var givenPublicId = data.getFirst().getSecond();
        var expectedPayload = data.getSecond();

        var entityCaptor = ArgumentCaptor.forClass(SectionEntity.class);

        when(sectionReactiveRepository.findByPublicId(any())).thenReturn(Mono.just(FULL_SECTION_ENTITY));
        when(categoryReactiveRepository.findByPublicIdIn(any())).thenReturn(Flux.just(CATEGORY_ENTITY));
        when(sectionReactiveRepository.deleteById(any(BigInteger.class))).thenReturn(Mono.empty());
        //when
        //then
        StepVerifier.create(sectionService.update(givenPayload, givenPublicId))
                .expectNext(expectedPayload)
                .verifyComplete();
        verify(sectionReactiveRepository, times(1)).findByPublicId(eq(SECTION_PUBLIC_ID));
        verify(categoryReactiveRepository, times(1)).findByPublicIdIn(eq(CATEGORIES));
        verify(sectionReactiveRepository, times(1)).deleteById(eq(FULL_SECTION_ENTITY.getId()));
        verify(sectionReactiveRepository, times(1)).save(entityCaptor.capture());
        Assertions.assertThat(entityCaptor.getValue()).usingRecursiveComparison()
                .isEqualTo(UPDATED_SECTION_ENTITY);
    }

    @Test
    @DisplayName("section service update() exception test")
    void updateExceptionTest() {
        //given
        when(sectionReactiveRepository.findByPublicId(any())).thenReturn(Mono.empty());
        //when
        //then
        Assertions.assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> sectionService.update(new SectionPayload(null, null, null), SECTION_PUBLIC_ID).block());
    }

    private static Stream<Pair<String, SectionPayload>> getByPublicIdTestSource() {
        return Stream.of(
                Pair.of(
                        SECTION_PUBLIC_ID, new SectionPayload(SECTION_PUBLIC_ID, SECTION_NAME, CATEGORIES_PAYLOAD)
                )
        );
    }

    @ParameterizedTest
    @DisplayName("section service getByPublicId() test")
    @MethodSource("getByPublicIdTestSource")
    void getByPublicIdTest(Pair<String, SectionPayload> data) {
        //given
        var givenPublicId = data.getFirst();
        var expectedPayload = data.getSecond();

        when(sectionReactiveRepository.findByPublicId(any())).thenReturn(Mono.just(FULL_SECTION_ENTITY));
        //when
        //then
        StepVerifier.create(sectionService.getByPublicId(givenPublicId))
                .expectNext(expectedPayload)
                .verifyComplete();
        verify(sectionReactiveRepository, times(1)).findByPublicId(eq(givenPublicId));
        verify(categoryReactiveRepository, times(1)).findByPublicIdIn(eq(CATEGORIES));
    }

    @Test
    @DisplayName("section service getByPublicId() exception test")
    void getByPublicIdExceptionTest() {
        //given
        when(sectionReactiveRepository.findByPublicId(any())).thenReturn(Mono.empty());
        //when
        //then
        Assertions.assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> sectionService.getByPublicId(SECTION_PUBLIC_ID).block());
    }

    private static Stream<List<SectionPayload>> getAllTestSource() {
        return Stream.of(
                List.of(
                         new SectionPayload(SECTION_PUBLIC_ID, SECTION_NAME, CATEGORIES_PAYLOAD)
                )
        );
    }

    @ParameterizedTest
    @DisplayName("section service getAll() test")
    @MethodSource("getAllTestSource")
    void getAllTest(List<SectionPayload> expectedPayloadList) {
        //given
        when(sectionReactiveRepository.findAll()).thenReturn(Flux.just(FULL_SECTION_ENTITY));
        //when
        //then
        StepVerifier.create(sectionService.getAll())
                .expectNext(expectedPayloadList.get(0))
                .verifyComplete();
        verify(sectionReactiveRepository, times(1)).findAll();
        verify(categoryReactiveRepository, times(1)).findByPublicIdIn(eq(CATEGORIES));
    }

    @Test
    @DisplayName("section service delete() test")
    void deleteTest() {
        //given
        when(sectionReactiveRepository.deleteByPublicId(anyString())).thenReturn(Mono.empty());
        //when
        sectionService.delete(SECTION_PUBLIC_ID);
        //then
        verify(sectionReactiveRepository, times(1)).deleteByPublicId(eq(SECTION_PUBLIC_ID));
    }

    private static Stream<Pair<Pair<String, String>, SectionPayload>> assignCategoryTestSource() {
        return Stream.of(
                Pair.of(
                        Pair.of(SECTION_PUBLIC_ID, CATEGORY_PUBLIC_ID),
                        new SectionPayload(SECTION_PUBLIC_ID, SECTION_NAME, CATEGORIES_PAYLOAD)
                )
        );
    }

    @ParameterizedTest
    @MethodSource("assignCategoryTestSource")
    @DisplayName("section service assignCategory() test")
    void assignCategoryTest(Pair<Pair<String, String>, SectionPayload> data) {
        //given
        var sectionPublicId = data.getFirst().getFirst();
        var categoryPublicId = data.getFirst().getSecond();
        var expectedPayload = data.getSecond();

        when(sectionReactiveRepository.findByPublicId(any())).thenReturn(Mono.just(SECTION_ENTITY));
        when(categoryReactiveRepository.findByPublicId(any())).thenReturn(Mono.just(CATEGORY_ENTITY));
        when(categoryReactiveRepository.findByPublicIdIn(any())).thenReturn(Flux.just(CATEGORY_ENTITY));

        var entityCaptor = ArgumentCaptor.forClass(SectionEntity.class);
        //when
        //then
        StepVerifier.create(sectionService.assignCategory(sectionPublicId, categoryPublicId))
                .expectNext(expectedPayload)
                .verifyComplete();
        verify(sectionReactiveRepository, times(1)).findByPublicId(eq(SECTION_PUBLIC_ID));
        verify(categoryReactiveRepository, times(1)).findByPublicId(eq(CATEGORY_PUBLIC_ID));
        verify(sectionReactiveRepository, times(1)).save(entityCaptor.capture());
        verify(categoryReactiveRepository, times(1)).findByPublicIdIn(eq(CATEGORIES));
        Assertions.assertThat(entityCaptor.getValue()).usingRecursiveComparison()
                .ignoringActualNullFields()
                .isEqualTo(FULL_SECTION_ENTITY);
    }

    @Test
    @DisplayName("section service assignCategory() exception test")
    void assignCategorySectionExceptionTest() {
        //given
        when(sectionReactiveRepository.findByPublicId(any())).thenReturn(Mono.empty());
        //when
        //then
        Assertions.assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> sectionService.assignCategory(SECTION_PUBLIC_ID, CATEGORY_PUBLIC_ID).block());
    }

    @Test
    @DisplayName("section service assignCategory() exception test")
    void assignCategoryCategoryExceptionTest() {
        //given
        when(sectionReactiveRepository.findByPublicId(any())).thenReturn(Mono.just(SECTION_ENTITY));
        when(categoryReactiveRepository.findByPublicId(any())).thenReturn(Mono.empty());
        //when
        //then
        Assertions.assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> sectionService.assignCategory(SECTION_PUBLIC_ID, CATEGORY_PUBLIC_ID).block());
        verify(sectionReactiveRepository, times(1)).findByPublicId(SECTION_PUBLIC_ID);
    }

    private static Stream<Pair<Pair<String, String>, SectionPayload>> removeCategoryTestSource() {
        return Stream.of(
                Pair.of(
                        Pair.of(SECTION_PUBLIC_ID, CATEGORY_PUBLIC_ID),
                        new SectionPayload(SECTION_PUBLIC_ID, SECTION_NAME, SECOND_CATEGORY_PAYLOAD)
                )
        );
    }

    @ParameterizedTest
    @MethodSource("removeCategoryTestSource")
    @DisplayName("section service removeCategory() test")
    void removeCategoryTest(Pair<Pair<String, String>, SectionPayload> data) {
        //given
        var sectionPublicId = data.getFirst().getFirst();
        var categoryPublicId = data.getFirst().getSecond();
        var expectedPayload = data.getSecond();

        when(sectionReactiveRepository.findByPublicId(any())).thenReturn(Mono.just(FULL_SECTION_ENTITY_WITH_MULTIPLE_CATEGORIES));
        when(categoryReactiveRepository.findByPublicId(any())).thenReturn(Mono.just(CATEGORY_ENTITY));
        when(categoryReactiveRepository.findByPublicIdIn(any())).thenReturn(Flux.just(SECOND_CATEGORY_ENTITY));

        var entityCaptor = ArgumentCaptor.forClass(SectionEntity.class);
        //when
        //then
        StepVerifier.create(sectionService.removeCategory(sectionPublicId, categoryPublicId))
                .expectNext(expectedPayload)
                .verifyComplete();
        verify(sectionReactiveRepository, times(1)).findByPublicId(eq(SECTION_PUBLIC_ID));
        verify(categoryReactiveRepository, times(1)).findByPublicId(eq(CATEGORY_PUBLIC_ID));
        verify(sectionReactiveRepository, times(1)).save(entityCaptor.capture());
        verify(categoryReactiveRepository, times(1)).findByPublicIdIn(eq(Set.of(SECOND_CATEGORY_PUBLIC_ID)));
        Assertions.assertThat(entityCaptor.getValue()).usingRecursiveComparison()
                .ignoringActualNullFields()
                .isEqualTo(FULL_SECTION_ENTITY_WITH_REMOVED_CATEGORY);
    }

    @Test
    @DisplayName("section service removeCategory() exception test")
    void removeCategorySectionExceptionTest() {
        //given
        when(sectionReactiveRepository.findByPublicId(any())).thenReturn(Mono.empty());
        //when
        //then
        Assertions.assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> sectionService.removeCategory(SECTION_PUBLIC_ID, CATEGORY_PUBLIC_ID).block());
    }

    @Test
    @DisplayName("section service removeCategory() exception test")
    void removeCategoryCategoryExceptionTest() {
        //given
        when(sectionReactiveRepository.findByPublicId(any())).thenReturn(Mono.just(SECTION_ENTITY));
        when(categoryReactiveRepository.findByPublicId(any())).thenReturn(Mono.empty());
        //when
        //then
        Assertions.assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> sectionService.removeCategory(SECTION_PUBLIC_ID, CATEGORY_PUBLIC_ID).block());
        verify(sectionReactiveRepository, times(1)).findByPublicId(SECTION_PUBLIC_ID);
    }

    @Test
    @DisplayName("section service removeCategory() exception test")
    void removeCategoryNotFoundExceptionTest() {
        //given
        when(sectionReactiveRepository.findByPublicId(any())).thenReturn(Mono.just(SECTION_ENTITY));
        when(categoryReactiveRepository.findByPublicId(any())).thenReturn(Mono.just(CATEGORY_ENTITY));
        //when
        //then
        Assertions.assertThatExceptionOfType(AssignEntityNotFoundException.class)
                .isThrownBy(() -> sectionService.removeCategory(SECTION_PUBLIC_ID, CATEGORY_PUBLIC_ID).block());
        verify(sectionReactiveRepository, times(1)).findByPublicId(SECTION_PUBLIC_ID);
        verify(categoryReactiveRepository, times(1)).findByPublicId(CATEGORY_PUBLIC_ID);
    }

    @AfterEach
    void after() {
        Mockito.clearInvocations(sectionReactiveRepository, categoryReactiveRepository);
    }
}
