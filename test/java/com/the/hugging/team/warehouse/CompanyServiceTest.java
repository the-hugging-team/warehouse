package com.the.hugging.team.warehouse;

import com.the.hugging.team.entities.Address;
import com.the.hugging.team.entities.Company;
import com.the.hugging.team.services.CompanyService;
import org.junit.jupiter.api.*;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CompanyServiceTest {

    private static Company newCompany;
    private final CompanyService companyService = CompanyService.getInstance();

    @Test
    @Order(1)
    @DisplayName("Should add new company")
    void shouldAddNewCompany() {
        Address newAddress = new Address();
        newAddress.setAddress("test");
        Company company = new Company();
        company.setName("test");
        company.setDdsNumber("test");
        company.setMol("test");
        company.setAddress(newAddress);
        company.setBulstat("test");
        newCompany = companyService.addCompany(company);

        Assertions.assertTrue(companyService.getAllCompanies().contains(newCompany));
    }

    @Test
    @Order(3)
    @DisplayName("Should delete company")
    void shouldDeleteCompany() {
        companyService.deleteCompany(newCompany);

        Assertions.assertFalse(companyService.getAllCompanies().contains(newCompany));
    }

    @Test
    @Order(2)
    @DisplayName("Should update company")
    void shouldUpdateCompany() {
        newCompany.setName("testUPDATE");
        companyService.updateCompany(newCompany);

        Assertions.assertEquals("testUPDATE",newCompany.getName());
    }
}
