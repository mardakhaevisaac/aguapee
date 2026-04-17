package ru.latte.aguapee.model;

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
public class Folder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String name;

    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "folder-notes")
    @SQLRestriction("parent_note_id IS NULL")
    private List<Note> notes = new ArrayList<>();

    public void addNote(Note note) {
        if (note.getParent() == null) {
            notes.add(note);
            note.setFolder(this);
        }
    }

    public void removeNote(Note note) {
        notes.remove(note);
        note.setFolder(null);
    }
}
