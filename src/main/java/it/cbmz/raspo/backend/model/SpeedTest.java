package it.cbmz.raspo.backend.model;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
public class SpeedTest {
    @Id@GeneratedValue private long id;
    @ManyToOne private Device device;
    @Column private float ping;
    @Column private float dwSpeed;
    @Column private float upSpeed;
}
