package ru.latte.aguapee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.latte.aguapee.model.Image;
import ru.latte.aguapee.model.Note;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findImagesByNoteAndParentIsNull(Note note);
}
