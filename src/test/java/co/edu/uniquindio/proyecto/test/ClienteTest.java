package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.repositorios.ClienteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ClienteTest {
    @Autowired
    private ClienteRepo clienteRepo;

}
