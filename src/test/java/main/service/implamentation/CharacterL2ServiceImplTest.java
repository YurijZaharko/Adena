package main.service.implamentation;

import main.entity.CharacterL2;
import main.repository.CharacterL2Repository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class CharacterL2ServiceImplTest {

    @Mock
    private CharacterL2Repository characterL2Repository;

    @InjectMocks
    private CharacterL2ServiceImpl unit;

    @Test
    public void save() throws Exception {
        //given
        CharacterL2 characterL2Expected = CharacterL2.builder().name("testName").build();
        ArgumentCaptor<CharacterL2> captor = ArgumentCaptor.forClass(CharacterL2.class);
        //when
        unit.save(characterL2Expected);
        //then
        Mockito.verify(characterL2Repository, Mockito.times(1)).save(captor.capture());
        final CharacterL2 characterL2Actual = captor.getValue();
        Assert.assertTrue(characterL2Expected.equals(characterL2Actual));
    }

    @Test
    public void findAll() throws Exception {
        //given
        CharacterL2 characterL2First = CharacterL2.builder().name("testNameOne").build();
        CharacterL2 characterL2Second = CharacterL2.builder().name("testNameTwo").build();
        final List<CharacterL2> characterL2ListExpected = Arrays.asList(characterL2First, characterL2Second);

        Mockito.when(characterL2Repository.findAll()).thenReturn(characterL2ListExpected);
        //when
        final List<CharacterL2> characterL2ListActual = unit.findAll();
        //then
        Mockito.verify(characterL2Repository, Mockito.times(1)).findAll();
        Assert.assertTrue(characterL2ListExpected.equals(characterL2ListActual));
    }

    @Test
    public void findOne() throws Exception {
        //given
        Long idExpected = 333L;
        final CharacterL2 characterL2 = CharacterL2.builder().id(idExpected).name("testName").build();
        Mockito.when(characterL2Repository.findOne(idExpected)).thenReturn(characterL2);
        //when
        final CharacterL2 result = unit.findOne(idExpected);
        //then
        final Long idActual = result.getId();
        Assert.assertNotNull(idActual);
        Assert.assertEquals(idExpected, idActual);
    }

    @Test
    public void delete() throws Exception {
        //given
        Long idExpected = 1111L;
        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        //when
        unit.delete(idExpected);
        //then
        Mockito.verify(characterL2Repository, Mockito.times(1)).delete(captor.capture());
        final Long idActual = captor.getValue();
        Assert.assertNotNull(idActual);
        Assert.assertEquals(idExpected, idActual);
    }
}