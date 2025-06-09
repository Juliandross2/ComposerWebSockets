package co.unicauca.edu.co.compositor.controladores;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.unicauca.edu.co.compositor.fachadaServicios.PazYSalvoServiceImpl;
import co.unicauca.edu.co.compositor.fachadaServicios.dtos.StudentStatusDTO;
@RestController
@RequestMapping("/api/pazysalvo")
public class PazYSalvoController {
    
    private final PazYSalvoServiceImpl pazYSalvoService;

    public PazYSalvoController(PazYSalvoServiceImpl pazYSalvoService) {
        this.pazYSalvoService = pazYSalvoService;
    }

    @GetMapping("/{studentCode}")
    public StudentStatusDTO verificarEstado(@PathVariable String studentCode) {
        return pazYSalvoService.verificarEstado(studentCode);
    }
}
