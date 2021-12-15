package com.the.hugging.team.services;

import com.the.hugging.team.entities.Address;
import com.the.hugging.team.entities.Company;
import com.the.hugging.team.repositories.CompanyRepository;

import java.util.List;

public class CompaniesService {

    private static CompaniesService INSTANCE = null;
    private final CompanyRepository companyRepository = CompanyRepository.getInstance();

    public static CompaniesService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CompaniesService();
        }

        return INSTANCE;
    }

    public List<Company> getAllCompanies() {
        return companyRepository.getAll();
    }

    public void updateCompany(Company company) {
        companyRepository.update(company);
    }

    public Company addCompany(Company company) {
        companyRepository.save(company);
        return company;
    }

    public void deleteCompany(Company company) {
        companyRepository.delete(company);
    }
}
