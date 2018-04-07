package it.cbmz.raspo.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Device {
    @Id
    private ObjectId id;
    private User user;
    @Indexed(unique = true) private String mac;
    @Indexed(unique = true) private String qrCode;
    private Date createDate;
    private Date modifiedDate;
    private long offset;
}
