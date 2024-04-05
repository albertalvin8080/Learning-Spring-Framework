package org.albert.testsunitintregration.unit.service;

import org.albert.testsunitintregration.repository.ProductRepository;
import org.albert.testsunitintregration.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.albert.testsunitintregration.util.ProductUtil.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepositoryMock;
    @InjectMocks
    private ProductService productServiceMock;

    @Test
    public void findNamesWithEvenLength_ReturnsListOfEvenNames_WhenSuccessful() {
        BDDMockito.given(productRepositoryMock.findAllNames()).willReturn(findAllNamesUtil());

        final List<String> namesWithEvenLength = productServiceMock.findNamesWithEvenLength();

        assertEquals(namesWithEvenLength.size(), findNamesWithEvenLengthUtil().size());
        assertArrayEquals(namesWithEvenLength.toArray(), findNamesWithEvenLengthUtil().toArray());

         // verifying if certain method was called
        {
            BDDMockito.then(productRepositoryMock).should(Mockito.times(1))
                    .voidMethodExample();
            // equivalent to:
            BDDMockito.verify(productRepositoryMock, Mockito.times(1))
                    .voidMethodExample();
        }
    }
}