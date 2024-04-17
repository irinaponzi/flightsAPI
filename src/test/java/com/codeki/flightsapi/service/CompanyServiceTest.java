package com.codeki.flightsapi.service;

import com.codeki.flightsapi.dto.ResponseDto;
import com.codeki.flightsapi.model.Company;
import com.codeki.flightsapi.repository.CompanyRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CompanyServiceTest {

    @Autowired
    CompanyService companyService;

    static CompanyRepository mockedCompanyRepository = mock(CompanyRepository.class);
    List<Company> companyList;

    @BeforeEach
    void setUp() {

        Company company1 = new Company(1L,"Aerolíneas Argentinas", "https://www.aerolineas.com.ar", "banner1");
        Company company2 = new Company(2L,"Flybondi", "https://flybondi.com/ar", "banner2");
        Company company3 = new Company(3L, "Jet Smart", "https://jetsmart.com/ar", "banner3");

        companyList = new ArrayList<>();
        companyList.add(company1);
        companyList.add(company2);
        companyList.add(company3);
    }

    @AfterAll
    static void reset() {
        Mockito.reset(mockedCompanyRepository);
    }

    @Test
    void findAllTest() {
        List<Company> companyListExpected = companyList;

        when(mockedCompanyRepository.findAll()).thenReturn(companyListExpected);
        List<Company> companyListActual = companyService.findAll();

        assertEquals(companyListExpected.size(), companyListActual.size());
        assertEquals(companyListExpected.get(0).getId(), companyListActual.get(0).getId());
        assertEquals(companyListExpected.get(1).getName(), companyListActual.get(1).getName());
    }

    @Test
    void findByIdTest() {
        Long id = 3L;
        Company companyExpected = companyList.get(2);

        when(mockedCompanyRepository.findById(id)).thenReturn(Optional.of(companyExpected));
        Company companyActual = companyService.findById(id);

        assertEquals(companyExpected.getId(), companyActual.getId());
        assertEquals(companyExpected.getName(), companyActual.getName());
    }

    @Test
    void create() {
        Company companyToSave = new Company(1L,"Lade", "https://lade.com.ar", "banner4");

        when(mockedCompanyRepository.save(companyToSave)).thenReturn(companyToSave);
        Company companyActual = companyService.create(companyToSave);

        assertEquals(companyToSave.getId(), companyActual.getId());
        assertEquals(companyToSave.getName(), companyActual.getName());
    }

    @Test
    void deleteById() {
        Long id = 3L;
        Company companyToDeleted = companyList.get(2);
        ResponseDto responseDtoExpected = new ResponseDto("The company " + id + " has been deleted");

        when(mockedCompanyRepository.findById(id)).thenReturn(Optional.of(companyToDeleted));
        ResponseDto responseDtoActual = companyService.deleteById(id);

        assertEquals(responseDtoExpected.getMessage(), responseDtoActual.getMessage());
    }
}
