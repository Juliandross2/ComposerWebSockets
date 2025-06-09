package co.unicauca.edu.co.compositor.fachadaServicios;


import co.unicauca.edu.co.compositor.fachadaServicios.dtos.StudentStatusDTO;
import co.unicauca.edu.co.compositor.client.AdminWebSocketClient;
import co.unicauca.edu.co.compositor.fachadaServicios.IPazYSalvoService;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class PazYSalvoServiceImpl implements IPazYSalvoService {

    private AdminWebSocketClient adminWebSocketClient;


    @Override
    public StudentStatusDTO verificarEstado(String idEstudiante) {
        System.out.println("Verificando estado de paz y salvo para el estudiante: " + idEstudiante);
        adminWebSocketClient.conectar();
        return new StudentStatusDTO();
    }

}
