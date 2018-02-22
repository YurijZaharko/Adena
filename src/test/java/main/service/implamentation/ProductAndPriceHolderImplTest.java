package main.service.implamentation;

import main.entity.ProductAndPriceHolder;
import main.repository.ProductAndPriceHolderRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class ProductAndPriceHolderImplTest {

    @Mock
    private ProductAndPriceHolderRepository productAndPriceHolderRepository;

    @InjectMocks
    private ProductAndPriceHolderImpl unit;

    @Captor
    private ArgumentCaptor<Set<ProductAndPriceHolder>> captor;

    @Test
    public void save() throws Exception {
        //given
        final ProductAndPriceHolder p = ProductAndPriceHolder.builder().productPrice(1000L).productQuantity(1L).build();
        final Set<ProductAndPriceHolder> expected = Collections.singleton(p);
        //when
        unit.save(expected);
        //then
        Mockito.verify(productAndPriceHolderRepository, Mockito.times(1)).save(captor.capture());
        final Set<ProductAndPriceHolder> actual = captor.getValue();
        Assert.assertTrue(expected.equals(actual));
    }
}