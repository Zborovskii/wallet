package ru.wallet.domain;

import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.wallet.enums.Category;

@Entity
@Data
@EqualsAndHashCode(of = {"id", "amount"})
@ToString(of = {"id", "amount"})
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    @NotNull
    private Integer amount;

    @NotNull
    private LocalDateTime created;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Category category;

}
