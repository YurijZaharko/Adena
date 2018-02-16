package main.service;

import main.entity.CharacterL2;

import java.util.List;

public interface CharacterL2Service {
    void save(CharacterL2 characterL2);

    List<CharacterL2> findAll();

    CharacterL2 findOne(Long id);

    void delete(Long id);
}
