package com.example.notificationbackend.domain

import jakarta.persistence.*

@Entity
@NamedEntityGraphs(
    NamedEntityGraph(
        name = "Notification.appUserId",
        attributeNodes = [NamedAttributeNode("appUserId")]
    ),
    NamedEntityGraph(
        name = "Notification.documentId",
        attributeNodes = [NamedAttributeNode("documentId")]
    ),
    NamedEntityGraph(
        name = "Notification.full",
        attributeNodes = [NamedAttributeNode("documentId", subgraph = "document"),NamedAttributeNode("appUserId", subgraph = "user") ],
        subgraphs = [
            NamedSubgraph(name = "document", attributeNodes = [NamedAttributeNode("id"), NamedAttributeNode("title")]),
            NamedSubgraph(
                name = "user",
                attributeNodes = [NamedAttributeNode("id"), NamedAttributeNode("email"),NamedAttributeNode("firstName")])
        ]
    ),
)
@Access(AccessType.FIELD)
@Table(name = "notification")
class Notification(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,

    @OneToOne
    @JoinColumn(name = "app_user_id", referencedColumnName = "id")
    var appUserId : AppUser? = null,

    @OneToOne
    @JoinColumn(name = "document_id", referencedColumnName = "id")
    var documentId : Document? = null

    )