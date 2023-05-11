package br.com.uniamerica.estacionamento.controllerl

import org.sprigframework.stereotype.Controller;
import org.sprigframework.web.bind.annotation.DeleteMapping;






@Controller
@RequestMapping(value = "/api/modelo")
public class ModeloController {

@GetMapping("/{id}")

// api/modelo/1
public ResponseEntity<Modelo> findByIdPath(@PathVariable("id")final Long id){
final Modelo modelo = ResponseEntity.ok(this.modeloRepository.findById(id).orElse(null));

return modelo == null
? ResponseEntity.badRequest().body("Nenhum valor encontrado.")
: ResponseEntity.ok(modelo);
}

//Outra maneira de receber o id
// api/modelo?id=1
public ResponseEntity<Modelo> findById(@RequestParam("id")final Long id){
return ResponseEntity.ok(new Modelo());
}


//@GetMapping
//@PostMapping
//@PutMapping
//@DeleteMapping
}