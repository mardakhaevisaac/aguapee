package ru.latte.aguapee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.latte.aguapee.model.Node;

public interface NodeRepository extends JpaRepository<Node, Long> {
}
