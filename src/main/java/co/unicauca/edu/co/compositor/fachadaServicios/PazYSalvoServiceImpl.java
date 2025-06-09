package co.unicauca.edu.co.compositor.fachadaServicios;


import co.unicauca.edu.co.compositor.client.AdminWebSocketClient;
import co.unicauca.edu.co.compositor.fachadaServicios.dtos.StudentStatusDTO;
import co.unicauca.edu.co.compositor.fachadaServicios.servicios.IServicioDeportes;
import co.unicauca.edu.co.compositor.fachadaServicios.servicios.IServicioFinanciero;
import co.unicauca.edu.co.compositor.fachadaServicios.servicios.IServicioLaboratorios;

import org.springframework.stereotype.Service;
@Service
public class PazYSalvoServiceImpl implements IPazYSalvoService {
    private final AdminWebSocketClient adminWebSocketClient = new AdminWebSocketClient();
    private final IServicioLaboratorios labService;
    private final IServicioFinanciero finService;
    private final IServicioDeportes depService;

    public PazYSalvoServiceImpl(
        IServicioLaboratorios labService,
        IServicioFinanciero finService,
        IServicioDeportes depService
    ) {
        this.labService = labService;
        this.finService = finService;
        this.depService = depService;
    }

    @Override
    public StudentStatusDTO verificarEstado(String idEstudiante) {
        boolean lab = labService.estaAPazYSalvo(idEstudiante);
        boolean fin = finService.estaAPazYSalvo(idEstudiante);
        boolean dep = depService.estaAPazYSalvo(idEstudiante);

        StudentStatusDTO dto = new StudentStatusDTO();
        dto.setIdEstudiante(idEstudiante);
        dto.setEstadoLaboratorios(lab);
        dto.setEstadoFinanciero(fin);
        dto.setEstadoDeportes(dep);
        dto.setPazYSalvo(lab && fin && dep);
        dto.setMensaje("El estudiante " + idEstudiante + " está " +
            (dto.isPazYSalvo() ? "a paz y salvo" : "con deudas pendientes"));
        if (!dto.isPazYSalvo()) {
            adminWebSocketClient.notificar("/app/notificar", dto);
        }   

        return dto;
    }
}
