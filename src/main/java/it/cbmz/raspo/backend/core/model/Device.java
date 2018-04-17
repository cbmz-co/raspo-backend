package it.cbmz.raspo.backend.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    private String id;
    private User user;
    @Indexed(unique = true) private String mac;
    private Date createDate;
    private Date modifiedDate;
    private Date lastSignal;
}
