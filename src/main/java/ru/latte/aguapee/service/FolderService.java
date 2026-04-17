package ru.latte.aguapee.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.latte.aguapee.model.Folder;
import ru.latte.aguapee.repository.FolderRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderService {
    private final FolderRepository folderRepository;

    public List<Folder> getAllFolders() {
        return folderRepository.findAll();
    }

    public Folder getFolderById(Long id) {
        return folderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Folder not found"));
    }

    @Transactional
    public Folder createFolder(String name) {
        Folder folder = new Folder();
        folder.setName(name);

        return folderRepository.save(folder);
    }

    @Transactional
    public Folder updateFolder(Long id, String name) {
        Folder folder = getFolderById(id);
        folder.setName(name);
        return folderRepository.save(folder);
    }

    @Transactional
    public void deleteFolder(Long id) {
        Folder folder = getFolderById(id);

        folderRepository.delete(folder);
    }
}
