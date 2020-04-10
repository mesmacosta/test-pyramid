package com.github.ricardocomar.testpyramid.microservice.book.architecture;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import com.tngtech.archunit.library.Architectures;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;
import org.junit.runner.RunWith;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;

@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "com.github.ricardocomar.testpyramid.microservice")
public class CleanArchitectureTest {

    private static final int USECASES_PUBLIC_METHODS_LIMIT = 1;

    @ArchTest
    static final ArchRule usecases_can_be_acessed_by_external_world = Architectures.layeredArchitecture()
            .layer("UseCases")
            .definedBy("com.github.ricardocomar.testpyramid.microservice.book.core.usecase..")
            .layer("Gateways")
            .definedBy("com.github.ricardocomar.testpyramid.microservice.book.gateway..")
            .layer("Configuration")
            .definedBy("com.github.ricardocomar.testpyramid.microservice.book.configuration..")
            .whereLayer("UseCases")
            .mayOnlyBeAccessedByLayers("UseCases", "Gateways", "Configuration");

    @ArchTest
    static final ArchRule usecases_should_reside_inside_core = ArchRuleDefinition.classes()
            .that()
            .haveSimpleNameEndingWith("UseCase")
            .should()
            .resideInAPackage("com.github.ricardocomar.testpyramid.microservice.book.core.usecase..");

    @ArchTest
    static final ArchRule usecases_gateways_belong_to_usecases = Architectures.layeredArchitecture()
            .layer("UseCases")
            .definedBy("com.github.ricardocomar.testpyramid.microservice.book.core.usecase..")
            .layer("UseCasesGateways")
            .definedBy("com.github.ricardocomar.testpyramid.microservice.book.core.usecase.gateway..")
            .layer("Gateways")
            .definedBy("com.github.ricardocomar.testpyramid.microservice.book.gateway..")
            .layer("Configuration")
            .definedBy("com.github.ricardocomar.testpyramid.microservice.book.configuration..")
            .whereLayer("UseCasesGateways")
            .mayOnlyBeAccessedByLayers("UseCases", "Gateways");

    @ArchTest
    public static ArchRule usecases_should_have_only_one_public_method = ArchRuleDefinition.classes()
            .that()
            .haveSimpleNameEndingWith("UseCase")
            .should(CleanArchitectureTest.containOnlyOnePublicMethod())
            .because("Precisamos garantir que o usecase contém somente uma responsabilidade de negócio");

    @ArchTest
    static final ArchRule dataprovider_should_reside_outside_core = ArchRuleDefinition.classes()
            .that()
            .haveSimpleNameEndingWith("DataProvider")
            .should()
            .resideInAPackage("com.github.ricardocomar.testpyramid.microservice.book.gateway.dataprovider..");

    @ArchTest
    static final ArchRule entrypoint_should_reside_outside_core = ArchRuleDefinition.classes()
            .that()
            .haveSimpleNameEndingWith("Endpoint")
            .or()
            .haveSimpleNameEndingWith("Entrypoint")
            .should()
            .resideInAPackage("com.github.ricardocomar.testpyramid.microservice.book.gateway.entrypoint..");

    @ArchTest
    static final ArchRule gateways_cant_be_acessed = Architectures.layeredArchitecture()
            .layer("UseCases")
            .definedBy("com.github.ricardocomar.testpyramid.microservice.book.core.usecase..")
            .layer("Gateways")
            .definedBy("com.github.ricardocomar.testpyramid.microservice.book.gateway..")
            .layer("Configuration")
            .definedBy("com.github.ricardocomar.testpyramid.microservice.book.configuration..")
            .whereLayer("Gateways")
            .mayNotBeAccessedByAnyLayer();

    @ArchTest
    static final ArchRule tables_should_be_annotated_by_entity = ArchRuleDefinition.classes()
            .that()
            .haveSimpleNameEndingWith("Table")
            .should()
            .beAnnotatedWith(Entity.class)
            .andShould()
            .beAnnotatedWith(Table.class);

    @ArchTest
    static final ArchRule entities_should_be_free_of_cycles = SlicesRuleDefinition.slices()
            .matching("..entity.(*)..")
            .should()
            .beFreeOfCycles();

    @ArchTest
    static final ArchRule entities_should_not_be_components = ArchRuleDefinition.classes()
            .that()
            .resideInAPackage("..entity..")
            .or()
            .resideInAPackage("..model..")
            .should()
            .notBeAnnotatedWith(Component.class);

    @ArchTest
    static final ArchRule configuration_cant_be_acessed = Architectures.layeredArchitecture()
            .layer("UseCases")
            .definedBy("com.github.ricardocomar.testpyramid.microservice.book.core.usecase..")
            .layer("Gateways")
            .definedBy("com.github.ricardocomar.testpyramid.microservice.book.gateway..")
            .layer("Configuration")
            .definedBy("com.github.ricardocomar.testpyramid.microservice.book.configuration..")
            .whereLayer("Configuration")
            .mayNotBeAccessedByAnyLayer();

    private static ArchCondition<JavaClass> containOnlyOnePublicMethod() {

        return new ArchCondition<JavaClass>("Only one public method") {

            @Override
            public void check(final JavaClass clazz, final ConditionEvents events) {

                final String name = clazz.getName();
                final Set<JavaMethod> methodsSet = clazz.getMethods();
                int qtdMetodosPublicos = 0;

                for (final JavaMethod javaMethod : methodsSet) {
                    if (javaMethod.getModifiers()
                            .contains(JavaModifier.PUBLIC)) {
                        qtdMetodosPublicos = qtdMetodosPublicos + 1;
                    }
                }

                if (qtdMetodosPublicos > CleanArchitectureTest.USECASES_PUBLIC_METHODS_LIMIT) {
                    throw new AssertionError(String.format("Classe %s contém %d métodos públicos, quando o limite é %d", name, qtdMetodosPublicos, CleanArchitectureTest.USECASES_PUBLIC_METHODS_LIMIT));
                }
            }
        };
    }
}
