package com.Crudexample.crud.controller;

import com.Crudexample.crud.model.RespostaComentario;
import com.Crudexample.crud.service.RespostaComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/respostas")
public class RespostaComentarioController {

    private final RespostaComentarioService respostaComentarioService;

    @Autowired
    public RespostaComentarioController(RespostaComentarioService respostaComentarioService) {
        this.respostaComentarioService = respostaComentarioService;
    }

    @PostMapping("/{idComentario}")
    public ResponseEntity<?> adicionarResposta(
            @PathVariable Long idComentario,
            @RequestBody Map<String, Object> requestBody) {
        try {
            if (!requestBody.containsKey("idUsuario") || !requestBody.containsKey("conteudo")) {
                return ResponseEntity.badRequest().body("Os campos 'idUsuario' e 'conteudo' são obrigatórios.");
            }

            Long idUsuario = ((Number) requestBody.get("idUsuario")).longValue();
            String conteudo = (String) requestBody.get("conteudo");

            if (conteudo == null || conteudo.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("O conteúdo da resposta não pode ser vazio.");
            }

            RespostaComentario resposta = respostaComentarioService.adicionarResposta(idComentario, idUsuario, conteudo);

            return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao adicionar resposta ao comentário: " + e.getMessage());
        }
    }


    @GetMapping("/{idComentario}")
    public ResponseEntity<List<RespostaComentario>> listarRespostasPorComentario(@PathVariable Long idComentario) {
        try {
            List<RespostaComentario> respostas = respostaComentarioService.listarRespostasPorComentario(idComentario);
            return ResponseEntity.ok(respostas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
