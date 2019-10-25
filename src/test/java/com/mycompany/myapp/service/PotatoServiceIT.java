package com.mycompany.myapp.service;

import com.mycompany.myapp.TesterApp;
import com.mycompany.myapp.domain.Potato;
import com.mycompany.myapp.repository.PotatoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.*;
import java.util.Optional;

@SpringBootTest(classes = TesterApp.class)
@Transactional
public class PotatoServiceIT {

    @Autowired
    private PotatoService potatoService;

    @MockBean
    private PotatoRepository potatoRepository;

    @BeforeEach
    public void setup(){
        Mockito.when(potatoRepository.findById(Mockito.any()))
            .thenReturn(Optional.of(new Potato().shape("Square")));
    }

    @Test
    public void testUselessTest() {
        assertThat(potatoService.findOne(12L).get().getShape()).isEqualTo("Square");
    }
}
