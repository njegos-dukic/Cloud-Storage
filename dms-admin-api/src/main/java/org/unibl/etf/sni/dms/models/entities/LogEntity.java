package org.unibl.etf.sni.dms.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Table(name = "LOGS")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class LogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "type", nullable = false, length = 256)
    private String type;

    @Column(name = "info", nullable = false, length = 512)
    private String info;

    @Column(name = "dateTime", nullable = false)
    private Instant dateTime;
}