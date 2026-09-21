package com.conforttravel.api.cliente.controller;

import com.conforttravel.api.cliente.dto.ClienteFilterRequest;
import com.conforttravel.api.cliente.dto.ClienteRequest;
import com.conforttravel.api.cliente.dto.ClienteResponse;
import com.conforttravel.api.cliente.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<ClienteResponse>> obtenerTodos() {
        return ResponseEntity.ok(clienteService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> obtenerPorId(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                clienteService.obtenerPorId(id)
        );
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> crear(
            @Valid @RequestBody ClienteRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(clienteService.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ClienteRequest request
    ) {
        return ResponseEntity.ok(
                clienteService.actualizar(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id
    ) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<ClienteResponse>> buscar(

            @RequestParam(required = false) String nombres,

            @RequestParam(required = false) String apellidos,

            @RequestParam(required = false) String telefono,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "id") String sortBy,

            @RequestParam(defaultValue = "desc") String direction

    ) {

        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(
                page,
                size,
                sort
        );

        ClienteFilterRequest filter =
                new ClienteFilterRequest(
                        nombres,
                        apellidos,
                        telefono
                );

        return ResponseEntity.ok(
                clienteService.buscar(filter, pageable)
        );
    }
}