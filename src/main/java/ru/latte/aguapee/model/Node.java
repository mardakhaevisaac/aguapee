package ru.latte.aguapee.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor
@Getter @Setter
public abstract class Node {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne
    @JoinColumn(name = "note_id")
    @JsonBackReference(value = "note-node")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Note note;

    @ManyToOne
    @JoinColumn(name = "parent_node_id")
    @JsonBackReference(value = "node-sub")
    private Node parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "node-sub")
    private List<Node> subNodes = new ArrayList<>();

    public void addSubNode(Node subNode) {
        subNodes.add(subNode);
        subNode.setParent(this);
    }

    public void removeSubNode(Node subNode) {
        subNodes.remove(subNode);
        subNode.setParent(null);
    }
}
