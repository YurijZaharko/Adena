package main.service.implamentation;

import main.entity.CharacterL2;
import main.repository.CharacterL2Repository;
import main.service.CharacterL2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterL2ServiceImpl implements CharacterL2Service {

    private CharacterL2Repository characterL2Repository;

    @Override
    public void save(CharacterL2 characterL2) {
        characterL2Repository.save(characterL2);
    }

    @Override
    public List<CharacterL2> findAll() {
        return characterL2Repository.findAll();
    }

    @Override
    public CharacterL2 findOne(Long id) {
        return characterL2Repository.findOne(id);
    }

    @Override
    public void delete(Long id) {
        characterL2Repository.delete(id);
    }

    @Autowired
    public void setCharacterL2Repository(CharacterL2Repository characterL2Repository) {
        this.characterL2Repository = characterL2Repository;
    }
}
