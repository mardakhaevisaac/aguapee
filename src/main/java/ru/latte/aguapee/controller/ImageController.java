package ru.latte.aguapee.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.latte.aguapee.model.*;
import ru.latte.aguapee.service.ImageService;
import ru.latte.aguapee.service.NoteService;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/image")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;
    private final NoteService noteService;

    @GetMapping("/{id}")
    public ResponseEntity<List<Image>> getAll(@PathVariable Long id) {
        Note note = noteService.getNoteById(id);
        return ResponseEntity.ok(imageService.getImagesByNote(note));
    }

    @GetMapping("/certain/{id}")
    public ResponseEntity<Image> getById(@PathVariable Long id) {
        return ResponseEntity.ok(imageService.getImageById(id));
    }

    @PostMapping("/{id}")
    public ResponseEntity<Image> create(
            @PathVariable Long id,
            @RequestBody ImageRequest request,
            @RequestParam(required = false) Long parent
    ) {
        Note note = noteService.getNoteById(id);
        return ResponseEntity.ok(imageService.createImage(note, request.URL(), parent));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Image> update(@PathVariable Long id, @RequestBody ImageRequest request) {
        return ResponseEntity.ok(imageService.updateImage(id, request.URL()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        imageService.deleteImage(id);
        return ResponseEntity.noContent().build();
    }
}
