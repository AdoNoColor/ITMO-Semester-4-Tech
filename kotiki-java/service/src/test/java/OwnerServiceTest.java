import models.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class OwnerServiceTest {
    @Mock
    OwnerDaoImpl dao;
    OwnerService service;

    public OwnerServiceTest() {
    }

    @BeforeEach
    public void setUp() {
        this.dao = mock(OwnerDaoImpl.class);
        this.service = new OwnerService(dao);
    }

    @Test
    void findOwner_returnsNull() {
        Mockito.when(dao.findById("51bb2ec9-b775-48bd-a1f6-6447e4a1f662")).thenReturn(null);
        Owner max = service.findOwner("51bb2ec9-b775-48bd-a1f6-6447e4a1f662");
        assertThat(max).isNull();
        Mockito.verify(dao, times(1)).findById("51bb2ec9-b775-48bd-a1f6-6447e4a1f662");
    }

    @Test
    void findOwner_returnsOwner() {
        Mockito.when(dao.findById("51bb2ec9-b775-48bd-a1f6-6447e4a1f662")).thenReturn(new Owner("Max", LocalDate.now()));
        Owner max = service.findOwner("51bb2ec9-b775-48bd-a1f6-6447e4a1f662");
        assertThat(max).isNotNull();
        assertThat(max.getName()).isEqualTo("Max");
        Mockito.verify(dao, times(1)).findById("51bb2ec9-b775-48bd-a1f6-6447e4a1f662");
    }
}