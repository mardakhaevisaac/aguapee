package ru.latte.aguapee.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.latte.aguapee.model.Note;
import ru.latte.aguapee.model.Paragraph;
import ru.latte.aguapee.model.ParagraphRequest;
import ru.latte.aguapee.service.NoteService;
import ru.latte.aguapee.service.ParagraphService;

import java.util.List;

@RestController
@RequestMapping("api/paragraph")
@CrossOrigin
@RequiredArgsConstructor
public class ParagraphController {
    private final ParagraphService paragraphService;
    private final NoteService noteService;

    @GetMapping("/{id}")
    public ResponseEntity<List<Paragraph>> getAll(@PathVariable Long id) {
        Note note = noteService.getNoteById(id);
        return ResponseEntity.ok(paragraphService.getParagraphsByNote(note));
    }

    @GetMapping("/certain/{id}")
    public ResponseEntity<Paragraph> getById(@PathVariable Long id) {
        return ResponseEntity.ok(paragraphService.getParagraphById(id));
    }

    @PostMapping("/{id}")
    public ResponseEntity<Paragraph> create(
            @PathVariable Long id,
            @RequestBody ParagraphRequest request,
            @RequestParam(required = false) Long parent
    ) {
        Note note = noteService.getNoteById(id);
        return ResponseEntity.ok(paragraphService.createParagraph(note, request.body(), parent));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paragraph> update(@PathVariable Long id, @RequestBody ParagraphRequest request) {
        return ResponseEntity.ok(paragraphService.updateParagraph(id, request.body()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        paragraphService.deleteParagraph(id);
        return ResponseEntity.noContent().build();
    }
}
