package ru.latte.aguapee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.latte.aguapee.model.Folder;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Long> {
}
