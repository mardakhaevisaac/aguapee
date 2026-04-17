package ru.latte.aguapee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.latte.aguapee.model.Note;
import ru.latte.aguapee.model.Paragraph;

import java.util.List;

@Repository
public interface ParagraphRepository extends JpaRepository<Paragraph, Long> {
    List<Paragraph> findParagraphsByNoteAndParentIsNull(Note note);
}
