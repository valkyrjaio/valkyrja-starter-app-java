/*
 * This file is part of the Valkyrja Application package.
 *
 * Copyright (c) 2016-present Melech Mizrachi
 *
 * Released under the MIT License. See LICENSE.md for details.
 */

package app.archunit;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packages = "app", importOptions = ImportOption.DoNotIncludeTests.class)
public class ArchitectureTest {

    @ArchTest
    public static final ArchRule data_records_should_reside_in_data_packages =
            classes()
                    .that()
                    .areRecords()
                    .and()
                    .haveSimpleNameNotEndingWith("Config")
                    .should()
                    .resideInAPackage("..data..")
                    .because("All data records should reside in an appropriate data namespace");

    @ArchTest
    public static final ArchRule data_packages_should_only_contain_records =
            classes()
                    .that()
                    .resideInAPackage("..data..")
                    .and()
                    .resideOutsideOfPackage("..data.contract..")
                    .should()
                    .beRecords()
                    .because("All classes in a data namespace must be records");
}
