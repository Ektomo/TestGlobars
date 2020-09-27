package ivan.gorbunov.testglobars.model.retrofit.data

data class Session(
    val success: Boolean,
    val data: List<Datum>
)

data class Datum(
    val id: String
)
