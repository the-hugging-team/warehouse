package com.the.hugging.team.warehouse;

import com.the.hugging.team.entities.Address;
import com.the.hugging.team.entities.Company;
import com.the.hugging.team.repositories.AddressRepository;
import com.the.hugging.team.repositories.CompanyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class CompanyServiceTest {

    private static Address address;
    public static void setUp(){
        AddressRepository.getInstance().getById(1).ifPresent(address1 -> {address = address1;});
    }

    @Test//TODO needs to be reworked
    @DisplayName("Should add new company")
    void shouldAddNewCompany(){
        Company company = new Company();
        company.setId(1);
        company.setName("CELLA");
        company.setAddress(address);
        company.setBulstat("4813573204");
        company.setDdsNumber("BG84124589");
        company.setMol("Mihail Georgiev");
        Optional<Company> companyOptional=Optional.of(company);

        Assertions.assertEquals(companyOptional, CompanyRepository.getInstance().getById(1));
    }
}
