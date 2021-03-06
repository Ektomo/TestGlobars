package ivan.gorbunov.testglobars.model.retrofit.data

data class Units(
    val success: Boolean,
    val data: List<UnitTest>
)

data class UnitTest(
    val id: String,
    val name: String,
    val checked: Boolean,
    val eye: Boolean,
    val position: Position
)

data class Position(
    val lt: Double,
    val ln: Double
)