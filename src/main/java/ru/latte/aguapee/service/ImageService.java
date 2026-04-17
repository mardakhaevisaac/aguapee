package ru.latte.aguapee.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.latte.aguapee.model.Image;
import ru.latte.aguapee.model.Node;
import ru.latte.aguapee.model.Note;
import ru.latte.aguapee.model.Paragraph;
import ru.latte.aguapee.repository.ImageRepository;
import ru.latte.aguapee.repository.NodeRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final NodeRepository nodeRepository;

    public List<Image> getImagesByNote(Note note) {
        return imageRepository.findImagesByNoteAndParentIsNull(note);
    }

    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found"));
    }

    @Transactional
    public Image createImage(Note note, String URL, Long parentId) {
        Image img = new Image();
        img.setURL(URL);
        note.addNode(img);

        if (parentId != null) {
            Optional<Node> parent = nodeRepository.findById(parentId);
            parent.ifPresent(node -> node.addSubNode(img));
        }

        return imageRepository.save(img);
    }

    @Transactional
    public Image updateImage(Long id, String URL) {
        Image image = getImageById(id);
        image.setURL(URL);
        return imageRepository.save(image);
    }

    @Transactional
    public void deleteImage(Long id) {
        Image image = getImageById(id);
        if (image.getParent() != null) {
            image.getParent().removeSubNode(image);
        }
        imageRepository.delete(image);
    }
}
