package ru.latte.aguapee.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.latte.aguapee.model.Folder;
import ru.latte.aguapee.model.Note;
import ru.latte.aguapee.repository.NoteRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;

    public List<Note> getNotesByFolder(Folder folder) {
        return noteRepository.findNotesByFolderAndParentIsNull(folder);
    }

    public Note getNoteById(Long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found"));
    }

    @Transactional
    public Note createNote(Folder folder, String name, Long parentId) {
        Note note = new Note();
        note.setName(name);
        folder.addNote(note);

        if (parentId != null) {
            Note parent = getNoteById(parentId);
            if (parent != null) {
                parent.addSubNote(note);
            }
        }

        return noteRepository.save(note);
    }

    @Transactional
    public Note updateNote(Long id, String name) {
        Note note = getNoteById(id);
        note.setName(name);
        return noteRepository.save(note);
    }

    @Transactional
    public void deleteNote(Long id) {
        Note note = getNoteById(id);
        if (note.getParent() != null) {
            note.getParent().removeSubNote(note);
        }
        noteRepository.delete(note);
    }
}
