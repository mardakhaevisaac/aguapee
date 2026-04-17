package ru.latte.aguapee.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;
import ru.latte.aguapee.model.Folder;
import ru.latte.aguapee.model.FolderRequest;
import ru.latte.aguapee.service.FolderService;

import java.util.List;

@RestController
@RequestMapping("api/folders")
@CrossOrigin
@RequiredArgsConstructor
public class FolderController {
    private final FolderService folderService;

    @GetMapping
    public ResponseEntity<List<Folder>> getAll() {
        return ResponseEntity.ok(folderService.getAllFolders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Folder> getById(@PathVariable Long id) {
        return ResponseEntity.ok(folderService.getFolderById(id));
    }

    @PostMapping
    public ResponseEntity<Folder> create(@RequestBody FolderRequest request) {
        return ResponseEntity.ok(folderService.createFolder(request.name()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Folder> update(@PathVariable Long id, @RequestBody FolderRequest request) {
        return ResponseEntity.ok(folderService.updateFolder(id, request.name()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        folderService.deleteFolder(id);
        return ResponseEntity.noContent().build();
    }
}
