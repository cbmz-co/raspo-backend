package it.cbmz.raspo.backend.model;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
public class Device {
    @Id@GeneratedValue private long id;
    @ManyToOne private User user;
    @Column(unique = true) private String mac;
}
