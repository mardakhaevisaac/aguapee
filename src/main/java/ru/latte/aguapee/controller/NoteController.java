package ru.latte.aguapee.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.latte.aguapee.model.Folder;
import ru.latte.aguapee.model.Note;
import ru.latte.aguapee.model.NoteRequest;
import ru.latte.aguapee.service.FolderService;
import ru.latte.aguapee.service.NoteService;

import java.util.List;

@RestController
@RequestMapping("api/note")
@CrossOrigin
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;
    private final FolderService folderService;

    @GetMapping("/{id}")
    public ResponseEntity<List<Note>> getAll(@PathVariable Long id) {
        Folder folder = folderService.getFolderById(id);
        return ResponseEntity.ok(noteService.getNotesByFolder(folder));
    }

    @GetMapping("/certain/{id}")
    public ResponseEntity<Note> getById(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.getNoteById(id));
    }

    @PostMapping("/{id}")
    public ResponseEntity<Note> create(
            @PathVariable Long id,
            @RequestBody NoteRequest request,
            @RequestParam(required = false) Long parent
    ) {
        Folder folder = folderService.getFolderById(id);
        return ResponseEntity.ok(noteService.createNote(folder, request.name(), parent));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> update(@PathVariable Long id, @RequestBody NoteRequest request) {
        return ResponseEntity.ok(noteService.updateNote(id, request.name()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }
}
