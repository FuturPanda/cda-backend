package com.example.notificationbackend.domain

import jakarta.persistence.*

@Entity
@Access(AccessType.FIELD)
@Table(name = "app_user")
class AppUser (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,

    var email: String = "",

    var password: String = "",

    @Column(name = "first_name")
    var firstName: String = "",

    @Column(name = "last_name")
    var lastName: String = "",

    )