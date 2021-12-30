package com.the.hugging.team.warehouse;

import com.the.hugging.team.entities.Address;
import com.the.hugging.team.entities.Company;
import com.the.hugging.team.repositories.CompanyRepository;
import com.the.hugging.team.services.CompanyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CompanyServiceTest {

    private static Company newCompany;
    private final CompanyService companyService = CompanyService.getInstance();

    @Test
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
    @DisplayName("Should delete company")
    void shouldDeleteCompany() {
        companyService.deleteCompany(newCompany);

        Assertions.assertFalse(companyService.getAllCompanies().contains(newCompany));
    }

    @Test
    @DisplayName("Should update company")
    void shouldUpdateCompany() {
        List<Company> companies = CompanyRepository.getInstance().getAll();
        Company existingCompany = companies.get(1);
        existingCompany.setName("testUPDATE");
        companyService.updateCompany(existingCompany);

        Assertions.assertTrue(companyService.getAllCompanies().contains(existingCompany));
    }
}
