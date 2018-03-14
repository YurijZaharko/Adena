package main.service.implamentation;

import main.entity.ProductL2;
import main.repository.ProductL2Repository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ProductL2ServiceImplTest {

    @Mock
    private ProductL2Repository productL2Repository;

    @InjectMocks
    private ProductL2ServiceImpl unit;

    @Test
    public void save() throws Exception {
        //given
        ProductL2 expected = ProductL2.builder().productName("testName").build();
        ArgumentCaptor<ProductL2> captor = ArgumentCaptor.forClass(ProductL2.class);
        //when
        unit.save(expected);
        //then
        Mockito.verify(productL2Repository, Mockito.times(1)).save(captor.capture());
        final ProductL2 actual = captor.getValue();
        Assert.assertTrue(expected.equals(actual));
    }

    @Test
    public void findAll() throws Exception {
        //given
        List<ProductL2> expected = Collections.singletonList(ProductL2.builder().productName("testName").build());
        Mockito.when(unit.findAll()).thenReturn(expected);
        //when
        final List<ProductL2> actual = unit.findAll();
        //then
        Mockito.verify(productL2Repository, Mockito.times(1)).findAll();
        Assert.assertTrue(expected.equals(actual));
    }

    @Test
    public void findOne() throws Exception {
        //given
        Long idExpected = 333L;
        final ProductL2 productL2Expected = ProductL2.builder().productName("testName").id(idExpected).build();
        Mockito.when(productL2Repository.findOne(idExpected)).thenReturn(productL2Expected);
        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        //when
        final ProductL2 productL2Actual = unit.findOne(idExpected);
        //then
        Mockito.verify(productL2Repository, Mockito.times(1)).findOne(captor.capture());
        final Long idActual = captor.getValue();
        Assert.assertTrue(idExpected.equals(idActual));
        Assert.assertTrue(productL2Expected.equals(productL2Actual));
    }

    @Test
    public void delete() throws Exception {
        //given
        Long idExpected = 333L;
        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        //when
        unit.delete(idExpected);
        //then
        Mockito.verify(productL2Repository, Mockito.times(1)).delete(captor.capture());
        final Long idActual = captor.getValue();
        Assert.assertTrue(idExpected.equals(idActual));
        Mockito.verifyNoMoreInteractions(productL2Repository);
    }
}