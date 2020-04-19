package in.ravikalla.pdfeditor;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("in.ravikalla.pdfeditor");

        noClasses()
            .that()
                .resideInAnyPackage("in.ravikalla.pdfeditor.service..")
            .or()
                .resideInAnyPackage("in.ravikalla.pdfeditor.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..in.ravikalla.pdfeditor.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
