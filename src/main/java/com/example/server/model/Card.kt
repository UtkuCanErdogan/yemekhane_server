package com.example.server.model

import org.hibernate.annotations.Type
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "card")
data class Card(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id : Long,

        @Column(name = "card_code", unique = true)
        val code : String,

        @Column(name = "card_balance")
        val balance : Long,

        @Column(name = "card_is_active", nullable = false)
        @Type(type = "org.hibernate.type.NumericBooleanType")
        val isActive : Boolean,

        @Column(name = "card_creation_date", nullable = false)
        val creationDate : LocalDateTime,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "customer_id", referencedColumnName = "id")
        val customer: Customer,

        @OneToMany(mappedBy = "card", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        val transaction: List<Transaction>?,
) {
    constructor(code: String, customer: Customer) : this(
            0,
            code,
            0,
            true,
            LocalDateTime.now(),
            customer,
            null
    )



    override fun toString(): String {
        return "Card(id=$id, code=$code, balance=$balance, isActive=$isActive, creationDate=$creationDate, customer=$customer)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Card

        if (id != other.id) return false
        if (code != other.code) return false
        if (balance != other.balance) return false
        if (isActive != other.isActive) return false
        if (creationDate != other.creationDate) return false
        if (customer != other.customer) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + code.hashCode()
        result = 31 * result + balance.hashCode()
        result = 31 * result + isActive.hashCode()
        result = 31 * result + creationDate.hashCode()
        result = 31 * result + customer.hashCode()
        return result
    }


}
