package com.seminarioProyect.apiMusic.controllers;
import com.seminarioProyect.apiMusic.dtos.TemaRequest;
import com.seminarioProyect.apiMusic.dtos.TemaResponse;
import com.seminarioProyect.apiMusic.dtos.TemasResponse;
import com.seminarioProyect.apiMusic.services.TemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/tema")
@Controller
public class TemaController {
    @Autowired
    private TemaService temaService;

    @PostMapping(value = "/nuevoTema", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> nuevoTema(@RequestBody TemaRequest temaRequest) {
        return temaService.setTema(temaRequest);
    }

    @GetMapping(value = "/listarTemas", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public TemasResponse listarTemas() {
        return temaService.listarTemas();
    }

    @PutMapping(value = "/actualizarTema/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> actualizarTemas(@PathVariable Long id, @RequestBody TemaRequest temaRequest) {
        return temaService.updateTema(id, temaRequest);
    }

    @DeleteMapping(value = "/eliminarTema/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> eliminarTema(@PathVariable Long id) {
        return temaService.deleteTema(id);
    }

    @GetMapping(value = "/buscarTema/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<TemaResponse> buscarTemaPorId(@PathVariable Long id){
        TemaResponse temaResponse = temaService.buscarTemaPorId(id);
        return ResponseEntity.ok(temaResponse);
    }
}

