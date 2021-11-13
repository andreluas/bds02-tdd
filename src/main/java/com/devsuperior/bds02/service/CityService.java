package com.devsuperior.bds02.service;

import java.util.List;
import java.util.stream.Collectors;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.repository.CityRepository;
import com.devsuperior.bds02.service.exceptions.ControllerNotFoundException;
import com.devsuperior.bds02.service.exceptions.DatabaseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CityService {

    @Autowired
    private CityRepository repository;

    // Find all
    @Transactional(readOnly = true)
    public List<CityDTO> findAll() {

        List<City> list = repository.findAll(Sort.by("name"));
        return list.stream().map(l -> new CityDTO(l)).collect(Collectors.toList());
    }

    // Insert
    @Transactional
    public CityDTO insert(CityDTO dto) {

        City entity = new City();
        entity.setName(dto.getName());
        entity = repository.save(entity);
        return new CityDTO(entity);
    }

    // Delete
    public void delete(Long id) {

        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ControllerNotFoundException("Id not foud " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }
}
