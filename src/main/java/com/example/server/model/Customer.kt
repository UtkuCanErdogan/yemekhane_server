package com.example.server.model

import org.hibernate.annotations.Type
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "customers")
data class Customer(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id : Long,

        @Column(name = "customer_tc", nullable = false, unique = true)
        val tc : Long,

        @Column(name = "customer_mail", nullable = false, unique = true)
        val mail : String,

        @Column(name = "customer_name", nullable = false)
        val name : String,

        @Column(name = "customer_surname", nullable = false)
        val surname : String,

        @Column(name = "customer_age", nullable = false)
        val age : Int,

        @Column(name = "customer_date", nullable = false)
        val creationDate : LocalDateTime,

        @Column(name = "customer_role", nullable = false)
        val role : String,

        @Column(name = "customer_is_active", nullable = false)
        @Type(type = "org.hibernate.type.NumericBooleanType")
        val isActive : Boolean,

        @OneToMany(mappedBy = "customer",fetch = FetchType.LAZY,cascade = [CascadeType.ALL])
        val cards : List<Card>?

){
    constructor(tc: Long, mail: String, name: String, surname: String, age: Int) : this(
            0,
            tc,
            mail,
            name,
            surname,
            age,
            LocalDateTime.now(),
            "personel",
            true,
            null
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Customer

        if (id != other.id) return false
        if (tc != other.tc) return false
        if (mail != other.mail) return false
        if (name != other.name) return false
        if (surname != other.surname) return false
        if (age != other.age) return false
        if (creationDate != other.creationDate) return false
        if (isActive != other.isActive) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + tc.hashCode()
        result = 31 * result + mail.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + surname.hashCode()
        result = 31 * result + age
        result = 31 * result + creationDate.hashCode()
        result = 31 * result + isActive.hashCode()
        return result
    }

    override fun toString(): String {
        return "Customer(id=$id, tc=$tc, mail='$mail', name='$name', surname='$surname', age=$age, creationDate=$creationDate, isActive=$isActive)"
    }


}
