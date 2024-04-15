package com.codeki.flightsapi.service;

import com.codeki.flightsapi.dto.ResponseDto;
import com.codeki.flightsapi.exceptions.NotFoundException;
import com.codeki.flightsapi.model.Company;
import com.codeki.flightsapi.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Company findById(Long id) {
        Optional<Company> companyOptional = companyRepository.findById(id);
        if (companyOptional.isPresent()) {
            return companyOptional.get();
        }
        throw new NotFoundException("La compañía no fue encontrada");
    }

    public List<Company> findByName(String name) {
        List<Company> companyList = companyRepository.findByNameContainingIgnoreCase(name);
        if (!companyList.isEmpty()) {
            return companyList;
        }
        throw new NotFoundException("No se encontraron resultados");
    }

    public Company create(Company company) {
        companyRepository.save(company);
        return company;
    }

    public Company update(Long id, Company company) {
        Optional<Company> companyOptional = companyRepository.findById(id);
        if (companyOptional.isPresent()) {
            company.setId(id);
            companyRepository.save(company);
            return company;
        }
        throw new NotFoundException("La compañía no fue encontrada");
    }

    public ResponseDto deleteById(Long id) {
        Optional<Company> companyOptional = companyRepository.findById(id);
        if (companyOptional.isPresent()) {
            companyRepository.deleteById(id);
            return new ResponseDto("La compañía " + id + " ha sido eliminada");
        }
        throw new NotFoundException("La compañía no fue encontrada");
    }
}
