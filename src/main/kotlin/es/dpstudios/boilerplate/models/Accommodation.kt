package es.dpstudios.boilerplate.models

import com.fasterxml.jackson.annotation.JsonManagedReference
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "accommodations")
data class Accommodation(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @get: NotBlank
    var name: String? = null,

    var lat: Double? = null,
    var lng: Double? = null,

    @JsonManagedReference
    @OneToMany(mappedBy = "accommodation", fetch = FetchType.EAGER, cascade = arrayOf(CascadeType.ALL))
    var rooms: List<Room>? = null

)
