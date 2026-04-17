package ru.latte.aguapee.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.latte.aguapee.model.Node;
import ru.latte.aguapee.model.Note;
import ru.latte.aguapee.model.Paragraph;
import ru.latte.aguapee.repository.NodeRepository;
import ru.latte.aguapee.repository.ParagraphRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParagraphService {
    private final ParagraphRepository paragraphRepository;
    private final NodeRepository nodeRepository;

    public List<Paragraph> getParagraphsByNote(Note note) {
        return paragraphRepository.findParagraphsByNoteAndParentIsNull(note);
    }

    public Paragraph getParagraphById(Long id) {
        return paragraphRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paragraph not found"));
    }

    @Transactional
    public Paragraph createParagraph(Note note, String body, Long parentId) {
        Paragraph paragraph = new Paragraph();
        paragraph.setBody(body);
        note.addNode(paragraph);

        if (parentId != null) {
            Optional<Node> parent = nodeRepository.findById(parentId);
            parent.ifPresent(node -> node.addSubNode(paragraph));
        }

        return paragraphRepository.save(paragraph);
    }

    @Transactional
    public Paragraph updateParagraph(Long id, String body) {
        Paragraph paragraph = getParagraphById(id);
        paragraph.setBody(body);
        return paragraphRepository.save(paragraph);
    }

    @Transactional
    public void deleteParagraph(Long id) {
        Paragraph paragraph = getParagraphById(id);
        if (paragraph.getParent() != null) {
            paragraph.getParent().removeSubNode(paragraph);
        }
        paragraphRepository.delete(paragraph);
    }
}
