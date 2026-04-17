package ru.latte.aguapee.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter @Setter
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "folder_id")
    @JsonBackReference(value = "folder-notes")
    private Folder folder;

    @OneToMany(mappedBy = "note", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "note-nodes")
    @SQLRestriction("parent_node_id IS NULL")
    private List<Node> nodes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "parent_note_id")
    @JsonBackReference(value = "note-sub")
    private Note parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "note-sub")
    private List<Note> subNotes = new ArrayList<>();

    public void addSubNote(Note subNote) {
        subNotes.add(subNote);
        subNote.setParent(this);
    }

    public void removeSubNote(Note subNote) {
        subNotes.remove(subNote);
        subNote.setParent(null);
    }

    public void addNode(Node node) {
        nodes.add(node);
        node.setNote(this);
    }

    public void removeParagraph(Node node) {
        nodes.remove(node);
        node.setNote(null);
    }
}
