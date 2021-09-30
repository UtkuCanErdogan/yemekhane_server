package com.example.server.model

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "transactions")
data class Transaction(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id : Long,

        @Column(name = "transaction_type")
        val type : TransactionType,

        @Column(name = "transaction_date")
        val creationDate : LocalDateTime,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "card_id")
        val card: Card

){
    constructor(type: TransactionType, card: Card) : this(
            0,
            type,
            LocalDateTime.now(),
            card
    )




    override fun toString(): String {
        return "Transaction(id=$id, type=$type, creationDate=$creationDate, card=$card)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Transaction

        if (id != other.id) return false
        if (type != other.type) return false
        if (creationDate != other.creationDate) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + creationDate.hashCode()
        return result
    }


}

enum class TransactionType{
    DINNER, LOAD_BALANCE
}

