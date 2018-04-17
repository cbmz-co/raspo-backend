package it.cbmz.raspo.backend.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class SpeedTest {
    @Id
    private String id;
    private Device device;
    private float ping;
    private String dwSpeed;
    private String upSpeed;
}
