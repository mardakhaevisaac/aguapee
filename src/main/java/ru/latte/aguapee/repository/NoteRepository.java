package ru.latte.aguapee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.latte.aguapee.model.Folder;
import ru.latte.aguapee.model.Note;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findNotesByFolderAndParentIsNull(Folder folder);
}
